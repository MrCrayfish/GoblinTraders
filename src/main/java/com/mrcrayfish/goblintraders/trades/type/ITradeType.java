package com.mrcrayfish.goblintraders.trades.type;

import com.google.gson.JsonObject;
import net.minecraft.entity.merchant.villager.VillagerTrades;

/**
 * Author: MrCrayfish
 */
public interface ITradeType<T extends VillagerTrades.ITrade>
{
    JsonObject serialize();

    T createVillagerTrade();
}
