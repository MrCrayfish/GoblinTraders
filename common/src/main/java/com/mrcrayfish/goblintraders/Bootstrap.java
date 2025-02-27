package com.mrcrayfish.goblintraders;

import com.mrcrayfish.framework.api.event.TickEvents;
import com.mrcrayfish.goblintraders.core.ModEntities;
import com.mrcrayfish.goblintraders.spawner.GoblinTraderSpawner;
import com.mrcrayfish.goblintraders.trades.TradeManager;
import net.minecraft.world.level.Level;

/**
 * Author: MrCrayfish
 */
public class Bootstrap
{
    public static void init()
    {
        GoblinTraderSpawner.register(ModEntities.GOBLIN_TRADER.get(), Level.OVERWORLD, () -> Config.ENTITIES.goblinTrader);
        GoblinTraderSpawner.register(ModEntities.VEIN_GOBLIN_TRADER.get(), Level.NETHER, () -> Config.ENTITIES.veinGoblinTrader);

        TickEvents.START_SERVER.register(server -> {
            GoblinTraderSpawner.get(server, ModEntities.GOBLIN_TRADER.get()).ifPresent(GoblinTraderSpawner::serverTick);
            GoblinTraderSpawner.get(server, ModEntities.VEIN_GOBLIN_TRADER.get()).ifPresent(GoblinTraderSpawner::serverTick);
        });

        TradeManager manager = TradeManager.instance();
        manager.registerTrader(ModEntities.GOBLIN_TRADER.get());
        manager.registerTrader(ModEntities.VEIN_GOBLIN_TRADER.get());

        //ModStats.init(); // TODO add back stats
    }
}
