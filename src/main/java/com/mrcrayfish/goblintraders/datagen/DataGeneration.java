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
        registerCommonProviders(event.getGenerator());
    }

    private static void registerCommonProviders(DataGenerator generator)
    {
        generator.addProvider(new GoblinTradeProvider(generator));
        generator.addProvider(new GoblinLootTableProvider(generator));
    }
}
