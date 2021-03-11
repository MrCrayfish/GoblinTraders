package com.mrcrayfish.goblintraders.trades;

/**
 * Author: MrCrayfish
 */
public enum TradeRarity
{
    COMMON("common"),
    UNCOMMON("uncommon"),
    RARE("rare");

    private final String key;

    TradeRarity(String key)
    {
        this.key = key;
    }

    public String getKey()
    {
        return this.key;
    }
}
