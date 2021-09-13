package com.mrcrayfish.goblintraders.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.SpawnEggItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

/**
 * Author: MrCrayfish
 */
@Mixin(SpawnEggItem.class)
public interface SpawnEggItemMixin
{
    @Accessor(value = "BY_ID")
    static Map<EntityType<?>, SpawnEggItem> getEggMap() {
        throw new AssertionError();
    }
}
