package com.mrcrayfish.goblintraders.entity.ai.goal;

import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;

import java.util.EnumSet;

/**
 * Author: MrCrayfish
 */
public class AttackRevengeTargetGoal extends Goal
{
    private AbstractGoblinEntity entity;

    public AttackRevengeTargetGoal(AbstractGoblinEntity entity)
    {
        this.entity = entity;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean shouldExecute()
    {
        return this.entity.canAttackBack() && this.entity.getRevengeTarget() != null && this.entity.getRevengeTarget().isAlive() && this.entity.getDistance(this.entity.getRevengeTarget()) <= 10.0F && (!(this.entity.getRevengeTarget() instanceof PlayerEntity) || !((PlayerEntity)this.entity.getRevengeTarget()).isCreative());
    }

    @Override
    public void tick()
    {
        LivingEntity revengeTarget = this.entity.getRevengeTarget();
        if(revengeTarget != null && this.entity.getCustomer() == null && !this.entity.isStunned())
        {
            this.entity.getLookController().setLookPositionWithEntity(revengeTarget, 10.0F, (float) this.entity.getVerticalFaceSpeed());
            if(this.entity.getDistance(revengeTarget) >= 1.5D)
            {
                this.entity.getNavigator().tryMoveToEntityLiving(revengeTarget, 0.5F);
            }
            else
            {
                revengeTarget.attackEntityFrom(DamageSource.causeMobDamage(this.entity), 1.0F);
                this.entity.swingArm(Hand.MAIN_HAND);
                this.entity.setRevengeTarget(null);
            }
        }
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return this.entity.getRevengeTarget() != null && this.entity.getRevengeTarget().isAlive() && this.entity.getDistance(this.entity.getRevengeTarget()) <= 10.0F && this.entity.getCustomer() == null;
    }

    @Override
    public void resetTask()
    {
        this.entity.setRevengeTarget(null);
    }
}
