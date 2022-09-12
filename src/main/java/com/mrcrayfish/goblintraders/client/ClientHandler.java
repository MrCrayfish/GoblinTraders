package com.mrcrayfish.goblintraders.client;

import com.mrcrayfish.goblintraders.client.renderer.entity.GoblinModelLayers;
import com.mrcrayfish.goblintraders.client.renderer.entity.GoblinTraderRenderer;
import com.mrcrayfish.goblintraders.client.renderer.entity.model.GoblinTraderModel;
import com.mrcrayfish.goblintraders.init.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

/**
 * Author: MrCrayfish
 */
public class ClientHandler implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        EntityRendererRegistry.register(ModEntities.GOBLIN_TRADER, GoblinTraderRenderer::new);
        EntityRendererRegistry.register(ModEntities.VEIN_GOBLIN_TRADER, GoblinTraderRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(GoblinModelLayers.GOBLIN_TRADER, GoblinTraderModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(GoblinModelLayers.VEIN_GOBLIN_TRADER, GoblinTraderModel::createBodyLayer);
    }
}
