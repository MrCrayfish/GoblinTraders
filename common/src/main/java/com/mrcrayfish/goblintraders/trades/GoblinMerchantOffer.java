package com.mrcrayfish.goblintraders.trades;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
public class GoblinMerchantOffer extends MerchantOffer
{
    public GoblinMerchantOffer(MerchantOffer offer)
    {
        super(offer.getItemCostA(), offer.getItemCostB(), offer.getResult(), offer.getUses(), offer.getMaxUses(), offer.getXp(), offer.getPriceMultiplier(), offer.getDemand());
    }

    public GoblinMerchantOffer(ItemCost paymentStack, Optional<ItemCost> secondaryPaymentStack, ItemStack offerStack, int maxUses, int experience, float priceMultiplier)
    {
        super(paymentStack, secondaryPaymentStack, offerStack, maxUses, experience, priceMultiplier);
    }

    @Override
    public boolean satisfiedBy(ItemStack primary, ItemStack secondary)
    {
        return this.isMatching(primary, Optional.of(this.getItemCostA())) && primary.getCount() >= this.getCostA().getCount() && this.isMatching(secondary, this.getItemCostB()) && secondary.getCount() >= this.getCostB().getCount();
    }

    private boolean isMatching(ItemStack given, Optional<ItemCost> costOptional)
    {
        if(costOptional.isEmpty() && given.isEmpty())
            return true;

        if(costOptional.isEmpty())
            return true;

        ItemStack givenCopy = given.copy();
        if(givenCopy.getMaxDamage() > 0)
        {
            givenCopy.setDamageValue(givenCopy.getDamageValue());
        }

        // Check if the same item
        ItemCost cost = costOptional.get();
        if(!givenCopy.is(cost.item()))
            return false;

        // Finally compare all the components
        return cost.components().test(givenCopy);
    }
}
