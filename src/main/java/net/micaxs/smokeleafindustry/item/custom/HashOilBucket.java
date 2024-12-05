package net.micaxs.smokeleafindustry.item.custom;

import net.micaxs.smokeleafindustry.SmokeleafIndustryMod;
import net.micaxs.smokeleafindustry.utils.HashOilHelper;
import net.micaxs.smokeleafindustry.utils.WeedEffectHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class HashOilBucket extends BucketItem {
    public HashOilBucket(Supplier<? extends Fluid> supplier, Properties builder) {
        super(supplier, builder);

        // TODO: correctly register dispenser
        /*DispenserBlock.registerBehavior(this, new DefaultDispenseItemBehavior() {
            @Override
            public ItemStack execute(BlockSource source, ItemStack stack) {
                DispensibleContainerItem bucket = (DispensibleContainerItem) stack.getItem();
                BlockPos dispenseAt = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                Level level = source.getLevel();

                if (bucket.emptyContents(null, level, dispenseAt, null, stack)) {
                    bucket.checkExtraContent(null, level, stack, dispenseAt);
                    return dispense(source, new ItemStack(Items.BUCKET));
                } else {
                    return dispense(source, stack);
                }
            }
        });*/
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        // TODO: maintain NBT data when placed on the ground and picked up
        return super.use(pLevel, pPlayer, pHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        CompoundTag tag = pStack.getTag();
        if (tag == null) {
            return;
        }

        List<BaseWeedItem> activeIngredients = HashOilHelper.getActiveWeedIngredient(getFluidStack(tag));
        pTooltipComponents.add(Component.empty()
                .append(WeedEffectHelper.getEffectTooltip(activeIngredients, true)));
    }

    @Override
    public Component getName(ItemStack pStack) {
        CompoundTag tag = pStack.getTag();
        if (tag == null) {
            return Component.translatable("item.smokeleafindustry.hash_oil_bucket");
        }

        List<BaseWeedItem> activeIngredients = HashOilHelper.getActiveWeedIngredient(getFluidStack(tag));
        return HashOilHelper.getHashOilName(activeIngredients, "item.smokeleafindustry.hash_oil_bucket",
                "item.smokeleafindustry.hash_oil_bucket_blend");
    }

    private FluidStack getFluidStack(CompoundTag tag) {
        return new FluidStack(getFluid(), 1000, tag);
    }
}
