package com.mrcrayfish.goblintraders.entity;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

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
            new ItemsForEmeraldsTrade(Items.DIAMOND_HELMET, 28, 1, 1, 10),
            new ItemsForEmeraldsTrade(Items.DIAMOND_CHESTPLATE, 326, 1, 1, 10),
            new ItemsForEmeraldsTrade(Items.DIAMOND_LEGGINGS, 32, 1, 1, 10),
            new ItemsForEmeraldsTrade(Items.DIAMOND_BOOTS, 24, 1, 1, 10),
            new ItemsForEmeraldsTrade(Items.DIAMOND_PICKAXE, 24, 1, 1, 10),
            new ItemsForEmeraldsTrade(Items.DIAMOND_SHOVEL, 18, 1, 1, 10),
            new ItemsForEmeraldsTrade(Items.DIAMOND_SWORD, 20, 1, 1, 10),
            new ItemsForEmeraldsTrade(Items.DIAMOND_HOE, 10, 1, 1, 10),
            new ItemsForEmeraldsTrade(Items.CHAINMAIL_HELMET, 24, 1, 1, 10),
            new ItemsForEmeraldsTrade(Items.CHAINMAIL_CHESTPLATE, 32, 1, 1, 10),
            new ItemsForEmeraldsTrade(Items.CHAINMAIL_LEGGINGS, 28, 1, 1, 10),
            new ItemsForEmeraldsTrade(Items.CHAINMAIL_BOOTS, 20, 1, 1, 10),
        };
        VillagerTrades.ITrade[] rareOffers = new VillagerTrades.ITrade[] {
            new TradeWithEnchantment(new ItemStack(Items.EMERALD, 32), new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.DIAMOND_PICKAXE), 1, 100, 0.5F, Enchantments.EFFICIENCY, 5),
            new TradeWithEnchantment(new ItemStack(Items.EMERALD, 64), new ItemStack(Items.GOLD_INGOT, 64), new ItemStack(Items.DIAMOND_PICKAXE), 1, 100, 0.5F, new EnchantmentData(Enchantments.EFFICIENCY, 5), new EnchantmentData(Enchantments.UNBREAKING, 4)),
            new TradeWithEnchantment(new ItemStack(Items.EMERALD, 64), new ItemStack(Items.GOLD_INGOT, 64), new ItemStack(Items.DIAMOND_PICKAXE), 1, 100, 0.5F, new EnchantmentData(Enchantments.FORTUNE, 5), new EnchantmentData(Enchantments.UNBREAKING, 4)),
            new TradeWithEnchantment(new ItemStack(Items.EMERALD, 64), new ItemStack(Items.GOLD_INGOT, 64), new ItemStack(Items.DIAMOND_PICKAXE), 1, 100, 0.5F, new EnchantmentData(Enchantments.SWEEPING, 5), new EnchantmentData(Enchantments.UNBREAKING, 4)),
            new TradeWithEnchantment(new ItemStack(Items.IRON_INGOT, 64), new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.IRON_SWORD), 1, 100, 0.5F, new EnchantmentData(Enchantments.KNOCKBACK, 8)),
            new TradeWithEnchantment(new ItemStack(Items.IRON_INGOT, 64), new ItemStack(Items.WOODEN_SWORD), 1, 100, 0.5F, new EnchantmentData(Enchantments.UNBREAKING, 10), new EnchantmentData(Enchantments.SHARPNESS, 8)),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.PROTECTION, 5),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.FIRE_PROTECTION, 5),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.FEATHER_FALLING, 5),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.BLAST_PROTECTION, 5),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.PROJECTILE_PROTECTION, 5),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.RESPIRATION, 4),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.THORNS, 4),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.DEPTH_STRIDER, 4),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.FROST_WALKER, 3),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.SHARPNESS, 6),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.SMITE, 6),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.BANE_OF_ARTHROPODS, 6),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.KNOCKBACK, 3),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.FIRE_ASPECT, 3),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.LOOTING, 4),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.SWEEPING, 4),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.EFFICIENCY, 6),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.UNBREAKING, 4),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.FORTUNE, 4),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.POWER, 6),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.PUNCH, 3),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.LUCK_OF_THE_SEA, 4),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.LURE, 4),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.LOYALTY, 4),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.IMPALING, 6),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.RIPTIDE, 4),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.QUICK_CHARGE, 4),
            new TradeWithEnchantment(new ItemStack(Items.GOLD_INGOT, 32), new ItemStack(Items.EMERALD, 20), new ItemStack(Items.ENCHANTED_BOOK), 1, 100, 0.5F, Enchantments.PIERCING, 5)
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

        public TradeWithEnchantment(ItemStack buyingStack, ItemStack sellingStack, int maxUses, int experience, float priceMultiplier, EnchantmentData ... enchantments)
        {
            this.buyingStack = buyingStack;
            this.sellingStack = sellingStack;
            this.maxUses = maxUses;
            this.experience = experience;
            this.priceMultiplier = priceMultiplier;
            for(EnchantmentData data : enchantments)
            {
                this.sellingStack.addEnchantment(data.enchantment, data.enchantmentLevel);
            }
        }

        public TradeWithEnchantment(ItemStack buyingStack, ItemStack secondBuyingStack, ItemStack sellingStack, int maxUses, int experience, float priceMultiplier, EnchantmentData ... enchantments)
        {
            this.buyingStack = buyingStack;
            this.secondBuyingStack = secondBuyingStack;
            this.sellingStack = sellingStack;
            this.maxUses = maxUses;
            this.experience = experience;
            this.priceMultiplier = priceMultiplier;
            for(EnchantmentData data : enchantments)
            {
                this.sellingStack.addEnchantment(data.enchantment, data.enchantmentLevel);
            }
        }

        @Nullable
        @Override
        public MerchantOffer getOffer(Entity trader, Random rand)
        {
            this.buyingStack.setCount(MathHelper.clamp(Math.max(this.buyingStack.getCount() - 8, 1) + rand.nextInt(16) + 1, 1, 64));
            if(!this.secondBuyingStack.isEmpty())
            {
                this.secondBuyingStack.setCount(MathHelper.clamp(Math.max(this.secondBuyingStack.getCount() - 8, 1) + rand.nextInt(16) + 1, 1, 64));
            }
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
