package com.mrcrayfish.goblintraders.client;

import com.mrcrayfish.framework.api.datagen.FrameworkModelProvider;
import com.mrcrayfish.goblintraders.Constants;
import com.mrcrayfish.goblintraders.client.renderer.entity.GoblinModelLayers;
import com.mrcrayfish.goblintraders.client.renderer.entity.GoblinTraderRenderer;
import com.mrcrayfish.goblintraders.client.renderer.entity.model.GoblinTraderModel;
import com.mrcrayfish.goblintraders.core.ModEntities;
import com.mrcrayfish.goblintraders.core.ModItems;
import com.mrcrayfish.goblintraders.core.ModMenuTypes;
import com.mrcrayfish.goblintraders.datagen.*;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.trading.VillagerTrade;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Author: MrCrayfish
 */
@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class ClientHandler
{
    @SubscribeEvent
    private static void onClientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(ClientBootstrap::init);
    }

    @SubscribeEvent
    private static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(ModEntities.GOBLIN_TRADER.get(), GoblinTraderRenderer::new);
        event.registerEntityRenderer(ModEntities.VEIN_GOBLIN_TRADER.get(), GoblinTraderRenderer::new);
    }

    @SubscribeEvent
    private static void registerEntityRenderers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(GoblinModelLayers.GOBLIN_TRADER, GoblinTraderModel::createBodyLayer);
        event.registerLayerDefinition(GoblinModelLayers.VEIN_GOBLIN_TRADER, GoblinTraderModel::createBodyLayer);
    }

    @SubscribeEvent
    private static void onRegisterCreativeTab(BuildCreativeModeTabContentsEvent event)
    {
        if(event.getTabKey().equals(CreativeModeTabs.SPAWN_EGGS))
        {
            event.accept(ModItems.GOBLIN_TRADER_SPAWN_EGG.get());
            event.accept(ModItems.VEIN_GOBLIN_TRADER_SPAWN_EGG.get());
        }
    }

    @SubscribeEvent
    private static void registerMenuScreens(RegisterMenuScreensEvent event)
    {
        event.register(ModMenuTypes.GOBLIN_MERCHANT.get(), MerchantScreen::new);
    }

    @SubscribeEvent
    @SuppressWarnings({"unchecked", "UnstableApiUsage"})
    public static void onGatherData(GatherDataEvent.Client event)
    {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        event.createProvider(GoblinLootTableProvider::new);
        event.addProvider(new FrameworkModelProvider(output, GoblinItemModelProvider::new));
        event.addProvider(new TagsProvider<VillagerTrade>(output, Registries.VILLAGER_TRADE, lookupProvider) {
            @Override
            protected void addTags(HolderLookup.Provider provider) {
                GoblinTradeTagsProvider.addTags(this::tag);
            }
        });
        event.addProvider(new DatapackBuiltinEntriesProvider(output, lookupProvider, RegistriesProvider.GOBLIN_REGISTRY_SET, Set.of(Constants.MOD_ID)));
    }
}
