package com.mrcrayfish.goblintraders;

import com.mrcrayfish.framework.FrameworkSetup;
import com.mrcrayfish.goblintraders.core.ModEntities;
import com.mrcrayfish.goblintraders.datagen.GoblinTradeProvider;
import com.mrcrayfish.goblintraders.datagen.PlatformLootTableProvider;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.trades.TradeManager;
import com.mrcrayfish.goblintraders.util.Utils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Author: MrCrayfish
 */
public class GoblinTraders implements ModInitializer, DataGeneratorEntrypoint
{
    @Override
    public void onInitialize()
    {
        FrameworkSetup.run();
        Bootstrap.init();
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new IdentifiableResourceReloadListener()
        {
            @Override
            public ResourceLocation getFabricId()
            {
                return Utils.resource("trade_manager");
            }

            @Override
            public CompletableFuture<Void> reload(PreparationBarrier barrier, ResourceManager manager, ProfilerFiller profilerFiller, ProfilerFiller profilerFiller2, Executor executor, Executor executor2)
            {
                return TradeManager.instance().reload(barrier, manager, profilerFiller, profilerFiller2, executor, executor2);
            }
        });
        FabricDefaultAttributeRegistry.register(ModEntities.GOBLIN_TRADER.get(), AbstractGoblinEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.VEIN_GOBLIN_TRADER.get(), AbstractGoblinEntity.createAttributes());
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator)
    {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(GoblinTradeProvider::new);
        pack.addProvider(PlatformLootTableProvider.Entity::new);
    }
}
