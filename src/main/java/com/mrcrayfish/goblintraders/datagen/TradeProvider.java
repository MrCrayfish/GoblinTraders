package com.mrcrayfish.goblintraders.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mrcrayfish.goblintraders.Reference;
import com.mrcrayfish.goblintraders.trades.TradeRarity;
import com.mrcrayfish.goblintraders.trades.type.ITradeType;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public abstract class TradeProvider implements DataProvider
{
    private static final Logger LOGGER = LogManager.getLogger();

    private final DataGenerator generator;
    private final Map<EntityType<?>, EnumMap<TradeRarity, List<ITradeType<?>>>> trades = new HashMap<>();

    protected TradeProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    protected abstract void registerTrades();

    protected final void addTrade(EntityType<?> type, TradeRarity rarity, ITradeType<?> trade)
    {
        this.trades.putIfAbsent(type, new EnumMap<>(TradeRarity.class));
        this.trades.get(type).putIfAbsent(rarity, new ArrayList<>());
        this.trades.get(type).get(rarity).add(trade);
    }

    @Override
    public void run(CachedOutput output) throws IOException
    {
        this.trades.clear();
        this.registerTrades();
        this.trades.forEach((entityType, tradeRarityListEnumMap) ->
        {
            tradeRarityListEnumMap.forEach((tradeRarity, tradeList) ->
            {
                JsonObject object = new JsonObject();
                object.addProperty("replace", false);
                JsonArray tradeArray = new JsonArray();
                tradeList.forEach(trade -> tradeArray.add(trade.serialize()));
                object.add("trades", tradeArray);
                ResourceLocation id = EntityType.getKey(entityType);
                Path path = this.generator.getOutputFolder().resolve("data/" + id.getNamespace() + "/trades/" + id.getPath() + "/" + tradeRarity.getKey() + ".json");
                try
                {
                    DataProvider.saveStable(output, object, path);
                }
                catch(IOException e)
                {
                    LOGGER.error("Couldn't save trades to {}", path, e);
                }
            });
        });
    }

    @Override
    public String getName()
    {
        return "Trades: " + Reference.MOD_ID;
    }
}
