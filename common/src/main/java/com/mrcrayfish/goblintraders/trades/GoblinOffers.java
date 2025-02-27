package com.mrcrayfish.goblintraders.trades;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.item.trading.MerchantOffers;

/**
 * Author: MrCrayfish
 */
public class GoblinOffers extends MerchantOffers
{
    public GoblinOffers() {}

    public GoblinOffers(CompoundTag tag)
    {
        CODEC.parse(NbtOps.INSTANCE, tag).result().ifPresent(offers -> {
            offers.forEach(offer -> {
                this.add(new GoblinMerchantOffer(offer));
            });
        });
    }
}
