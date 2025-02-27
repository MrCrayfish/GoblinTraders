package com.mrcrayfish.goblintraders;

import net.minecraft.world.item.enchantment.Enchantment;

/**
 * Author: MrCrayfish
 */
public class Hooks
{
    public static int getEnchantmentLevel(Enchantment enchantment)
    {
        return enchantment.getMaxLevel();
    }
}
