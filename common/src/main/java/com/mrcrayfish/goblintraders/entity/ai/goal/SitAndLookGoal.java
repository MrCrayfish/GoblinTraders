package com.mrcrayfish.goblintraders.entity.ai.goal;

import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class SitAndLookGoal extends Goal
{
    private static final int MIN_REST_TIME = 60;
    private static final int MAX_REST_TIME = 200;
    private static final double LOOK_RANGE = 8;

    private final AbstractGoblinEntity goblin;
    private final TargetingConditions conditions;
    private int timeout;
    protected @Nullable Entity focusAt;

    public SitAndLookGoal(AbstractGoblinEntity goblin)
    {
        this.goblin = goblin;
        this.conditions = TargetingConditions.forNonCombat().range(LOOK_RANGE);
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse()
    {
        if(this.goblin.isSitting())
            return false;

        return this.goblin.getRandom().nextInt(200) == 0;
    }

    @Override
    public boolean canContinueToUse()
    {
        return !this.goblin.isStunned() && this.goblin.isSitting() && this.timeout > 0;
    }

    @Override
    public void start()
    {
        this.goblin.setSitting(true);
        this.timeout = this.goblin.getRandom().nextIntBetweenInclusive(MIN_REST_TIME, MAX_REST_TIME);
    }

    @Override
    public void stop()
    {
        this.goblin.setSitting(false);
        this.goblin.setCurious(false);
        this.focusAt = null;
    }

    @Override
    public void tick()
    {
        this.timeout--;

        if(this.focusAt != null)
        {
            if(!this.goblin.isCurious())
            {
                if(this.goblin.getRandom().nextFloat() < 0.05F)
                {
                    this.goblin.setCurious(true);
                }
            }
        }
        else if(this.goblin.isCurious())
        {
            this.goblin.setCurious(false);
        }

        this.updateFocusTarget();
        if(this.focusAt != null)
        {
            this.goblin.getLookControl().setLookAt(this.focusAt.getX(), this.focusAt.getEyeY(), this.focusAt.getZ());
        }
    }

    private void updateFocusTarget()
    {
        if(this.focusAt == null)
        {
            if(this.goblin.getRandom().nextFloat() >= 0.05F)
                return;
            Level level = this.goblin.level();
            this.focusAt = level.getNearestEntity(level.getEntitiesOfClass(LivingEntity.class, this.goblin.getBoundingBox().inflate(LOOK_RANGE, 3.0, LOOK_RANGE), v -> true), this.conditions, this.goblin, this.goblin.getX(), this.goblin.getEyeY(), this.goblin.getZ());
        }
        if(this.focusAt != null && (!this.focusAt.isAlive() || this.focusAt.distanceToSqr(this.goblin) > LOOK_RANGE * LOOK_RANGE))
        {
            this.focusAt = null;
        }
    }
}
