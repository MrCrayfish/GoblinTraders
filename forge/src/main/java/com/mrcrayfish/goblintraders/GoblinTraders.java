package com.mrcrayfish.goblintraders;

import com.mrcrayfish.goblintraders.client.ClientBootstrap;
import com.mrcrayfish.goblintraders.core.ModEntities;
import com.mrcrayfish.goblintraders.datagen.GoblinTradeProvider;
import com.mrcrayfish.goblintraders.datagen.PlatformLootTableProvider;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.trades.TradeManager;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.concurrent.CompletableFuture;

/**
 * Author: MrCrayfish
 */
@Mod(Constants.MOD_ID)
public class GoblinTraders
{
    public GoblinTraders()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::onCommonSetup);
        bus.addListener(this::onClientSetup);
        bus.addListener(this::onGatherData);
        bus.addListener(this::onEntityAttributeCreation);
        MinecraftForge.EVENT_BUS.addListener(this::addReloadListener);
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
        generator.addProvider(event.includeServer(), new PlatformLootTableProvider(output));
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
