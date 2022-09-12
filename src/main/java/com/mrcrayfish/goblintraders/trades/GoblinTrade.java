package com.mrcrayfish.goblintraders.trades;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Author: MrCrayfish
 */
public record GoblinTrade(ItemStack offerStack, ItemStack paymentStack, ItemStack secondaryPaymentStack, int maxUses, int experience, float priceMultiplier) implements VillagerTrades.ItemListing
{
    @Override
    public MerchantOffer getOffer(Entity trader, RandomSource rand)
    {
        return new GoblinMerchantOffer(this.paymentStack, this.secondaryPaymentStack, this.offerStack, this.maxUses, this.experience, this.priceMultiplier);
    }
}
