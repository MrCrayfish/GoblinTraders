package com.mrcrayfish.goblintraders.datagen;

import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.goblintraders.init.ModEntities;
import com.mrcrayfish.goblintraders.init.ModPotions;
import com.mrcrayfish.goblintraders.trades.TradeRarity;
import com.mrcrayfish.goblintraders.trades.type.BasicTrade;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Objects;

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
        /* ************************************************************************************** *
         *                                     COMMON                                             *
         * ************************************************************************************** */

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
                .setOfferStack(new ItemStack(Items.EMERALD))
                .setPaymentStack(new ItemStack(Items.COBBLESTONE, 32))
                .setPriceMultiplier(0F)
                .setMaxTrades(128)
                .setExperience(15)
                .build());

        /* ************************************************************************************** *
         *                                     UNCOMMON                                           *
         * ************************************************************************************** */

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.GUNPOWDER, 2))
                .setPaymentStack(new ItemStack(Items.EMERALD))
                .setPriceMultiplier(0F)
                .setMaxTrades(32)
                .setExperience(20)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD, 6))
                .setPaymentStack(new ItemStack(Items.TURTLE_EGG))
                .setPriceMultiplier(0F)
                .setExperience(200)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD, 8))
                .setPaymentStack(new ItemStack(Items.PUFFERFISH_BUCKET))
                .setPriceMultiplier(0F)
                .setMaxTrades(4)
                .setExperience(200)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.SPONGE))
                .setPaymentStack(new ItemStack(Items.EMERALD, 8))
                .setPriceMultiplier(0F)
                .setExperience(50)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.CHIPPED_ANVIL))
                .setPaymentStack(new ItemStack(Items.DAMAGED_ANVIL))
                .setSecondaryPaymentStack(new ItemStack(Items.IRON_INGOT, 4))
                .setPriceMultiplier(0F)
                .setMaxTrades(2)
                .setExperience(50)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.ANVIL))
                .setPaymentStack(new ItemStack(Items.CHIPPED_ANVIL))
                .setSecondaryPaymentStack(new ItemStack(Items.IRON_INGOT, 4))
                .setPriceMultiplier(0F)
                .setMaxTrades(2)
                .setExperience(50)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.TERRACOTTA, 2))
                .setPaymentStack(new ItemStack(Items.CLAY))
                .setPriceMultiplier(0F)
                .setMaxTrades(64)
                .setExperience(20)
                .build());
        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.BOOK, 2))
                .setPaymentStack(new ItemStack(Items.LEATHER))
                .setSecondaryPaymentStack(new ItemStack(Items.PAPER, 3))
                .setPriceMultiplier(0F)
                .setMaxTrades(32)
                .setExperience(20)
                .build());

        /* ************************************************************************************** *
         *                                      RARE                                              *
         * ************************************************************************************** */

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.EXPERIENCE_BOTTLE))
                .setPaymentStack(new ItemStack(Items.EMERALD, 3))
                .setPriceMultiplier(0F)
                .setMaxTrades(64)
                .setExperience(50)
                .build());

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.NAME_TAG))
                .setPaymentStack(new ItemStack(Items.EMERALD, 24))
                .setSecondaryPaymentStack(new ItemStack(Items.PAPER, 8))
                .setPriceMultiplier(0F)
                .setMaxTrades(2)
                .setExperience(40)
                .build());

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.BLUE_ICE))
                .setPaymentStack(new ItemStack(Items.PACKED_ICE, 4))
                .setPriceMultiplier(0F)
                .setMaxTrades(64)
                .setExperience(40)
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

        Item[] creeperMusicDiscs = new Item[]{Items.MUSIC_DISC_CAT, Items.MUSIC_DISC_BLOCKS, Items.MUSIC_DISC_CHIRP, Items.MUSIC_DISC_MELLOHI, Items.MUSIC_DISC_STAL};
        for(Item disc : creeperMusicDiscs)
        {
            this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create().setOfferStack(new ItemStack(disc, 1)).setPaymentStack(new ItemStack(Items.EMERALD, 32)).setPriceMultiplier(0F).setMaxTrades(1).setExperience(100).build());
        }

        EnchantmentData[] pickaxeEnchantments = new EnchantmentData[] {new EnchantmentData(Enchantments.EFFICIENCY, 5), new EnchantmentData(Enchantments.UNBREAKING, 3), new EnchantmentData(Enchantments.FORTUNE, 3)};
        for(EnchantmentData pickaxeEnchant : pickaxeEnchantments)
        {
            ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
            EnchantmentHelper.setEnchantments(ImmutableMap.of(pickaxeEnchant.enchantment, pickaxeEnchant.enchantmentLevel), enchantedBook);
            this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create().setOfferStack(new ItemStack(Items.DIAMOND_PICKAXE)).setPaymentStack(new ItemStack(Items.DIAMOND_PICKAXE)).setSecondaryPaymentStack(enchantedBook).setPriceMultiplier(0F).setMaxTrades(1).setExperience(200).addEnchantment(new EnchantmentData(pickaxeEnchant.enchantment, pickaxeEnchant.enchantmentLevel + 1)).build());
        }

        EnchantmentData[] axeEnchantments = new EnchantmentData[] {new EnchantmentData(Enchantments.EFFICIENCY, 5), new EnchantmentData(Enchantments.UNBREAKING, 3)};
        for(EnchantmentData axeEnchant : axeEnchantments)
        {
            ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
            EnchantmentHelper.setEnchantments(ImmutableMap.of(axeEnchant.enchantment, axeEnchant.enchantmentLevel), enchantedBook);
            this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create().setOfferStack(new ItemStack(Items.DIAMOND_AXE)).setPaymentStack(new ItemStack(Items.DIAMOND_AXE)).setSecondaryPaymentStack(enchantedBook).setPriceMultiplier(0F).setMaxTrades(1).setExperience(200).addEnchantment(new EnchantmentData(axeEnchant.enchantment, axeEnchant.enchantmentLevel + 1)).build());
        }

        EnchantmentData[] shovelEnchantments = new EnchantmentData[] {new EnchantmentData(Enchantments.EFFICIENCY, 5), new EnchantmentData(Enchantments.UNBREAKING, 3)};
        for(EnchantmentData shovelEnchant : shovelEnchantments)
        {
            ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
            EnchantmentHelper.setEnchantments(ImmutableMap.of(shovelEnchant.enchantment, shovelEnchant.enchantmentLevel), enchantedBook);
            this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create().setOfferStack(new ItemStack(Items.DIAMOND_SHOVEL)).setPaymentStack(new ItemStack(Items.DIAMOND_SHOVEL)).setSecondaryPaymentStack(enchantedBook).setPriceMultiplier(0F).setMaxTrades(1).setExperience(200).addEnchantment(new EnchantmentData(shovelEnchant.enchantment, shovelEnchant.enchantmentLevel + 1)).build());
        }

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create().setOfferStack(new ItemStack(Items.DIAMOND_PICKAXE)).setPaymentStack(new ItemStack(Items.DRAGON_HEAD, 5)).setSecondaryPaymentStack(new ItemStack(Items.DIAMOND_PICKAXE)).setPriceMultiplier(0F).setMaxTrades(1).setExperience(500).addEnchantment(new EnchantmentData(Enchantments.FORTUNE, 5)).addEnchantment(new EnchantmentData(Enchantments.UNBREAKING, 5)).build());
    }

    private void registerVeinGoblinTraderTrades()
    {
        /* ************************************************************************************** *
         *                                     COMMON                                             *
         * ************************************************************************************** */

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD))
                .setPaymentStack(new ItemStack(Items.CARROT))
                .setPriceMultiplier(0F)
                .setMaxTrades(24)
                .setExperience(20)
                .build());
        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.GLOWSTONE_DUST, 4))
                .setPaymentStack(new ItemStack(Items.GLOWSTONE))
                .setPriceMultiplier(0F)
                .setMaxTrades(32)
                .setExperience(20)
                .build());
        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.EMERALD, 1))
                .setPaymentStack(new ItemStack(Items.NETHERRACK, 64))
                .setPriceMultiplier(0F)
                .setMaxTrades(64)
                .setExperience(20)
                .build());
        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.COMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.NETHER_WART, 2))
                .setPaymentStack(new ItemStack(Items.EMERALD))
                .setPriceMultiplier(0F)
                .setExperience(20)
                .build());

        /* ************************************************************************************** *
         *                                     UNCOMMON                                           *
         * ************************************************************************************** */

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.TOTEM_OF_UNDYING))
                .setPaymentStack(new ItemStack(Items.NETHERITE_INGOT, 2))
                .setPriceMultiplier(0F)
                .setExperience(200)
                .build());
        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.NETHERITE_SCRAP, 5))
                .setPaymentStack(new ItemStack(Items.ANCIENT_DEBRIS, 4))
                .setPriceMultiplier(0F)
                .setMaxTrades(20)
                .setExperience(100)
                .build());
        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.UNCOMMON, BasicTrade.Builder.create()
                .setOfferStack(new ItemStack(Items.RED_NETHER_BRICKS, 1))
                .setPaymentStack(new ItemStack(Items.NETHER_WART))
                .setSecondaryPaymentStack(new ItemStack(Items.NETHER_BRICK))
                .setPriceMultiplier(0F)
                .setMaxTrades(128)
                .setExperience(20)
                .build());

        /* ************************************************************************************** *
         *                                      RARE                                              *
         * ************************************************************************************** */

        EnchantmentData[] swordEnchantments = new EnchantmentData[] {new EnchantmentData(Enchantments.SHARPNESS, 5), new EnchantmentData(Enchantments.LOOTING, 3), new EnchantmentData(Enchantments.SWEEPING, 3), new EnchantmentData(Enchantments.FIRE_ASPECT, 2), new EnchantmentData(Enchantments.KNOCKBACK, 2), new EnchantmentData(Enchantments.BANE_OF_ARTHROPODS, 5), new EnchantmentData(Enchantments.SMITE, 5)};
        for(EnchantmentData swordEnchant : swordEnchantments)
        {
            ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
            EnchantmentHelper.setEnchantments(ImmutableMap.of(swordEnchant.enchantment, swordEnchant.enchantmentLevel), enchantedBook);
            this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create().setOfferStack(new ItemStack(Items.DIAMOND_SWORD)).setPaymentStack(new ItemStack(Items.DIAMOND_SWORD)).setSecondaryPaymentStack(enchantedBook).setPriceMultiplier(0F).setMaxTrades(1).setExperience(100).addEnchantment(new EnchantmentData(swordEnchant.enchantment, swordEnchant.enchantmentLevel + 1)).build());
        }

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

        Potion[] rarePotions = new Potion[] {
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
            PotionUtils.addPotionToItemStack(potionStack, potion);
            ItemStack awkwardPotion = new ItemStack(Items.POTION);
            PotionUtils.addPotionToItemStack(awkwardPotion, Potions.AWKWARD);
            this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create()
                    .setOfferStack(potionStack)
                    .setPaymentStack(new ItemStack(Items.EMERALD, 15))
                    .setSecondaryPaymentStack(awkwardPotion)
                    .setPriceMultiplier(0.5F)
                    .setMaxTrades(8)
                    .setExperience(1000)
                    .build());
        }

        //TODO not sure if this will make it into the final version.
        /*ImmutableMap<EntityType<?>, Item> spawnerMap = ImmutableMap.of(EntityType.ZOMBIE, Items.ZOMBIE_HEAD, EntityType.SKELETON, Items.SKELETON_SKULL, EntityType.CREEPER, Items.CREEPER_HEAD);
        spawnerMap.forEach((entityType, item) ->
        {
            MobSpawnerTileEntity mobSpawner = new MobSpawnerTileEntity();
            mobSpawner.getSpawnerBaseLogic().setEntityType(entityType);
            CompoundNBT tileTag = mobSpawner.write(new CompoundNBT());
            ResourceLocation id = Objects.requireNonNull(TileEntityType.getId(mobSpawner.getType()));
            tileTag.putString("id", id.toString());
            mobSpawner.getSpawnerBaseLogic().write(tileTag);
            tileTag.putShort("MaxNearbyEntities", (short) 3);
            tileTag.putShort("RequiredPlayerRange", (short) 32);
            tileTag.putShort("SpawnCount", (short) 2);
            ItemStack stack = new ItemStack(Items.SPAWNER);
            stack.getOrCreateTag().put("BlockEntityTag", tileTag);
            CompoundNBT display = new CompoundNBT();
            ListNBT loreList = new ListNBT();
            loreList.add(StringNBT.valueOf(ITextComponent.Serializer.toJson(new TranslationTextComponent(String.format("mob_spawner.type.%s", entityType.getRegistryName().getPath())))));
            display.put("Lore", loreList);
            stack.getOrCreateTag().put("display", display);
            this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create()
                    .setOfferStack(stack)
                    .setPaymentStack(new ItemStack(item))
                    .setSecondaryPaymentStack(new ItemStack(Items.NETHER_STAR))
                    .setPriceMultiplier(0.5F)
                    .setMaxTrades(1)
                    .setExperience(1000)
                    .build());
        });*/

        /* ************************************************************************************** *
         *                                      EPIC                                              *
         * ************************************************************************************** */

        Potion[] epicPotions = new Potion[] {
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
            PotionUtils.addPotionToItemStack(potionStack, potion);
            ItemStack awkwardPotion = new ItemStack(Items.POTION);
            PotionUtils.addPotionToItemStack(awkwardPotion, Potions.AWKWARD);
            this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.EPIC, BasicTrade.Builder.create()
                    .setOfferStack(potionStack)
                    .setPaymentStack(new ItemStack(Items.EMERALD, 25))
                    .setSecondaryPaymentStack(awkwardPotion)
                    .setPriceMultiplier(0.5F)
                    .setMaxTrades(4)
                    .setExperience(1000)
                    .build());
        }

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create().setOfferStack(new ItemStack(Items.DIAMOND_SWORD)).setPaymentStack(new ItemStack(Items.DRAGON_HEAD, 5)).setSecondaryPaymentStack(new ItemStack(Items.DIAMOND_SWORD)).setPriceMultiplier(0F).setMaxTrades(1).setExperience(500).addEnchantment(new EnchantmentData(Enchantments.SHARPNESS, 7)).addEnchantment(new EnchantmentData(Enchantments.UNBREAKING, 7)).build());
    }
}
