package com.mrcrayfish.goblintraders.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.potion.*;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static final Int2ObjectMap<VillagerTrades.ITrade[]> VEIN_GOBLIN_TRADER = Util.make(() -> {
        VillagerTrades.ITrade[] baseOffers = new VillagerTrades.ITrade[] {
                new ItemsForEmeraldsTrade(Items.CARROT, 1, 10, 10, 1),
                new ItemsForEmeraldsTrade(Items.GLOWSTONE_DUST, 1, 16, 10, 1),
                new ItemsForEmeraldsTrade(Items.NETHER_BRICKS, 1, 8, 20, 1),
                new ItemsForEmeraldsTrade(Items.RED_NETHER_BRICKS, 1, 4, 20, 1),
                new ItemsForEmeraldsTrade(Items.BREWING_STAND, 16, 1, 2, 2),
                new ItemsForEmeraldsTrade(Items.CAULDRON, 16, 1, 2, 2),
                new ItemsForEmeraldsTrade(Items.BLAZE_POWDER, 1, 4, 10, 1),
                new ItemsForEmeraldsTrade(Items.GLASS_BOTTLE, 1, 8, 10, 1),
                new ItemsForEmeraldsTrade(Items.NETHER_WART, 1, 8, 10, 1),
                new ItemsForEmeraldsTrade(Items.REDSTONE, 1, 16, 10, 1),
                new ItemsForEmeraldsTrade(Items.GLOWSTONE_DUST, 1, 16, 10, 1),
                new ItemsForEmeraldsTrade(Items.FERMENTED_SPIDER_EYE, 1, 2, 10, 1),
                new ItemsForEmeraldsTrade(Items.GUNPOWDER, 1, 8, 10, 1),
                new ItemsForEmeraldsTrade(Items.DRAGON_BREATH, 12, 1, 10, 1),
                new ItemsForEmeraldsTrade(Items.SUGAR, 1, 16, 10, 1),
                new ItemsForEmeraldsTrade(Items.RABBIT_FOOT, 1, 2, 10, 1),
                new ItemsForEmeraldsTrade(Items.GLISTERING_MELON_SLICE, 1, 4, 10, 1),
                new ItemsForEmeraldsTrade(Items.SPIDER_EYE, 1, 8, 10, 1),
                new ItemsForEmeraldsTrade(Items.PUFFERFISH, 1, 8, 10, 1),
                new ItemsForEmeraldsTrade(Items.MAGMA_CREAM, 1, 8, 10, 1),
                new ItemsForEmeraldsTrade(Items.GOLDEN_CARROT, 1, 2, 10, 1),
                new ItemsForEmeraldsTrade(Items.GHAST_TEAR, 1, 1, 10, 1),
                new ItemsForEmeraldsTrade(Items.TURTLE_HELMET, 1, 1, 10, 1),
                new ItemsForEmeraldsTrade(Items.PHANTOM_MEMBRANE, 1, 4, 10, 1),
        };
        VillagerTrades.ITrade[] rareOffers = new VillagerTrades.ITrade[] {
                new Trade(new ItemStack(Items.DIAMOND, 20), new ItemStack(Items.DRAGON_HEAD), 1, 100, 0.5F),
                new Trade(new ItemStack(Items.DIAMOND, 32), new ItemStack(Items.NETHER_STAR), 1, 100, 0.5F),
                new Trade(new ItemStack(Items.EMERALD, 16), PotionUtils.appendEffects(new ItemStack(Items.POTION, 1), ImmutableList.of(new EffectInstance(Effects.STRENGTH, 2400, 3))), 5, 100, 0.5F),
                new Trade(new ItemStack(Items.EMERALD, 16), PotionUtils.appendEffects(new ItemStack(Items.POTION, 1), ImmutableList.of(new EffectInstance(Effects.SPEED, 2400, 3))), 5, 100, 0.5F),
                new Trade(new ItemStack(Items.EMERALD, 16), PotionUtils.appendEffects(new ItemStack(Items.POTION, 1), ImmutableList.of(new EffectInstance(Effects.INSTANT_HEALTH, 0, 3))), 5, 100, 0.5F),
                new Trade(new ItemStack(Items.EMERALD, 16), PotionUtils.appendEffects(new ItemStack(Items.POTION, 1), ImmutableList.of(new EffectInstance(Effects.JUMP_BOOST, 2400, 3))), 5, 100, 0.5F),
                new Trade(new ItemStack(Items.EMERALD, 16), PotionUtils.appendEffects(new ItemStack(Items.POTION, 1), ImmutableList.of(new EffectInstance(Effects.NAUSEA, 600))), 5, 100, 0.5F),
                new Trade(new ItemStack(Items.EMERALD, 16), PotionUtils.appendEffects(new ItemStack(Items.POTION, 1), ImmutableList.of(new EffectInstance(Effects.REGENERATION, 2400, 3))), 5, 100, 0.5F),
                new Trade(new ItemStack(Items.EMERALD, 16), PotionUtils.appendEffects(new ItemStack(Items.POTION, 1), ImmutableList.of(new EffectInstance(Effects.RESISTANCE, 2400))), 5, 100, 0.5F),
                new Trade(new ItemStack(Items.EMERALD, 16), PotionUtils.appendEffects(new ItemStack(Items.POTION, 1), ImmutableList.of(new EffectInstance(Effects.FIRE_RESISTANCE, 24000))), 5, 100, 0.5F),
                new Trade(new ItemStack(Items.EMERALD, 16), PotionUtils.appendEffects(new ItemStack(Items.POTION, 1), ImmutableList.of(new EffectInstance(Effects.WATER_BREATHING, 24000))), 5, 100, 0.5F),
                new Trade(new ItemStack(Items.EMERALD, 16), PotionUtils.appendEffects(new ItemStack(Items.POTION, 1), ImmutableList.of(new EffectInstance(Effects.INVISIBILITY, 24000))), 5, 100, 0.5F),
                new Trade(new ItemStack(Items.EMERALD, 16), PotionUtils.appendEffects(new ItemStack(Items.POTION, 1), ImmutableList.of(new EffectInstance(Effects.NIGHT_VISION, 24000))), 5, 100, 0.5F),
                new Trade(new ItemStack(Items.EMERALD, 16), PotionUtils.appendEffects(new ItemStack(Items.POTION, 1), ImmutableList.of(new EffectInstance(Effects.HEALTH_BOOST, 24000, 3))), 5, 100, 0.5F),
                new Trade(new ItemStack(Items.EMERALD, 16), PotionUtils.appendEffects(new ItemStack(Items.POTION, 1), ImmutableList.of(new EffectInstance(Effects.ABSORPTION, 2400))), 5, 100, 0.5F),
                new Trade(new ItemStack(Items.EMERALD, 16), PotionUtils.appendEffects(new ItemStack(Items.POTION, 1), ImmutableList.of(new EffectInstance(Effects.SATURATION, 2400))), 5, 100, 0.5F),
                new Trade(new ItemStack(Items.EMERALD, 16), PotionUtils.appendEffects(new ItemStack(Items.POTION, 1), ImmutableList.of(new EffectInstance(Effects.GLOWING, 2400))), 5, 100, 0.5F),
                new Trade(new ItemStack(Items.EMERALD, 16), PotionUtils.appendEffects(new ItemStack(Items.POTION, 1), ImmutableList.of(new EffectInstance(Effects.LEVITATION, 200))), 5, 100, 0.5F),
                new Trade(new ItemStack(Items.EMERALD, 16), PotionUtils.appendEffects(new ItemStack(Items.POTION, 1), ImmutableList.of(new EffectInstance(Effects.SLOW_FALLING, 9600))), 5, 100, 0.5F),
                new Trade(new ItemStack(Items.EMERALD, 16), PotionUtils.appendEffects(new ItemStack(Items.POTION, 1), ImmutableList.of(new EffectInstance(Effects.DOLPHINS_GRACE, 2400))), 5, 100, 0.5F)
        };
        return getAsIntMap(ImmutableMap.of(0, baseOffers, 1, rareOffers));
    });
    private static Int2ObjectMap<VillagerTrades.ITrade[]> getAsIntMap(ImmutableMap<Integer, VillagerTrades.ITrade[]> map)
    {
        return new Int2ObjectOpenHashMap<>(map);
    }

    public static class TradeWithEnchantment implements VillagerTrades.ITrade
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
            if(this.sellingStack.getItem() == Items.ENCHANTED_BOOK)
            {
                EnchantmentHelper.setEnchantments(ImmutableMap.of(enchantment, level), this.sellingStack);
            }
            else
            {
                this.sellingStack.addEnchantment(enchantment, level);
            }
        }

        public TradeWithEnchantment(ItemStack buyingStack, ItemStack sellingStack, int maxUses, int experience, float priceMultiplier, Enchantment enchantment, int level)
        {
            this.buyingStack = buyingStack;
            this.sellingStack = sellingStack;
            this.maxUses = maxUses;
            this.experience = experience;
            this.priceMultiplier = priceMultiplier;
            if(this.sellingStack.getItem() == Items.ENCHANTED_BOOK)
            {
                EnchantmentHelper.setEnchantments(ImmutableMap.of(enchantment, level), this.sellingStack);
            }
            else
            {
                this.sellingStack.addEnchantment(enchantment, level);
            }
        }

        public TradeWithEnchantment(ItemStack buyingStack, ItemStack secondBuyingStack, ItemStack sellingStack, int maxUses, int experience, float priceMultiplier, Enchantment enchantment, int level)
        {
            this.buyingStack = buyingStack;
            this.secondBuyingStack = secondBuyingStack;
            this.sellingStack = sellingStack;
            this.maxUses = maxUses;
            this.experience = experience;
            this.priceMultiplier = priceMultiplier;
            if(this.sellingStack.getItem() == Items.ENCHANTED_BOOK)
            {
                EnchantmentHelper.setEnchantments(ImmutableMap.of(enchantment, level), this.sellingStack);
            }
            else
            {
                this.sellingStack.addEnchantment(enchantment, level);
            }
        }

        public TradeWithEnchantment(ItemStack buyingStack, ItemStack sellingStack, int maxUses, int experience, float priceMultiplier, EnchantmentData ... enchantments)
        {
            this.buyingStack = buyingStack;
            this.sellingStack = sellingStack;
            this.maxUses = maxUses;
            this.experience = experience;
            this.priceMultiplier = priceMultiplier;
            if(this.sellingStack.getItem() == Items.ENCHANTED_BOOK)
            {
                EnchantmentHelper.setEnchantments(Stream.of(enchantments).collect(Collectors.toMap(o -> o.enchantment, e -> e.enchantmentLevel)), this.sellingStack);
            }
            else
            {
                for(EnchantmentData data : enchantments)
                {
                    this.sellingStack.addEnchantment(data.enchantment, data.enchantmentLevel);
                }
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

    public static class ItemsForEmeraldsTrade implements VillagerTrades.ITrade
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

        @Override
        public MerchantOffer getOffer(Entity trader, Random rand)
        {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.price), new ItemStack(this.offerStack.getItem(), this.offerStackCount), this.maxUses, this.experience, this.priceMultiplier);
        }
    }

    static class Trade implements VillagerTrades.ITrade
    {
        private final ItemStack firstBuyStack;
        private final ItemStack secondBuyStack;
        private final ItemStack sellingStack;
        private final int maxUses;
        private final int experience;
        private final float priceMultiplier;

        public Trade(ItemStack firstBuyStack, ItemStack sellingStack, int maxUses, int experience, float priceMultiplier)
        {
            this.firstBuyStack = firstBuyStack;
            this.secondBuyStack = ItemStack.EMPTY;
            this.sellingStack = sellingStack;
            this.maxUses = maxUses;
            this.experience = experience;
            this.priceMultiplier = priceMultiplier;
        }

        public Trade(ItemStack firstBuyStack, ItemStack secondBuyStack, ItemStack sellingStack, int maxUses, int experience, float priceMultiplier)
        {
            this.firstBuyStack = firstBuyStack;
            this.secondBuyStack = secondBuyStack;
            this.sellingStack = sellingStack;
            this.maxUses = maxUses;
            this.experience = experience;
            this.priceMultiplier = priceMultiplier;
        }

        @Override
        public MerchantOffer getOffer(Entity trader, Random rand)
        {
            this.firstBuyStack.setCount(MathHelper.clamp(Math.max(this.firstBuyStack.getCount() - 4, 1) + rand.nextInt(8) + 1, 1, 64));
            if(!this.secondBuyStack.isEmpty())
            {
                this.secondBuyStack.setCount(MathHelper.clamp(Math.max(this.secondBuyStack.getCount() - 4, 1) + rand.nextInt(8) + 1, 1, 64));
            }
            return new MerchantOffer(this.firstBuyStack, this.secondBuyStack, this.sellingStack, this.maxUses, this.experience, this.priceMultiplier);
        }
    }
}
