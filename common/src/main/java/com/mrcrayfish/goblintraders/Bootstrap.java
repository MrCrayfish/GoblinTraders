package com.mrcrayfish.goblintraders;

import com.mrcrayfish.framework.api.event.FrameworkTickEvents;
import com.mrcrayfish.goblintraders.core.ModEntities;
import com.mrcrayfish.goblintraders.spawner.GoblinTraderSpawner;
import com.mrcrayfish.goblintraders.trades.TradeManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

/**
 * Author: MrCrayfish
 */
public class Bootstrap
{
    public static void init()
    {
        //GoblinTraderSpawner.register(ModEntities.GOBLIN_TRADER.get(), Level.OVERWORLD, () -> Config.ENTITIES.goblinTrader);
        //GoblinTraderSpawner.register(ModEntities.VEIN_GOBLIN_TRADER.get(), Level.NETHER, () -> Config.ENTITIES.veinGoblinTrader);

        FrameworkTickEvents.START_SERVER.register(server -> {
            GoblinTraderSpawner.getGoblinTraderSpawner(server).ifPresent(spawner -> {
                ServerLevel level = server.getLevel(Level.OVERWORLD);
                if(level != null) {
                    spawner.serverTick(level);
                }
            });
            GoblinTraderSpawner.getVeinGoblinTraderSpawner(server).ifPresent(spawner -> {
                ServerLevel level = server.getLevel(Level.NETHER);
                if(level != null) {
                    spawner.serverTick(level);
                }
            });
        });

        TradeManager manager = TradeManager.instance();
        manager.registerTrader(ModEntities.GOBLIN_TRADER.get());
        manager.registerTrader(ModEntities.VEIN_GOBLIN_TRADER.get());

        //ModStats.init(); // TODO add back stats
    }
}
