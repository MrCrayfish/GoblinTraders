package com.mrcrayfish.goblintraders.trades;

import com.google.common.collect.ImmutableList;
import com.mrcrayfish.goblintraders.loot_functions.IncreaseDurabilityFunction;
import com.mrcrayfish.goblintraders.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.SwingAnimation;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.minecraft.world.item.trading.VillagerTrades.register;

public final class GoblinTrades
{
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_COMMON_APPLES_FOR_EMERALD = createKey("goblin_trader/common/apples_for_emerald");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_COMMON_RAW_IRON_FOR_IRON_INGOTS = createKey("goblin_trader/common/raw_iron_for_iron_ingots");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_COMMON_RAW_GOLD_FOR_GOLD_INGOTS = createKey("goblin_trader/common/raw_gold_for_gold_ingots");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_COMMON_RAW_COPPER_FOR_COPPER_INGOTS = createKey("goblin_trader/common/raw_copper_for_copper_ingots");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_COMMON_ROTTEN_FLESH_FOR_COAL = createKey("goblin_trader/common/rotten_flesh_for_coal");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_COMMON_GRAVEL_FOR_FLINT = createKey("goblin_trader/common/gravel_for_flint");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_COMMON_COBBLESTONE_FOR_EMERALDS = createKey("goblin_trader/common/cobblestone_for_emeralds");

    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_UNCOMMON_EMERALD_FOR_GUNPOWDER = createKey("goblin_trader/uncommon/emerald_for_gunpowder");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_UNCOMMON_TURTLE_EGG_FOR_EMERALDS = createKey("goblin_trader/uncommon/turtle_egg_for_emeralds");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_UNCOMMON_PUFFERFISH_BUCKET_FOR_EMERALDS = createKey("goblin_trader/uncommon/pufferfish_bucket_for_emeralds");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_UNCOMMON_EMERALDS_FOR_SPONGE = createKey("goblin_trader/uncommon/emeralds_for_sponge");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_UNCOMMON_DAMAGED_ANVIL_AND_IRON_INGOTS_FOR_ANVIL = createKey("goblin_trader/uncommon/damaged_anvil_and_iron_ingots_for_anvil");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_UNCOMMON_CHIPPED_ANVIL_AND_IRON_INGOTS_FOR_ANVIL = createKey("goblin_trader/uncommon/chipped_anvil_and_iron_ingots_for_anvil");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_UNCOMMON_CLAY_FOR_TERRACOTTA = createKey("goblin_trader/uncommon/clay_for_terracotta");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_UNCOMMON_LEATHER_AND_PAPER_FOR_BOOKS = createKey("goblin_trader/uncommon/leather_and_paper_for_books");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_UNCOMMON_AMETHYST_SHARDS_FOR_EMERALD = createKey("goblin_trader/uncommon/amethyst_shards_for_emerald");

    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_EXPERIENCE_BOTTLE = createKey("goblin_trader/rare/emeralds_for_experience_bottle");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_NAME_TAG = createKey("goblin_trader/rare/emeralds_for_name_tag");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_PACKED_ICE_FOR_BLUE_ICE = createKey("goblin_trader/rare/packed_ice_for_blue_ice");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_11 = createKey("goblin_trader/rare/emeralds_for_music_disc_11");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_13 = createKey("goblin_trader/rare/emeralds_for_music_disc_13");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_CAT = createKey("goblin_trader/rare/emeralds_for_music_disc_cat");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_BLOCKS = createKey("goblin_trader/rare/emeralds_for_music_disc_blocks");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_FAR = createKey("goblin_trader/rare/emeralds_for_music_disc_far");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_MALL = createKey("goblin_trader/rare/emeralds_for_music_disc_mall");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_MELLOHI = createKey("goblin_trader/rare/emeralds_for_music_disc_mellohi");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_STAL = createKey("goblin_trader/rare/emeralds_for_music_disc_stal");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_STRAD = createKey("goblin_trader/rare/emeralds_for_music_disc_strad");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_WARD = createKey("goblin_trader/rare/emeralds_for_music_disc_ward");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_WAIT = createKey("goblin_trader/rare/emeralds_for_music_disc_wait");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_OTHERSIDE = createKey("goblin_trader/rare/emeralds_for_music_disc_otherside");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_RELIC = createKey("goblin_trader/rare/emeralds_for_music_disc_relic");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_PRECIPICE = createKey("goblin_trader/rare/emeralds_for_music_disc_precipice");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_CREATOR = createKey("goblin_trader/rare/emeralds_for_music_disc_creator");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_CREATOR_MUSIC_BOX = createKey("goblin_trader/rare/emeralds_for_music_disc_creator_music_box");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_BOUNCE = createKey("goblin_trader/rare/emeralds_for_music_disc_bounce");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE = createKey("goblin_trader/rare/emeralds_for_wayfinder_armor_trim_smithing_template");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_RAISER_ARMOR_TRIM_SMITHING_TEMPLATE = createKey("goblin_trader/rare/emeralds_for_raiser_armor_trim_smithing_template");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE = createKey("goblin_trader/rare/emeralds_for_shaper_armor_trim_smithing_template");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_HOST_ARMOR_TRIM_SMITHING_TEMPLATE = createKey("goblin_trader/rare/emeralds_for_host_armor_trim_smithing_template");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_WARD_ARMOR_TRIM_SMITHING_TEMPLATE = createKey("goblin_trader/rare/emeralds_for_ward_armor_trim_smithing_template");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE = createKey("goblin_trader/rare/emeralds_for_silence_armor_trim_smithing_template");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_EYE_ARMOR_TRIM_SMITHING_TEMPLATE = createKey("goblin_trader/rare/emeralds_for_eye_armor_trim_smithing_template");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_BOLT_ARMOR_TRIM_SMITHING_TEMPLATE = createKey("goblin_trader/rare/emeralds_for_bolt_armor_trim_smithing_template");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_FOR_FLOW_ARMOR_TRIM_SMITHING_TEMPLATE = createKey("goblin_trader/rare/emeralds_for_flow_armor_trim_smithing_template");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_ANGLER_POTTERY_SHERD = createKey("goblin_trader/rare/emeralds_and_brick_for_angler_pottery_sherd");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_ARCHER_POTTERY_SHERD = createKey("goblin_trader/rare/emeralds_and_brick_for_archer_pottery_sherd");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_ARMS_UP_POTTERY_SHERD = createKey("goblin_trader/rare/emeralds_and_brick_for_arms_up_pottery_sherd");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_BLADE_POTTERY_SHERD = createKey("goblin_trader/rare/emeralds_and_brick_for_blade_pottery_sherd");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_BREWER_POTTERY_SHERD = createKey("goblin_trader/rare/emeralds_and_brick_for_brewer_pottery_sherd");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_BURN_POTTERY_SHERD = createKey("goblin_trader/rare/emeralds_and_brick_for_burn_pottery_sherd");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_DANGER_POTTERY_SHERD = createKey("goblin_trader/rare/emeralds_and_brick_for_danger_pottery_sherd");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_EXPLORER_POTTERY_SHERD  = createKey("goblin_trader/rare/emeralds_and_brick_for_explorer_pottery_sherd");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_FLOW_POTTERY_SHERD = createKey("goblin_trader/rare/emeralds_and_brick_for_flow_pottery_sherd");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_FRIEND_POTTERY_SHERD = createKey("goblin_trader/rare/emeralds_and_brick_for_friend_pottery_sherd");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_GUSTER_POTTERY_SHERD = createKey("goblin_trader/rare/emeralds_and_brick_for_guster_pottery_sherd");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_HEART_POTTERY_SHERD = createKey("goblin_trader/rare/emeralds_and_brick_for_heart_pottery_sherd");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_HEARTBREAK_POTTERY_SHERD = createKey("goblin_trader/rare/emeralds_and_brick_for_heartbreak_pottery_sherd");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_HOWL_POTTERY_SHERD = createKey("goblin_trader/rare/emeralds_and_brick_for_howl_pottery_sherd");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_MINER_POTTERY_SHERD = createKey("goblin_trader/rare/emeralds_and_brick_for_miner_pottery_sherd");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_MOURNER_POTTERY_SHERD = createKey("goblin_trader/rare/emeralds_and_brick_for_mourner_pottery_sherd");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_PLENTY_POTTERY_SHERD = createKey("goblin_trader/rare/emeralds_and_brick_for_plenty_pottery_sherd");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_PRIZE_POTTERY_SHERD = createKey("goblin_trader/rare/emeralds_and_brick_for_prize_pottery_sherd");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_SCRAPE_POTTERY_SHERD = createKey("goblin_trader/rare/emeralds_and_brick_for_scrape_pottery_sherd");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_SHEAF_POTTERY_SHERD = createKey("goblin_trader/rare/emeralds_and_brick_for_sheaf_pottery_sherd");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_SHELTER_POTTERY_SHERD = createKey("goblin_trader/rare/emeralds_and_brick_for_shelter_pottery_sherd");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_SKULL_POTTERY_SHERD = createKey("goblin_trader/rare/emeralds_and_brick_for_skull_pottery_sherd");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_SNORT_POTTERY_SHERD = createKey("goblin_trader/rare/emeralds_and_brick_for_snort_pottery_sherd");

    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_EMERALDS_AND_IRON_INGOT_FOR_GOBLIN_TUNED_CHAINMAIL_HELMET = createKey("goblin_trader/epic/emeralds_and_iron_ingot_for_goblin_tuned_chainmail_helmet");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_EMERALDS_AND_IRON_INGOT_FOR_GOBLIN_TUNED_CHAINMAIL_CHESTPLATE = createKey("goblin_trader/epic/emeralds_and_iron_ingot_for_goblin_tuned_chainmail_chestplate");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_EMERALDS_AND_IRON_INGOT_FOR_GOBLIN_TUNED_CHAINMAIL_LEGGINGS = createKey("goblin_trader/epic/emeralds_and_iron_ingot_for_goblin_tuned_chainmail_leggings");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_EMERALDS_AND_IRON_INGOT_FOR_GOBLIN_TUNED_CHAINMAIL_BOOTS = createKey("goblin_trader/epic/emeralds_and_iron_ingot_for_goblin_tuned_chainmail_boots");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_EMERALDS_AND_IRON_INGOT_FOR_GOBLIN_TUNED_COPPER_HELMET = createKey("goblin_trader/epic/emeralds_and_iron_ingot_for_goblin_tuned_copper_helmet");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_EMERALDS_AND_IRON_INGOT_FOR_GOBLIN_TUNED_COPPER_CHESTPLATE = createKey("goblin_trader/epic/emeralds_and_iron_ingot_for_goblin_tuned_copper_chestplate");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_EMERALDS_AND_IRON_INGOT_FOR_GOBLIN_TUNED_COPPER_LEGGINGS = createKey("goblin_trader/epic/emeralds_and_iron_ingot_for_goblin_tuned_copper_leggings");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_EMERALDS_AND_IRON_INGOT_FOR_GOBLIN_TUNED_COPPER_BOOTS = createKey("goblin_trader/epic/emeralds_and_iron_ingot_for_goblin_tuned_copper_boots");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_NIGHT_VISION = createKey("goblin_trader/epic/glass_bottle_and_emeralds_for_night_vision");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_INVISIBILITY = createKey("goblin_trader/epic/glass_bottle_and_emeralds_for_invisibility");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_LEAPING = createKey("goblin_trader/epic/glass_bottle_and_emeralds_for_leaping");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_STRONG_LEAPING = createKey("goblin_trader/epic/glass_bottle_and_emeralds_for_strong_leaping");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_FIRE_RESISTANCE = createKey("goblin_trader/epic/glass_bottle_and_emeralds_for_fire_resistance");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_SWIFTNESS = createKey("goblin_trader/epic/glass_bottle_and_emeralds_for_swiftness");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_STRONG_SWIFTNESS = createKey("goblin_trader/epic/glass_bottle_and_emeralds_for_strong_swiftness");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_WATER_BREATHING = createKey("goblin_trader/epic/glass_bottle_and_emeralds_for_water_breathing");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_HEALING = createKey("goblin_trader/epic/glass_bottle_and_emeralds_for_healing");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_STRONG_HEALING = createKey("goblin_trader/epic/glass_bottle_and_emeralds_for_strong_healing");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_REGENERATION = createKey("goblin_trader/epic/glass_bottle_and_emeralds_for_regeneration");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_LONG_REGENERATION = createKey("goblin_trader/epic/glass_bottle_and_emeralds_for_long_regeneration");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_STRONG_REGENERATION = createKey("goblin_trader/epic/glass_bottle_and_emeralds_for_strong_regeneration");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_STRENGTH = createKey("goblin_trader/epic/glass_bottle_and_emeralds_for_strength");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_STRONG_STRENGTH = createKey("goblin_trader/epic/glass_bottle_and_emeralds_for_strong_strength");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_SLOW_FALLING = createKey("goblin_trader/epic/glass_bottle_and_emeralds_for_slow_falling");
    
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_LEGENDARY_EMERALDS_AND_APPLES_FOR_MOON_BOOTS = createKey("goblin_trader/legendary/emeralds_and_apples_for_moon_boots");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_LEGENDARY_EMERALDS_FOR_MYSTERY_MAP = createKey("goblin_trader/legendary/emeralds_for_mystery_map");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_LEGENDARY_NETHER_STAR_AND_DRAGON_HEAD_FOR_GOBLIN_TUNED_PICKAXE = createKey("goblin_trader/legendary/nether_star_and_dragon_head_for_goblin_tuned_pickaxe");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_LEGENDARY_NETHER_STAR_AND_DRAGON_HEAD_FOR_GOBLIN_TUNED_AXE = createKey("goblin_trader/legendary/nether_star_and_dragon_head_for_goblin_tuned_axe");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_LEGENDARY_NETHER_STAR_AND_DRAGON_HEAD_FOR_GOBLIN_TUNED_SHOVEL = createKey("goblin_trader/legendary/nether_star_and_dragon_head_for_goblin_tuned_shovel");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_LEGENDARY_NETHER_STAR_AND_DRAGON_HEAD_FOR_GOBLIN_TUNED_HOE = createKey("goblin_trader/legendary/nether_star_and_dragon_head_for_goblin_tuned_hoe");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_LEGENDARY_NETHER_STAR_AND_DRAGON_HEAD_FOR_GOBLIN_TUNED_SWORD = createKey("goblin_trader/legendary/nether_star_and_dragon_head_for_goblin_tuned_sword");
    public static final ResourceKey<VillagerTrade> GOBLIN_TRADER_LEGENDARY_EMERALDS_FOR_GOBLIN_TUNED_ELYTRA = createKey("goblin_trader/legendary/emeralds_for_goblin_tuned_elytra");

    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_COMMON_CARROTS_FOR_EMERALD = createKey("vein_goblin_trader/common/carrots_for_emerald");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_COMMON_GLOWSTONE_FOR_GLOWSTONE_DUST = createKey("vein_goblin_trader/common/glowstone_for_glowstone_dust");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_COMMON_NETHERRACK_FOR_EMERALD = createKey("vein_goblin_trader/common/netherrack_for_emerald");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_COMMON_EMERALD_FOR_NETHER_WART = createKey("vein_goblin_trader/common/emerald_for_nether_wart");

    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_UNCOMMON_NETHERITE_INGOT_FOR_TOTEM_OF_UNDYING = createKey("vein_goblin_trader/uncommon/netherite_ingot_for_totem_of_undying");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_UNCOMMON_ACIENT_DEBRIS_FOR_NETHERITE_SCRAP = createKey("vein_goblin_trader/uncommon/acient_debris_for_netherite_scrap");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_UNCOMMON_NETHER_WART_AND_NETHER_BRICK_FOR_RED_NETHER_BRICKS = createKey("vein_goblin_trader/uncommon/nether_wart_and_nether_brick_for_red_nether_bricks");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_UNCOMMON_GOLDEN_CARROT_FOR_EMERALDS = createKey("vein_goblin_trader/uncommon/golden_carrot_for_emeralds");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_UNCOMMON_NETHER_QUARTZ_ORE_FOR_EXPERIENCE_BOTTLE = createKey("vein_goblin_trader/uncommon/nether_quartz_ore_for_experience_bottle");

    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_RARE_GOLDEN_CARROTS_FOR_WITHER_SKELETON_SKULL = createKey("vein_goblin_trader/rare/golden_carrots_for_wither_skeleton_skull");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_RARE_DIAMONDS_FOR_NETHERITE_UPGRADE_SMITHING_TEMPLATE = createKey("vein_goblin_trader/rare/diamonds_for_netherite_upgrade_smithing_template");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_RARE_EMERALDS_FOR_RIB_ARMOR_TRIM_SMITHING_TEMPLATE = createKey("vein_goblin_trader/rare/emeralds_for_rib_armor_trim_smithing_template");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_PIGSTEP = createKey("vein_goblin_trader/rare/emeralds_for_music_disc_pigstep");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_LAVA_CHICKEN = createKey("vein_goblin_trader/rare/emeralds_for_music_disc_lava_chicken");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_5 = createKey("vein_goblin_trader/rare/emeralds_for_music_disc_5");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_TEARS = createKey("vein_goblin_trader/rare/emeralds_for_music_disc_tears");

    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_SLOWNESS = createKey("vein_goblin_trader/epic/emeralds_and_glass_bottle_for_slowness");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_STRONG_SLOWNESS = createKey("vein_goblin_trader/epic/emeralds_and_glass_bottle_for_strong_slowness");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_HARMING = createKey("vein_goblin_trader/epic/emeralds_and_glass_bottle_for_harming");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_STRONG_HARMING = createKey("vein_goblin_trader/epic/emeralds_and_glass_bottle_for_strong_harming");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_POISON = createKey("vein_goblin_trader/epic/emeralds_and_glass_bottle_for_poison");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_LONG_POISON = createKey("vein_goblin_trader/epic/emeralds_and_glass_bottle_for_long_poison");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_STRONG_POISON = createKey("vein_goblin_trader/epic/emeralds_and_glass_bottle_for_strong_poison");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_WEAKNESS = createKey("vein_goblin_trader/epic/emeralds_and_glass_bottle_for_weakness");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_WIND_CHARGED = createKey("vein_goblin_trader/epic/emeralds_and_glass_bottle_for_wind_charged");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_WEAVING = createKey("vein_goblin_trader/epic/emeralds_and_glass_bottle_for_weaving");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_OOZING = createKey("vein_goblin_trader/epic/emeralds_and_glass_bottle_for_oozing");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_INFESTED = createKey("vein_goblin_trader/epic/emeralds_and_glass_bottle_for_infested");

    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_LEGENDARY_NETHER_STAR_AND_DRAGON_HEAD_FOR_GOBLIN_TUNED_HELMET = createKey("vein_goblin_trader/legendary/nether_star_and_dragon_head_for_goblin_tuned_helmet");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_LEGENDARY_NETHER_STAR_AND_DRAGON_HEAD_FOR_GOBLIN_TUNED_CHESTPLATE = createKey("vein_goblin_trader/legendary/nether_star_and_dragon_head_for_goblin_tuned_chestplate");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_LEGENDARY_NETHER_STAR_AND_DRAGON_HEAD_FOR_GOBLIN_TUNED_LEGGINGS = createKey("vein_goblin_trader/legendary/nether_star_and_dragon_head_for_goblin_tuned_leggings");
    public static final ResourceKey<VillagerTrade> VEIN_GOBLIN_TRADER_LEGENDARY_NETHER_STAR_AND_DRAGON_HEAD_FOR_GOBLIN_TUNED_BOOTS = createKey("vein_goblin_trader/legendary/nether_star_and_dragon_head_for_goblin_tuned_boots");

    public static void bootstrap(BootstrapContext<VillagerTrade> context)
    {
        registerGoblinTraderTrades(context);
        registerVeinGoblinTraderTrades(context);
    }

    // .component(DataComponents.SWING_ANIMATION, new SwingAnimation(SwingAnimationType.STAB, (int)(attackDuration * 20.0F)))

    private static void registerGoblinTraderTrades(BootstrapContext<VillagerTrade> context)
    {
        /* ************************************************************************************** *
         *                                    COMMON                                              *
         * ************************************************************************************** */
        register(context, GOBLIN_TRADER_COMMON_APPLES_FOR_EMERALD, create(Items.APPLE, 8, Items.EMERALD, 1, 24, 4));
        register(context, GOBLIN_TRADER_COMMON_RAW_IRON_FOR_IRON_INGOTS, create(Items.RAW_IRON, 1, Items.IRON_INGOT, 2, 30, 6));
        register(context, GOBLIN_TRADER_COMMON_RAW_GOLD_FOR_GOLD_INGOTS, create(Items.RAW_GOLD, 2, Items.GOLD_INGOT, 3, 30, 6));
        register(context, GOBLIN_TRADER_COMMON_RAW_COPPER_FOR_COPPER_INGOTS, create(Items.RAW_COPPER, 1, Items.COPPER_INGOT, 2, 30, 6));
        register(context, GOBLIN_TRADER_COMMON_ROTTEN_FLESH_FOR_COAL, create(Items.ROTTEN_FLESH, 4, Items.COAL, 1, 24, 4));
        register(context, GOBLIN_TRADER_COMMON_GRAVEL_FOR_FLINT, create(Items.GRAVEL, 1, Items.FLINT, 2, 32, 4));
        register(context, GOBLIN_TRADER_COMMON_COBBLESTONE_FOR_EMERALDS, create(Items.COBBLESTONE, 22, 32, Items.EMERALD, 2, 32, 4));

        /* ************************************************************************************** *
         *                                    UNCOMMON                                            *
         * ************************************************************************************** */
        register(context, GOBLIN_TRADER_UNCOMMON_EMERALD_FOR_GUNPOWDER, create(Items.EMERALD, 1, Items.GUNPOWDER, 2, 32, 4));
        register(context, GOBLIN_TRADER_UNCOMMON_TURTLE_EGG_FOR_EMERALDS, create(Items.TURTLE_EGG, 1, Items.EMERALD, 6, 12, 10));
        register(context, GOBLIN_TRADER_UNCOMMON_PUFFERFISH_BUCKET_FOR_EMERALDS, create(Items.PUFFERFISH_BUCKET, 1, Items.EMERALD, 8, 4, 10));
        register(context, GOBLIN_TRADER_UNCOMMON_EMERALDS_FOR_SPONGE, create(Items.EMERALD, 8, Items.SPONGE, 1, 12, 10));
        register(context, GOBLIN_TRADER_UNCOMMON_DAMAGED_ANVIL_AND_IRON_INGOTS_FOR_ANVIL, create(Items.DAMAGED_ANVIL, 1, Items.IRON_INGOT, 2, Items.ANVIL, 1, 2, 10));
        register(context, GOBLIN_TRADER_UNCOMMON_CHIPPED_ANVIL_AND_IRON_INGOTS_FOR_ANVIL, create(Items.CHIPPED_ANVIL, 1, Items.IRON_INGOT, 1, Items.ANVIL, 1, 2, 10));
        register(context, GOBLIN_TRADER_UNCOMMON_CLAY_FOR_TERRACOTTA, create(Items.CLAY, 1, Items.TERRACOTTA, 2, 64, 4));
        register(context, GOBLIN_TRADER_UNCOMMON_LEATHER_AND_PAPER_FOR_BOOKS, create(Items.LEATHER, 1, Items.PAPER, 2, Items.BOOK, 2, 32, 4));
        register(context, GOBLIN_TRADER_UNCOMMON_AMETHYST_SHARDS_FOR_EMERALD, create(Items.AMETHYST_SHARD, 2, Items.EMERALD, 1, 20, 4));

        /* ************************************************************************************** *
         *                                      RARE                                              *
         * ************************************************************************************** */
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_EXPERIENCE_BOTTLE, create(Items.EMERALD, 2, 3, Items.EXPERIENCE_BOTTLE, 1, 64, 10));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_NAME_TAG, create(Items.EMERALD, 16, Items.NAME_TAG, 1, 2, 10));
        register(context, GOBLIN_TRADER_RARE_PACKED_ICE_FOR_BLUE_ICE, create(Items.PACKED_ICE, 4, Items.BLUE_ICE, 1, 64, 10));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_11, create(Items.EMERALD, 16, Items.MUSIC_DISC_11, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_13, create(Items.EMERALD, 16, Items.MUSIC_DISC_13, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_CAT, create(Items.EMERALD, 16, Items.MUSIC_DISC_CAT, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_BLOCKS, create(Items.EMERALD, 16, Items.MUSIC_DISC_BLOCKS, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_FAR, create(Items.EMERALD, 16, Items.MUSIC_DISC_FAR, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_MALL, create(Items.EMERALD, 16, Items.MUSIC_DISC_MALL, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_MELLOHI, create(Items.EMERALD, 16, Items.MUSIC_DISC_MELLOHI, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_STAL, create(Items.EMERALD, 16, Items.MUSIC_DISC_STAL, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_STRAD, create(Items.EMERALD, 16, Items.MUSIC_DISC_STRAD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_WARD, create(Items.EMERALD, 16, Items.MUSIC_DISC_WARD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_WAIT, create(Items.EMERALD, 16, Items.MUSIC_DISC_WAIT, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_OTHERSIDE, create(Items.EMERALD, 16, Items.MUSIC_DISC_OTHERSIDE, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_RELIC, create(Items.EMERALD, 16, Items.MUSIC_DISC_RELIC, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_PRECIPICE, create(Items.EMERALD, 16, Items.MUSIC_DISC_PRECIPICE, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_CREATOR, create(Items.EMERALD, 16, Items.MUSIC_DISC_CREATOR, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_CREATOR_MUSIC_BOX, create(Items.EMERALD, 16, Items.MUSIC_DISC_CREATOR_MUSIC_BOX, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_BOUNCE, create(Items.EMERALD, 16, Items.MUSIC_DISC_BOUNCE, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, create(Items.EMERALD, 16, 28, Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, create(Items.EMERALD, 16, 28, Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, create(Items.EMERALD, 16, 28, Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_HOST_ARMOR_TRIM_SMITHING_TEMPLATE, create(Items.EMERALD, 16, 28, Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_WARD_ARMOR_TRIM_SMITHING_TEMPLATE, create(Items.EMERALD, 16, 28, Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, create(Items.EMERALD, 16, 28, Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_EYE_ARMOR_TRIM_SMITHING_TEMPLATE, create(Items.EMERALD, 16, 28, Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_BOLT_ARMOR_TRIM_SMITHING_TEMPLATE, create(Items.EMERALD, 16, 28, Items.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_FOR_FLOW_ARMOR_TRIM_SMITHING_TEMPLATE, create(Items.EMERALD, 16, 28, Items.FLOW_ARMOR_TRIM_SMITHING_TEMPLATE, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_ANGLER_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.ANGLER_POTTERY_SHERD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_ARCHER_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.ARCHER_POTTERY_SHERD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_ARMS_UP_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.ARMS_UP_POTTERY_SHERD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_BLADE_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.BLADE_POTTERY_SHERD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_BREWER_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.BREWER_POTTERY_SHERD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_BURN_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.BURN_POTTERY_SHERD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_DANGER_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.DANGER_POTTERY_SHERD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_EXPLORER_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.EXPLORER_POTTERY_SHERD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_FLOW_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.FLOW_POTTERY_SHERD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_FRIEND_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.FRIEND_POTTERY_SHERD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_GUSTER_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.GUSTER_POTTERY_SHERD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_HEART_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.HEART_POTTERY_SHERD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_HEARTBREAK_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.HEARTBREAK_POTTERY_SHERD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_HOWL_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.HOWL_POTTERY_SHERD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_MINER_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.MINER_POTTERY_SHERD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_MOURNER_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.MOURNER_POTTERY_SHERD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_PLENTY_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.PLENTY_POTTERY_SHERD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_PRIZE_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.PRIZE_POTTERY_SHERD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_SCRAPE_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.SCRAPE_POTTERY_SHERD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_SHEAF_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.SHEAF_POTTERY_SHERD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_SHELTER_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.SHELTER_POTTERY_SHERD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_SKULL_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.SKULL_POTTERY_SHERD, 1, 1, 100));
        register(context, GOBLIN_TRADER_RARE_EMERALDS_AND_BRICK_FOR_SNORT_POTTERY_SHERD, create(Items.EMERALD, 2, Items.BRICK, 1, Items.SNORT_POTTERY_SHERD, 1, 1, 100));

        /* ************************************************************************************** *
         *                                      EPIC                                              *
         * ************************************************************************************** */
        register(context, GOBLIN_TRADER_EPIC_EMERALDS_AND_IRON_INGOT_FOR_GOBLIN_TUNED_CHAINMAIL_HELMET, createGoblinTunedChainmailTrade(Items.CHAINMAIL_HELMET, EquipmentSlotGroup.HEAD));
        register(context, GOBLIN_TRADER_EPIC_EMERALDS_AND_IRON_INGOT_FOR_GOBLIN_TUNED_CHAINMAIL_CHESTPLATE, createGoblinTunedChainmailTrade(Items.CHAINMAIL_CHESTPLATE, EquipmentSlotGroup.CHEST));
        register(context, GOBLIN_TRADER_EPIC_EMERALDS_AND_IRON_INGOT_FOR_GOBLIN_TUNED_CHAINMAIL_LEGGINGS, createGoblinTunedChainmailTrade(Items.CHAINMAIL_LEGGINGS, EquipmentSlotGroup.LEGS));
        register(context, GOBLIN_TRADER_EPIC_EMERALDS_AND_IRON_INGOT_FOR_GOBLIN_TUNED_CHAINMAIL_BOOTS, createGoblinTunedChainmailTrade(Items.CHAINMAIL_BOOTS, EquipmentSlotGroup.FEET));
        register(context, GOBLIN_TRADER_EPIC_EMERALDS_AND_IRON_INGOT_FOR_GOBLIN_TUNED_COPPER_HELMET, createGoblinTunedCopperTrade(Items.COPPER_HELMET, EquipmentSlotGroup.HEAD));
        register(context, GOBLIN_TRADER_EPIC_EMERALDS_AND_IRON_INGOT_FOR_GOBLIN_TUNED_COPPER_CHESTPLATE, createGoblinTunedCopperTrade(Items.COPPER_CHESTPLATE, EquipmentSlotGroup.CHEST));
        register(context, GOBLIN_TRADER_EPIC_EMERALDS_AND_IRON_INGOT_FOR_GOBLIN_TUNED_COPPER_LEGGINGS, createGoblinTunedCopperTrade(Items.COPPER_LEGGINGS, EquipmentSlotGroup.LEGS));
        register(context, GOBLIN_TRADER_EPIC_EMERALDS_AND_IRON_INGOT_FOR_GOBLIN_TUNED_COPPER_BOOTS, createGoblinTunedCopperTrade(Items.COPPER_BOOTS, EquipmentSlotGroup.FEET));
        register(context, GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_NIGHT_VISION, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.NIGHT_VISION)).build()), 4, 50));
        register(context, GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_INVISIBILITY, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.INVISIBILITY)).build()), 4, 50));
        register(context, GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_LEAPING, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.LEAPING)).build()), 4, 50));
        register(context, GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_STRONG_LEAPING, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.STRONG_LEAPING)).build()), 4, 50));
        register(context, GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_FIRE_RESISTANCE, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.FIRE_RESISTANCE)).build()), 4, 50));
        register(context, GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_SWIFTNESS, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.SWIFTNESS)).build()), 4, 50));
        register(context, GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_STRONG_SWIFTNESS, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.STRONG_SWIFTNESS)).build()), 4, 50));
        register(context, GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_WATER_BREATHING, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER_BREATHING)).build()), 4, 50));
        register(context, GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_HEALING, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.HEALING)).build()), 4, 50));
        register(context, GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_STRONG_HEALING, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.STRONG_HEALING)).build()), 4, 50));
        register(context, GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_REGENERATION, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.REGENERATION)).build()), 4, 50));
        register(context, GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_LONG_REGENERATION, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.LONG_REGENERATION)).build()), 4, 50));
        register(context, GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_STRONG_REGENERATION, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.STRONG_REGENERATION)).build()), 4, 50));
        register(context, GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_STRENGTH, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.STRENGTH)).build()), 4, 50));
        register(context, GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_STRONG_STRENGTH, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.STRONG_STRENGTH)).build()), 4, 50));
        register(context, GOBLIN_TRADER_EPIC_GLASS_BOTTLE_AND_EMERALDS_FOR_SLOW_FALLING, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.SLOW_FALLING)).build()), 4, 50));

        /* ************************************************************************************** *
         *                                      LEGENDARY                                         *
         * ************************************************************************************** */
        register(context, GOBLIN_TRADER_LEGENDARY_EMERALDS_AND_APPLES_FOR_MOON_BOOTS, createMoonBootsTrade());
        register(context, GOBLIN_TRADER_LEGENDARY_EMERALDS_FOR_MYSTERY_MAP, createMysteryMapTrade());
        register(context, GOBLIN_TRADER_LEGENDARY_NETHER_STAR_AND_DRAGON_HEAD_FOR_GOBLIN_TUNED_PICKAXE, createGoblinTunedTool(Items.DIAMOND_PICKAXE));
        register(context, GOBLIN_TRADER_LEGENDARY_NETHER_STAR_AND_DRAGON_HEAD_FOR_GOBLIN_TUNED_AXE, createGoblinTunedTool(Items.DIAMOND_AXE));
        register(context, GOBLIN_TRADER_LEGENDARY_NETHER_STAR_AND_DRAGON_HEAD_FOR_GOBLIN_TUNED_SHOVEL, createGoblinTunedTool(Items.DIAMOND_SHOVEL));
        register(context, GOBLIN_TRADER_LEGENDARY_NETHER_STAR_AND_DRAGON_HEAD_FOR_GOBLIN_TUNED_HOE, createGoblinTunedTool(Items.DIAMOND_HOE));
        register(context, GOBLIN_TRADER_LEGENDARY_NETHER_STAR_AND_DRAGON_HEAD_FOR_GOBLIN_TUNED_SWORD, createGoblinTunedWeapon(Items.DIAMOND_SWORD));
        register(context, GOBLIN_TRADER_LEGENDARY_EMERALDS_FOR_GOBLIN_TUNED_ELYTRA, createGoblinTunedElytra());
    }

    private static void registerVeinGoblinTraderTrades(BootstrapContext<VillagerTrade> context)
    {
        /* ************************************************************************************** *
         *                                    COMMON                                              *
         * ************************************************************************************** */
        register(context, VEIN_GOBLIN_TRADER_COMMON_CARROTS_FOR_EMERALD, create(Items.CARROT, 8, Items.EMERALD, 1, 16, 4));
        register(context, VEIN_GOBLIN_TRADER_COMMON_GLOWSTONE_FOR_GLOWSTONE_DUST, create(Items.GLOWSTONE, 1, Items.GLOWSTONE_DUST, 4, 32, 4));
        register(context, VEIN_GOBLIN_TRADER_COMMON_NETHERRACK_FOR_EMERALD, create(Items.NETHERRACK, 36, 48, Items.EMERALD, 1, 64, 4));
        register(context, VEIN_GOBLIN_TRADER_COMMON_EMERALD_FOR_NETHER_WART, create(Items.EMERALD, 1, Items.NETHER_WART, 2, 12, 4));

        /* ************************************************************************************** *
         *                                    UNCOMMON                                            *
         * ************************************************************************************** */
        register(context, VEIN_GOBLIN_TRADER_UNCOMMON_NETHERITE_INGOT_FOR_TOTEM_OF_UNDYING, create(Items.NETHERITE_INGOT, 2, Items.TOTEM_OF_UNDYING, 1, 2, 10));
        register(context, VEIN_GOBLIN_TRADER_UNCOMMON_ACIENT_DEBRIS_FOR_NETHERITE_SCRAP, create(Items.ANCIENT_DEBRIS, 4, Items.NETHERITE_SCRAP, 5, 12, 50));
        register(context, VEIN_GOBLIN_TRADER_UNCOMMON_NETHER_WART_AND_NETHER_BRICK_FOR_RED_NETHER_BRICKS, create(Items.NETHER_WART, 1, Items.NETHER_BRICK, 1, Items.RED_NETHER_BRICKS, 2, 128, 4));
        register(context, VEIN_GOBLIN_TRADER_UNCOMMON_GOLDEN_CARROT_FOR_EMERALDS, create(Items.GOLDEN_CARROT, 1, Items.EMERALD, 2, 8, 10));
        register(context, VEIN_GOBLIN_TRADER_UNCOMMON_NETHER_QUARTZ_ORE_FOR_EXPERIENCE_BOTTLE, create(Items.NETHER_QUARTZ_ORE, 1, Items.EXPERIENCE_BOTTLE, 1, 32, 5));

        /* ************************************************************************************** *
         *                                      RARE                                              *
         * ************************************************************************************** */
        register(context, VEIN_GOBLIN_TRADER_RARE_GOLDEN_CARROTS_FOR_WITHER_SKELETON_SKULL, create(Items.GOLDEN_CARROT, 8, 24, Items.WITHER_SKELETON_SKULL, 1, 12, 200));
        register(context, VEIN_GOBLIN_TRADER_RARE_DIAMONDS_FOR_NETHERITE_UPGRADE_SMITHING_TEMPLATE, create(Items.DIAMOND, 3, 9, Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1, 2, 100));
        register(context, VEIN_GOBLIN_TRADER_RARE_EMERALDS_FOR_RIB_ARMOR_TRIM_SMITHING_TEMPLATE, create(Items.EMERALD, 16, 24, Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, 1, 1, 100));
        register(context, VEIN_GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_PIGSTEP, create(Items.EMERALD, 24, 32, Items.MUSIC_DISC_PIGSTEP, 1, 1, 100));
        register(context, VEIN_GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_LAVA_CHICKEN, create(Items.EMERALD, 24, 32, Items.MUSIC_DISC_LAVA_CHICKEN, 1, 1, 100));
        register(context, VEIN_GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_5, create(Items.EMERALD, 24, 32, Items.MUSIC_DISC_5, 1, 1, 100));
        register(context, VEIN_GOBLIN_TRADER_RARE_EMERALDS_FOR_MUSIC_DISC_TEARS, create(Items.EMERALD, 24, 32, Items.MUSIC_DISC_TEARS, 1, 1, 100));
        
        /* ************************************************************************************** *
         *                                      EPIC                                              *
         * ************************************************************************************** */
        register(context, VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_SLOWNESS, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.SLOWNESS)).build()), 4, 50));
        register(context, VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_STRONG_SLOWNESS, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.STRONG_SLOWNESS)).build()), 4, 50));
        register(context, VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_HARMING, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.HARMING)).build()), 4, 50));
        register(context, VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_STRONG_HARMING, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.STRONG_HARMING)).build()), 4, 50));
        register(context, VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_POISON, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.POISON)).build()), 4, 50));
        register(context, VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_LONG_POISON, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.LONG_POISON)).build()), 4, 50));
        register(context, VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_STRONG_POISON, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.STRONG_POISON)).build()), 4, 50));
        register(context, VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_WEAKNESS, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WEAKNESS)).build()), 4, 50));
        register(context, VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_WIND_CHARGED, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WIND_CHARGED)).build()), 4, 50));
        register(context, VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_WEAVING, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WEAVING)).build()), 4, 50));
        register(context, VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_OOZING, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.OOZING)).build()), 4, 50));
        register(context, VEIN_GOBLIN_TRADER_EPIC_EMERALDS_AND_GLASS_BOTTLE_FOR_INFESTED, create(Items.EMERALD, 2, Items.GLASS_BOTTLE, 1, new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.INFESTED)).build()), 4, 50));

        /* ************************************************************************************** *
         *                                      LEGENDARY                                         *
         * ************************************************************************************** */
        register(context, VEIN_GOBLIN_TRADER_LEGENDARY_NETHER_STAR_AND_DRAGON_HEAD_FOR_GOBLIN_TUNED_HELMET, createGoblinTunedNetherite(Items.NETHERITE_HELMET, EquipmentSlotGroup.HEAD));
        register(context, VEIN_GOBLIN_TRADER_LEGENDARY_NETHER_STAR_AND_DRAGON_HEAD_FOR_GOBLIN_TUNED_CHESTPLATE, createGoblinTunedNetherite(Items.NETHERITE_CHESTPLATE, EquipmentSlotGroup.CHEST));
        register(context, VEIN_GOBLIN_TRADER_LEGENDARY_NETHER_STAR_AND_DRAGON_HEAD_FOR_GOBLIN_TUNED_LEGGINGS, createGoblinTunedNetherite(Items.NETHERITE_LEGGINGS, EquipmentSlotGroup.LEGS));
        register(context, VEIN_GOBLIN_TRADER_LEGENDARY_NETHER_STAR_AND_DRAGON_HEAD_FOR_GOBLIN_TUNED_BOOTS, createGoblinTunedNetherite(Items.NETHERITE_BOOTS, EquipmentSlotGroup.FEET));
    }

    // TODO create a builder because dis ugly

    private static VillagerTrade create(ItemLike cost, int costCount, Item gives, int giveCount, int maxUse, int exp)
    {
        return new VillagerTrade(new TradeCost(cost, costCount), new ItemStackTemplate(gives, giveCount), maxUse, exp, 0, Optional.empty(), List.of());
    }

    private static VillagerTrade create(ItemLike cost, int costCount, ItemLike secondCost, int secondCostCount, Item gives, int giveCount, int maxUse, int exp)
    {
        return new VillagerTrade(new TradeCost(cost, costCount), Optional.of(new TradeCost(secondCost, secondCostCount)), new ItemStackTemplate(gives, giveCount), maxUse, exp, 0, Optional.empty(), List.of());
    }

    private static VillagerTrade create(ItemLike cost, int costCount, ItemLike secondCost, int secondCostCount, ItemStackTemplate template, int maxUse, int exp)
    {
        return new VillagerTrade(new TradeCost(cost, costCount), Optional.of(new TradeCost(secondCost, secondCostCount)), template, maxUse, exp, 0, Optional.empty(), List.of());
    }

    private static VillagerTrade create(ItemLike cost, int minCostCount, int maxCostCount, Item gives, int giveCount, int maxUse, int exp)
    {
        return new VillagerTrade(new TradeCost(cost, UniformGenerator.between(minCostCount, maxCostCount)), new ItemStackTemplate(gives, giveCount), maxUse, exp, 0, Optional.empty(), List.of());
    }

    private static VillagerTrade createGoblinTunedChainmailTrade(Item item, EquipmentSlotGroup slot)
    {
        return new VillagerTrade(
            new TradeCost(Items.EMERALD, UniformGenerator.between(6, 14)),
            Optional.of(new TradeCost(Items.IRON_INGOT, UniformGenerator.between(1, 3))),
            new ItemStackTemplate(item),
            1, 150, 0F,
            Optional.empty(),
            lootFunctionBuilder().goblinTunedArmor(slot, 1).build()
        );
    }

    private static VillagerTrade createGoblinTunedCopperTrade(Item item, EquipmentSlotGroup slot)
    {
        return new VillagerTrade(
            new TradeCost(Items.NETHER_STAR, 1),
            Optional.of(new TradeCost(Items.DRAGON_HEAD, 1)),
            new ItemStackTemplate(item),
            1, 150, 0F,
            Optional.empty(),
            lootFunctionBuilder().goblinTunedArmor(slot, 1).build()
        );
    }

    private static VillagerTrade createGoblinTunedNetherite(Item item, EquipmentSlotGroup slot)
    {
        return new VillagerTrade(
            new TradeCost(Items.NETHER_STAR, 1),
            Optional.of(new TradeCost(Items.DRAGON_HEAD, 1)),
            new ItemStackTemplate(item),
            1, 300, 0F,
            Optional.empty(),
            lootFunctionBuilder().goblinTunedArmor(slot, 2).build()
        );
    }

    private static VillagerTrade createGoblinTunedTool(Item item)
    {
        return new VillagerTrade(
            new TradeCost(Items.NETHER_STAR, 1),
            Optional.of(new TradeCost(Items.DRAGON_HEAD, 1)),
            new ItemStackTemplate(item),
            1, 300, 0F,
            Optional.empty(),
            lootFunctionBuilder().goblinTunedTool(EquipmentSlotGroup.HAND).build()
        );
    }

    private static VillagerTrade createGoblinTunedWeapon(Item item)
    {
        return new VillagerTrade(
            new TradeCost(Items.NETHER_STAR, 1),
            Optional.of(new TradeCost(Items.DRAGON_HEAD, 3)),
            new ItemStackTemplate(item),
            1, 1000, 0F,
            Optional.empty(),
            lootFunctionBuilder().goblinTunedSword(EquipmentSlotGroup.HAND).build()
        );
    }

    private static VillagerTrade createMoonBootsTrade()
    {
        return new VillagerTrade(
            new TradeCost(Items.APPLE, 8),
            Optional.of(new TradeCost(Items.EMERALD, UniformGenerator.between(8, 32))),
            new ItemStackTemplate(Items.LEATHER_BOOTS),
            1, 150, 0F,
            Optional.empty(),
            lootFunctionBuilder()
                .name(Component.translatable("custom.goblintraders.moon_boots"))
                .glint()
                .gravity(0.835F, EquipmentSlotGroup.FEET)
                .rarity(Rarity.EPIC)
                .build()
        );
    }

    private static VillagerTrade createMysteryMapTrade()
    {
        return new VillagerTrade(
            new TradeCost(Items.EMERALD, UniformGenerator.between(8, 14)),
            Optional.empty(),
            new ItemStackTemplate(Items.MAP),
            2,
            100,
            0F,
            Optional.empty(),
            lootFunctionBuilder()
                .name(Component.translatable("custom.goblintraders.mystery_map"))
                .map(TagKey.create(Registries.STRUCTURE, Utils.id("on_mystery_maps")), MapDecorationTypes.TARGET_X)
                .lore(Component.translatable("custom.goblintraders.mystery_map.lore").withStyle(ChatFormatting.GRAY))
                .glint()
                .rarity(Rarity.EPIC)
                .build()
        );
    }

    private static VillagerTrade createGoblinTunedElytra()
    {
        return new VillagerTrade(
                new TradeCost(Items.ELYTRA, 1),
                Optional.of(new TradeCost(Items.EMERALD, UniformGenerator.between(24, 36))),
                new ItemStackTemplate(Items.ELYTRA),
                1, 500, 0F,
                Optional.empty(),
                lootFunctionBuilder().goblinTuned().build()
        );
    }

    public static ResourceKey<VillagerTrade> createKey(String name)
    {
        return ResourceKey.create(Registries.VILLAGER_TRADE, Utils.id(name));
    }

    private static LootFunctionBuilder lootFunctionBuilder()
    {
        return new LootFunctionBuilder();
    }

    private static class LootFunctionBuilder
    {
        private final List<LootItemFunction> functions = new ArrayList<>();

        public List<LootItemFunction> build()
        {
            return ImmutableList.copyOf(this.functions);
        }

        public LootFunctionBuilder goblinTuned()
        {
            this.durability(10);
            this.rarity(Rarity.EPIC);
            this.glint();
            this.functions.add(SetLoreFunction.setLore()
                .setMode(ListOperation.Append.INSTANCE)
                .addLine(Component.translatable("custom.goblintraders.tuned").withStyle(ChatFormatting.GREEN))
                .addLine(Component.translatable("custom.goblintraders.lore").withStyle(ChatFormatting.GRAY))
                .addLine(Component.translatable("custom.goblintraders.durability_buff").withStyle(ChatFormatting.GOLD))
                .build());
            return this;
        }

        public LootFunctionBuilder goblinTunedArmor(EquipmentSlotGroup group, int scale)
        {
            this.goblinTuned();
            this.functions.add(SetAttributesFunction.setAttributes()
                .withModifier(SetAttributesFunction.modifier(Utils.id("goblins_armour"), Attributes.ARMOR, AttributeModifier.Operation.ADD_VALUE, ConstantValue.exactly(1 * scale)).forSlot(group))
                .withModifier(SetAttributesFunction.modifier(Utils.id("goblins_knockback_resistance"), Attributes.KNOCKBACK_RESISTANCE, AttributeModifier.Operation.ADD_VALUE, ConstantValue.exactly(0.05F * scale)).forSlot(group))
                .build());
            this.functions.add(SetLoreFunction.setLore()
                .setMode(ListOperation.Append.INSTANCE)
                .addLine(Component.translatable("custom.goblintraders.armor_buff").withStyle(ChatFormatting.GOLD))
                .addLine(Component.translatable("custom.goblintraders.knockback_reduction").withStyle(ChatFormatting.GOLD))
                .build());
            return this;
        }

        public LootFunctionBuilder goblinTunedTool(EquipmentSlotGroup slot)
        {
            this.goblinTuned();
            this.functions.add(SetAttributesFunction.setAttributes()
                .withModifier(SetAttributesFunction.modifier(Utils.id("goblins_block_range"), Attributes.BLOCK_INTERACTION_RANGE, AttributeModifier.Operation.ADD_VALUE, ConstantValue.exactly(2)).forSlot(slot))
                .withModifier(SetAttributesFunction.modifier(Utils.id("goblins_break_speed"), Attributes.BLOCK_BREAK_SPEED, AttributeModifier.Operation.ADD_VALUE, ConstantValue.exactly(1)).forSlot(slot))
                .build());
            this.functions.add(SetLoreFunction.setLore()
                .setMode(ListOperation.Append.INSTANCE)
                .addLine(Component.translatable("custom.goblintraders.extended_range").withStyle(ChatFormatting.GOLD))
                .addLine(Component.translatable("custom.goblintraders.increased_break_speed").withStyle(ChatFormatting.GOLD))
                .build());
            return this;
        }

        public LootFunctionBuilder goblinTunedSword(EquipmentSlotGroup slot)
        {
            this.goblinTuned();
            this.functions.add(SetComponentsFunction.setComponent(DataComponents.SWING_ANIMATION, new SwingAnimation(SwingAnimationType.STAB, 10)).build());
            this.functions.add(SetAttributesFunction.setAttributes()
                .withModifier(SetAttributesFunction.modifier(Utils.id("goblins_attack_damage"), Attributes.ATTACK_DAMAGE, AttributeModifier.Operation.ADD_VALUE, ConstantValue.exactly(2)).forSlot(slot))
                .withModifier(SetAttributesFunction.modifier(Utils.id("goblins_range"), Attributes.ENTITY_INTERACTION_RANGE, AttributeModifier.Operation.ADD_VALUE, ConstantValue.exactly(1.5F)).forSlot(slot))
                .withModifier(SetAttributesFunction.modifier(Utils.id("goblins_attack_speed"), Attributes.ATTACK_SPEED, AttributeModifier.Operation.ADD_VALUE, ConstantValue.exactly(10)).forSlot(slot))
                .build());
            this.functions.add(SetLoreFunction.setLore()
                .setMode(ListOperation.Append.INSTANCE)
                .addLine(Component.translatable("custom.goblintraders.more_damage").withStyle(ChatFormatting.GOLD))
                .addLine(Component.translatable("custom.goblintraders.extended_range").withStyle(ChatFormatting.GOLD))
                .build());
            return this;
        }

        private LootFunctionBuilder durability(double scale)
        {
            this.functions.add(IncreaseDurabilityFunction.apply(scale).build());
            return this;
        }

        private LootFunctionBuilder rarity(Rarity rarity)
        {
            this.functions.add(SetComponentsFunction.setComponent(DataComponents.RARITY, rarity).build());
            return this;
        }

        private LootFunctionBuilder glint()
        {
            this.functions.add(SetComponentsFunction.setComponent(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true).build());
            return this;
        }

        private LootFunctionBuilder gravity(float scale, EquipmentSlotGroup slot)
        {
            this.functions.add(SetAttributesFunction.setAttributes()
                .withModifier(SetAttributesFunction.modifier(Utils.id("goblins_gravity"), Attributes.GRAVITY, AttributeModifier.Operation.ADD_VALUE, ConstantValue.exactly((float) (-Attributes.GRAVITY.value().getDefaultValue() * scale))).forSlot(slot))
                .withModifier(SetAttributesFunction.modifier(Utils.id("goblins_fall_damage"), Attributes.FALL_DAMAGE_MULTIPLIER, AttributeModifier.Operation.ADD_VALUE, ConstantValue.exactly((float) (-Attributes.FALL_DAMAGE_MULTIPLIER.value().getDefaultValue() * scale))).forSlot(slot))
                .withModifier(SetAttributesFunction.modifier(Utils.id("goblins_safe_fall"), Attributes.SAFE_FALL_DISTANCE, AttributeModifier.Operation.ADD_VALUE, ConstantValue.exactly((float) (Attributes.SAFE_FALL_DISTANCE.value().getDefaultValue() * 5))).forSlot(slot))
                .build());
            return this;
        }

        public LootFunctionBuilder name(Component name)
        {
            this.functions.add(SetComponentsFunction.setComponent(DataComponents.ITEM_NAME, name).build());
            return this;
        }

        public LootFunctionBuilder map(TagKey<Structure> structureKey, Holder<MapDecorationType> mapDecoration)
        {
            this.functions.add(ExplorationMapFunction.makeExplorationMap()
                .setMapDecoration(mapDecoration)
                .setDestination(structureKey)
                .setSkipKnownStructures(true)
                .setZoom((byte) 2)
                .build()
            );
            return this;
        }

        public LootFunctionBuilder lore(Component lore)
        {
            this.functions.add(SetLoreFunction.setLore()
                .setMode(ListOperation.Append.INSTANCE)
                .addLine(lore)
                .build());
            return this;
        }
    }

    private static DataComponentPatch.Builder tuned(DataComponentPatch.Builder patch)
    {
        patch.set(DataComponents.RARITY, Rarity.EPIC);
        patch.set(DataComponents.LORE, ItemLore.EMPTY
            .withLineAdded(Component.translatable("custom.goblintraders.tuned").withStyle(ChatFormatting.GREEN))
            .withLineAdded(Component.translatable("custom.goblintraders.lore").withStyle(ChatFormatting.GRAY))
        );
        return patch;
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
                Utils.id("goblins_damage"), 3, AttributeModifier.Operation.ADD_VALUE
        ), EquipmentSlotGroup.MAINHAND);
        modifiers = modifiers.withModifierAdded(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(
                Utils.id("goblins_knockback"), 2, AttributeModifier.Operation.ADD_VALUE
        ), EquipmentSlotGroup.MAINHAND);
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, modifiers);
        return stack;
    }

    private static ItemStack breakSpeed(ItemStack stack)
    {
        ItemAttributeModifiers modifiers = getModifiers(stack);
        modifiers = modifiers.withModifierAdded(Attributes.BLOCK_BREAK_SPEED, new AttributeModifier(
                Utils.id("goblins_break_speed"), 1, AttributeModifier.Operation.ADD_VALUE
        ), EquipmentSlotGroup.MAINHAND);
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, modifiers);
        return stack;
    }

    private static ItemStack increaseReach(ItemStack stack)
    {
        ItemAttributeModifiers modifiers = getModifiers(stack);
        modifiers = modifiers.withModifierAdded(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(
                Utils.id("goblins_block_range"), 2, AttributeModifier.Operation.ADD_VALUE
        ), EquipmentSlotGroup.MAINHAND);
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, modifiers);
        return stack;
    }

    private static ItemStack movementSpeed(ItemStack stack)
    {
        ItemAttributeModifiers modifiers = getModifiers(stack);
        modifiers = modifiers.withModifierAdded(Attributes.MOVEMENT_SPEED, new AttributeModifier(
                Utils.id("goblins_movement"), 0.02, AttributeModifier.Operation.ADD_VALUE
        ), EquipmentSlotGroup.FEET);
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, modifiers);
        return stack;
    }

    private static ItemStack moreArmour(ItemStack stack, EquipmentSlotGroup group)
    {
        ItemAttributeModifiers modifiers = getModifiers(stack);
        modifiers = modifiers.withModifierAdded(Attributes.ARMOR, new AttributeModifier(
                Utils.id("goblins_armour"), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE
        ), group);
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, modifiers);
        return stack;
    }

    private static ItemStack oxygenBoost(ItemStack stack)
    {
        ItemAttributeModifiers modifiers = getModifiers(stack);
        modifiers = modifiers.withModifierAdded(Attributes.OXYGEN_BONUS, new AttributeModifier(
                Utils.id("goblins_oxygen_boost"), 5, AttributeModifier.Operation.ADD_VALUE
        ), EquipmentSlotGroup.HEAD);
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, modifiers);
        return stack;
    }

    private static ItemStack bigStep(ItemStack stack)
    {
        ItemAttributeModifiers modifiers = getModifiers(stack);
        modifiers = modifiers.withModifierAdded(Attributes.STEP_HEIGHT, new AttributeModifier(
                Utils.id("goblins_big_step"), 0.5, AttributeModifier.Operation.ADD_VALUE
        ), EquipmentSlotGroup.FEET);
        modifiers = modifiers.withModifierAdded(Attributes.SAFE_FALL_DISTANCE, new AttributeModifier(
                Utils.id("goblins_fall_distance"), 1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE
        ), EquipmentSlotGroup.FEET);
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, modifiers);
        return stack;
    }

    private static ItemStack nameItem(ItemStack stack, Component name)
    {
        stack.set(DataComponents.ITEM_NAME, name);
        return stack;
    }

    /*private static ItemStack createEnchantedItem(ItemLike item, int count, Consumer<ItemEnchantments.Mutable> consumer)
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

    private static DataComponentExactPredicate createComponentPredicate(Consumer<DataComponentExactPredicate.Builder> consumer)
    {
        DataComponentExactPredicate.Builder builder = DataComponentExactPredicate.builder();
        consumer.accept(builder);
        return builder.build();
    }

    private static ItemEnchantments createItemEnchantments(Consumer<ItemEnchantments.Mutable> consumer)
    {
        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        consumer.accept(mutable);
        return mutable.toImmutable();
    }*/
}
