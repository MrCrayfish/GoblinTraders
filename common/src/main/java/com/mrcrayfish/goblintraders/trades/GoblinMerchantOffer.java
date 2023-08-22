package com.mrcrayfish.goblintraders.trades;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class GoblinMerchantOffer extends MerchantOffer
{
    public GoblinMerchantOffer(CompoundTag compoundTag)
    {
        super(compoundTag);
    }

    public GoblinMerchantOffer(ItemStack paymentStack, ItemStack secondaryPaymentStack, ItemStack offerStack, int maxUses, int experience, float priceMultiplier)
    {
        super(paymentStack, secondaryPaymentStack, offerStack, maxUses, experience, priceMultiplier);
    }

    @Override
    public boolean satisfiedBy(ItemStack primary, ItemStack secondary)
    {
        return this.isMatching(primary, this.getCostA()) && primary.getCount() >= this.getCostA().getCount() && this.isMatching(secondary, this.getCostB()) && secondary.getCount() >= this.getCostB().getCount();
    }

    private boolean isMatching(ItemStack given, ItemStack payment)
    {
        if(payment.isEmpty() && given.isEmpty())
            return true;

        ItemStack givenCopy = given.copy();
        if(givenCopy.getItem().canBeDepleted())
        {
            givenCopy.setDamageValue(givenCopy.getDamageValue());
        }

        if(!ItemStack.isSameItem(givenCopy, payment))
            return false;

        if(!payment.hasTag())
            return true;

        if(!givenCopy.hasTag())
            return false;

        // Special case for enchanted books.
        if(givenCopy.getItem() == Items.ENCHANTED_BOOK && payment.getItem() == Items.ENCHANTED_BOOK)
        {
            Map<Enchantment, Integer> givenEnchantments = EnchantmentHelper.getEnchantments(givenCopy);
            Map<Enchantment, Integer> paymentEnchantments = EnchantmentHelper.getEnchantments(payment);
            paymentEnchantments.entrySet().removeIf(entry -> {
                Integer level = givenEnchantments.get(entry.getKey());
                return level != null && level >= entry.getValue();
            });
            if(paymentEnchantments.isEmpty()) {
                return true;
            }
        }

        return NbtUtils.compareNbt(payment.getTag(), givenCopy.getTag(), false);
    }
}
