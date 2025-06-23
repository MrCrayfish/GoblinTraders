package com.mrcrayfish.goblintraders.trades;

import net.minecraft.world.item.trading.MerchantOffers;

/**
 * Author: MrCrayfish
 */
public class GoblinOffers extends MerchantOffers
{
    public GoblinOffers() {}

    public GoblinOffers(MerchantOffers offers)
    {
        offers.forEach(offer -> this.add(new GoblinMerchantOffer(offer)));
    }
}
