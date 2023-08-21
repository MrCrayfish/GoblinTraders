package com.mrcrayfish.goblintraders.datagen;

import com.mrcrayfish.goblintraders.core.ModEntities;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

/**
 * Author: MrCrayfish
 */
public class GoblinLootTableProvider
{
    public static void accept(LootBuilder.Entity builder)
    {
        builder.add(ModEntities.GOBLIN_TRADER.get(), LootTable.lootTable().withPool(new LootPool.Builder().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.APPLE).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 3))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1))))));
        builder.add(ModEntities.VEIN_GOBLIN_TRADER.get(), LootTable.lootTable().withPool(new LootPool.Builder().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.CARROT).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 3))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1))))));
    }
}
