package net.micaxs.smokeleafindustry.item.custom;

import net.micaxs.smokeleafindustry.utils.HashOilHelper;
import net.micaxs.smokeleafindustry.utils.WeedEffectHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
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
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundTag nbt) {
        return new net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper(stack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        CompoundTag tag = pStack.getTag();
        if (tag == null) {
            return;
        }

        List<BaseWeedItem> activeIngredients = HashOilHelper.getActiveWeedIngredient(tag);
        pTooltipComponents.add(Component.empty()
                .append(WeedEffectHelper.getEffectTooltip(activeIngredients, true)));
    }

    @Override
    public @NotNull Component getName(ItemStack pStack) {
        CompoundTag tag = pStack.getTag();
        if (tag == null) {
            return Component.translatable("item.smokeleafindustry.hash_oil_bucket");
        }

        List<BaseWeedItem> activeIngredients = HashOilHelper.getActiveWeedIngredient(tag);
        return HashOilHelper.getHashOilName(activeIngredients, "item.smokeleafindustry.hash_oil_bucket",
                "item.smokeleafindustry.hash_oil_bucket_blend");
    }
}
