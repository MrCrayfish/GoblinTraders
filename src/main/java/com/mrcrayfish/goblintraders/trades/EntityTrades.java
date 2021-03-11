package com.mrcrayfish.goblintraders.trades;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class EntityTrades
{
    private final Map<TradeRarity, List<VillagerTrades.ITrade>> tradeMap;

    public EntityTrades(Map<TradeRarity, List<VillagerTrades.ITrade>> tradeMap)
    {
        this.tradeMap = ImmutableMap.copyOf(tradeMap);
    }

    public Map<TradeRarity, List<VillagerTrades.ITrade>> getTradeMap()
    {
        return this.tradeMap;
    }

    public static class Builder
    {
        private final Map<TradeRarity, List<VillagerTrades.ITrade>> tradeMap = Util.make(() ->
        {
            Map<TradeRarity, List<VillagerTrades.ITrade>> map = new EnumMap<>(TradeRarity.class);
            Arrays.stream(TradeRarity.values()).forEach(rarity -> map.put(rarity, new ArrayList<>()));
            return map;
        });

        private Builder() {}

        public EntityTrades build()
        {
            return new EntityTrades(this.tradeMap);
        }

        void deserialize(TradeRarity rarity, JsonObject object)
        {
            List<VillagerTrades.ITrade> trades = this.tradeMap.get(rarity);

            if(JSONUtils.getBoolean(object, "replace", false))
            {
                trades.clear();
            }

            JsonArray tradeArray = JSONUtils.getJsonArray(object, "trades");
            for(JsonElement tradeElement : tradeArray)
            {
                JsonObject tradeObject = tradeElement.getAsJsonObject();
                String rawType = JSONUtils.getString(tradeObject, "type");
                ResourceLocation typeKey = ResourceLocation.tryCreate(rawType);
                if(typeKey == null)
                {
                    throw new JsonParseException("");
                }
                TradeSerializer<?> serializer = TradeManager.instance().getTypeSerializer(typeKey);
                if(serializer == null)
                {
                    throw new JsonParseException(String.format("Invalid trade type: %s", typeKey));
                }
                trades.add(serializer.deserialize(tradeObject).createVillagerTrade());
            }
        }

        static Builder create()
        {
            return new Builder();
        }
    }
}
