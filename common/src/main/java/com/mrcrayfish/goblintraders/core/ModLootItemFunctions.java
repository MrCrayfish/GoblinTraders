package com.mrcrayfish.goblintraders.core;

import com.mojang.serialization.MapCodec;
import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.goblintraders.loot_functions.IncreaseDurabilityFunction;
import com.mrcrayfish.goblintraders.util.Utils;

@RegistryContainer
public class ModLootItemFunctions
{
    public static final RegistryEntry<MapCodec<IncreaseDurabilityFunction>> INCREASE_DURABILITY = RegistryEntry.lootFunctionType(Utils.id("increase_durability"), () -> IncreaseDurabilityFunction.MAP_CODEC);
}
