package com.mrcrayfish.goblintraders.entity;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public abstract class TraderCreatureEntity extends CreatureEntity implements IMerchant
{
    protected TraderCreatureEntity(EntityType<? extends CreatureEntity> type, World worldIn)
    {
        super(type, worldIn);
    }
}
