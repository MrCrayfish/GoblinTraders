package com.mrcrayfish.goblintraders.mixin;

import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

/**
 * Author: MrCrayfish
 */
@Mixin(MerchantMenu.class)
public class MerchantMenuMixin
{
    @Shadow
    @Final
    private MerchantContainer tradeContainer;

    @Inject(method = "moveFromInventoryToPaymentSlot", at = @At(value = "HEAD"), cancellable = true)
    private void headMoveFromInventoryToPaymentSlot(int tradeIndex, ItemStack itemStack, CallbackInfo ci)
    {
        if(itemStack.isEmpty())
            return;

        MerchantMenu menu = (MerchantMenu) (Object) this;
        for(int i = 3; i < menu.slots.size(); i++)
        {
            ItemStack slotStack = menu.slots.get(i).getItem();
            if(slotStack.isEmpty() || !isMatching(itemStack, slotStack))
                continue;

            ItemStack stack = this.tradeContainer.getItem(tradeIndex);
            int count = stack.isEmpty() ? 0 : stack.getCount();
            int addCount = Math.min(itemStack.getMaxStackSize() - count, slotStack.getCount());
            ItemStack copy = slotStack.copy();
            int newCount = count + addCount;
            slotStack.shrink(addCount);
            copy.setCount(newCount);
            this.tradeContainer.setItem(tradeIndex, copy);
            if(newCount >= itemStack.getMaxStackSize())
            {
                ci.cancel();
            }
        }
    }

    private static boolean isMatching(ItemStack a, ItemStack b)
    {
        if(a.getItem() == Items.ENCHANTED_BOOK && b.getItem() == Items.ENCHANTED_BOOK)
        {
            Map<Enchantment, Integer> givenEnchantments = EnchantmentHelper.getEnchantments(b);
            Map<Enchantment, Integer> paymentEnchantments = EnchantmentHelper.getEnchantments(a);
            paymentEnchantments.entrySet().removeIf(entry -> {
                Integer level = givenEnchantments.get(entry.getKey());
                return level != null && level >= entry.getValue();
            });
            if(paymentEnchantments.isEmpty()) {
                return true;
            }
        }
        return ItemStack.isSameItemSameTags(a, b);
    }
}
