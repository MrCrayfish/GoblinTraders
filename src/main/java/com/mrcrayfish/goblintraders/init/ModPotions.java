package com.mrcrayfish.goblintraders.init;

import com.mrcrayfish.goblintraders.Reference;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Author: MrCrayfish
 */
public class ModPotions
{
    public static final DeferredRegister<Potion> REGISTER = DeferredRegister.create(ForgeRegistries.POTIONS, Reference.MOD_ID);

    public static final RegistryObject<Potion> EXTENDED_NIGHT_VISION = REGISTER.register("extended_night_vision", () -> new Potion("night_vision", new MobEffectInstance(MobEffects.NIGHT_VISION, 18000)));
    public static final RegistryObject<Potion> EXTENDED_INVISIBILITY = REGISTER.register("extended_invisibility", () -> new Potion("invisibility", new MobEffectInstance(MobEffects.INVISIBILITY, 7200)));
    public static final RegistryObject<Potion> POWERFUL_JUMP_BOOST = REGISTER.register("powerful_jump_boost", () -> new Potion("leaping", new MobEffectInstance(MobEffects.JUMP, 1800, 2)));
    public static final RegistryObject<Potion> EXTENDED_FIRE_RESISTANCE = REGISTER.register("extended_fire_resistance", () -> new Potion("fire_resistance", new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 7200)));
    public static final RegistryObject<Potion> POWERFUL_SPEED = REGISTER.register("powerful_speed", () -> new Potion("swiftness", new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1800, 2)));
    public static final RegistryObject<Potion> EXTENDED_WATER_BREATHING = REGISTER.register("extended_water_breathing", () -> new Potion("water_breathing", new MobEffectInstance(MobEffects.WATER_BREATHING, 18000)));
    public static final RegistryObject<Potion> POWERFUL_INSTANT_HEALTH = REGISTER.register("powerful_instant_health", () -> new Potion("healing", new MobEffectInstance(MobEffects.HEAL, 1, 2)));
    public static final RegistryObject<Potion> POWERFUL_REGENERATION = REGISTER.register("powerful_regeneration", () -> new Potion("regeneration", new MobEffectInstance(MobEffects.REGENERATION, 450, 2)));
    public static final RegistryObject<Potion> EXTENDED_SLOW_FALLING = REGISTER.register("extended_slow_falling", () -> new Potion("slow_falling", new MobEffectInstance(MobEffects.SLOW_FALLING, 7200)));
    public static final RegistryObject<Potion> POWERFUL_STRENGTH = REGISTER.register("powerful_strength", () -> new Potion("strength", new MobEffectInstance(MobEffects.DAMAGE_BOOST, 900, 2)));
    public static final RegistryObject<Potion> HASTE = REGISTER.register("haste", () -> new Potion("haste", new MobEffectInstance(MobEffects.DIG_SPEED, 3600)));
    public static final RegistryObject<Potion> ABSORPTION = REGISTER.register("absorption", () -> new Potion("absorption", new MobEffectInstance(MobEffects.ABSORPTION, 3600)));
    public static final RegistryObject<Potion> LEVITATION = REGISTER.register("levitation", () -> new Potion("levitation", new MobEffectInstance(MobEffects.LEVITATION, 900)));
    public static final RegistryObject<Potion> LUCK = REGISTER.register("luck", () -> new Potion("luck", new MobEffectInstance(MobEffects.LUCK, 3600)));
    public static final RegistryObject<Potion> DOLPHINS_GRACE = REGISTER.register("dolphins_grace", () -> new Potion("dolphins_grace", new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 3600)));
}
