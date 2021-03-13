package com.mrcrayfish.goblintraders.init;

import com.mrcrayfish.goblintraders.Reference;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Author: MrCrayfish
 */
public class ModPotions
{
    public static final DeferredRegister<Potion> REGISTER = DeferredRegister.create(ForgeRegistries.POTION_TYPES, Reference.MOD_ID);

    public static final RegistryObject<Potion> EXTENDED_NIGHT_VISION = REGISTER.register("extended_night_vision", () -> new Potion("night_vision", new EffectInstance(Effects.NIGHT_VISION, 18000)));
    public static final RegistryObject<Potion> EXTENDED_INVISIBILITY = REGISTER.register("extended_invisibility", () -> new Potion("invisibility", new EffectInstance(Effects.INVISIBILITY, 7200)));
    public static final RegistryObject<Potion> POWERFUL_JUMP_BOOST = REGISTER.register("powerful_jump_boost", () -> new Potion("leaping", new EffectInstance(Effects.JUMP_BOOST, 1800, 2)));
    public static final RegistryObject<Potion> EXTENDED_FIRE_RESISTANCE = REGISTER.register("extended_fire_resistance", () -> new Potion("fire_resistance", new EffectInstance(Effects.FIRE_RESISTANCE, 7200)));
    public static final RegistryObject<Potion> POWERFUL_SPEED = REGISTER.register("powerful_speed", () -> new Potion("swiftness", new EffectInstance(Effects.SPEED, 1800, 2)));
    public static final RegistryObject<Potion> EXTENDED_WATER_BREATHING = REGISTER.register("extended_water_breathing", () -> new Potion("water_breathing", new EffectInstance(Effects.WATER_BREATHING, 18000)));
    public static final RegistryObject<Potion> POWERFUL_INSTANT_HEALTH = REGISTER.register("powerful_instant_health", () -> new Potion("healing", new EffectInstance(Effects.INSTANT_HEALTH, 1, 2)));
    public static final RegistryObject<Potion> POWERFUL_REGENERATION = REGISTER.register("powerful_regeneration", () -> new Potion("regeneration", new EffectInstance(Effects.REGENERATION, 450, 2)));
    public static final RegistryObject<Potion> EXTENDED_SLOW_FALLING = REGISTER.register("extended_slow_falling", () -> new Potion("slow_falling", new EffectInstance(Effects.SLOW_FALLING, 7200)));
    public static final RegistryObject<Potion> POWERFUL_STRENGTH = REGISTER.register("powerful_strength", () -> new Potion("strength", new EffectInstance(Effects.STRENGTH, 900, 2)));
    public static final RegistryObject<Potion> HASTE = REGISTER.register("haste", () -> new Potion("haste", new EffectInstance(Effects.HASTE, 3600)));
    public static final RegistryObject<Potion> ABSORPTION = REGISTER.register("absorption", () -> new Potion("absorption", new EffectInstance(Effects.ABSORPTION, 3600)));
    public static final RegistryObject<Potion> LEVITATION = REGISTER.register("levitation", () -> new Potion("levitation", new EffectInstance(Effects.LEVITATION, 900)));
    public static final RegistryObject<Potion> LUCK = REGISTER.register("luck", () -> new Potion("luck", new EffectInstance(Effects.LUCK, 3600)));
    public static final RegistryObject<Potion> DOLPHINS_GRACE = REGISTER.register("dolphins_grace", () -> new Potion("dolphins_grace", new EffectInstance(Effects.DOLPHINS_GRACE, 3600)));
}
