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
        this.registerGoblinTraderTrades();
        this.registerVeinGoblinTraderTrades();
    }

    private void registerGoblinTraderTrades()
    {
        // COMMON
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD))
                .setPaymentStack(new ItemStack(Items.APPLE))
                .setPriceMultiplier(0F)
                .setMaxTrades(24)
                .setExperience(20)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.IRON_INGOT, 2))
                .setPaymentStack(new ItemStack(Items.IRON_ORE))
                .setPriceMultiplier(0F)
                .setMaxTrades(30)
                .setExperience(20)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.GOLD_INGOT, 3))
                .setPaymentStack(new ItemStack(Items.GOLD_ORE, 2))
                .setPriceMultiplier(0F)
                .setMaxTrades(30)
                .setExperience(30)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.COAL))
                .setPaymentStack(new ItemStack(Items.ROTTEN_FLESH, 4))
                .setPriceMultiplier(0F)
                .setMaxTrades(24)
                .setExperience(15)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.FLINT, 2))
                .setPaymentStack(new ItemStack(Items.GRAVEL))
                .setPriceMultiplier(0F)
                .setMaxTrades(32)
                .setExperience(15)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.GUNPOWDER, 2))
                .setPaymentStack(new ItemStack(Items.EMERALD))
                .setPriceMultiplier(0F)
                .setMaxTrades(32)
                .setExperience(20)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.NAME_TAG))
                .setPaymentStack(new ItemStack(Items.EMERALD, 24))
                .setSecondaryPaymentStack(new ItemStack(Items.PAPER, 8))
                .setPriceMultiplier(0F)
                .setMaxTrades(2)
                .setExperience(40)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD, 8))
                .setPaymentStack(new ItemStack(Items.TURTLE_EGG))
                .setPriceMultiplier(0F)
                .setExperience(200)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD, 10))
                .setPaymentStack(new ItemStack(Items.PUFFERFISH_BUCKET))
                .setPriceMultiplier(0F)
                .setMaxTrades(4)
                .setExperience(200)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.SPONGE))
                .setPaymentStack(new ItemStack(Items.EMERALD, 8))
                .setPriceMultiplier(0F)
                .setExperience(50)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.CHIPPED_ANVIL))
                .setPaymentStack(new ItemStack(Items.DAMAGED_ANVIL))
                .setSecondaryPaymentStack(new ItemStack(Items.IRON_INGOT, 4))
                .setPriceMultiplier(0F)
                .setMaxTrades(2)
                .setExperience(50)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.ANVIL))
                .setPaymentStack(new ItemStack(Items.CHIPPED_ANVIL))
                .setSecondaryPaymentStack(new ItemStack(Items.IRON_INGOT, 4))
                .setPriceMultiplier(0F)
                .setMaxTrades(2)
                .setExperience(50)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD))
                .setPaymentStack(new ItemStack(Items.COBBLESTONE, 16))
                .setPriceMultiplier(0F)
                .setMaxTrades(128)
                .setExperience(15)
                .build());

        // RARE
        ItemStack fortuneBook = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantmentHelper.setEnchantments(ImmutableMap.of(Enchantments.FORTUNE, 3), fortuneBook);
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.DIAMOND_PICKAXE))
                .setPaymentStack(new ItemStack(Items.DIAMOND_PICKAXE))
                .setSecondaryPaymentStack(fortuneBook)
                .setPriceMultiplier(0.5F)
                .setMaxTrades(2)
                .setExperience(100)
                .addEnchantment(new EnchantmentData(Enchantments.FORTUNE, 4))
                .build());

        ItemStack luckOfTheSeaBook = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantmentHelper.setEnchantments(ImmutableMap.of(Enchantments.LUCK_OF_THE_SEA, 3), luckOfTheSeaBook);
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.FISHING_ROD))
                .setPaymentStack(new ItemStack(Items.FISHING_ROD))
                .setSecondaryPaymentStack(luckOfTheSeaBook)
                .setPriceMultiplier(0.5F)
                .setMaxTrades(2)
                .setExperience(100)
                .addEnchantment(new EnchantmentData(Enchantments.LUCK_OF_THE_SEA, 5))
                .build());

        ItemStack lureBook = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantmentHelper.setEnchantments(ImmutableMap.of(Enchantments.LURE, 3), lureBook);
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.FISHING_ROD))
                .setPaymentStack(new ItemStack(Items.FISHING_ROD))
                .setSecondaryPaymentStack(lureBook)
                .setPriceMultiplier(0.5F)
                .setMaxTrades(2)
                .setExperience(100)
                .addEnchantment(new EnchantmentData(Enchantments.LURE, 5))
                .build());

        Item[] creeperMusicDiscs = new Item[]{Items.MUSIC_DISC_13, Items.MUSIC_DISC_CAT, Items.MUSIC_DISC_BLOCKS, Items.MUSIC_DISC_CHIRP, Items.MUSIC_DISC_FAR, Items.MUSIC_DISC_MALL, Items.MUSIC_DISC_MELLOHI, Items.MUSIC_DISC_STAL, Items.MUSIC_DISC_STRAD, Items.MUSIC_DISC_WARD, Items.MUSIC_DISC_11, Items.MUSIC_DISC_WAIT};
        for(Item disc : creeperMusicDiscs)
        {
            this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create().setOfferStack(new ItemStack(disc, 1)).setPaymentStack(new ItemStack(Items.EMERALD, 20)).setPriceMultiplier(0F).setMaxTrades(1).setExperience(100).build());
        }

        Item[] diamondToolSet = new Item[] {Items.DIAMOND_PICKAXE, Items.DIAMOND_AXE, Items.DIAMOND_SHOVEL};
        EnchantmentData[] toolEnchantments = new EnchantmentData[] {new EnchantmentData(Enchantments.EFFICIENCY, 5), new EnchantmentData(Enchantments.UNBREAKING, 3)};
        for(Item piece : diamondToolSet)
        {
            for(EnchantmentData armorEnchant : toolEnchantments)
            {
                ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
                EnchantmentHelper.setEnchantments(ImmutableMap.of(armorEnchant.enchantment, armorEnchant.enchantmentLevel), enchantedBook);
                this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create().setOfferStack(new ItemStack(piece)).setPaymentStack(new ItemStack(piece)).setSecondaryPaymentStack(enchantedBook).setPriceMultiplier(0F).setMaxTrades(2).setExperience(100).addEnchantment(new EnchantmentData(armorEnchant.enchantment, armorEnchant.enchantmentLevel + 1)).build());
            }
        }

        EnchantmentData[] swordEnchantments = new EnchantmentData[] {new EnchantmentData(Enchantments.SHARPNESS, 5), new EnchantmentData(Enchantments.LOOTING, 3), new EnchantmentData(Enchantments.SWEEPING, 3), new EnchantmentData(Enchantments.FIRE_ASPECT, 2), new EnchantmentData(Enchantments.KNOCKBACK, 2), new EnchantmentData(Enchantments.BANE_OF_ARTHROPODS, 5)};
        for(EnchantmentData swordEnchant : swordEnchantments)
        {
            ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
            EnchantmentHelper.setEnchantments(ImmutableMap.of(swordEnchant.enchantment, swordEnchant.enchantmentLevel), enchantedBook);
            this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create().setOfferStack(new ItemStack(Items.DIAMOND_SWORD)).setPaymentStack(new ItemStack(Items.DIAMOND_SWORD)).setSecondaryPaymentStack(enchantedBook).setPriceMultiplier(0F).setMaxTrades(1).setExperience(100).addEnchantment(new EnchantmentData(swordEnchant.enchantment, swordEnchant.enchantmentLevel + 1)).build());
        }

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create().setOfferStack(new ItemStack(Items.DIAMOND_PICKAXE)).setPaymentStack(new ItemStack(Items.DRAGON_HEAD, 5)).setSecondaryPaymentStack(new ItemStack(Items.DIAMOND_PICKAXE)).setPriceMultiplier(0F).setMaxTrades(1).setExperience(100).addEnchantment(new EnchantmentData(Enchantments.FORTUNE, 5)).addEnchantment(new EnchantmentData(Enchantments.UNBREAKING, 5)).addEnchantment(new EnchantmentData(Enchantments.VANISHING_CURSE, 1)).build());
    }

    private void registerVeinGoblinTraderTrades()
    {
        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.TOTEM_OF_UNDYING))
                .setPaymentStack(new ItemStack(Items.NETHERITE_INGOT, 2))
                .setPriceMultiplier(0F)
                .setExperience(200)
                .build());
        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.NETHERITE_SCRAP, 5))
                .setPaymentStack(new ItemStack(Items.ANCIENT_DEBRIS, 4))
                .setPriceMultiplier(0F)
                .setMaxTrades(20)
                .setExperience(100)
                .build());

        Item[] diamondArmorSet = new Item[] {Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS};
        EnchantmentData[] armorEnchantments = new EnchantmentData[] {new EnchantmentData(Enchantments.BLAST_PROTECTION, 4), new EnchantmentData(Enchantments.FIRE_PROTECTION, 4), new EnchantmentData(Enchantments.PROJECTILE_PROTECTION, 4), new EnchantmentData(Enchantments.PROTECTION, 4), new EnchantmentData(Enchantments.UNBREAKING, 3)};
        for(Item piece : diamondArmorSet)
        {
            for(EnchantmentData armorEnchant : armorEnchantments)
            {
                ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
                EnchantmentHelper.setEnchantments(ImmutableMap.of(armorEnchant.enchantment, armorEnchant.enchantmentLevel), enchantedBook);
                this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create().setOfferStack(new ItemStack(piece)).setPaymentStack(new ItemStack(piece)).setSecondaryPaymentStack(enchantedBook).setPriceMultiplier(0F).setMaxTrades(1).setExperience(100).addEnchantment(new EnchantmentData(armorEnchant.enchantment, armorEnchant.enchantmentLevel + 1)).build());
            }
        }
    }
}
