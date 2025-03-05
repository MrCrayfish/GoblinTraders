package com.mrcrayfish.goblintraders.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.goblintraders.entity.GoblinTrader;
import com.mrcrayfish.goblintraders.entity.VeinGoblinTrader;
import com.mrcrayfish.goblintraders.util.Utils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModEntities
{
    public static final RegistryEntry<EntityType<GoblinTrader>> GOBLIN_TRADER = RegistryEntry.entityType(Utils.resource("goblin_trader"), () -> EntityType.Builder.of(GoblinTrader::new, MobCategory.CREATURE).sized(0.5F, 1.0F));
    public static final RegistryEntry<EntityType<VeinGoblinTrader>> VEIN_GOBLIN_TRADER = RegistryEntry.entityType(Utils.resource("vein_goblin_trader"), () -> EntityType.Builder.of(VeinGoblinTrader::new, MobCategory.CREATURE).sized(0.5F, 1.0F));;
}
