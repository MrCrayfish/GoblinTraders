package com.mrcrayfish.goblintraders.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.goblintraders.util.Utils;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModItems
{
    public static final RegistryEntry<SpawnEggItem> GOBLIN_TRADER_SPAWN_EGG = RegistryEntry.item(Utils.resource("goblin_trader_spawn_egg"), properties -> new SpawnEggItem(ModEntities.GOBLIN_TRADER.get(), properties), Item.Properties::new);
    public static final RegistryEntry<SpawnEggItem> VEIN_GOBLIN_TRADER_SPAWN_EGG = RegistryEntry.item(Utils.resource("vein_goblin_trader_spawn_egg"), properties -> new SpawnEggItem(ModEntities.VEIN_GOBLIN_TRADER.get(), properties), Item.Properties::new);
}
