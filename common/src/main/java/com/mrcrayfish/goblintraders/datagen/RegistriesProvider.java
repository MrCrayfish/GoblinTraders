package com.mrcrayfish.goblintraders.datagen;

import com.mrcrayfish.goblintraders.trades.GoblinTradeSets;
import com.mrcrayfish.goblintraders.trades.GoblinTrades;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class RegistriesProvider
{
    public static final RegistrySetBuilder GOBLIN_REGISTRY_SET = new RegistrySetBuilder()
        .add(Registries.TRADE_SET, GoblinTradeSets::bootstrap)
        .add(Registries.VILLAGER_TRADE, GoblinTrades::bootstrap);
}
