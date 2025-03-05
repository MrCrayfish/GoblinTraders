package com.mrcrayfish.goblintraders;

import com.mrcrayfish.framework.api.datagen.FrameworkModelProvider;
import com.mrcrayfish.goblintraders.datagen.GoblinItemModelProvider;
import com.mrcrayfish.goblintraders.datagen.GoblinLootTableProvider;
import com.mrcrayfish.goblintraders.datagen.GoblinTradeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

@SuppressWarnings("UnstableApiUsage")
public class GoblinDataGeneration implements DataGeneratorEntrypoint
{
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator)
    {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(GoblinTradeProvider::new);
        pack.addProvider(GoblinLootTableProvider::new);
        pack.addProvider((FabricDataGenerator.Pack.Factory<FrameworkModelProvider>) output ->
                new FrameworkModelProvider(output, GoblinItemModelProvider::new));
    }
}
