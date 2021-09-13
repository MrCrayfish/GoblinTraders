package com.mrcrayfish.goblintraders.init;

import com.mrcrayfish.goblintraders.Reference;
import com.mrcrayfish.goblintraders.item.SupplierSpawnEggItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Author: MrCrayfish
 */
public class ModItems
{
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MOD_ID);

    public static final RegistryObject<Item> GOBLIN_TRADER_SPAWN_EGG = REGISTER.register("goblin_trader_spawn_egg", () -> new SupplierSpawnEggItem(ModEntities.GOBLIN_TRADER::get, 0x4da744, 0x316f5d, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> VEIN_GOBLIN_TRADER_SPAWN_EGG = REGISTER.register("vein_goblin_trader_spawn_egg", () -> new SupplierSpawnEggItem(ModEntities.VEIN_GOBLIN_TRADER::get, 0xf3982e, 0xf45b1f, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
}
