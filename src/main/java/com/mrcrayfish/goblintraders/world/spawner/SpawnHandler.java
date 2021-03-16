package com.mrcrayfish.goblintraders.world.spawner;

import com.mrcrayfish.goblintraders.Config;
import com.mrcrayfish.goblintraders.Reference;
import com.mrcrayfish.goblintraders.init.ModEntities;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class SpawnHandler
{
    private static Map<ResourceLocation, GoblinTraderSpawner> spawners = new HashMap<>();

    @SubscribeEvent
    public static void onServerStart(FMLServerStartedEvent event)
    {
        MinecraftServer server = event.getServer();
        spawners.put(DimensionType.OVERWORLD_ID, new GoblinTraderSpawner(server, "GoblinTrader", ModEntities.GOBLIN_TRADER.get(), Config.COMMON.goblinTrader));
        spawners.put(DimensionType.THE_NETHER_ID, new GoblinTraderSpawner(server, "VeinGoblinTrader", ModEntities.VEIN_GOBLIN_TRADER.get(), Config.COMMON.veinGoblinTrader));
    }

    @SubscribeEvent
    public static void onServerStart(FMLServerStoppedEvent event)
    {
        spawners.clear();
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if(event.phase != TickEvent.Phase.START)
            return;

        if(event.side != LogicalSide.SERVER)
            return;

        GoblinTraderSpawner spawner = spawners.get(event.world.getDimensionKey().getLocation());
        if(spawner != null)
        {
            spawner.tick(event.world);
        }
    }
}
