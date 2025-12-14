package com.mrcrayfish.goblintraders.trades;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public record GoblinTrade(ItemStack offerStack, ItemCost primaryCost, Optional<ItemCost> secondaryCost, int maxUses, int experience, float priceMultiplier) implements VillagerTrades.ItemListing
{
    @Override
    public MerchantOffer getOffer(ServerLevel level, Entity trader, RandomSource rand)
    {
        return new GoblinMerchantOffer(this.primaryCost, this.secondaryCost, this.offerStack, this.maxUses, this.experience, this.priceMultiplier);
    }
}
