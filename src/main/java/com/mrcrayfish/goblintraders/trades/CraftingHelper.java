/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package com.mrcrayfish.goblintraders.trades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CraftingHelper
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    // This method has been modified.
    public static ItemStack getItemStack(JsonObject json, boolean readNBT) // Removed disallowsAirInRecipe since not needed
    {
        String itemName = GsonHelper.getAsString(json, "item");
        Item item = GsonHelper.getAsItem(json, "item"); // Changed to use vanilla method to get item
        if(readNBT && json.has("nbt"))
        {
            CompoundTag nbt = getNBT(json.get("nbt"));
            CompoundTag tmp = new CompoundTag();
            // Removed ForgeCaps block since doesn't exist in Fabric
            tmp.put("tag", nbt);
            tmp.putString("id", itemName);
            tmp.putInt("Count", GsonHelper.getAsInt(json, "count", 1));
            return ItemStack.of(tmp);
        }
        return new ItemStack(item, GsonHelper.getAsInt(json, "count", 1));
    }

    public static CompoundTag getNBT(JsonElement element)
    {
        try
        {
            if(element.isJsonObject())
            {
                return TagParser.parseTag(GSON.toJson(element));
            }
            else
            {
                return TagParser.parseTag(GsonHelper.convertToString(element, "nbt"));
            }
        }
        catch(CommandSyntaxException e)
        {
            throw new JsonSyntaxException("Invalid NBT Entry: " + e);
        }
    }
}
