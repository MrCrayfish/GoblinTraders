package com.mrcrayfish.goblintraders.entity;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
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
    //TODO for vein goblin trader
    //dragon head
    //NETHER_STAR

    public static final Int2ObjectMap<VillagerTrades.ITrade[]> GOBLIN_TRADER = Util.make(() -> {
        VillagerTrades.ITrade[] baseOffers = new VillagerTrades.ITrade[] {
            new ItemsForEmeraldsTrade(Items.APPLE, 1, 10, 10, 1),
            new ItemsForEmeraldsTrade(Items.COOKIE, 1, 8, 10, 1),
            new ItemsForEmeraldsTrade(Items.COOKED_BEEF, 1, 4, 10, 1),
            new ItemsForEmeraldsTrade(Items.COOKED_CHICKEN, 1, 5, 10, 1),
            new ItemsForEmeraldsTrade(Items.COOKED_MUTTON, 1, 4, 10, 1),
            new ItemsForEmeraldsTrade(Items.COOKED_RABBIT, 1, 4, 10, 1),
            new ItemsForEmeraldsTrade(Items.ZOMBIE_VILLAGER_SPAWN_EGG, 8, 1, 2, 5),
            new ItemsForEmeraldsTrade(Items.EXPERIENCE_BOTTLE, 10, 16, 20, 2),
            new ItemsForEmeraldsTrade(Items.TNT, 4, 8, 10, 2),
            new ItemsForEmeraldsTrade(Items.DIAMOND_HELMET, 24, 1, 1, 10),
            new ItemsForEmeraldsTrade(Items.DIAMOND_CHESTPLATE, 32, 1, 1, 10),
            new ItemsForEmeraldsTrade(Items.DIAMOND_LEGGINGS, 28, 1, 1, 10),
            new ItemsForEmeraldsTrade(Items.DIAMOND_BOOTS, 20, 1, 1, 10)
        };
        VillagerTrades.ITrade[] rareOffers = new VillagerTrades.ITrade[] {
            new TradeWithEnchantment(new ItemStack(Items.EMERALD, 32), new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.DIAMOND_PICKAXE), 1, 100, 0.5F, Enchantments.EFFICIENCY, 6),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 40), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.MENDING, 1),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.LOOTING, 5),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 36), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.SHARPNESS, 6),
            new TradeWithEnchantment(new ItemStack(Items.EMERALD, 16), new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.FORTUNE, 5),
            new TradeWithEnchantment(new ItemStack(Items.EMERALD, 8), new ItemStack(Items.GOLD_INGOT, 48), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.FEATHER_FALLING, 6),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 64), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.UNBREAKING, 5)
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
        private ItemStack secondBuyingStack = ItemStack.EMPTY;
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

        public TradeWithEnchantment(ItemStack buyingStack, ItemStack secondBuyingStack, ItemStack sellingStack, int maxUses, int experience, float priceMultiplier, Enchantment enchantment, int level)
        {
            this.buyingStack = buyingStack;
            this.secondBuyingStack = secondBuyingStack;
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
            return new MerchantOffer(this.buyingStack, this.secondBuyingStack, this.sellingStack, this.maxUses, this.experience, this.priceMultiplier);
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
