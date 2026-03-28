package com.mrcrayfish.goblintraders.client;

import com.mrcrayfish.goblintraders.client.renderer.entity.GoblinModelLayers;
import com.mrcrayfish.goblintraders.client.renderer.entity.GoblinTraderRenderer;
import com.mrcrayfish.goblintraders.client.renderer.entity.model.GoblinTraderModel;
import com.mrcrayfish.goblintraders.core.ModEntities;
import com.mrcrayfish.goblintraders.core.ModItems;
import com.mrcrayfish.goblintraders.core.ModMenuTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;

/**
 * Author: MrCrayfish
 */
public class ClientHandler implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        EntityRenderers.register(ModEntities.GOBLIN_TRADER.get(), GoblinTraderRenderer::new);
        EntityRenderers.register(ModEntities.VEIN_GOBLIN_TRADER.get(), GoblinTraderRenderer::new);
        ModelLayerRegistry.registerModelLayer(GoblinModelLayers.GOBLIN_TRADER, GoblinTraderModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(GoblinModelLayers.VEIN_GOBLIN_TRADER, GoblinTraderModel::createBodyLayer);
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS).register(entries -> {
            entries.accept(ModItems.GOBLIN_TRADER_SPAWN_EGG.get());
            entries.accept(ModItems.VEIN_GOBLIN_TRADER_SPAWN_EGG.get());
        });
        MenuScreens.register(ModMenuTypes.GOBLIN_MERCHANT.get(), MerchantScreen::new);
    }
}
