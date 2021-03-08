package com.mrcrayfish.goblintraders.trades;

/**
 * Author: MrCrayfish
 */
public enum TradeRarity
{
    COMMON("common"),
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
