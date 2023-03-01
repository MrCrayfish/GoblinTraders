package com.mrcrayfish.goblintraders.init;

import com.mrcrayfish.goblintraders.Reference;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

/**
 * Author: MrCrayfish
 */
public class ModItems implements ModInitializer
{
    public static final Item GOBLIN_TRADER_SPAWN_EGG = new SpawnEggItem(ModEntities.GOBLIN_TRADER, 0x4DA744, 0x316F5D, new Item.Properties());
    public static final Item VEIN_GOBLIN_TRADER_SPAWN_EGG = new SpawnEggItem(ModEntities.VEIN_GOBLIN_TRADER, 0xF3982E, 0xF45B1F, new Item.Properties());

    @Override
    public void onInitialize()
    {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Reference.MOD_ID, "goblin_trader_spawn_egg"), GOBLIN_TRADER_SPAWN_EGG);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Reference.MOD_ID, "vein_goblin_trader_spawn_egg"), VEIN_GOBLIN_TRADER_SPAWN_EGG);
    }
}
