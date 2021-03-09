package com.mrcrayfish.goblintraders.datagen;

import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.goblintraders.init.ModEntities;
import com.mrcrayfish.goblintraders.trades.TradeRarity;
import com.mrcrayfish.goblintraders.trades.type.BasicTrade;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Author: MrCrayfish
 */
public class GoblinTradeProvider extends TradeProvider
{
    public GoblinTradeProvider(DataGenerator generator)
    {
        super(generator);
    }

    @Override
    public void registerTrades()
    {
    }
}
