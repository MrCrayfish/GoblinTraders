package com.mrcrayfish.goblintraders.datagen;

import com.mrcrayfish.goblintraders.core.ModEntities;
import com.mrcrayfish.goblintraders.trades.TradeCost;
import com.mrcrayfish.goblintraders.trades.TradeRarity;
import com.mrcrayfish.goblintraders.trades.type.BasicTrade;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Author: MrCrayfish
 */
public class GoblinTradeProvider extends TradeProvider
{
    public GoblinTradeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        super(output, lookupProvider);
    }

    @Override
    public void registerTrades(HolderLookup.Provider provider)
    {
        this.registerGoblinTraderTrades(provider);
        this.registerVeinGoblinTraderTrades(provider);
    }

    private void registerGoblinTraderTrades(HolderLookup.Provider provider)
    {
        HolderLookup.RegistryLookup<Enchantment> enchantmentLookup = provider.lookupOrThrow(Registries.ENCHANTMENT);

        /* ************************************************************************************** *
         *                                     COMMON                                             *
         * ************************************************************************************** */

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD))
                .setPaymentStack(new TradeCost(Items.APPLE, 8))
                .setPriceMultiplier(0F)
                .setMaxTrades(24)
                .setExperience(4)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.IRON_INGOT, 2))
                .setPaymentStack(new TradeCost(Items.RAW_IRON))
                .setPriceMultiplier(0F)
                .setMaxTrades(30)
                .setExperience(6)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.GOLD_INGOT, 3))
                .setPaymentStack(new TradeCost(Items.RAW_GOLD, 2))
                .setPriceMultiplier(0F)
                .setMaxTrades(30)
                .setExperience(6)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.COPPER_INGOT, 2))
                .setPaymentStack(new TradeCost(Items.RAW_COPPER, 1))
                .setPriceMultiplier(0F)
                .setMaxTrades(30)
                .setExperience(6)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.COAL))
                .setPaymentStack(new TradeCost(Items.ROTTEN_FLESH, 4))
                .setPriceMultiplier(0F)
                .setMaxTrades(24)
                .setExperience(4)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.FLINT, 2))
                .setPaymentStack(new TradeCost(Items.GRAVEL))
                .setPriceMultiplier(0F)
                .setMaxTrades(32)
                .setExperience(4)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD))
                .setPaymentStack(new TradeCost(Items.COBBLESTONE, 22, 32))
                .setPriceMultiplier(0F)
                .setMaxTrades(128)
                .setExperience(4)
                .build());

        /* ************************************************************************************** *
         *                                     UNCOMMON                                           *
         * ************************************************************************************** */

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.GUNPOWDER, 2))
                .setPaymentStack(new TradeCost(Items.EMERALD))
                .setPriceMultiplier(0F)
                .setMaxTrades(32)
                .setExperience(4)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD, 6))
                .setPaymentStack(new TradeCost(Items.TURTLE_EGG))
                .setPriceMultiplier(0F)
                .setExperience(10)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD, 8))
                .setPaymentStack(new TradeCost(Items.PUFFERFISH_BUCKET))
                .setPriceMultiplier(0F)
                .setMaxTrades(4)
                .setExperience(10)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.SPONGE))
                .setPaymentStack(new TradeCost(Items.EMERALD, 8))
                .setPriceMultiplier(0F)
                .setExperience(10)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.ANVIL))
                .setPaymentStack(new TradeCost(Items.DAMAGED_ANVIL))
                .setSecondaryPaymentStack(new TradeCost(Items.IRON_INGOT, 2))
                .setPriceMultiplier(0F)
                .setMaxTrades(2)
                .setExperience(10)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.ANVIL))
                .setPaymentStack(new TradeCost(Items.CHIPPED_ANVIL))
                .setSecondaryPaymentStack(new TradeCost(Items.IRON_INGOT, 1))
                .setPriceMultiplier(0F)
                .setMaxTrades(2)
                .setExperience(10)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.TERRACOTTA, 2))
                .setPaymentStack(new TradeCost(Items.CLAY))
                .setPriceMultiplier(0F)
                .setMaxTrades(64)
                .setExperience(4)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.BOOK, 2))
                .setPaymentStack(new TradeCost(Items.LEATHER))
                .setSecondaryPaymentStack(new TradeCost(Items.PAPER, 2, 3))
                .setPriceMultiplier(0F)
                .setMaxTrades(32)
                .setExperience(4)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD))
                .setPaymentStack(new TradeCost(Items.AMETHYST_SHARD, 2))
                .setPriceMultiplier(0F)
                .setMaxTrades(20)
                .setExperience(4)
                .build());

        /* ************************************************************************************** *
         *                                      RARE                                              *
         * ************************************************************************************** */

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create()
            .setOfferStack(new ItemStack(Items.EXPERIENCE_BOTTLE))
            .setPaymentStack(new TradeCost(Items.EMERALD, 2, 3))
            .setPriceMultiplier(0F)
            .setMaxTrades(64)
            .setExperience(10)
            .build());

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create()
            .setOfferStack(new ItemStack(Items.NAME_TAG))
            .setPaymentStack(new TradeCost(Items.EMERALD, 16))
            .setSecondaryPaymentStack(new TradeCost(Items.PAPER, 6, 8))
            .setPriceMultiplier(0F)
            .setMaxTrades(2)
            .setExperience(10)
            .build());

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create()
            .setOfferStack(new ItemStack(Items.BLUE_ICE))
            .setPaymentStack(new TradeCost(Items.PACKED_ICE, 4))
            .setPriceMultiplier(0F)
            .setMaxTrades(64)
            .setExperience(10)
            .build());

        Item[] musicDiscs = new Item[]{
            Items.MUSIC_DISC_11,
            Items.MUSIC_DISC_13,
            Items.MUSIC_DISC_CAT,
            Items.MUSIC_DISC_BLOCKS,
            Items.MUSIC_DISC_FAR,
            Items.MUSIC_DISC_MALL,
            Items.MUSIC_DISC_MELLOHI,
            Items.MUSIC_DISC_STAL,
            Items.MUSIC_DISC_STRAD,
            Items.MUSIC_DISC_WARD,
            Items.MUSIC_DISC_WAIT,
            Items.MUSIC_DISC_OTHERSIDE,
            Items.MUSIC_DISC_RELIC,
            Items.MUSIC_DISC_PRECIPICE,
            Items.MUSIC_DISC_CREATOR,
            Items.MUSIC_DISC_CREATOR_MUSIC_BOX
        };
        for(Item disc : musicDiscs)
        {
            this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(disc, 1))
                .setPaymentStack(new TradeCost(Items.EMERALD, 24, 36))
                .setPriceMultiplier(0F)
                .setMaxTrades(1)
                .setExperience(100)
                .build());
        }

        Item[] armorTrims = new Item[]{
            Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE,
            Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE,
            Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE,
            Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE,
            Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE,
            Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE,
            Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE,
            Items.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE,
            Items.FLOW_ARMOR_TRIM_SMITHING_TEMPLATE
        };
        for(Item item : armorTrims)
        {
            this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(item))
                .setPaymentStack(new TradeCost(Items.EMERALD, 24, 36))
                .setPriceMultiplier(0F)
                .setMaxTrades(1)
                .setExperience(100)
                .build());
        }
        /*this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
            .setOfferStack(durable(nameItem(createEnchantedItem(Items.DIAMOND_PICKAXE, 1, mutable -> {
                mutable.set(enchantmentLookup.getOrThrow(Enchantments.EFFICIENCY), 5);
                mutable.set(enchantmentLookup.getOrThrow(Enchantments.FORTUNE), 3);
            }), Component.translatable("custom.goblintraders.goblin_pickaxe").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GREEN))))
            .setPaymentStack(new TradeCost(Items.DRAGON_HEAD, 1))
            .setSecondaryPaymentStack(new TradeCost(Items.DIAMOND_PICKAXE))
            .setPriceMultiplier(0F)
            .setMaxTrades(1)
            .setExperience(1000)
            .build());

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
            .setOfferStack(durable(nameItem(createEnchantedItem(Items.DIAMOND_AXE, 1, mutable -> {
                mutable.set(enchantmentLookup.getOrThrow(Enchantments.EFFICIENCY), 5);
                mutable.set(enchantmentLookup.getOrThrow(Enchantments.SHARPNESS), 5);
            }), Component.translatable("custom.goblintraders.goblin_axe").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GREEN))))
            .setPaymentStack(new TradeCost(Items.DRAGON_HEAD, 1))
            .setSecondaryPaymentStack(new TradeCost(Items.DIAMOND_AXE))
            .setPriceMultiplier(0F)
            .setMaxTrades(1)
            .setExperience(1000)
            .build());

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
            .setOfferStack(durable(nameItem(createEnchantedItem(Items.DIAMOND_SHOVEL, 1, mutable -> {
                mutable.set(enchantmentLookup.getOrThrow(Enchantments.EFFICIENCY), 5);
                mutable.set(enchantmentLookup.getOrThrow(Enchantments.SILK_TOUCH), 1);
            }), Component.translatable("custom.goblintraders.goblin_shovel").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GREEN))))
            .setPaymentStack(new TradeCost(Items.DRAGON_HEAD, 1))
            .setSecondaryPaymentStack(new TradeCost(Items.DIAMOND_SHOVEL))
            .setPriceMultiplier(0F)
            .setMaxTrades(1)
            .setExperience(1000)
            .build());

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
            .setOfferStack(durable(nameItem(createEnchantedItem(Items.DIAMOND_HOE, 1, mutable -> {
                mutable.set(Enchantments.FORTUNE, 3);
            }), Component.translatable("custom.goblintraders.goblin_hoe").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GREEN))))
            .setPaymentStack(new TradeCost(Items.DRAGON_HEAD, 1))
            .setSecondaryPaymentStack(new TradeCost(Items.DIAMOND_HOE))
            .setPriceMultiplier(0F)
            .setMaxTrades(1)
            .setExperience(1000)
            .build());

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
            .setOfferStack(durable(nameItem(createEnchantedItem(Items.DIAMOND_SWORD, 1, mutable -> {
                mutable.set(Enchantments.SHARPNESS, 5);
                mutable.set(Enchantments.SMITE, 5);
                mutable.set(Enchantments.BANE_OF_ARTHROPODS, 5);
                mutable.set(Enchantments.SWEEPING_EDGE, 3);
                mutable.set(Enchantments.KNOCKBACK, 3);
                mutable.set(Enchantments.LOOTING, 3);
            }), Component.translatable("custom.goblintraders.goblin_sword").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GREEN))))
            .setPaymentStack(new TradeCost(Items.DRAGON_HEAD, 1))
            .setSecondaryPaymentStack(new TradeCost(Items.DIAMOND_SWORD))
            .setPriceMultiplier(0F)
            .setMaxTrades(1)
            .setExperience(1000)
            .build());

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
            .setOfferStack(durable(nameItem(createEnchantedItem(Items.BOW, 1, mutable -> {
                mutable.set(Enchantments.POWER, 5);
                mutable.set(Enchantments.PUNCH, 2);
                mutable.set(Enchantments.FLAME, 1);
                mutable.set(Enchantments.INFINITY, 3);
            }), Component.translatable("custom.goblintraders.goblin_bow").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GREEN))))
            .setPaymentStack(new TradeCost(Items.DRAGON_HEAD, 1))
            .setSecondaryPaymentStack(new TradeCost(Items.BOW))
            .setPriceMultiplier(0F)
            .setMaxTrades(1)
            .setExperience(1000)
            .build());

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
            .setOfferStack(durable(nameItem(createEnchantedItem(Items.DIAMOND_HELMET, 1, mutable -> {
                mutable.set(Enchantments.PROTECTION, 4);
                mutable.set(Enchantments.THORNS, 3);
                mutable.set(Enchantments.RESPIRATION, 3);
                mutable.set(Enchantments.AQUA_AFFINITY, 1);
            }), Component.translatable("custom.goblintraders.goblin_helmet").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GREEN))))
            .setPaymentStack(new TradeCost(Items.DRAGON_HEAD, 2))
            .setSecondaryPaymentStack(new TradeCost(Items.DIAMOND_HELMET))
            .setPriceMultiplier(0F)
            .setMaxTrades(1)
            .setExperience(1000)
            .build());

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
            .setOfferStack(durable(nameItem(createEnchantedItem(Items.DIAMOND_CHESTPLATE, 1, mutable -> {
                mutable.set(Enchantments.PROTECTION, 4);
                mutable.set(Enchantments.PROJECTILE_PROTECTION, 3);
                mutable.set(Enchantments.BLAST_PROTECTION, 3);
                mutable.set(Enchantments.FIRE_PROTECTION, 3);
                mutable.set(Enchantments.THORNS, 3);
            }), Component.translatable("custom.goblintraders.goblin_chestplate").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GREEN))))
            .setPaymentStack(new TradeCost(Items.DRAGON_HEAD, 2))
            .setSecondaryPaymentStack(new TradeCost(Items.DIAMOND_CHESTPLATE))
            .setPriceMultiplier(0F)
            .setMaxTrades(1)
            .setExperience(1000)
            .build());

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
            .setOfferStack(durable(nameItem(createEnchantedItem(Items.DIAMOND_LEGGINGS, 1, mutable -> {
                mutable.set(Enchantments.PROTECTION, 4);
                mutable.set(Enchantments.PROJECTILE_PROTECTION, 3);
                mutable.set(Enchantments.BLAST_PROTECTION, 3);
                mutable.set(Enchantments.FIRE_PROTECTION, 3);
                mutable.set(Enchantments.SWIFT_SNEAK, 3);
                mutable.set(Enchantments.THORNS, 3);
            }), Component.translatable("custom.goblintraders.goblin_leggings").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GREEN))))
            .setPaymentStack(new TradeCost(Items.DRAGON_HEAD, 2))
            .setSecondaryPaymentStack(new TradeCost(Items.DIAMOND_LEGGINGS))
            .setPriceMultiplier(0F)
            .setMaxTrades(1)
            .setExperience(1000)
            .build());

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
            .setOfferStack(durable(nameItem(createEnchantedItem(Items.DIAMOND_BOOTS, 1, mutable -> {
                mutable.set(Enchantments.PROTECTION, 4);
                mutable.set(Enchantments.FEATHER_FALLING, 4);
                mutable.set(Enchantments.DEPTH_STRIDER, 3);
                mutable.set(Enchantments.SOUL_SPEED, 3);
                mutable.set(Enchantments.THORNS, 3);
            }), Component.translatable("custom.goblintraders.goblin_boots").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GREEN))))
            .setPaymentStack(new TradeCost(Items.DRAGON_HEAD, 2))
            .setSecondaryPaymentStack(new TradeCost(Items.DIAMOND_BOOTS))
            .setPriceMultiplier(0F)
            .setMaxTrades(1)
            .setExperience(1000)
            .build());*/
    }

    private void registerVeinGoblinTraderTrades(HolderLookup.Provider provider)
    {
        /* ************************************************************************************** *
         *                                     COMMON                                             *
         * ************************************************************************************** */

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD))
                .setPaymentStack(new TradeCost(Items.CARROT, 8))
                .setPriceMultiplier(0F)
                .setMaxTrades(16)
                .setExperience(4)
                .build());
        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.GLOWSTONE_DUST, 4))
                .setPaymentStack(new TradeCost(Items.GLOWSTONE))
                .setPriceMultiplier(0F)
                .setMaxTrades(32)
                .setExperience(4)
                .build());
        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD, 1))
                .setPaymentStack(new TradeCost(Items.NETHERRACK, 48, 64))
                .setPriceMultiplier(0F)
                .setMaxTrades(64)
                .setExperience(4)
                .build());
        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.NETHER_WART, 2))
                .setPaymentStack(new TradeCost(Items.EMERALD))
                .setPriceMultiplier(0F)
                .setExperience(4)
                .build());

        /* ************************************************************************************** *
         *                                     UNCOMMON                                           *
         * ************************************************************************************** */

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
            .setOfferStack(new ItemStack(Items.TOTEM_OF_UNDYING))
            .setPaymentStack(new TradeCost(Items.NETHERITE_INGOT, 2))
            .setPriceMultiplier(0F)
            .setExperience(10)
            .build());

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
            .setOfferStack(new ItemStack(Items.NETHERITE_SCRAP, 5))
            .setPaymentStack(new TradeCost(Items.ANCIENT_DEBRIS, 4))
            .setPriceMultiplier(0F)
            .setMaxTrades(20)
            .setExperience(50)
            .build());

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
            .setOfferStack(new ItemStack(Items.RED_NETHER_BRICKS, 1))
            .setPaymentStack(new TradeCost(Items.NETHER_WART))
            .setSecondaryPaymentStack(new TradeCost(Items.NETHER_BRICK))
            .setPriceMultiplier(0F)
            .setMaxTrades(128)
            .setExperience(4)
            .build());

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
            .setOfferStack(new ItemStack(Items.EMERALD, 2))
            .setPaymentStack(new TradeCost(Items.GOLDEN_CARROT))
            .setPriceMultiplier(0F)
            .setMaxTrades(8)
            .setExperience(4)
            .build());

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
            .setOfferStack(new ItemStack(Items.EXPERIENCE_BOTTLE))
            .setPaymentStack(new TradeCost(Items.NETHER_QUARTZ_ORE))
            .setPriceMultiplier(0F)
            .setMaxTrades(32)
            .setExperience(3)
            .build());

        /* ************************************************************************************** *
         *                                      RARE                                              *
         * ************************************************************************************** */

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create()
            .setOfferStack(new ItemStack(Items.WITHER_SKELETON_SKULL))
            .setPaymentStack(new TradeCost(Items.GOLDEN_CARROT, 8, 24))
            .setExperience(200)
            .build());

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create()
            .setOfferStack(new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE))
            .setPaymentStack(new TradeCost(Items.DIAMOND, 12, 16))
            .setMaxTrades(1)
            .setExperience(100)
            .build());

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create()
            .setOfferStack(new ItemStack(Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE))
            .setPaymentStack(new TradeCost(Items.EMERALD, 24, 32))
            .setMaxTrades(1)
            .setExperience(100)
            .build());

        Item[] musicDiscs = new Item[]{
            Items.MUSIC_DISC_PIGSTEP
        };
        for(Item disc : musicDiscs)
        {
            this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(disc, 1))
                .setPaymentStack(new TradeCost(Items.EMERALD, 24, 32))
                .setPriceMultiplier(0F)
                .setMaxTrades(1)
                .setExperience(100)
                .build());
        }

        /*Potion[] rarePotions = new Potion[] {
            ModPotions.EXTENDED_NIGHT_VISION.get(),
            ModPotions.EXTENDED_INVISIBILITY.get(),
            ModPotions.POWERFUL_JUMP_BOOST.get(),
            ModPotions.EXTENDED_FIRE_RESISTANCE.get(),
            ModPotions.POWERFUL_SPEED.get(),
            ModPotions.EXTENDED_WATER_BREATHING.get(),
            ModPotions.POWERFUL_INSTANT_HEALTH.get(),
            ModPotions.POWERFUL_REGENERATION.get(),
            ModPotions.EXTENDED_SLOW_FALLING.get()
        };
        for(Potion potion : rarePotions)
        {
            ItemStack potionStack = new ItemStack(Items.POTION);
            potionStack.set(DataComponents.POTION_CONTENTS, PotionContents.)
            PotionUtils.setPotion(potionStack, potion);
            ItemStack awkwardPotion = new ItemStack(Items.POTION);
            PotionUtils.setPotion(awkwardPotion, Potions.AWKWARD);
            this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create()
                    .setOfferStack(potionStack)
                    .setPaymentStack(new TradeCost(Items.EMERALD, 15))
                    .setSecondaryPaymentStack(awkwardPotion)
                    .setPriceMultiplier(0.5F)
                    .setMaxTrades(8)
                    .setExperience(100)
                    .build());
        }*/

        /* ************************************************************************************** *
         *                                      EPIC                                              *
         * ************************************************************************************** */

        /*Potion[] epicPotions = new Potion[] {
            ModPotions.HASTE.get(),
            ModPotions.ABSORPTION.get(),
            ModPotions.LEVITATION.get(),
            ModPotions.LUCK.get(),
            ModPotions.DOLPHINS_GRACE.get(),
            ModPotions.POWERFUL_STRENGTH.get()
        };
        for(Potion potion : epicPotions)
        {
            ItemStack potionStack = new ItemStack(Items.POTION);
            PotionUtils.setPotion(potionStack, potion);
            ItemStack awkwardPotion = new ItemStack(Items.POTION);
            PotionUtils.setPotion(awkwardPotion, Potions.AWKWARD);
            this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.EPIC, BasicTrade.Builder.create()
                    .setOfferStack(potionStack)
                    .setPaymentStack(new TradeCost(Items.EMERALD, 25))
                    .setSecondaryPaymentStack(awkwardPotion)
                    .setPriceMultiplier(0.5F)
                    .setMaxTrades(4)
                    .setExperience(100)
                    .build());
        }*/

        // TODO Wait for 1.21
        /*this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
            .setOfferStack(new ItemStack(Items.MACE))
            .setPaymentStack(new TradeCost(Items.DRAGON_HEAD, 5))
            .setSecondaryPaymentStack(new TradeCost(Items.MACE))
            .setPriceMultiplier(0F)
            .setMaxTrades(1)
            .setExperience(20000)
            .addEnchantment(new EnchantmentInstance(Enchantments.DENSITY, 6))
            .addEnchantment(new EnchantmentInstance(Enchantments.BREACH, 6))
            .addEnchantment(new EnchantmentInstance(Enchantments.WIND_BURST, 6))
            .addEnchantment(new EnchantmentInstance(Enchantments.SMITE, 6))
            .addEnchantment(new EnchantmentInstance(Enchantments.UNBREAKING, 6))
            .build());*/

        /*this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
            .setOfferStack(durable(nameItem(createEnchantedItem(Items.NETHERITE_PICKAXE, 1, mutable -> {
                mutable.set(ModEnchantments.ANCIENT_EFFICIENCY.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_FORTUNE.get(), 1);
            }), Component.translatable("custom.goblintraders.ancient_pickaxe").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY))))
            .setPaymentStack(new TradeCost(Items.DRAGON_HEAD, 3))
            .setSecondaryPaymentStack(new TradeCost(Items.NETHERITE_PICKAXE))
            .setPriceMultiplier(0F)
            .setMaxTrades(1)
            .setExperience(1000)
            .build());

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
            .setOfferStack(durable(nameItem(createEnchantedItem(Items.NETHERITE_AXE, 1, mutable -> {
                mutable.set(ModEnchantments.ANCIENT_EFFICIENCY.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_SHARPNESS.get(), 1);
            }), Component.translatable("custom.goblintraders.ancient_axe").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY))))
            .setPaymentStack(new TradeCost(Items.DRAGON_HEAD, 3))
            .setSecondaryPaymentStack(new TradeCost(Items.NETHERITE_AXE))
            .setPriceMultiplier(0F)
            .setMaxTrades(1)
            .setExperience(1000)
            .build());

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
            .setOfferStack(durable(nameItem(createEnchantedItem(Items.NETHERITE_SHOVEL, 1, mutable -> {
                mutable.set(ModEnchantments.ANCIENT_EFFICIENCY.get(), 1);
                mutable.set(Enchantments.SILK_TOUCH, 1);
            }), Component.translatable("custom.goblintraders.ancient_shovel").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY))))
            .setPaymentStack(new TradeCost(Items.DRAGON_HEAD, 3))
            .setSecondaryPaymentStack(new TradeCost(Items.NETHERITE_SHOVEL))
            .setPriceMultiplier(0F)
            .setMaxTrades(1)
            .setExperience(1000)
            .build());

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
            .setOfferStack(durable(nameItem(createEnchantedItem(Items.NETHERITE_HOE, 1, mutable -> {
                mutable.set(ModEnchantments.ANCIENT_FORTUNE.get(), 1);
            }), Component.translatable("custom.goblintraders.ancient_hoe").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY))))
            .setPaymentStack(new TradeCost(Items.DRAGON_HEAD, 3))
            .setSecondaryPaymentStack(new TradeCost(Items.NETHERITE_HOE))
            .setPriceMultiplier(0F)
            .setMaxTrades(1)
            .setExperience(1000)
            .build());

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
            .setOfferStack(durable(nameItem(createEnchantedItem(Items.NETHERITE_SWORD, 1, mutable -> {
                mutable.set(ModEnchantments.ANCIENT_SHARPNESS.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_SMITE.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_BANE_OF_ARTHROPODS.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_SWEEPING_EDGE.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_KNOCKBACK.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_LOOTING.get(), 1);
            }), Component.translatable("custom.goblintraders.ancient_sword").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY))))
            .setPaymentStack(new TradeCost(Items.DRAGON_HEAD, 3))
            .setSecondaryPaymentStack(new TradeCost(Items.NETHERITE_SWORD))
            .setPriceMultiplier(0F)
            .setMaxTrades(1)
            .setExperience(1000)
            .build());

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
            .setOfferStack(durable(nameItem(createEnchantedItem(Items.BOW, 1, mutable -> {
                mutable.set(ModEnchantments.ANCIENT_POWER.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_PUNCH.get(), 1);
                mutable.set(Enchantments.FLAME, 1);
                mutable.set(Enchantments.INFINITY, 1);
            }), Component.translatable("custom.goblintraders.ancient_bow").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY))))
            .setPaymentStack(new TradeCost(Items.DRAGON_HEAD, 3))
            .setSecondaryPaymentStack(new TradeCost(Items.BOW))
            .setPriceMultiplier(0F)
            .setMaxTrades(1)
            .setExperience(1000)
            .build());

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
            .setOfferStack(durable(nameItem(createEnchantedItem(Items.NETHERITE_HELMET, 1, mutable -> {
                mutable.set(ModEnchantments.ANCIENT_PROTECTION.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_THORNS.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_RESPIRATION.get(), 1);
                mutable.set(Enchantments.AQUA_AFFINITY, 1);
            }), Component.translatable("custom.goblintraders.ancient_helmet").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY))))
            .setPaymentStack(new TradeCost(Items.DRAGON_HEAD, 3))
            .setSecondaryPaymentStack(new TradeCost(Items.NETHERITE_HELMET))
            .setPriceMultiplier(0F)
            .setMaxTrades(1)
            .setExperience(1000)
            .build());

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
            .setOfferStack(durable(nameItem(createEnchantedItem(Items.NETHERITE_CHESTPLATE, 1, mutable -> {
                mutable.set(ModEnchantments.ANCIENT_PROTECTION.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_PROJECTILE_PROTECTION.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_BLAST_PROTECTION.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_FIRE_PROTECTION.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_THORNS.get(), 1);
            }), Component.translatable("custom.goblintraders.ancient_chestplate").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY))))
            .setPaymentStack(new TradeCost(Items.DRAGON_HEAD, 3))
            .setSecondaryPaymentStack(new TradeCost(Items.NETHERITE_CHESTPLATE))
            .setPriceMultiplier(0F)
            .setMaxTrades(1)
            .setExperience(1000)
            .build());

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
            .setOfferStack(durable(nameItem(createEnchantedItem(Items.NETHERITE_LEGGINGS, 1, mutable -> {
                mutable.set(ModEnchantments.ANCIENT_PROTECTION.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_PROJECTILE_PROTECTION.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_BLAST_PROTECTION.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_FIRE_PROTECTION.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_SWIFT_SNEAK.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_THORNS.get(), 1);
            }), Component.translatable("custom.goblintraders.ancient_leggings").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY))))
            .setPaymentStack(new TradeCost(Items.DRAGON_HEAD, 3))
            .setSecondaryPaymentStack(new TradeCost(Items.NETHERITE_LEGGINGS))
            .setPriceMultiplier(0F)
            .setMaxTrades(1)
            .setExperience(1000)
            .build());

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
            .setOfferStack(durable(nameItem(createEnchantedItem(Items.NETHERITE_BOOTS, 1, mutable -> {
                mutable.set(ModEnchantments.ANCIENT_PROTECTION.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_FEATHER_FALLING.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_DEPTH_STRIDER.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_SOUL_SPEED.get(), 1);
                mutable.set(ModEnchantments.ANCIENT_THORNS.get(), 1);
            }), Component.translatable("custom.goblintraders.ancient_boots").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GRAY))))
            .setPaymentStack(new TradeCost(Items.DRAGON_HEAD, 3))
            .setSecondaryPaymentStack(new TradeCost(Items.NETHERITE_BOOTS))
            .setPriceMultiplier(0F)
            .setMaxTrades(1)
            .setExperience(1000)
            .build());*/
    }

    private static ItemStack durable(ItemStack stack)
    {
        stack.set(DataComponents.MAX_DAMAGE, stack.getMaxDamage() * 10);
        stack.set(DataComponents.LORE, ItemLore.EMPTY.withLineAdded(
            Component.translatable("custom.goblintraders.durable").withStyle(ChatFormatting.BLUE)
        ));
        return stack;
    }

    private static ItemStack nameItem(ItemStack stack, Component name)
    {
        stack.set(DataComponents.ITEM_NAME, name);
        return stack;
    }

    private static ItemStack createEnchantedItem(ItemLike item, int count, Consumer<ItemEnchantments.Mutable> consumer)
    {
        ItemStack stack = new ItemStack(item, count);
        EnchantmentHelper.setEnchantments(stack, createItemEnchantments(consumer));
        return stack;
    }

    private static TradeCost createEnchantedBookCost(Consumer<ItemEnchantments.Mutable> consumer)
    {
        return new TradeCost(Items.ENCHANTED_BOOK, 1, createComponentPredicate(builder -> {
            builder.expect(DataComponents.STORED_ENCHANTMENTS, createItemEnchantments(consumer));
        }));
    }

    private static DataComponentPredicate createComponentPredicate(Consumer<DataComponentPredicate.Builder> consumer)
    {
        DataComponentPredicate.Builder builder = DataComponentPredicate.builder();
        consumer.accept(builder);
        return builder.build();
    }

    private static ItemEnchantments createItemEnchantments(Consumer<ItemEnchantments.Mutable> consumer)
    {
        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        consumer.accept(mutable);
        return mutable.toImmutable();
    }
}
