package net.micaxs.smokeleafindustry.block.custom;

import net.micaxs.smokeleafindustry.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.function.Supplier;

public class BaseWeedCropBlock extends CropBlock {

    public static final int FIRST_STAGE_MAX_AGE = 7;
    public static final int SECOND_STAGE_MAX_AGE = 3;

    private final Supplier<Item> seedItem;
    private final Supplier<Item> budItem;
    public int THC = 0;
    public int CBD = 0;

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
    };


    public static final IntegerProperty AGE = IntegerProperty.create("age",0, 10);

    public BaseWeedCropBlock(Properties pProperties, Supplier<Item> seedItem, Supplier<Item> budItem) {
        super(pProperties);
        this.seedItem = seedItem;
        this.budItem = budItem;
    }

    public BaseWeedCropBlock(Properties pProperties, Supplier<Item> seedItem, Supplier<Item> budItem, int pSeedTHC, int pSeedCBD) {
        super(pProperties);
        this.seedItem = seedItem;
        this.budItem = budItem;
        this.THC = pSeedTHC;
        this.CBD = pSeedCBD;
    }

    public Item getSeedItem() {
        return seedItem.get();
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_AGE[this.getAge(pState)];
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return super.mayPlaceOn(state, world, pos);
    }


    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1)) return;

        if (pLevel.getRawBrightness(pPos, 0) >= 11) {
            int currentAge = this.getAge(pState);

            if (currentAge < this.getMaxAge()) {
                float growthSpeed = getGrowthSpeed(this, pLevel, pPos);

                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt((int)(25.0F / growthSpeed) + 1) == 0)) {

                    BlockPos abovePos = pPos.above(1);

                    if (currentAge == FIRST_STAGE_MAX_AGE) {
                        if (pLevel.getBlockState(abovePos).is(Blocks.AIR)) {
                            pLevel.setBlock(abovePos, this.getStateForAge(currentAge + 1), 2);
                        } else if (pLevel.getBlockState(abovePos).is(this)) {
                            // get the age of the block above and set it to +1
                            BlockState aboveBlockState = pLevel.getBlockState(abovePos);
                            int aboveBlockAge = this.getAge(aboveBlockState);
                            if (aboveBlockAge < this.getMaxAge()) {
                                pLevel.setBlock(abovePos, this.getStateForAge(aboveBlockAge + 1), 2);
                            }
                        }
                    } else {
                        pLevel.setBlock(pPos, this.getStateForAge(currentAge + 1), 2);
                    }

                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                }
            }
        }
    }


    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return super.canSurvive(pState, pLevel, pPos) ||
                (pLevel.getBlockState(pPos.below(1)).is(this) &&
                        pLevel.getBlockState(pPos.below(1)).getValue(AGE) == 7);
    }

    @Override
    public void growCrops(Level pLevel, BlockPos pPos, BlockState pState) {
        int nextAge = this.getAge(pState) + this.getBonemealAgeIncrease(pLevel);
        int maxAge = this.getMaxAge();
        int currentAge = this.getAge(pState);

        if (nextAge > maxAge) {
            nextAge = maxAge;
        }

        BlockPos belowPos = pPos.below(1);
        if (!pLevel.getBlockState(belowPos).is(this)) {
            if (currentAge < FIRST_STAGE_MAX_AGE) {
                if (nextAge > FIRST_STAGE_MAX_AGE) {
                    BlockPos abovePos = pPos.above(1);
                    if (pLevel.getBlockState(abovePos).is(Blocks.AIR)) {
                        pLevel.setBlock(pPos, this.getStateForAge(FIRST_STAGE_MAX_AGE), 2);
                        pLevel.setBlock(abovePos, this.getStateForAge(nextAge), 2);
                    }
                } else {
                    pLevel.setBlock(pPos, this.getStateForAge(nextAge), 2);
                }
            } else {
                if (nextAge < maxAge + 1) {
                    BlockPos abovePos = pPos.above(1);
                    BlockState aboveBlockState = pLevel.getBlockState(abovePos);

                    if (aboveBlockState.is(this)) {
                        int aboveBlockAge = this.getAge(aboveBlockState);

                        if (aboveBlockAge < maxAge) {
                            pLevel.setBlock(pPos.above(1), this.getStateForAge(nextAge), 2);
                        } else if (aboveBlockAge == maxAge) {
                            pLevel.destroyBlock(pPos, true);
                            // Maybe replant the first stage here?
                            pLevel.setBlock(pPos, this.getStateForAge(0), 2);
                        }
                    } else if (aboveBlockState.is(Blocks.AIR)) {
                        pLevel.setBlock(pPos.above(1), this.getStateForAge(nextAge), 2);
                    }
                }
            }
        }
    }

    @Override
    public void destroy(LevelAccessor pLevel, BlockPos pPos, BlockState pState) {
        if (!(pLevel instanceof Level level)) {
            return;
        }

        BlockPos belowPos = pPos.below();
        BlockState belowBlockState = level.getBlockState(belowPos);

        if (belowBlockState.is(this)) {
            int belowBlockAge = this.getAge(belowBlockState);
            int topAge = this.getAge(pState);
            if (topAge == this.getMaxAge() && belowBlockAge == FIRST_STAGE_MAX_AGE) {
                Item budItem = this.budItem.get();
                ItemEntity budItemEntity = new ItemEntity(EntityType.ITEM, level);
                budItemEntity.setItem(new ItemStack(budItem));
                budItemEntity.setPos(belowPos.getX() + 0.5, belowPos.getY() + 0.5, belowPos.getZ() + 0.5);
                level.addFreshEntity(budItemEntity);
            }
            level.destroyBlock(belowPos, true);
        }
        super.destroy(level, pPos, pState);
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
}
