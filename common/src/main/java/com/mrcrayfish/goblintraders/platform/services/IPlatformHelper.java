package com.mrcrayfish.goblintraders.platform.services;

import com.google.gson.JsonObject;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public interface IPlatformHelper
{
    SpawnEggItem createSpawnEgg(Supplier<EntityType<? extends AbstractGoblinEntity>> type, int primaryColour, int secondaryColour, Item.Properties properties);

    ItemStack deserializeItem(JsonObject object);
}
