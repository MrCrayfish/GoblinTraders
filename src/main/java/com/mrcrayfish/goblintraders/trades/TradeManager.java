package com.mrcrayfish.goblintraders.trades;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mrcrayfish.goblintraders.Reference;
import com.mrcrayfish.goblintraders.entity.TraderCreatureEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class TradeManager implements PreparableReloadListener
{
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

    private List<EntityType<?>> traders = new ArrayList<>();
    private Map<EntityType<?>, EntityTrades> tradeMap = new HashMap<>();
    private Map<ResourceLocation, TradeSerializer<?>> tradeSerializer = new HashMap<>();

    @SubscribeEvent
    public static void addReloadListener(AddReloadListenerEvent event)
    {
        event.addListener(instance());
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
        return this.tradeMap.get(type);
    }

    public void registerTypeSerializer(TradeSerializer<?> serializer)
    {
        this.tradeSerializer.putIfAbsent(serializer.getId(), serializer);
    }

    @Nullable
    public TradeSerializer<?> getTypeSerializer(ResourceLocation id)
    {
        return this.tradeSerializer.get(id);
    }

    @Override
    public CompletableFuture<Void> reload(PreparationBarrier stage, ResourceManager manager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor)
    {
        Map<EntityType<?>, EntityTrades> entityToResourceList = new HashMap<>();
        return CompletableFuture.allOf(CompletableFuture.runAsync(() ->
        {
            this.traders.forEach(entityType ->
            {
                String folder = String.format("trades/%s", Objects.requireNonNull(entityType.getRegistryName()).getPath());
                List<ResourceLocation> resources = new ArrayList<>(manager.listResources(folder, (fileName) -> fileName.endsWith(".json")));
                resources.sort((r1, r2) -> {
                    if(r1.getNamespace().equals(r2.getNamespace())) return 0;
                    return r2.getNamespace().equals(Reference.MOD_ID) ? 1 : -1;
                });
                Map<TradeRarity, LinkedHashSet<ResourceLocation>> tradeResources = new EnumMap<>(TradeRarity.class);
                Arrays.stream(TradeRarity.values()).forEach(rarity -> tradeResources.put(rarity, new LinkedHashSet<>()));
                resources.forEach(resource ->
                {
                    String path = resource.getPath().substring(0, resource.getPath().length() - FILE_TYPE_LENGTH_VALUE);
                    String[] splitPath = path.split("/");
                    if(splitPath.length != 3)
                        return;
                    Arrays.stream(TradeRarity.values()).forEach(rarity ->
                    {
                        if(rarity.getKey().equals(splitPath[2]))
                        {
                            tradeResources.get(rarity).add(resource);
                        }
                    });
                });
                EntityTrades.Builder builder = EntityTrades.Builder.create();
                Arrays.stream(TradeRarity.values()).forEach(rarity -> this.deserializeTrades(manager, builder, rarity, tradeResources.get(rarity)));
                entityToResourceList.put(entityType, builder.build());
                this.tradeMap = ImmutableMap.copyOf(entityToResourceList);
            });
        }, backgroundExecutor)).thenCompose(stage::wait);
    }

    private void deserializeTrades(ResourceManager manager, EntityTrades.Builder builder, TradeRarity rarity, LinkedHashSet<ResourceLocation> resources)
    {
        for(ResourceLocation resource : resources)
        {
            try(InputStream inputstream = manager.getResource(resource).getInputStream(); Reader reader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8)))
            {
                JsonObject object = GsonHelper.fromJson(GSON, reader, JsonObject.class);
                builder.deserialize(rarity, object);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
