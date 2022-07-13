package com.mrcrayfish.goblintraders.init;

import com.mrcrayfish.goblintraders.Reference;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Author: MrCrayfish
 */
public class ModItems
{
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);

    public static final RegistryObject<Item> GOBLIN_TRADER_SPAWN_EGG = REGISTER.register("goblin_trader_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.GOBLIN_TRADER, 0x4DA744, 0x316F5D, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> VEIN_GOBLIN_TRADER_SPAWN_EGG = REGISTER.register("vein_goblin_trader_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.VEIN_GOBLIN_TRADER, 0xF3982E, 0xF45B1F, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
}
