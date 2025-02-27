package com.mrcrayfish.goblintraders.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.goblintraders.util.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModStats
{
    public static final RegistryEntry<ResourceLocation> TRADE_WITH_GOBLIN = RegistryEntry.customStat(Utils.resource( "trade_with_goblin"), StatFormatter.DEFAULT);
}
