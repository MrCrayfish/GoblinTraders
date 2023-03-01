package com.mrcrayfish.goblintraders.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataProvider;

/**
 * Author: MrCrayfish
 */
public class DataGeneration implements DataGeneratorEntrypoint
{
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator)
    {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(GoblinTradeProvider::new);
        pack.addProvider((FabricDataGenerator.Pack.Factory<? extends DataProvider>) GoblinLootTableProvider::new);
    }
}
