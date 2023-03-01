package com.mrcrayfish.goblintraders.datagen;

import com.mrcrayfish.goblintraders.init.ModEntities;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

/**
 * Author: MrCrayfish
 */
public class GoblinLootTableProvider extends SimpleFabricLootTableProvider
{
    public GoblinLootTableProvider(FabricDataOutput output)
    {
        super(output, LootContextParamSets.ENTITY);
    }

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer)
    {
        consumer.accept(this.createKey(ModEntities.GOBLIN_TRADER), LootTable.lootTable().withPool(new LootPool.Builder().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.APPLE).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 3))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1))))));
        consumer.accept(this.createKey(ModEntities.VEIN_GOBLIN_TRADER), LootTable.lootTable().withPool(new LootPool.Builder().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.CARROT).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 3))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1))))));
    }

    private ResourceLocation createKey(EntityType<?> type)
    {
        ResourceLocation id = EntityType.getKey(type);
        return new ResourceLocation(id.getNamespace(), "entities/" + id.getPath());
    }
}
