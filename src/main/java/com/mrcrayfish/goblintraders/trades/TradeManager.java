package com.mrcrayfish.goblintraders.trades;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mrcrayfish.goblintraders.Reference;
import com.mrcrayfish.goblintraders.entity.TraderCreatureEntity;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EntityType;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
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

/**
 * Author: MrCrayfish
 */
public class TradeManager implements SimpleSynchronousResourceReloadListener
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

    private final List<EntityType<?>> traders = new ArrayList<>();
    private Map<EntityType<?>, EntityTrades> tradeMap = new HashMap<>();
    private final Map<ResourceLocation, TradeSerializer<?>> tradeSerializer = new HashMap<>();

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

    private void deserializeTrades(ResourceManager manager, EntityTrades.Builder builder, TradeRarity rarity, LinkedHashSet<ResourceLocation> resources)
    {
        for(ResourceLocation resourceLocation : resources)
        {
            manager.getResource(resourceLocation).ifPresent(resource ->
            {
                try(Reader reader = new BufferedReader(new InputStreamReader(resource.open(), StandardCharsets.UTF_8)))
                {
                    JsonObject object = GsonHelper.fromJson(GSON, reader, JsonObject.class);
                    builder.deserialize(rarity, object);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public ResourceLocation getFabricId()
    {
        return new ResourceLocation(Reference.MOD_ID, "trades");
    }

    @Override
    public void onResourceManagerReload(ResourceManager manager)
    {
        Map<EntityType<?>, EntityTrades> entityToResourceList = new HashMap<>();
        this.traders.forEach(entityType ->
        {
            String folder = String.format("trades/%s", EntityType.getKey(entityType).getPath());
            List<ResourceLocation> resources = new ArrayList<>(manager.listResources(folder, (fileName) -> fileName.getPath().endsWith(".json")).keySet());
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
    }
}
