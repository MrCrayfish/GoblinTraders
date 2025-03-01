package com.mrcrayfish.goblintraders.entity.ai.goal;

import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class FollowPotentialCustomerGoal extends Goal
{
    private static final int FOLLOW_TIME = 200;

    private Player potentialCustomer;
    private AbstractGoblinEntity entity;
    private int coolDown = 0;
    private int timeout = FOLLOW_TIME;

    public FollowPotentialCustomerGoal(AbstractGoblinEntity entity)
    {
        this.entity = entity;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse()
    {
        if(this.entity.getTradingPlayer() != null)
        {
            return false;
        }
        if(this.coolDown > 0)
        {
            this.coolDown--;
            return false;
        }
        if(this.entity.isStunned())
        {
            return false;
        }
        this.findCustomer();
        return this.potentialCustomer != null && this.potentialCustomer.isAlive() && !this.entity.isPreviousCustomer(this.potentialCustomer);
    }

    @Override
    public void tick()
    {
        this.entity.getLookControl().setLookAt(this.potentialCustomer, 20.0F, (float) this.entity.getHeadRotSpeed());
        if(this.entity.distanceTo(this.potentialCustomer) >= 2.0D)
        {
            this.goblin.getNavigation().moveTo(this.potentialCustomer, 1.0F);
        }
        this.timeout--;
    }

    @Override
    public boolean canContinueToUse()
    {
        return this.potentialCustomer != null && this.potentialCustomer.isAlive() && this.entity.getTradingPlayer() == null && !this.entity.isPreviousCustomer(this.potentialCustomer) && this.entity.distanceTo(this.potentialCustomer) <= 10.0D && this.timeout > 0;
    }

    @Override
    public void stop()
    {
        this.entity.getNavigation().stop();
        this.potentialCustomer = null;
        this.timeout = FOLLOW_TIME;
        this.coolDown = FOLLOW_TIME;
    }

    private void findCustomer()
    {
        List<Player> players = this.entity.level().getEntitiesOfClass(Player.class, this.entity.getBoundingBox().inflate(10), playerEntity -> !playerEntity.isSpectator());
        if(!players.isEmpty())
        {
            this.potentialCustomer = players.stream().min(Comparator.comparing(this.entity::distanceTo)).get();
        }
    }
}
