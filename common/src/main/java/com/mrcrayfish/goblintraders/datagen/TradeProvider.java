package com.mrcrayfish.goblintraders.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.mrcrayfish.goblintraders.Constants;
import com.mrcrayfish.goblintraders.trades.TradeManager;
import com.mrcrayfish.goblintraders.trades.TradeRarity;
import com.mrcrayfish.goblintraders.trades.type.BaseTrade;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Author: MrCrayfish
 */
public abstract class TradeProvider implements DataProvider
{
    private final PackOutput.PathProvider pathProvider;
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;
    private final Map<EntityType<?>, EnumMap<TradeRarity, List<BaseTrade>>> trades = new HashMap<>();

    protected TradeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        this.pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, TradeManager.RESOURCE_DIR);
        this.lookupProvider = lookupProvider;
    }

    protected abstract void registerTrades(HolderLookup.Provider provider);

    protected final void addTrade(EntityType<?> type, TradeRarity rarity, BaseTrade trade)
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
            this.registerTrades(provider);
            return CompletableFuture.allOf(this.trades.entrySet().stream().map(e1 -> {
                EntityType<?> type = e1.getKey();
                return CompletableFuture.allOf(e1.getValue().entrySet().stream().map(e2 -> {
                    JsonObject object = new JsonObject();
                    object.addProperty("replace", false);
                    JsonArray tradeArray = new JsonArray();
                    e2.getValue().forEach(trade -> {
                        BaseTrade.CODEC.encodeStart(JsonOps.INSTANCE, trade).result().ifPresent(tradeArray::add);
                    });
                    object.add("trades", tradeArray);
                    Identifier id = EntityType.getKey(type);
                    Path path = this.pathProvider.json(Identifier.fromNamespaceAndPath(id.getNamespace(), id.getPath() + "/" + e2.getKey().getKey()));
                    return DataProvider.saveStable(output, object, path);
                }).toArray(CompletableFuture[]::new));
            }).toArray(CompletableFuture[]::new));
        });
    }

    @Override
    public String getName()
    {
        return "Trades: " + Constants.MOD_ID;
    }
}
