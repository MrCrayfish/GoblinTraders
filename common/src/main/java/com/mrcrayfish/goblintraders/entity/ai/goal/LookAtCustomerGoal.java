package com.mrcrayfish.goblintraders.entity.ai.goal;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.Merchant;

/**
 * Author: MrCrayfish
 */
public class LookAtCustomerGoal extends LookAtPlayerGoal
{
    public <T extends Mob & Merchant> LookAtCustomerGoal(T entityIn)
    {
        super(entityIn, Player.class, 8.0F);
    }

    @Override
    public boolean canUse()
    {
        if(((Merchant) this.mob).getTradingPlayer() != null)
        {
            this.lookAt = ((Merchant) this.mob).getTradingPlayer();
            return true;
        }
        return false;
    }
}
