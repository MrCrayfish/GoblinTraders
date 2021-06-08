package com.mrcrayfish.goblintraders.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mrcrayfish.goblintraders.Reference;
import com.mrcrayfish.goblintraders.init.ModEntities;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ForgeLootTableProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class LootTableProvider extends ForgeLootTableProvider
{
    private static final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> TABLES = ImmutableList.of(Pair.of(EntityProvider::new, LootParameterSets.ENTITY));

    public LootTableProvider(DataGenerator generator)
    {
        super(generator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables()
    {
        return TABLES;
    }

    private static class EntityProvider extends EntityLootTables
    {
        @Override
        protected void addTables()
        {
            this.registerLootTable(ModEntities.GOBLIN_TRADER.get(), LootTable.builder().addLootPool(new LootPool.Builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.APPLE).acceptFunction(SetCount.builder(RandomValueRange.of(0, 3))).acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0, 1))))));
            this.registerLootTable(ModEntities.VEIN_GOBLIN_TRADER.get(), LootTable.builder().addLootPool(new LootPool.Builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.CARROT).acceptFunction(SetCount.builder(RandomValueRange.of(0, 3))).acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0, 1))))));
        }

        @Override
        protected Iterable<EntityType<?>> getKnownEntities()
        {
            return ForgeRegistries.ENTITIES.getValues().stream().filter(entityType -> entityType.getRegistryName() != null && Reference.MOD_ID.equals(entityType.getRegistryName().getNamespace())).collect(Collectors.toSet());
        }
    }
}
