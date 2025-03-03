package com.mrcrayfish.goblintraders.trades;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.serialization.MapCodec;
import com.mrcrayfish.goblintraders.Constants;
import com.mrcrayfish.goblintraders.entity.TraderCreatureEntity;
import com.mrcrayfish.goblintraders.trades.type.BasicTrade;
import com.mrcrayfish.goblintraders.trades.type.BaseTrade;
import com.mrcrayfish.goblintraders.trades.type.TreasureMapTrade;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Author: MrCrayfish
 */
public class TradeManager implements PreparableReloadListener
{
    public static final String RESOURCE_DIR = "goblin_trades";
    private static final int FILE_TYPE_LENGTH_VALUE = ".json".length();
    private static final Gson GSON = new GsonBuilder().create();
    private static TradeManager instance;

    public static TradeManager instance()
    {
        if(instance == null)
        {
            instance = new TradeManager();
        }
        return instance;
    }

    private final List<EntityType<?>> traders = new ArrayList<>();
    private final Map<ResourceLocation, MapCodec<? extends BaseTrade>> codecs = new HashMap<>();
    private Map<EntityType<?>, EntityTrades> entityToTrades = new HashMap<>();

    public TradeManager()
    {
        // Register default trades types
        this.registerTradeCodec(BasicTrade.ID, BasicTrade.CODEC);
        this.registerTradeCodec(TreasureMapTrade.ID, TreasureMapTrade.CODEC);
    }

    public void registerTrader(EntityType<? extends TraderCreatureEntity> type)
    {
        if(!this.traders.contains(type))
        {
            this.traders.add(type);
        }
    }

    @Nullable
    public EntityTrades getTrades(EntityType<? extends TraderCreatureEntity> type)
    {
        return this.entityToTrades.get(type);
    }

    public void registerTradeCodec(ResourceLocation id, MapCodec<? extends BaseTrade> codec)
    {
        this.codecs.putIfAbsent(id, codec);
    }

    @Nullable
    public MapCodec<? extends BaseTrade> getTradeCodec(ResourceLocation id)
    {
        return this.codecs.get(id);
    }

    @Override
    public CompletableFuture<Void> reload(PreparationBarrier stage, ResourceManager manager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor)
    {
        List<CompletableFuture<Pair<EntityType<?>, EntityTrades>>> list = this.traders.stream().map(type -> {
            return CompletableFuture.supplyAsync(() -> {
                String folder = String.format("%s/%s", RESOURCE_DIR, EntityType.getKey(type).getPath());
                List<ResourceLocation> resources = new ArrayList<>(manager.listResources(folder, (fileName) -> fileName.getPath().endsWith(".json")).keySet());
                resources.sort((r1, r2) -> {
                    if(r1.getNamespace().equals(r2.getNamespace())) return 0;
                    return r2.getNamespace().equals(Constants.MOD_ID) ? 1 : -1;
                });
                Map<TradeRarity, LinkedHashSet<ResourceLocation>> tradeResources = new EnumMap<>(TradeRarity.class);
                Arrays.stream(TradeRarity.values()).forEach(rarity -> tradeResources.put(rarity, new LinkedHashSet<>()));
                resources.forEach(resource -> {
                    String path = resource.getPath().substring(0, resource.getPath().length() - FILE_TYPE_LENGTH_VALUE);
                    String[] splitPath = path.split("/");
                    if(splitPath.length != 3)
                        return;
                    Arrays.stream(TradeRarity.values()).forEach(rarity -> {
                        if(rarity.getKey().equals(splitPath[2])) {
                            tradeResources.get(rarity).add(resource);
                        }
                    });
                });
                EntityTrades.Builder builder = EntityTrades.builder();
                Arrays.stream(TradeRarity.values()).forEach(rarity -> this.deserializeTrades(manager, builder, rarity, tradeResources.get(rarity)));
                return Pair.<EntityType<?>, EntityTrades>of(type, builder.build());
            }, backgroundExecutor);
        }).toList();

        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new))
            .thenCompose(stage::wait)
            .thenAcceptAsync(obj -> {
                this.entityToTrades = list.stream()
                    .map(CompletableFuture::join)
                    .collect(ImmutableMap.toImmutableMap(Pair::left, Pair::right));
            }, gameExecutor);
    }

    private void deserializeTrades(ResourceManager manager, EntityTrades.Builder builder, TradeRarity rarity, LinkedHashSet<ResourceLocation> resources)
    {
        for(ResourceLocation resourceLocation : resources)
        {
            manager.getResource(resourceLocation).ifPresent(resource ->
            {
                try(Reader reader = resource.openAsReader())
                {
                    JsonObject object = GsonHelper.fromJson(GSON, reader, JsonObject.class);
                    builder.deserialize(rarity, object);
                }
                catch(IOException e)
                {
                    Constants.LOG.error("Failed to load trade file: " + resourceLocation);
                }
            });
        }
    }
}
