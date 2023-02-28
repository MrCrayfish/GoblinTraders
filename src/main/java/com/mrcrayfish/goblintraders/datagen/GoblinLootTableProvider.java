package com.mrcrayfish.goblintraders.datagen;

import com.mrcrayfish.goblintraders.Reference;
import com.mrcrayfish.goblintraders.init.ModEntities;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Author: MrCrayfish
 */
public class GoblinLootTableProvider extends LootTableProvider
{
    public GoblinLootTableProvider(PackOutput output)
    {
        super(output, Set.of(), List.of(new SubProviderEntry(EntityProvider::new, LootContextParamSets.ENTITY)));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext context) {}

    private static class EntityProvider extends EntityLootSubProvider
    {
        protected EntityProvider()
        {
            super(FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        public void generate()
        {
            this.add(ModEntities.GOBLIN_TRADER.get(), LootTable.lootTable().withPool(new LootPool.Builder().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.APPLE).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 3))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1))))));
            this.add(ModEntities.VEIN_GOBLIN_TRADER.get(), LootTable.lootTable().withPool(new LootPool.Builder().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.CARROT).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 3))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1))))));
        }

        @Override
        protected Stream<EntityType<?>> getKnownEntityTypes()
        {
            return ForgeRegistries.ENTITY_TYPES.getValues().stream().filter(entityType -> Reference.MOD_ID.equals(EntityType.getKey(entityType).getNamespace()));
        }
    }
}
