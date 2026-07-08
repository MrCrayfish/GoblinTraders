package com.mrcrayfish.goblintraders.datagen;

import com.mojang.serialization.Lifecycle;
import com.mrcrayfish.goblintraders.trades.GoblinTradeSets;
import com.mrcrayfish.goblintraders.trades.GoblinTrades;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.*;
import net.minecraft.data.registries.RegistryPatchGenerator;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.item.trading.VillagerTrade;

import java.util.concurrent.CompletableFuture;

/**
 * Author: MrCrayfish
 */
public class GoblinRegistryProvider extends FabricDynamicRegistryProvider
{
    public GoblinRegistryProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture)
    {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries)
    {
        GoblinTrades.bootstrap(new BootstrapContext<>() {
            @Override
            public Holder.Reference<VillagerTrade> register(ResourceKey<VillagerTrade> key, VillagerTrade value, Lifecycle lifecycle) {
                return (Holder.Reference<VillagerTrade>) entries.add(key, value);
            }
            @Override
            public <S> HolderGetter<S> lookup(ResourceKey<? extends Registry<? extends S>> key) {
                return registries.lookup(key).orElseThrow();
            }
        });
        GoblinTradeSets.bootstrap(new BootstrapContext<>() {
            @Override
            public Holder.Reference<TradeSet> register(ResourceKey<TradeSet> key, TradeSet value, Lifecycle lifecycle) {
                return (Holder.Reference<TradeSet>) entries.add(key, value);
            }
            @Override
            public <S> HolderGetter<S> lookup(ResourceKey<? extends Registry<? extends S>> key) {
                return registries.lookup(key).orElseThrow();
            }
        });
    }

    @Override
    public String getName()
    {
        return "Goblin Trader Registry Provider";
    }
}
