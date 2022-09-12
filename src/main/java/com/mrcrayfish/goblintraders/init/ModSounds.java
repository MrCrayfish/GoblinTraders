package com.mrcrayfish.goblintraders.init;

import com.mrcrayfish.goblintraders.Reference;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

/**
 * Author: MrCrayfish
 */
public class ModSounds implements ModInitializer
{
    public static final SoundEvent ENTITY_GOBLIN_TRADER_ANNOYED_GRUNT = register(new ResourceLocation(Reference.MOD_ID, "entity.goblin_trader.annoyed_grunt"));
    public static final SoundEvent ENTITY_GOBLIN_TRADER_IDLE_GRUNT = register(new ResourceLocation(Reference.MOD_ID, "entity.goblin_trader.idle_grunt"));

    private static SoundEvent register(ResourceLocation id)
    {
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }

    @Override
    public void onInitialize()
    {

    }
}
