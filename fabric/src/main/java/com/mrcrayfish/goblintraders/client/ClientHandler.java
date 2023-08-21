package com.mrcrayfish.goblintraders.client;

import com.mrcrayfish.goblintraders.client.renderer.entity.GoblinModelLayers;
import com.mrcrayfish.goblintraders.client.renderer.entity.GoblinTraderRenderer;
import com.mrcrayfish.goblintraders.client.renderer.entity.model.GoblinTraderModel;
import com.mrcrayfish.goblintraders.core.ModEntities;
import com.mrcrayfish.goblintraders.core.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;

/**
 * Author: MrCrayfish
 */
public class ClientHandler implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        EntityRendererRegistry.register(ModEntities.GOBLIN_TRADER.get(), GoblinTraderRenderer::new);
        EntityRendererRegistry.register(ModEntities.VEIN_GOBLIN_TRADER.get(), GoblinTraderRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(GoblinModelLayers.GOBLIN_TRADER, GoblinTraderModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(GoblinModelLayers.VEIN_GOBLIN_TRADER, GoblinTraderModel::createBodyLayer);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(entries -> {
            entries.accept(ModItems.GOBLIN_TRADER_SPAWN_EGG.get());
            entries.accept(ModItems.VEIN_GOBLIN_TRADER_SPAWN_EGG.get());
        });
    }
}
