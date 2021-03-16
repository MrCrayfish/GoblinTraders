package com.mrcrayfish.goblintraders;

import com.mrcrayfish.goblintraders.client.ClientHandler;
import com.mrcrayfish.goblintraders.init.ModEntities;
import com.mrcrayfish.goblintraders.init.ModItems;
import com.mrcrayfish.goblintraders.init.ModPotions;
import com.mrcrayfish.goblintraders.init.ModSounds;
import com.mrcrayfish.goblintraders.init.ModStats;
import com.mrcrayfish.goblintraders.trades.TradeManager;
import com.mrcrayfish.goblintraders.trades.type.BasicTrade;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * Author: MrCrayfish
 */
@Mod(Reference.MOD_ID)
public class GoblinTraders
{
    public GoblinTraders()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.commonSpec);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEntities.REGISTER.register(bus);
        ModItems.REGISTER.register(bus);
        ModPotions.REGISTER.register(bus);
        ModSounds.REGISTER.register(bus);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
    }

    private void onClientSetup(FMLClientSetupEvent event)
    {
        ClientHandler.setup();
    }

    private void onCommonSetup(FMLCommonSetupEvent event)
    {
        ModStats.init();
        ModEntities.registerEntityTypeAttributes();
        TradeManager manager = TradeManager.instance();
        manager.registerTrader(ModEntities.GOBLIN_TRADER.get());
        manager.registerTrader(ModEntities.VEIN_GOBLIN_TRADER.get());
        manager.registerTypeSerializer(BasicTrade.SERIALIZER);
    }
}
