package com.mrcrayfish.goblintraders.entity.ai.goal;

import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.entity.GoblinTraderEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class FollowPotentialCustomerGoal extends Goal
{
    private PlayerEntity potentialCustomer;
    private AbstractGoblinEntity entity;
    private boolean hitOnce = false;
    private int coolDown = 0;
    private int timeout = 600;

    public FollowPotentialCustomerGoal(AbstractGoblinEntity entity)
    {
        this.entity = entity;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean shouldExecute()
    {
        if(this.entity.getCustomer() != null)
        {
            return false;
        }
        if(this.coolDown > 0)
        {
            this.coolDown--;
            return false;
        }
        this.findCustomer();
        return this.potentialCustomer != null && this.potentialCustomer.isAlive();
    }

    @Override
    public void tick()
    {
        this.entity.getLookController().setLookPositionWithEntity(this.potentialCustomer, 10.0F, (float) this.entity.getVerticalFaceSpeed());
        if(this.entity.getDistance(this.potentialCustomer) >= 2.0D)
        {
            this.entity.getNavigator().tryMoveToEntityLiving(this.potentialCustomer, 0.4F);
        }
        else if(!this.hitOnce)
        {
            this.potentialCustomer.attackEntityFrom(DamageSource.causeMobDamage(this.entity), 0.5F);
            this.hitOnce = true;
            //TODO play a sound
        }
        this.timeout--;
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return this.potentialCustomer != null && this.potentialCustomer.isAlive() && this.entity.getCustomer() == null && this.timeout > 0;
    }

    @Override
    public void resetTask()
    {
        this.potentialCustomer = null;
        this.hitOnce = false;
        this.timeout = 600;
        this.coolDown = 300;
    }

    @Nullable
    private void findCustomer()
    {
        List<PlayerEntity> players = this.entity.world.getEntitiesWithinAABB(PlayerEntity.class, this.entity.getBoundingBox().grow(10));
        if(players.size() > 0)
        {
            this.potentialCustomer = players.stream().min(Comparator.comparing(this.entity::getDistance)).get();
        }
    }
}
