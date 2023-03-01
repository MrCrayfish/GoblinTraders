package com.mrcrayfish.goblintraders.entity;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.level.Level;

/**
 * Author: MrCrayfish
 */
public abstract class TraderCreatureEntity extends AgeableMob implements Merchant
{
    protected TraderCreatureEntity(EntityType<? extends AgeableMob> type, Level level)
    {
        super(type, level);
    }
}
