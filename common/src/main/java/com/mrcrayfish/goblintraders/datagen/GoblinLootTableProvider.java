package com.mrcrayfish.goblintraders.datagen;

import com.mrcrayfish.goblintraders.core.ModEntities;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * Author: MrCrayfish
 */
public class GoblinLootTableProvider extends LootTableProvider
{
    public GoblinLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
    {
        super(output, Set.of(), List.of(
            new SubProviderEntry(Entity::new, LootContextParamSets.ENTITY)
        ), registries);
    }

    private static class Entity extends EntityLootSubProvider
    {
        protected Entity(HolderLookup.Provider registries)
        {
            super(FeatureFlags.REGISTRY.allFlags(), registries);
        }

        @Override
        public void generate() {}

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer)
        {
            this.add(ModEntities.GOBLIN_TRADER.get(), consumer, LootTable.lootTable().withPool(new LootPool.Builder().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.APPLE).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 3))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));
            this.add(ModEntities.VEIN_GOBLIN_TRADER.get(), consumer, LootTable.lootTable().withPool(new LootPool.Builder().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.CARROT).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 3))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));
        }

        private void add(EntityType<?> type, BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer, LootTable.Builder builder)
        {
            type.getDefaultLootTable().ifPresent(key -> consumer.accept(key, builder));
        }
    }
}
