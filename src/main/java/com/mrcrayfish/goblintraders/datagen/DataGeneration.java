package com.mrcrayfish.goblintraders.datagen;

import com.mrcrayfish.goblintraders.Reference;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneration
{
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event)
    {
        registerCommonProviders(event.includeServer(), event.getGenerator(), event.getLookupProvider());
    }

    private static void registerCommonProviders(boolean server, DataGenerator generator, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        PackOutput output = generator.getPackOutput();
        generator.addProvider(server, new GoblinTradeProvider(output, lookupProvider));
        generator.addProvider(server, new GoblinLootTableProvider(output));
    }
}
