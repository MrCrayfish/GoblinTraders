package com.mrcrayfish.goblintraders.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.goblintraders.util.Utils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModPotions
{
    public static final RegistryEntry<Potion> EXTENDED_NIGHT_VISION = RegistryEntry.potion(Utils.resource("extended_night_vision"), () -> new Potion("night_vision", new MobEffectInstance(MobEffects.NIGHT_VISION, 18000)));
    public static final RegistryEntry<Potion> EXTENDED_INVISIBILITY = RegistryEntry.potion(Utils.resource("extended_invisibility"), () -> new Potion("invisibility", new MobEffectInstance(MobEffects.INVISIBILITY, 7200)));
    public static final RegistryEntry<Potion> POWERFUL_JUMP_BOOST = RegistryEntry.potion(Utils.resource("powerful_jump_boost"), () -> new Potion("leaping", new MobEffectInstance(MobEffects.JUMP, 1800, 2)));
    public static final RegistryEntry<Potion> EXTENDED_FIRE_RESISTANCE = RegistryEntry.potion(Utils.resource("extended_fire_resistance"), () -> new Potion("fire_resistance", new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 7200)));
    public static final RegistryEntry<Potion> POWERFUL_SPEED = RegistryEntry.potion(Utils.resource("powerful_speed"), () -> new Potion("swiftness", new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1800, 2)));
    public static final RegistryEntry<Potion> EXTENDED_WATER_BREATHING = RegistryEntry.potion(Utils.resource("extended_water_breathing"), () -> new Potion("water_breathing", new MobEffectInstance(MobEffects.WATER_BREATHING, 18000)));
    public static final RegistryEntry<Potion> POWERFUL_INSTANT_HEALTH = RegistryEntry.potion(Utils.resource("powerful_instant_health"), () -> new Potion("healing", new MobEffectInstance(MobEffects.HEAL, 1, 2)));
    public static final RegistryEntry<Potion> POWERFUL_REGENERATION = RegistryEntry.potion(Utils.resource("powerful_regeneration"), () -> new Potion("regeneration", new MobEffectInstance(MobEffects.REGENERATION, 450, 2)));
    public static final RegistryEntry<Potion> EXTENDED_SLOW_FALLING = RegistryEntry.potion(Utils.resource("extended_slow_falling"), () -> new Potion("slow_falling", new MobEffectInstance(MobEffects.SLOW_FALLING, 7200)));
    public static final RegistryEntry<Potion> POWERFUL_STRENGTH = RegistryEntry.potion(Utils.resource("powerful_strength"), () -> new Potion("strength", new MobEffectInstance(MobEffects.DAMAGE_BOOST, 900, 2)));
    public static final RegistryEntry<Potion> HASTE = RegistryEntry.potion(Utils.resource("haste"), () -> new Potion("haste", new MobEffectInstance(MobEffects.DIG_SPEED, 3600)));
    public static final RegistryEntry<Potion> ABSORPTION = RegistryEntry.potion(Utils.resource("absorption"), () -> new Potion("absorption", new MobEffectInstance(MobEffects.ABSORPTION, 3600)));
    public static final RegistryEntry<Potion> LEVITATION = RegistryEntry.potion(Utils.resource("levitation"), () -> new Potion("levitation", new MobEffectInstance(MobEffects.LEVITATION, 900)));
    public static final RegistryEntry<Potion> LUCK = RegistryEntry.potion(Utils.resource("luck"), () -> new Potion("luck", new MobEffectInstance(MobEffects.LUCK, 3600)));
    public static final RegistryEntry<Potion> DOLPHINS_GRACE = RegistryEntry.potion(Utils.resource("dolphins_grace"), () -> new Potion("dolphins_grace", new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 3600)));
}
