package net.micaxs.smokeleafindustry.item.custom;

import net.micaxs.smokeleafindustry.fluid.ModFluids;
import net.micaxs.smokeleafindustry.utils.HashOilHelper;
import net.micaxs.smokeleafindustry.utils.WeedEffectHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HashOilTinctureItem extends Item {
    public HashOilTinctureItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        CompoundTag tag = pStack.getTag();
        if (tag == null || !tag.contains("active_ingredients")) {
            return;
        }

        List<BaseWeedItem> activeIngredients = HashOilHelper.getActiveWeedIngredient(tag);
        pTooltipComponents.add(Component.empty()
                .append(WeedEffectHelper.getEffectTooltip(activeIngredients, true)));
    }

    @Override
    public @NotNull Component getName(ItemStack pStack) {
        CompoundTag tag = pStack.getTag();
        if (tag == null || !tag.contains("active_ingredients")) {
            return Component.translatable("item.smokeleafindustry.hash_oil_tincture");
        }

        List<BaseWeedItem> activeIngredients = HashOilHelper.getActiveWeedIngredient(tag);
        return HashOilHelper.getHashOilName(activeIngredients, "item.smokeleafindustry.hash_oil_tincture",
                "item.smokeleafindustry.hash_oil_tincture_blend");
    }

    @Override
    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }
}
