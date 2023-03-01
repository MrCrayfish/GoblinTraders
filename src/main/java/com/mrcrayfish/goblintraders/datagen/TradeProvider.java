package com.mrcrayfish.goblintraders.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.mrcrayfish.goblintraders.Reference;
import com.mrcrayfish.goblintraders.trades.TradeRarity;
import com.mrcrayfish.goblintraders.trades.type.ITradeType;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagFile;
import net.minecraft.world.entity.EntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public abstract class TradeProvider implements DataProvider
{
    private final PackOutput.PathProvider pathProvider;
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;
    private final Map<EntityType<?>, EnumMap<TradeRarity, List<ITradeType<?>>>> trades = new HashMap<>();

    protected TradeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        this.pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "trades");
        this.lookupProvider = lookupProvider;
    }

    protected abstract void registerTrades();

    protected final void addTrade(EntityType<?> type, TradeRarity rarity, ITradeType<?> trade)
    {
        this.trades.putIfAbsent(type, new EnumMap<>(TradeRarity.class));
        this.trades.get(type).putIfAbsent(rarity, new ArrayList<>());
        this.trades.get(type).get(rarity).add(trade);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output)
    {
        return this.lookupProvider.thenCompose((provider) -> {
            this.trades.clear();
            this.registerTrades();
            return CompletableFuture.allOf(this.trades.entrySet().stream().map(e1 -> {
                EntityType<?> type = e1.getKey();
                return CompletableFuture.allOf(e1.getValue().entrySet().stream().map(e2 -> {
                    JsonObject object = new JsonObject();
                    object.addProperty("replace", false);
                    JsonArray tradeArray = new JsonArray();
                    e2.getValue().forEach(trade -> tradeArray.add(trade.serialize()));
                    object.add("trades", tradeArray);
                    ResourceLocation id = EntityType.getKey(type);
                    Path path = this.pathProvider.json(new ResourceLocation(id.getNamespace(), id.getPath() + "/" + e2.getKey().getKey()));
                    return DataProvider.saveStable(output, object, path);
                }).toArray(CompletableFuture[]::new));
            }).toArray(CompletableFuture[]::new));
        });
    }

    @Override
    public String getName()
    {
        return "Trades: " + Reference.MOD_ID;
    }
}
