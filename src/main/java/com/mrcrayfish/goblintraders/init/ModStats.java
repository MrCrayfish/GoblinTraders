package com.mrcrayfish.goblintraders.init;

import com.mrcrayfish.goblintraders.Reference;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;

/**
 * Author: MrCrayfish
 */
public class ModStats implements ModInitializer
{
    public static final ResourceLocation TRADE_WITH_GOBLIN = registerCustom("trade_with_goblin", StatFormatter.DEFAULT);

    @Override
    public void onInitialize()
    {

    }

    private static ResourceLocation registerCustom(String name, StatFormatter formatter)
    {
        String key = Reference.MOD_ID + ":" + name;
        ResourceLocation id = new ResourceLocation(key);
        Registry.register(BuiltInRegistries.CUSTOM_STAT, key, id);
        Stats.CUSTOM.get(id, formatter);
        return id;
    }
}
