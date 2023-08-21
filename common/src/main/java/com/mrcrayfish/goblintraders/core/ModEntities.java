package com.mrcrayfish.goblintraders.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.goblintraders.entity.GoblinTrader;
import com.mrcrayfish.goblintraders.entity.VeinGoblinTrader;
import com.mrcrayfish.goblintraders.util.Utils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;

import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModEntities
{
    public static final RegistryEntry<EntityType<GoblinTrader>> GOBLIN_TRADER = build("goblin_trader", GoblinTrader::new, 0.5F, 1.0F);
    public static final RegistryEntry<EntityType<VeinGoblinTrader>> VEIN_GOBLIN_TRADER = build("vein_goblin_trader", VeinGoblinTrader::new, 0.5F, 1.0F);

    private static <T extends Entity> RegistryEntry<EntityType<T>> build(String name, Function<Level, T> function, float width, float height)
    {
        return RegistryEntry.entityType(Utils.resource(name), () -> EntityType.Builder.<T>of((entityType, world) -> function.apply(world), MobCategory.CREATURE).sized(width, height).build(name));
    }
}
