package com.mrcrayfish.goblintraders.init;

import com.mrcrayfish.goblintraders.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class ModItems
{
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);

    public static final RegistryObject<Item> GOBLIN_TRADER_SPAWN_EGG = REGISTER.register("goblin_trader_spawn_egg", () -> new SpawnEggItem(ModEntities.GOBLIN_TRADER.get(), 0x4da744, 0x316f5d, new Item.Properties().group(ItemGroup.MISC)));
    public static final RegistryObject<Item> VEIN_GOBLIN_TRADER_SPAWN_EGG = REGISTER.register("vein_goblin_trader_spawn_egg", () -> new SpawnEggItem(ModEntities.VEIN_GOBLIN_TRADER.get(), 0xf3982e, 0xf45b1f, new Item.Properties().group(ItemGroup.MISC)));
}
