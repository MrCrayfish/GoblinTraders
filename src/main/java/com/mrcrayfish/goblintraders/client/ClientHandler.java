package com.mrcrayfish.goblintraders.client;

import com.mrcrayfish.goblintraders.Reference;
import com.mrcrayfish.goblintraders.client.renderer.entity.GoblinModelLayers;
import com.mrcrayfish.goblintraders.client.renderer.entity.GoblinTraderRenderer;
import com.mrcrayfish.goblintraders.client.renderer.entity.model.GoblinTraderModel;
import com.mrcrayfish.goblintraders.init.ModEntities;
import com.mrcrayfish.goblintraders.init.ModItems;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Author: MrCrayfish
 */
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientHandler
{
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(ModEntities.GOBLIN_TRADER.get(), GoblinTraderRenderer::new);
        event.registerEntityRenderer(ModEntities.VEIN_GOBLIN_TRADER.get(), GoblinTraderRenderer::new);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(GoblinModelLayers.GOBLIN_TRADER, GoblinTraderModel::createBodyLayer);
        event.registerLayerDefinition(GoblinModelLayers.VEIN_GOBLIN_TRADER, GoblinTraderModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterCreativeTab(CreativeModeTabEvent.BuildContents event)
    {
        if(event.getTab().equals(CreativeModeTabs.SPAWN_EGGS))
        {
            event.accept(ModItems.GOBLIN_TRADER_SPAWN_EGG);
            event.accept(ModItems.VEIN_GOBLIN_TRADER_SPAWN_EGG);
        }
    }
}
