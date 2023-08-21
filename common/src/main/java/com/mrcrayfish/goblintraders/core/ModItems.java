package com.mrcrayfish.goblintraders.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.goblintraders.platform.Services;
import com.mrcrayfish.goblintraders.util.Utils;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModItems
{
    public static final RegistryEntry<SpawnEggItem> GOBLIN_TRADER_SPAWN_EGG = RegistryEntry.item(Utils.resource("goblin_trader_spawn_egg"), () -> Services.PLATFORM.createSpawnEgg(ModEntities.GOBLIN_TRADER::get, 0x4DA744, 0x316F5D, new Item.Properties()));
    public static final RegistryEntry<SpawnEggItem> VEIN_GOBLIN_TRADER_SPAWN_EGG = RegistryEntry.item(Utils.resource("vein_goblin_trader_spawn_egg"), () -> Services.PLATFORM.createSpawnEgg(ModEntities.VEIN_GOBLIN_TRADER::get, 0xF3982E, 0xF45B1F, new Item.Properties()));
}
