package com.mrcrayfish.goblintraders.entity.ai.goal;

import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.EnumSet;

/**
 * Author: MrCrayfish
 */
public class FirePanicGoal extends Goal
{
    private static final int HORIZONTAL_SEARCH_RANGE = 5;
    private static final int VERTICAL_SEARCH_RANGE = 2;

    private final AbstractGoblinEntity goblin;
    private final double speedModifier;
    private double randPosX;
    private double randPosY;
    private double randPosZ;

    public FirePanicGoal(AbstractGoblinEntity goblin, double speedModifier)
    {
        this.goblin = goblin;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse()
    {
        if(!this.goblin.isOnFire() || this.goblin.isStunned())
            return false;

        BlockPos blockpos = this.findClosestWaterPos();
        if(blockpos == null)
            return this.findRandomPosition();

        this.randPosX = blockpos.getX();
        this.randPosY = blockpos.getY();
        this.randPosZ = blockpos.getZ();
        return true;
    }

    @Override
    public void start()
    {
        this.goblin.getNavigation().moveTo(this.randPosX, this.randPosY, this.randPosZ, this.speedModifier);
    }

    @Override
    public boolean canContinueToUse()
    {
        return !this.goblin.getNavigation().isDone();
    }

    private boolean findRandomPosition()
    {
        Vec3 randomPos = DefaultRandomPos.getPos(this.goblin, 5, 4);
        if(randomPos == null)
            return false;
        this.randPosX = randomPos.x;
        this.randPosY = randomPos.y;
        this.randPosZ = randomPos.z;
        return true;
    }

    @Nullable
    private BlockPos findClosestWaterPos()
    {
        Level level = this.goblin.level();
        BlockPos entityPos = this.goblin.blockPosition();
        return BlockPos.findClosestMatch(entityPos, HORIZONTAL_SEARCH_RANGE, VERTICAL_SEARCH_RANGE, pos -> {
            return level.getFluidState(pos).is(FluidTags.WATER);
        }).orElse(null);
    }
}
