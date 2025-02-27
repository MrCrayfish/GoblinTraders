package com.mrcrayfish.goblintraders.platform;

import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.platform.services.IPlatformHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class FabricPlatformHelper implements IPlatformHelper
{
    @Override
    public SpawnEggItem createSpawnEgg(Supplier<EntityType<? extends AbstractGoblinEntity>> type, int primaryColour, int secondaryColour, Item.Properties properties)
    {
        return new SpawnEggItem(type.get(), primaryColour, secondaryColour, properties);
    }
}
