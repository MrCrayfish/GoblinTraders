package com.mrcrayfish.goblintraders.platform;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.item.GoblinSpawnEggItem;
import com.mrcrayfish.goblintraders.platform.services.IPlatformHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.ShapedRecipe;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class FabricPlatformHelper implements IPlatformHelper
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    @Override
    public SpawnEggItem createSpawnEgg(Supplier<EntityType<? extends AbstractGoblinEntity>> type, int primaryColour, int secondaryColour, Item.Properties properties)
    {
        return new GoblinSpawnEggItem(type, primaryColour, secondaryColour, properties);
    }

    @Override
    public ItemStack deserializeItem(JsonObject object)
    {
        ItemStack stack = ShapedRecipe.itemStackFromJson(object);
        Optional.ofNullable(object.get("nbt")).flatMap(this::parseTag).ifPresent(stack::setTag);
        return stack;
    }

    private Optional<CompoundTag> parseTag(JsonElement element)
    {
        try
        {
            if(element.isJsonObject())
            {
                return Optional.of(TagParser.parseTag(GSON.toJson(element)));
            }
            return Optional.of(TagParser.parseTag(GsonHelper.convertToString(element, "nbt")));
        }
        catch(CommandSyntaxException e)
        {
            throw new RuntimeException(e);
        }
    }
}
