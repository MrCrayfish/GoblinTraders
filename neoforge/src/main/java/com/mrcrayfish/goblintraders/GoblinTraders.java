package com.mrcrayfish.goblintraders;

import com.mrcrayfish.goblintraders.core.ModEntities;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.trades.TradeManager;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

/**
 * Author: MrCrayfish
 */
@Mod(Constants.MOD_ID)
public class GoblinTraders
{
    public GoblinTraders(IEventBus bus)
    {
        bus.addListener(this::onCommonSetup);
        bus.addListener(this::onEntityAttributeCreation);
        NeoForge.EVENT_BUS.addListener(this::addReloadListener);
    }

    private void onCommonSetup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(Bootstrap::init);
    }

    private void onEntityAttributeCreation(EntityAttributeCreationEvent event)
    {
        event.put(ModEntities.GOBLIN_TRADER.get(), AbstractGoblinEntity.createAttributes().build());
        event.put(ModEntities.VEIN_GOBLIN_TRADER.get(), AbstractGoblinEntity.createAttributes().build());
    }

    public void addReloadListener(AddServerReloadListenersEvent event)
    {
        event.addListener(TradeManager.ID, TradeManager.instance());
    }
}
