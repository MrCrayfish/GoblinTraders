package com.mrcrayfish.goblintraders.world.spawner;

import com.mrcrayfish.goblintraders.Config;
import com.mrcrayfish.goblintraders.init.ModEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class SpawnHandler implements ModInitializer
{
    private static final Map<ResourceLocation, GoblinTraderSpawner> SPAWNERS = new HashMap<>();

    @Override
    public void onInitialize()
    {
        ServerLifecycleEvents.SERVER_STARTING.register(server ->
        {
            SPAWNERS.put(BuiltinDimensionTypes.OVERWORLD.location(), new GoblinTraderSpawner(server, "GoblinTrader", ModEntities.GOBLIN_TRADER, Config.ENTITIES.goblinTrader));
            SPAWNERS.put(BuiltinDimensionTypes.NETHER.location(), new GoblinTraderSpawner(server, "VeinGoblinTrader", ModEntities.VEIN_GOBLIN_TRADER, Config.ENTITIES.veinGoblinTrader));
        });

        ServerLifecycleEvents.SERVER_STOPPED.register(server ->
        {
            SPAWNERS.clear();
        });

        ServerTickEvents.START_WORLD_TICK.register(world ->
        {
            GoblinTraderSpawner spawner = SPAWNERS.get(world.dimension().location());
            if(spawner != null)
            {
                spawner.tick(world);
            }
        });
    }
}
