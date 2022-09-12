package com.mrcrayfish.goblintraders.entity.ai.goal;

import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

/**
 * Author: MrCrayfish
 */
public class TradeWithPlayerGoal extends Goal
{
    private final AbstractGoblinEntity entity;

    public TradeWithPlayerGoal(AbstractGoblinEntity entity)
    {
        this.entity = entity;
        this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse()
    {
        if(!this.entity.isAlive())
        {
            return false;
        }
        else if(this.entity.isInWater())
        {
            return false;
        }
        else if(!this.entity.isOnGround())
        {
            return false;
        }
        else if(this.entity.hurtMarked)
        {
            return false;
        }
        else
        {
            Player player = this.entity.getTradingPlayer();
            if(player == null)
            {
                return false;
            }
            else if(this.entity.distanceToSqr(player) > 16.0D)
            {
                return false;
            }
            return player.containerMenu != null;
        }
    }

    @Override
    public void start()
    {
        this.entity.getNavigation().stop();
    }

    @Override
    public void stop()
    {
        this.entity.setTradingPlayer(null);
    }
}
