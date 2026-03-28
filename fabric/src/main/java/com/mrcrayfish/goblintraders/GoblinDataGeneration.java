package com.mrcrayfish.goblintraders;

import com.mrcrayfish.goblintraders.datagen.GoblinRegistryProvider;
import com.mrcrayfish.goblintraders.datagen.GoblinLootTableProvider;
import com.mrcrayfish.goblintraders.datagen.RegistriesProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.registries.RegistriesDatapackGenerator;
import net.minecraft.data.registries.RegistryPatchGenerator;

public class GoblinDataGeneration implements DataGeneratorEntrypoint
{
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator)
    {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(GoblinLootTableProvider::new);
        pack.addProvider(GoblinRegistryProvider::new);
        pack.addProvider((output, registriesFuture) -> {
            var lookup = RegistryPatchGenerator.createLookup(registriesFuture, RegistriesProvider.GOBLIN_TRADE_SETS);
            return new RegistriesDatapackGenerator(output, lookup.thenApply(RegistrySetBuilder.PatchedRegistries::patches));
        });
    }
}
