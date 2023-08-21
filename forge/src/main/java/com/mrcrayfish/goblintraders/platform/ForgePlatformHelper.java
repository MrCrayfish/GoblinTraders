package com.mrcrayfish.goblintraders.platform;

import com.google.gson.JsonObject;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.platform.services.IPlatformHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.crafting.CraftingHelper;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class ForgePlatformHelper implements IPlatformHelper
{
    @Override
    public SpawnEggItem createSpawnEgg(Supplier<EntityType<? extends AbstractGoblinEntity>> type, int primaryColour, int secondaryColour, Item.Properties properties)
    {
        return new ForgeSpawnEggItem(type, primaryColour, secondaryColour, properties);
    }

    @Override
    public ItemStack deserializeItem(JsonObject object)
    {
        return CraftingHelper.getItemStack(object, true);
    }
}
