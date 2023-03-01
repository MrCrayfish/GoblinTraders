package com.mrcrayfish.goblintraders.init;

import com.mrcrayfish.goblintraders.Reference;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;

/**
 * Author: MrCrayfish
 */
public class ModPotions implements ModInitializer
{
    public static final Potion EXTENDED_NIGHT_VISION = register("extended_night_vision", new Potion("night_vision", new MobEffectInstance(MobEffects.NIGHT_VISION, 18000)));
    public static final Potion EXTENDED_INVISIBILITY = register("extended_invisibility", new Potion("invisibility", new MobEffectInstance(MobEffects.INVISIBILITY, 7200)));
    public static final Potion POWERFUL_JUMP_BOOST = register("powerful_jump_boost", new Potion("leaping", new MobEffectInstance(MobEffects.JUMP, 1800, 2)));
    public static final Potion EXTENDED_FIRE_RESISTANCE = register("extended_fire_resistance", new Potion("fire_resistance", new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 7200)));
    public static final Potion POWERFUL_SPEED = register("powerful_speed", new Potion("swiftness", new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1800, 2)));
    public static final Potion EXTENDED_WATER_BREATHING = register("extended_water_breathing", new Potion("water_breathing", new MobEffectInstance(MobEffects.WATER_BREATHING, 18000)));
    public static final Potion POWERFUL_INSTANT_HEALTH = register("powerful_instant_health", new Potion("healing", new MobEffectInstance(MobEffects.HEAL, 1, 2)));
    public static final Potion POWERFUL_REGENERATION = register("powerful_regeneration", new Potion("regeneration", new MobEffectInstance(MobEffects.REGENERATION, 450, 2)));
    public static final Potion EXTENDED_SLOW_FALLING = register("extended_slow_falling", new Potion("slow_falling", new MobEffectInstance(MobEffects.SLOW_FALLING, 7200)));
    public static final Potion POWERFUL_STRENGTH = register("powerful_strength", new Potion("strength", new MobEffectInstance(MobEffects.DAMAGE_BOOST, 900, 2)));
    public static final Potion HASTE = register("haste", new Potion("haste", new MobEffectInstance(MobEffects.DIG_SPEED, 3600)));
    public static final Potion ABSORPTION = register("absorption", new Potion("absorption", new MobEffectInstance(MobEffects.ABSORPTION, 3600)));
    public static final Potion LEVITATION = register("levitation", new Potion("levitation", new MobEffectInstance(MobEffects.LEVITATION, 900)));
    public static final Potion DOLPHINS_GRACE = register("dolphins_grace", new Potion("dolphins_grace", new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 3600)));

    private static Potion register(String id, Potion potion)
    {
        return Registry.register(BuiltInRegistries.POTION, new ResourceLocation(Reference.MOD_ID, id), potion);
    }

    @Override
    public void onInitialize()
    {

    }
}
