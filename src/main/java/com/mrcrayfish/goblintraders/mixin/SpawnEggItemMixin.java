package com.mrcrayfish.goblintraders.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

/**
 * Author: MrCrayfish
 */
@Mixin(SpawnEggItem.class)
public interface SpawnEggItemMixin
{
    @Accessor(value = "EGGS")
    static Map<EntityType<?>, SpawnEggItem> getEggMap() {
        throw new AssertionError();
    }
}
