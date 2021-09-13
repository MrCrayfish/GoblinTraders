package com.mrcrayfish.goblintraders.trades.type;

import com.google.gson.JsonObject;
import net.minecraft.world.entity.npc.VillagerTrades;

/**
 * Author: MrCrayfish
 */
public interface ITradeType<T extends VillagerTrades.ItemListing>
{
    JsonObject serialize();

    T createVillagerTrade();
}
