package com.mrcrayfish.goblintraders;

import com.mrcrayfish.goblintraders.init.ModEntities;
import com.mrcrayfish.goblintraders.trades.TradeManager;
import com.mrcrayfish.goblintraders.trades.type.BasicTrade;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

/**
 * Author: MrCrayfish
 */
public class GoblinTraders implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        TradeManager manager = TradeManager.instance();
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(manager);
        manager.registerTrader(ModEntities.GOBLIN_TRADER);
        manager.registerTrader(ModEntities.VEIN_GOBLIN_TRADER);
        manager.registerTypeSerializer(BasicTrade.SERIALIZER);
    }
}
