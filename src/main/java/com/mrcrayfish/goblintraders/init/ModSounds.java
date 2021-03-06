package com.mrcrayfish.goblintraders.init;

import com.mrcrayfish.goblintraders.Reference;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Author: MrCrayfish
 */
public class ModSounds
{
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Reference.MOD_ID);

    public static final RegistryObject<SoundEvent> ENTITY_GOBLIN_TRADER_ANNOYED_GRUNT = build("entity.goblin_trader.annoyed_grunt");
    public static final RegistryObject<SoundEvent> ENTITY_GOBLIN_TRADER_IDLE_GRUNT = build("entity.goblin_trader.idle_grunt");

    private static RegistryObject<SoundEvent> build(String id)
    {
        return REGISTER.register(id, () -> new SoundEvent(new ResourceLocation(Reference.MOD_ID, id)));
    }
}
