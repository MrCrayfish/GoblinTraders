package com.mrcrayfish.goblintraders.trades;

/**
 * Author: MrCrayfish
 */
public enum TradeRarity
{
    COMMON("common", true),
    UNCOMMON("uncommon", true),
    RARE("rare", true),
    EPIC("epic", true),
    LEGENDARY("legendary", true);

    private final String key;
    private final boolean shuffle;

    TradeRarity(String key, boolean shuffle)
    {
        this.key = key;
        this.shuffle = shuffle;
    }

    public String getKey()
    {
        return this.key;
    }

    public boolean shouldShuffle()
    {
        return this.shuffle;
    }
}
