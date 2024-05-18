package net.micaxs.smokeleafindustry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BaseWeedCropBlock extends CropBlock {
    public static final int FIRST_STAGE_MAX_AGE = 7;
    public static final int SECOND_STAGE_MAX_AGE = 3;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 10);
    public static final BooleanProperty TOP = BooleanProperty.create("top");
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
    };
    private final Supplier<Item> seedItem;

    public BaseWeedCropBlock(Properties pProperties, Supplier<Item> seedItem) {
        super(pProperties);
        this.seedItem = seedItem;
        this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), 0).setValue(TOP, false));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        if (isTop(state)) {
            return SHAPE_BY_AGE[getAge(state)];
        }
        return SHAPE_BY_AGE[Math.max(0, Math.min(FIRST_STAGE_MAX_AGE, getAge(state)))];
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        if (!level.isAreaLoaded(pos, 1) || isTop(state) ||
                level.getRawBrightness(pos, 0) < 11 || !canSurvive(state, level, pos)) {
            return;
        }

        int currentAge = this.getAge(state);
        if (currentAge >= this.getMaxAge()) {
            return;
        }

        float growthSpeed = getGrowthSpeed(this, level, pos);

        if (ForgeHooks.onCropsGrowPre(level, pos, state, randomSource.nextInt((int) (25.0F / growthSpeed) + 1) == 0)) {
            int nextAge = currentAge + 1;
            level.setBlock(pos, getStateForAge(nextAge), 2);
            if (nextAge >= getTallAge()) {
                level.setBlockAndUpdate(pos.above(), getStateForAge(nextAge).setValue(TOP, true));
            }
            ForgeHooks.onCropsGrowPost(level, pos, state);
        }
    }

    @Override
    public void growCrops(Level level, BlockPos pos, BlockState state) {
        if (isTop(state) || !canSurvive(state, level, pos)) {
            return;
        }

        BlockPos above = pos.above();
        if (!level.isEmptyBlock(above) && level.getBlockState(above).getBlock() != this) {
            return;
        }

        int nextAge = this.getAge(state) + this.getBonemealAgeIncrease(level);
        nextAge = Math.min(nextAge, getMaxAge());
        level.setBlock(pos, getStateForAge(nextAge), 2);
        if (nextAge >= getTallAge()) {
            level.setBlockAndUpdate(above, getStateForAge(nextAge).setValue(TOP, true));
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        // TODO: kinda complicated with many branching logic paths, also doesn't work perfectly with HWE
        if (isTop(state)) {
            if (level.getBlockState(pos.below()).getBlock() == this && level.getBlockState(pos.below()).getValue(AGE) < getTallAge() - 1) {
                return false;
            }
            return level.getBlockState(pos.below()).getBlock() == this && getAge(state) >= getTallAge();
        }

        if (getAge(state) >= getTallAge()) {
            if (level.getBlockState(pos.above()).getBlock() == this && level.getBlockState(pos.above()).getValue(AGE) <= getTallAge() - 1) {
                return false;
            }
            return level.getBlockState(pos.above()).getBlock() == this && super.canSurvive(state, level, pos);
        }

        return pos.getY() < level.getMaxBuildHeight() && super.canSurvive(state, level, pos) && (level.isEmptyBlock(pos.above()));
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantAble) {
        return super.mayPlaceOn(state, world, pos);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state, boolean isClient) {
        return !this.isMaxAge(state) && !state.getValue(TOP);
    }

    @Override
    public int getMaxAge() {
        return FIRST_STAGE_MAX_AGE + SECOND_STAGE_MAX_AGE;
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return this.seedItem.get();
    }

    @Override
    public @NotNull IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, TOP);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(TOP);
    }

    protected boolean isTop(BlockState state) {
        return state.getValue(TOP);
    }

    public BooleanProperty getTop() {
        return TOP;
    }

    public int getTallAge() {
        return FIRST_STAGE_MAX_AGE + 1;
    }
}
