package net.micaxs.smokeleafindustry.block.custom;

import net.micaxs.smokeleafindustry.block.entity.HerbExtractorBlockEntity;
import net.micaxs.smokeleafindustry.block.entity.ModBlockEntities;
import net.micaxs.smokeleafindustry.item.ModItems;
import net.micaxs.smokeleafindustry.utils.HashOilHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HerbExtractorBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public HerbExtractorBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    /*      FACING       */

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING);
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof HerbExtractorBlockEntity) {
                ((HerbExtractorBlockEntity) blockEntity).drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        // Return default if called from client
        if (pLevel.isClientSide()) {
            return InteractionResult.sidedSuccess(pLevel.isClientSide());
        }

        // If the block isn't the extractor, throw an error
        BlockEntity entity = pLevel.getBlockEntity(pPos);
        if (!(entity instanceof HerbExtractorBlockEntity herbExtractorBlockEntity)) {
            throw new IllegalStateException("Container provider is missing!");
        }

        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        if (itemStack.getItem() == Items.BUCKET) {
            // If we can't drain the liquid, open machine screen
            if (herbExtractorBlockEntity.getFluidTank().getFluidAmount() < 1000) {
                NetworkHooks.openScreen(((ServerPlayer) pPlayer), (HerbExtractorBlockEntity) entity, pPos);
                return InteractionResult.CONSUME;
            }

            ItemStack hashOilBucket = new ItemStack(herbExtractorBlockEntity.getFluidTank().getFluid().getFluid().getBucket());
            HashOilHelper.transferWeedFluidNBTToBucket(hashOilBucket, herbExtractorBlockEntity.getFluidStack());
            herbExtractorBlockEntity.getFluidTank().drain(1000, IFluidHandler.FluidAction.EXECUTE);

            // Replace bucket item, or add to inventory
            if (itemStack.getCount() > 1) {
                itemStack.shrink(1);
                if (!pPlayer.getInventory().add(hashOilBucket)) {
                    pPlayer.drop(hashOilBucket, false);
                }
            } else {
                pPlayer.setItemInHand(pHand, hashOilBucket);
            }
            pLevel.sendBlockUpdated(pPos, pState, pState, 3);
            return InteractionResult.SUCCESS;
        } else if (itemStack.getItem() == ModItems.EMPTY_TINCTURE.get()) {
            // If we can't drain the liquid, open machine screen
            if (herbExtractorBlockEntity.getFluidTank().getFluidAmount() < 300) {
                NetworkHooks.openScreen(((ServerPlayer) pPlayer), (HerbExtractorBlockEntity) entity, pPos);
                return InteractionResult.CONSUME;
            }

            // Create hash oil tincture
            ItemStack hashOilTincture = new ItemStack(ModItems.HASH_OIL_TINCTURE.get());
            HashOilHelper.transferWeedFluidNBTToBucket(hashOilTincture, herbExtractorBlockEntity.getFluidStack());
            herbExtractorBlockEntity.getFluidTank().drain(300, IFluidHandler.FluidAction.EXECUTE);

            // Replace tincture item, or add to inventory
            if (itemStack.getCount() > 1) {
                itemStack.shrink(1);
                if (!pPlayer.getInventory().add(hashOilTincture)) {
                    pPlayer.drop(hashOilTincture, false);
                }
            } else {
                pPlayer.setItemInHand(pHand, hashOilTincture);
            }
            pLevel.sendBlockUpdated(pPos, pState, pState, 3);
            return InteractionResult.SUCCESS;
        }

        // If the player is not holding an empty bucket, open the screen as before
        NetworkHooks.openScreen(((ServerPlayer) pPlayer), (HerbExtractorBlockEntity) entity, pPos);
        return InteractionResult.SUCCESS;
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new HerbExtractorBlockEntity(blockPos, blockState);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()) return null;

        return createTickerHelper(pBlockEntityType, ModBlockEntities.HERB_EXTRACTOR_BE.get(), (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1, pBlockEntity));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }


}
