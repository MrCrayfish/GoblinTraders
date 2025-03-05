package com.mrcrayfish.goblintraders;

import com.mrcrayfish.framework.FrameworkSetup;
import com.mrcrayfish.goblintraders.core.ModEntities;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.trades.TradeManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Author: MrCrayfish
 */
public class GoblinTraders implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        FrameworkSetup.run();
        Bootstrap.init();
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public ResourceLocation getFabricId() {
                return TradeManager.ID;
            }
            @Override
            public CompletableFuture<Void> reload(PreparationBarrier barrier, ResourceManager manager, Executor executor, Executor executor2) {
                return TradeManager.instance().reload(barrier, manager, executor, executor2);
            }
        });
        FabricDefaultAttributeRegistry.register(ModEntities.GOBLIN_TRADER.get(), AbstractGoblinEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.VEIN_GOBLIN_TRADER.get(), AbstractGoblinEntity.createAttributes());
    }
}
