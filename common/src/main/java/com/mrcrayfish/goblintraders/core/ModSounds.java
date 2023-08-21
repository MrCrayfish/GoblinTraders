package com.mrcrayfish.goblintraders.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.goblintraders.util.Utils;
import net.minecraft.sounds.SoundEvent;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModSounds
{
    public static final RegistryEntry<SoundEvent> ENTITY_GOBLIN_TRADER_ANNOYED_GRUNT = build("entity.goblin_trader.annoyed_grunt");
    public static final RegistryEntry<SoundEvent> ENTITY_GOBLIN_TRADER_IDLE_GRUNT = build("entity.goblin_trader.idle_grunt");

    private static RegistryEntry<SoundEvent> build(String name)
    {
        return RegistryEntry.soundEvent(Utils.resource(name), id -> () -> SoundEvent.createVariableRangeEvent(id));
    }
}
