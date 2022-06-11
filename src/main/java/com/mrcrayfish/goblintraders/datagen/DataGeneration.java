package com.mrcrayfish.goblintraders.datagen;

import com.mrcrayfish.goblintraders.Reference;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneration
{
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event)
    {
        registerCommonProviders(event.includeServer(), event.getGenerator());
    }

    private static void registerCommonProviders(boolean server, DataGenerator generator)
    {
        generator.addProvider(server, new GoblinTradeProvider(generator));
        generator.addProvider(server, new GoblinLootTableProvider(generator));
    }
}
