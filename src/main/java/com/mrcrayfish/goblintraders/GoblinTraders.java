package com.mrcrayfish.goblintraders;

import com.mrcrayfish.goblintraders.client.ClientHandler;
import com.mrcrayfish.goblintraders.init.ModEntities;
import com.mrcrayfish.goblintraders.init.ModItems;
import com.mrcrayfish.goblintraders.init.ModSounds;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Map;

/**
 * Author: MrCrayfish
 */
@Mod(Reference.MOD_ID)
public class GoblinTraders
{
    public GoblinTraders()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEntities.REGISTER.register(bus);
        ModItems.REGISTER.register(bus);
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
        ModEntities.registerEntityTypeAttributes();
    }
}
