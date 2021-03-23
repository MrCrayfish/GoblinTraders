package com.mrcrayfish.goblintraders.trades;

import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Author: MrCrayfish
 */
public class GoblinTrade implements VillagerTrades.ITrade
{
    private final ItemStack offerStack;
    private final ItemStack paymentStack;
    private final ItemStack secondaryPaymentStack;
    private final int maxUses;
    private final int experience;
    private final float priceMultiplier;

    public GoblinTrade(ItemStack offerStack, ItemStack paymentStack, ItemStack secondaryPaymentStack, int maxUses, int experience, float priceMultiplier)
    {
        this.offerStack = offerStack;
        this.paymentStack = paymentStack;
        this.secondaryPaymentStack = secondaryPaymentStack;
        this.maxUses = maxUses;
        this.experience = experience;
        this.priceMultiplier = priceMultiplier;
    }

    @Nullable
    @Override
    public MerchantOffer getOffer(Entity trader, Random rand)
    {
        return new MerchantOffer(this.paymentStack, this.secondaryPaymentStack, this.offerStack, this.maxUses, this.experience, this.priceMultiplier);
    }
}
