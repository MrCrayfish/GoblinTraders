package com.mrcrayfish.goblintraders.init;

import com.mrcrayfish.goblintraders.Reference;
import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

/**
 * Author: MrCrayfish
 */
public class ModStats
{
    public static final ResourceLocation TRADE_WITH_GOBLIN = registerCustom("trade_with_goblin", IStatFormatter.DEFAULT);

    public static void init()
    {
        //Does nothing, just triggers java to load static fields
    }

    private static ResourceLocation registerCustom(String name, IStatFormatter formatter)
    {
        String key = Reference.MOD_ID + ":" + name;
        ResourceLocation id = new ResourceLocation(key);
        Registry.register(Registry.CUSTOM_STAT, key, id);
        Stats.CUSTOM.get(id, formatter);
        return id;
    }
}
