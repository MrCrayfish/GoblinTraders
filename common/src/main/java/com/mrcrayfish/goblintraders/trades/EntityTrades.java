package com.mrcrayfish.goblintraders.trades;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.mrcrayfish.goblintraders.trades.type.BaseTrade;
import net.minecraft.Util;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.npc.VillagerTrades;

import java.util.*;

/**
 * Author: MrCrayfish
 */
public record EntityTrades(Map<TradeRarity, List<BaseTrade>> map)
{
    public EntityTrades(Map<TradeRarity, List<BaseTrade>> map)
    {
        this.map = ImmutableMap.copyOf(map);
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder
    {
        private final Map<TradeRarity, List<BaseTrade>> tradeMap = Util.make(() -> {
            Map<TradeRarity, List<BaseTrade>> map = new EnumMap<>(TradeRarity.class);
            Arrays.stream(TradeRarity.values()).forEach(rarity -> map.put(rarity, new ArrayList<>()));
            return map;
        });

        private Builder() {}

        public void deserialize(TradeRarity rarity, JsonObject object)
        {
            List<BaseTrade> trades = this.tradeMap.get(rarity);
            if(GsonHelper.getAsBoolean(object, "replace", false))
            {
                trades.clear();
            }
            JsonArray tradeArray = GsonHelper.getAsJsonArray(object, "trades");
            for(JsonElement tradeElement : tradeArray)
            {
                JsonObject tradeObject = tradeElement.getAsJsonObject();
                BaseTrade.CODEC.parse(JsonOps.INSTANCE, tradeObject).result().ifPresent(trades::add);
            }
        }

        public EntityTrades build()
        {
            return new EntityTrades(this.tradeMap);
        }
    }
}
