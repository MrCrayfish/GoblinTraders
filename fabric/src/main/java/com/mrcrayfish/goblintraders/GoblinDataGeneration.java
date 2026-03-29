package com.mrcrayfish.goblintraders;

import com.mrcrayfish.framework.api.datagen.FrameworkModelProvider;
import com.mrcrayfish.goblintraders.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.registries.RegistryPatchGenerator;

import java.util.concurrent.CompletableFuture;

public class GoblinDataGeneration implements DataGeneratorEntrypoint
{
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator)
    {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(GoblinLootTableProvider::new);
        pack.addProvider((FabricDataGenerator.Pack.Factory<FrameworkModelProvider>) output -> new FrameworkModelProvider(output, GoblinItemModelProvider::new));

        var lookup = RegistryPatchGenerator.createLookup(generator.getRegistries(), RegistriesProvider.GOBLIN_REGISTRY_SET);
        CompletableFuture<HolderLookup.Provider> provider = lookup.thenApply(RegistrySetBuilder.PatchedRegistries::patches);
        pack.addProvider((FabricDataGenerator.Pack.Factory<GoblinRegistryProvider>) output -> new GoblinRegistryProvider(output, provider));
        pack.addProvider((FabricDataGenerator.Pack.Factory<GoblinTradeTagsProvider>) output -> new GoblinTradeTagsProvider(output, provider));
    }
}
