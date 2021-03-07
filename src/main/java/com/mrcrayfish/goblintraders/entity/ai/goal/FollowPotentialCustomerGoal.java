package com.mrcrayfish.goblintraders.entity.ai.goal;

import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.init.ModSounds;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;

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
    private int hitTimer = 200;
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
        this.entity.getLookController().setLookPositionWithEntity(this.potentialCustomer, 10.0F, (float) this.entity.getVerticalFaceSpeed());
        if(this.entity.getDistance(this.potentialCustomer) >= 2.0D)
        {
            this.entity.getNavigator().tryMoveToEntityLiving(this.potentialCustomer, 0.4F);
        }
        else if(!this.hitOnce)
        {
            if(this.hitTimer-- == 0)
            {
                this.entity.world.playSound(null, this.entity.getPosX(), this.entity.getPosY(), this.entity.getPosZ(), ModSounds.ENTITY_GOBLIN_TRADER_ANNOYED_GRUNT.get(), SoundCategory.NEUTRAL, 1.0F, 0.9F + this.entity.getRNG().nextFloat() * 0.2F);
                this.potentialCustomer.attackEntityFrom(DamageSource.causeMobDamage(this.entity), 0.5F);
                this.entity.swingArm(Hand.MAIN_HAND);
                this.hitOnce = true;
                //TODO play a sound
            }
        }
        this.timeout--;
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return this.potentialCustomer != null && this.potentialCustomer.isAlive() && this.entity.getCustomer() == null && !this.entity.isPreviousCustomer(this.potentialCustomer) && this.entity.getDistance(this.potentialCustomer) <= 10.0D && this.timeout > 0;
    }

    @Override
    public void resetTask()
    {
        this.entity.getNavigator().clearPath();
        this.potentialCustomer = null;
        this.hitOnce = false;
        this.timeout = 600;
        this.coolDown = 300;
        this.hitTimer = 200;
    }

    @Nullable
    private void findCustomer()
    {
        List<PlayerEntity> players = this.entity.world.getEntitiesWithinAABB(PlayerEntity.class, this.entity.getBoundingBox().grow(10), playerEntity -> !playerEntity.isCreative() && !playerEntity.isSpectator());
        if(players.size() > 0)
        {
            this.potentialCustomer = players.stream().min(Comparator.comparing(this.entity::getDistance)).get();
        }
    }
}
