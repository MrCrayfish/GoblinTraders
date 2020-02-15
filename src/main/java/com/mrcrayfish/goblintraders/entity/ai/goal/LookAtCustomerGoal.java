package com.mrcrayfish.goblintraders.entity.ai.goal;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Author: MrCrayfish
 */
public class LookAtCustomerGoal extends LookAtGoal
{
    public <T extends MobEntity & IMerchant> LookAtCustomerGoal(T entityIn)
    {
        super(entityIn, PlayerEntity.class, 8.0F);
    }

    public boolean shouldExecute()
    {
        if(((IMerchant)this.entity).getCustomer() != null)
        {
            this.closestEntity = ((IMerchant)this.entity).getCustomer();
            return true;
        }
        return false;
    }
}
