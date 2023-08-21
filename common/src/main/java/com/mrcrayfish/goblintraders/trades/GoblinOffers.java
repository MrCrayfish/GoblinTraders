package com.mrcrayfish.goblintraders.trades;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.trading.MerchantOffers;

/**
 * Author: MrCrayfish
 */
public class GoblinOffers extends MerchantOffers
{
    public GoblinOffers() {}

    public GoblinOffers(CompoundTag compoundTag)
    {
        ListTag recipes = compoundTag.getList("Recipes", 10);
        for(int i = 0; i < recipes.size(); ++i)
        {
            this.add(new GoblinMerchantOffer(recipes.getCompound(i)));
        }
    }
}
