package com.mrcrayfish.goblintraders.entity;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.Util;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Author: MrCrayfish
 */
public class GoblinTrades
{
    public static final Int2ObjectMap<VillagerTrades.ITrade[]> GOBLIN_TRADER = Util.make(() -> {
        VillagerTrades.ITrade[] baseOffers = new VillagerTrades.ITrade[] {
            new ItemsForEmeraldsTrade(Items.GOLDEN_SWORD, 2, 1, 5, 1),
        };
        VillagerTrades.ITrade[] rareOffers = new VillagerTrades.ITrade[] {
            new TradeWithEnchantment(Items.EMERALD, 64, Items.DIAMOND_PICKAXE, 1, 100, 0.5F, Enchantments.EFFICIENCY, 6)
        };
        return getAsIntMap(ImmutableMap.of(0, baseOffers, 1, rareOffers));
    });

    private static Int2ObjectMap<VillagerTrades.ITrade[]> getAsIntMap(ImmutableMap<Integer, VillagerTrades.ITrade[]> map)
    {
        return new Int2ObjectOpenHashMap<>(map);
    }

    static class TradeWithEnchantment implements VillagerTrades.ITrade
    {
        private final ItemStack buyingStack;
        private final ItemStack sellingStack;
        private final int maxUses;
        private final int experience;
        private final float priceMultiplier;

        public TradeWithEnchantment(Item buyingItem, int price, Item sellingItem, int maxUses, int experience, float priceMultiplier, Enchantment enchantment, int level)
        {
            this.buyingStack = new ItemStack(buyingItem, price);
            this.sellingStack = new ItemStack(sellingItem);
            this.maxUses = maxUses;
            this.experience = experience;
            this.priceMultiplier = priceMultiplier;
            this.sellingStack.addEnchantment(enchantment, level);
        }

        public TradeWithEnchantment(ItemStack buyingStack, ItemStack sellingStack, int maxUses, int experience, float priceMultiplier, Enchantment enchantment, int level)
        {
            this.buyingStack = buyingStack;
            this.sellingStack = sellingStack;
            this.maxUses = maxUses;
            this.experience = experience;
            this.priceMultiplier = priceMultiplier;
            this.sellingStack.addEnchantment(enchantment, level);
        }

        @Nullable
        @Override
        public MerchantOffer getOffer(Entity trader, Random rand)
        {
            return new MerchantOffer(this.buyingStack, this.sellingStack, this.maxUses, this.experience, this.priceMultiplier);
        }
    }

    static class ItemsForEmeraldsTrade implements VillagerTrades.ITrade
    {
        private final ItemStack offerStack;
        private final int price;
        private final int offerStackCount;
        private final int maxUses;
        private final int experience;
        private final float priceMultiplier;

        public ItemsForEmeraldsTrade(Block block, int price, int offerStackCount, int maxUses, int experience)
        {
            this(new ItemStack(block), price, offerStackCount, maxUses, experience);
        }

        public ItemsForEmeraldsTrade(Item item, int price, int offerStackCount, int experience)
        {
            this(new ItemStack(item), price, offerStackCount, 12, experience);
        }

        public ItemsForEmeraldsTrade(Item item, int price, int offerStackCount, int maxUses, int experience)
        {
            this(new ItemStack(item), price, offerStackCount, maxUses, experience);
        }

        public ItemsForEmeraldsTrade(ItemStack stack, int price, int offerStackCount, int maxUses, int experience)
        {
            this(stack, price, offerStackCount, maxUses, experience, 0.05F);
        }

        public ItemsForEmeraldsTrade(ItemStack stack, int price, int offerStackCount, int maxUses, int experience, float priceMultiplier)
        {
            this.offerStack = stack;
            this.price = price;
            this.offerStackCount = offerStackCount;
            this.maxUses = maxUses;
            this.experience = experience;
            this.priceMultiplier = priceMultiplier;
        }

        public MerchantOffer getOffer(Entity trader, Random rand)
        {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.price), new ItemStack(this.offerStack.getItem(), this.offerStackCount), this.maxUses, this.experience, this.priceMultiplier);
        }
    }
}
