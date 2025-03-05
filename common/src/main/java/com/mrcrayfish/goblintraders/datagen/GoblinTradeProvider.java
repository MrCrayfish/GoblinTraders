package com.mrcrayfish.goblintraders.datagen;

import com.mrcrayfish.goblintraders.core.ModEntities;
import com.mrcrayfish.goblintraders.trades.TradeCost;
import com.mrcrayfish.goblintraders.trades.TradeRarity;
import com.mrcrayfish.goblintraders.trades.type.BasicTrade;
import com.mrcrayfish.goblintraders.trades.type.TreasureMapTrade;
import com.mrcrayfish.goblintraders.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.MapDecorations;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;

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
                .setPaymentStack(new TradeCost(Items.EMERALD, 16, 28))
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
                .setPaymentStack(new TradeCost(Items.EMERALD, 16, 28))
                .setPriceMultiplier(0F)
                .setMaxTrades(1)
                .setExperience(100)
                .build());
        }

        Item[] sherds = new Item[]{
            Items.ANGLER_POTTERY_SHERD,
            Items.ARCHER_POTTERY_SHERD,
            Items.ARMS_UP_POTTERY_SHERD,
            Items.BLADE_POTTERY_SHERD,
            Items.BREWER_POTTERY_SHERD,
            Items.BURN_POTTERY_SHERD,
            Items.DANGER_POTTERY_SHERD,
            Items.EXPLORER_POTTERY_SHERD,
            Items.FLOW_POTTERY_SHERD,
            Items.FRIEND_POTTERY_SHERD,
            Items.GUSTER_POTTERY_SHERD,
            Items.HEART_POTTERY_SHERD,
            Items.HEARTBREAK_POTTERY_SHERD,
            Items.HOWL_POTTERY_SHERD,
            Items.MINER_POTTERY_SHERD,
            Items.MOURNER_POTTERY_SHERD,
            Items.PLENTY_POTTERY_SHERD,
            Items.PRIZE_POTTERY_SHERD,
            Items.SCRAPE_POTTERY_SHERD,
            Items.SHEAF_POTTERY_SHERD,
            Items.SHELTER_POTTERY_SHERD,
            Items.SKULL_POTTERY_SHERD,
            Items.SNORT_POTTERY_SHERD
        };
        for(Item item : sherds)
        {
            this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.RARE, BasicTrade.Builder.create()
                    .setOfferStack(new ItemStack(item))
                    .setPaymentStack(new TradeCost(Items.BRICK, 1))
                    .setSecondaryPaymentStack(new TradeCost(Items.EMERALD, 2, 4))
                    .setPriceMultiplier(0F)
                    .setMaxTrades(1)
                    .setExperience(100)
                    .build());
        }

        /* ************************************************************************************** *
         *                                      EPIC                                              *
         * ************************************************************************************** */

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.EPIC, BasicTrade.Builder.create()
                .setOfferStack(tuned(durable(moreArmour(new ItemStack(Items.CHAINMAIL_HELMET), EquipmentSlotGroup.HEAD))))
                .setPaymentStack(new TradeCost(Items.IRON_INGOT, 1, 3))
                .setSecondaryPaymentStack(new TradeCost(Items.EMERALD, 6, 14))
                .setMaxTrades(1)
                .setExperience(150)
                .build()
        );

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.EPIC, BasicTrade.Builder.create()
                .setOfferStack(tuned(durable(moreArmour(new ItemStack(Items.CHAINMAIL_CHESTPLATE), EquipmentSlotGroup.CHEST))))
                .setPaymentStack(new TradeCost(Items.IRON_INGOT, 1, 3))
                .setSecondaryPaymentStack(new TradeCost(Items.EMERALD, 6, 14))
                .setMaxTrades(1)
                .setExperience(150)
                .build()
        );

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.EPIC, BasicTrade.Builder.create()
                .setOfferStack(tuned(durable(moreArmour(new ItemStack(Items.CHAINMAIL_LEGGINGS), EquipmentSlotGroup.LEGS))))
                .setPaymentStack(new TradeCost(Items.IRON_INGOT, 1, 3))
                .setSecondaryPaymentStack(new TradeCost(Items.EMERALD, 6, 14))
                .setMaxTrades(1)
                .setExperience(150)
                .build()
        );

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.EPIC, BasicTrade.Builder.create()
                .setOfferStack(tuned(durable(moreArmour(new ItemStack(Items.CHAINMAIL_BOOTS), EquipmentSlotGroup.FEET))))
                .setPaymentStack(new TradeCost(Items.IRON_INGOT, 1, 3))
                .setSecondaryPaymentStack(new TradeCost(Items.EMERALD, 6, 14))
                .setMaxTrades(1)
                .setExperience(150)
                .build()
        );

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.EPIC, BasicTrade.Builder.create()
                .setOfferStack(tuned(durable(gravity(nameItem(glint(new ItemStack(Items.LEATHER_BOOTS)), Component.translatable("custom.goblintraders.moon_boots"))))))
                .setPaymentStack(new TradeCost(Items.APPLE, 8))
                .setSecondaryPaymentStack(new TradeCost(Items.EMERALD, 8, 32))
                .setMaxTrades(1)
                .setExperience(150)
                .build()
        );

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.EPIC, TreasureMapTrade.Builder.create()
                .setStructure(TagKey.create(Registries.STRUCTURE, Utils.resource("on_mystery_maps")))
                .setMapDecoration(MapDecorationTypes.TARGET_X)
                .setName(Component.translatable("custom.goblintraders.mystery_map"))
                .setPaymentStack(new TradeCost(Items.EMERALD, 8, 14))
                .setMaxTrades(1)
                .setExperience(100)
                .build()
        );

        BuiltInRegistries.POTION.stream().forEach(potion -> {
            if(!potion.getEffects().isEmpty() && potion.getEffects().stream().allMatch(instance -> {
                return instance.getEffect().value().getCategory() == MobEffectCategory.BENEFICIAL && instance.getDuration() <= 3600;
            })) {
                this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.EPIC, BasicTrade.Builder.create()
                    .setOfferStack(PotionContents.createItemStack(Items.POTION, BuiltInRegistries.POTION.wrapAsHolder(potion)))
                    .setPaymentStack(new TradeCost(Items.EMERALD, 2))
                    .setSecondaryPaymentStack(new TradeCost(Items.GLASS_BOTTLE, 1))
                    .setMaxTrades(4)
                    .setExperience(50)
                    .build()
                );
            }
        });

        /* ************************************************************************************** *
         *                                      LEGENDARY                                         *
         * ************************************************************************************** */

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
                .setOfferStack(tuned(durable(increaseReach(breakSpeed(new ItemStack(Items.DIAMOND_PICKAXE))))))
                .setPaymentStack(new TradeCost(Items.NETHER_STAR, 1))
                .setSecondaryPaymentStack(new TradeCost(Items.DRAGON_HEAD, 1))
                .setMaxTrades(1)
                .setExperience(300)
                .build());

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
                .setOfferStack(tuned(durable(increaseReach(breakSpeed(new ItemStack(Items.DIAMOND_SHOVEL))))))
                .setPaymentStack(new TradeCost(Items.NETHER_STAR, 1))
                .setSecondaryPaymentStack(new TradeCost(Items.DRAGON_HEAD, 1))
                .setMaxTrades(1)
                .setExperience(300)
                .build());

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
                .setOfferStack(tuned(durable(increaseReach(moreDamage(breakSpeed(new ItemStack(Items.DIAMOND_AXE)))))))
                .setPaymentStack(new TradeCost(Items.NETHER_STAR, 1))
                .setSecondaryPaymentStack(new TradeCost(Items.DRAGON_HEAD, 1))
                .setMaxTrades(1)
                .setExperience(300)
                .build());

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
                .setOfferStack(tuned(durable(increaseReach(new ItemStack(Items.DIAMOND_HOE)))))
                .setPaymentStack(new TradeCost(Items.NETHER_STAR, 1))
                .setSecondaryPaymentStack(new TradeCost(Items.DRAGON_HEAD, 1))
                .setMaxTrades(1)
                .setExperience(300)
                .build());

        this.addTrade(ModEntities.GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
                .setOfferStack(tuned(durable(new ItemStack(Items.ELYTRA))))
                .setPaymentStack(new TradeCost(Items.ELYTRA, 1))
                .setSecondaryPaymentStack(new TradeCost(Items.EMERALD, 24, 36))
                .setMaxTrades(1)
                .setExperience(500)
                .build());
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
            .setPaymentStack(new TradeCost(Items.DIAMOND, 3, 9))
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

        /* ************************************************************************************** *
         *                                      EPIC                                              *
         * ************************************************************************************** */

        BuiltInRegistries.POTION.stream().forEach(potion -> {
            if(!potion.getEffects().isEmpty() && potion.getEffects().stream().allMatch(instance -> {
                return instance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL && instance.getDuration() <= 3600;
            })) {
                this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.EPIC, BasicTrade.Builder.create()
                        .setOfferStack(PotionContents.createItemStack(Items.POTION, BuiltInRegistries.POTION.wrapAsHolder(potion)))
                        .setPaymentStack(new TradeCost(Items.EMERALD, 2))
                        .setSecondaryPaymentStack(new TradeCost(Items.GLASS_BOTTLE, 1))
                        .setMaxTrades(4)
                        .setExperience(50)
                        .build()
                );
            }
        });

        /* ************************************************************************************** *
         *                                      LEGENDARY                                         *
         * ************************************************************************************** */

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
                .setOfferStack(tuned(durable(oxygenBoost(moreArmour(new ItemStack(Items.NETHERITE_HELMET), EquipmentSlotGroup.HEAD)))))
                .setPaymentStack(new TradeCost(Items.NETHER_STAR, 2))
                .setSecondaryPaymentStack(new TradeCost(Items.DRAGON_HEAD, 1))
                .setMaxTrades(1)
                .setExperience(300)
                .build());

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
                .setOfferStack(tuned(durable(moreArmour(new ItemStack(Items.NETHERITE_CHESTPLATE), EquipmentSlotGroup.CHEST))))
                .setPaymentStack(new TradeCost(Items.NETHER_STAR, 2))
                .setSecondaryPaymentStack(new TradeCost(Items.DRAGON_HEAD, 1))
                .setMaxTrades(1)
                .setExperience(300)
                .build());

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
                .setOfferStack(tuned(durable(moreArmour(new ItemStack(Items.NETHERITE_LEGGINGS), EquipmentSlotGroup.LEGS))))
                .setPaymentStack(new TradeCost(Items.NETHER_STAR, 2))
                .setSecondaryPaymentStack(new TradeCost(Items.DRAGON_HEAD, 1))
                .setMaxTrades(1)
                .setExperience(300)
                .build());

        this.addTrade(ModEntities.VEIN_GOBLIN_TRADER.get(), TradeRarity.LEGENDARY, BasicTrade.Builder.create()
                .setOfferStack(tuned(durable(movementSpeed(moreArmour(bigStep(new ItemStack(Items.NETHERITE_BOOTS)), EquipmentSlotGroup.FEET)))))
                .setPaymentStack(new TradeCost(Items.NETHER_STAR, 2))
                .setSecondaryPaymentStack(new TradeCost(Items.DRAGON_HEAD, 1))
                .setMaxTrades(1)
                .setExperience(300)
                .build());
    }

    private static ItemStack glint(ItemStack stack)
    {
        stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
        return stack;
    }

    private static ItemStack gravity(ItemStack stack)
    {
        ItemAttributeModifiers modifiers = getModifiers(stack);
        modifiers = modifiers.withModifierAdded(Attributes.GRAVITY, new AttributeModifier(
                Utils.resource("goblins_gravity"), -Attributes.GRAVITY.value().getDefaultValue() * 0.835, AttributeModifier.Operation.ADD_VALUE
        ), EquipmentSlotGroup.FEET);
        modifiers = modifiers.withModifierAdded(Attributes.FALL_DAMAGE_MULTIPLIER, new AttributeModifier(
                Utils.resource("goblins_fall_damage"), -Attributes.FALL_DAMAGE_MULTIPLIER.value().getDefaultValue() * 0.835, AttributeModifier.Operation.ADD_VALUE
        ), EquipmentSlotGroup.FEET);
        modifiers = modifiers.withModifierAdded(Attributes.SAFE_FALL_DISTANCE, new AttributeModifier(
                Utils.resource("goblins_safe_fall"), Attributes.SAFE_FALL_DISTANCE.value().getDefaultValue() * 5, AttributeModifier.Operation.ADD_VALUE
        ), EquipmentSlotGroup.FEET);
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, modifiers);
        return stack;
    }

    private static ItemStack tuned(ItemStack stack)
    {
        stack.set(DataComponents.RARITY, Rarity.EPIC);
        stack.set(DataComponents.LORE, stack.getOrDefault(DataComponents.LORE, ItemLore.EMPTY).withLineAdded(
            Component.translatable("custom.goblintraders.tuned").withStyle(ChatFormatting.GREEN)
        ).withLineAdded(Component.translatable("custom.goblintraders.lore").withStyle(ChatFormatting.GRAY)));
        return stack;
    }

    private static ItemStack durable(ItemStack stack)
    {
        stack.set(DataComponents.MAX_DAMAGE, stack.getMaxDamage() * 10);
        return stack;
    }
    
    private static ItemAttributeModifiers getModifiers(ItemStack stack)
    {
        ItemAttributeModifiers modifiers = stack.get(DataComponents.ATTRIBUTE_MODIFIERS);
        if(modifiers != null && !modifiers.modifiers().isEmpty())
        {
            return modifiers;
        }
        return ItemAttributeModifiers.EMPTY;
    }

    private static ItemStack moreDamage(ItemStack stack)
    {
        ItemAttributeModifiers modifiers = getModifiers(stack);
        modifiers = modifiers.withModifierAdded(Attributes.ATTACK_DAMAGE, new AttributeModifier(
                Utils.resource("goblins_damage"), 3, AttributeModifier.Operation.ADD_VALUE
        ), EquipmentSlotGroup.MAINHAND);
        modifiers = modifiers.withModifierAdded(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(
                Utils.resource("goblins_knockback"), 2, AttributeModifier.Operation.ADD_VALUE
        ), EquipmentSlotGroup.MAINHAND);
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, modifiers);
        return stack;
    }

    private static ItemStack breakSpeed(ItemStack stack)
    {
        ItemAttributeModifiers modifiers = getModifiers(stack);
        modifiers = modifiers.withModifierAdded(Attributes.BLOCK_BREAK_SPEED, new AttributeModifier(
                Utils.resource("goblins_break_speed"), 1, AttributeModifier.Operation.ADD_VALUE
        ), EquipmentSlotGroup.MAINHAND);
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, modifiers);
        return stack;
    }

    private static ItemStack increaseReach(ItemStack stack)
    {
        ItemAttributeModifiers modifiers = getModifiers(stack);
        modifiers = modifiers.withModifierAdded(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(
                Utils.resource("goblins_block_range"), 2, AttributeModifier.Operation.ADD_VALUE
        ), EquipmentSlotGroup.MAINHAND);
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, modifiers);
        return stack;
    }

    private static ItemStack movementSpeed(ItemStack stack)
    {
        ItemAttributeModifiers modifiers = getModifiers(stack);
        modifiers = modifiers.withModifierAdded(Attributes.MOVEMENT_SPEED, new AttributeModifier(
                Utils.resource("goblins_movement"), 0.02, AttributeModifier.Operation.ADD_VALUE
        ), EquipmentSlotGroup.FEET);
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, modifiers);
        return stack;
    }

    private static ItemStack moreArmour(ItemStack stack, EquipmentSlotGroup group)
    {
        ItemAttributeModifiers modifiers = getModifiers(stack);
        modifiers = modifiers.withModifierAdded(Attributes.ARMOR, new AttributeModifier(
                Utils.resource("goblins_armour"), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE
        ), group);
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, modifiers);
        return stack;
    }

    private static ItemStack oxygenBoost(ItemStack stack)
    {
        ItemAttributeModifiers modifiers = getModifiers(stack);
        modifiers = modifiers.withModifierAdded(Attributes.OXYGEN_BONUS, new AttributeModifier(
                Utils.resource("goblins_oxygen_boost"), 5, AttributeModifier.Operation.ADD_VALUE
        ), EquipmentSlotGroup.HEAD);
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, modifiers);
        return stack;
    }

    private static ItemStack bigStep(ItemStack stack)
    {
        ItemAttributeModifiers modifiers = getModifiers(stack);
        modifiers = modifiers.withModifierAdded(Attributes.STEP_HEIGHT, new AttributeModifier(
                Utils.resource("goblins_big_step"), 0.5, AttributeModifier.Operation.ADD_VALUE
        ), EquipmentSlotGroup.FEET);
        modifiers = modifiers.withModifierAdded(Attributes.SAFE_FALL_DISTANCE, new AttributeModifier(
                Utils.resource("goblins_fall_distance"), 1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE
        ), EquipmentSlotGroup.FEET);
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, modifiers);
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
