package com.mrcrayfish.goblintraders;

import com.mrcrayfish.framework.api.event.TickEvents;
import com.mrcrayfish.goblintraders.core.ModEntities;
import com.mrcrayfish.goblintraders.core.ModItems;
import com.mrcrayfish.goblintraders.core.ModStats;
import com.mrcrayfish.goblintraders.mixin.SpawnEggItemMixin;
import com.mrcrayfish.goblintraders.spawner.GoblinTraderSpawner;
import com.mrcrayfish.goblintraders.trades.TradeManager;
import com.mrcrayfish.goblintraders.trades.type.BasicTrade;
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
        manager.registerTypeSerializer(BasicTrade.SERIALIZER);

        ModStats.init();

        SpawnEggItemMixin.goblinTradersGetEggMap().putIfAbsent(ModEntities.GOBLIN_TRADER.get(), ModItems.GOBLIN_TRADER_SPAWN_EGG.get());
        SpawnEggItemMixin.goblinTradersGetEggMap().putIfAbsent(ModEntities.VEIN_GOBLIN_TRADER.get(), ModItems.VEIN_GOBLIN_TRADER_SPAWN_EGG.get());
        SpawnEggItemMixin.goblinTradersGetEggMap().remove(null); // Remove null key
    }
}
