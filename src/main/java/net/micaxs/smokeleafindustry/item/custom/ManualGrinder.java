package net.micaxs.smokeleafindustry.item.custom;

import net.micaxs.smokeleafindustry.item.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class ManualGrinder extends Item {
    public ManualGrinder(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 32;
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }


    // TODO: Add all the Buds here...
    public static boolean isValidMainHandItem(ItemStack pStack) {

        System.out.println("Checking if item is valid...");
        System.out.println("Item: " + pStack.getItem());

        return pStack.getItem() == ModItems.WHITE_WIDOW_BUD.get() ||
                pStack.getItem() == ModItems.BUBBLEGUM_BUD.get() ||
                pStack.getItem() == ModItems.BLUE_ICE_BUD.get() ||
                pStack.getItem() == ModItems.BUBBLE_KUSH_BUD.get() ||
                pStack.getItem() == ModItems.LEMON_HAZE_BUD.get() ||
                pStack.getItem() == ModItems.SOUR_DIESEL_BUD.get();
    }

    public static ItemStack resultItem(ItemStack pStack) {
        Item item = pStack.getItem();
        if (item == ModItems.WHITE_WIDOW_BUD.get()) {
            return new ItemStack(ModItems.WHITE_WIDOW_WEED.get());
        } else if (item == ModItems.BUBBLEGUM_BUD.get()) {
            return new ItemStack(ModItems.BUBBLEGUM_WEED.get());
        } else if (item == ModItems.BLUE_ICE_BUD.get()) {
            return new ItemStack(ModItems.BLUE_ICE_WEED.get());
        } else if (item == ModItems.BUBBLE_KUSH_BUD.get()) {
            return new ItemStack(ModItems.BUBBLE_KUSH_WEED.get());
        } else if (item == ModItems.LEMON_HAZE_BUD.get()) {
            return new ItemStack(ModItems.LEMON_HAZE_WEED.get());
        } else if (item == ModItems.SOUR_DIESEL_BUD.get()) {
            return new ItemStack(ModItems.SOUR_DIESEL_WEED.get());
        }
        return ItemStack.EMPTY;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pPlayer.getItemInHand(InteractionHand.OFF_HAND).getItem() == ModItems.GRINDER.get() && isValidMainHandItem(pPlayer.getItemInHand(InteractionHand.MAIN_HAND))) {
            ItemStack grinderStack = pPlayer.getItemInHand(InteractionHand.OFF_HAND);
            if (grinderStack.getDamageValue() < grinderStack.getMaxDamage() - 1) {
                if (!pPlayer.isShiftKeyDown()) {
                    pPlayer.startUsingItem(pUsedHand);
                    grinderStack.hurtAndBreak(1, pPlayer, (entity) -> entity.broadcastBreakEvent(InteractionHand.OFF_HAND));
                    pPlayer.setItemInHand(InteractionHand.OFF_HAND, grinderStack);
                }
                return InteractionResultHolder.success(grinderStack);
            }
        }
        return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pLivingEntity instanceof Player) {
            Player pPlayer = (Player) pLivingEntity;
            InteractionHand pUsedHand = pPlayer.getUsedItemHand();
            ItemStack budStack = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
            if (!pPlayer.isShiftKeyDown()) {
                if (!pLevel.isClientSide) {
                    ItemStack result = resultItem(budStack);
                    pPlayer.addItem(new ItemStack(result.getItem(), 1));
                }
                budStack.shrink(1);
            }
        }
        return pStack;
    }

}
