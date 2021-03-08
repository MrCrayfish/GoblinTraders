package com.mrcrayfish.goblintraders.trades;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mrcrayfish.goblintraders.trades.type.ITradeType;
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
    private final List<VillagerTrades.ITrade> commonTrades;
    private final List<VillagerTrades.ITrade> rareTrades;

    public EntityTrades(List<VillagerTrades.ITrade> commonTrades, List<VillagerTrades.ITrade> rareTrades)
    {
        this.commonTrades = ImmutableList.copyOf(commonTrades);
        this.rareTrades = ImmutableList.copyOf(rareTrades);
    }

    public List<VillagerTrades.ITrade> getCommonTrades()
    {
        return this.commonTrades;
    }

    public List<VillagerTrades.ITrade> getRareTrades()
    {
        return this.rareTrades;
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
            return new EntityTrades(this.tradeMap.get(TradeRarity.COMMON), this.tradeMap.get(TradeRarity.RARE));
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
                ITradeType<?> type = TradeManager.instance().getTradeType(typeKey);
                if(type == null)
                {
                    throw new JsonParseException(String.format("Invalid trade type: %s", typeKey));
                }
                trades.add(type.deserialize(tradeObject));
            }
        }

        static Builder create()
        {
            return new Builder();
        }
    }
}
