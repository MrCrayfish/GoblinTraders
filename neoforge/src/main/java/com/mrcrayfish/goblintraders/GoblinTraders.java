package com.mrcrayfish.goblintraders;

import com.mrcrayfish.goblintraders.client.ClientBootstrap;
import com.mrcrayfish.goblintraders.core.ModEntities;
import com.mrcrayfish.goblintraders.datagen.GoblinLootTableProvider;
import com.mrcrayfish.goblintraders.datagen.GoblinTradeProvider;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.trades.TradeManager;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

import java.util.concurrent.CompletableFuture;

/**
 * Author: MrCrayfish
 */
@Mod(Constants.MOD_ID)
public class GoblinTraders
{
    public GoblinTraders(IEventBus bus)
    {
        bus.addListener(this::onCommonSetup);
        bus.addListener(this::onClientSetup);
        bus.addListener(this::onGatherData);
        bus.addListener(this::onEntityAttributeCreation);
        NeoForge.EVENT_BUS.addListener(this::addReloadListener);
    }

    private void onCommonSetup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(Bootstrap::init);
    }

    private void onClientSetup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(ClientBootstrap::init);
    }

    public void onGatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        generator.addProvider(event.includeServer(), new GoblinLootTableProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new GoblinTradeProvider(output, lookupProvider));
    }

    private void onEntityAttributeCreation(EntityAttributeCreationEvent event)
    {
        event.put(ModEntities.GOBLIN_TRADER.get(), AbstractGoblinEntity.createAttributes().build());
        event.put(ModEntities.VEIN_GOBLIN_TRADER.get(), AbstractGoblinEntity.createAttributes().build());
    }

    public void addReloadListener(AddReloadListenerEvent event)
    {
        event.addListener(TradeManager.instance());
    }
}
