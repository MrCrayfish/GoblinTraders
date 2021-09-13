package com.mrcrayfish.goblintraders.entity.ai.goal;

import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

/**
 * Author: MrCrayfish
 */
public class FirePanicGoal extends Goal
{
    private final AbstractGoblinEntity goblin;
    private final double speed;
    private double randPosX;
    private double randPosY;
    private double randPosZ;

    public FirePanicGoal(AbstractGoblinEntity goblin, double speedIn)
    {
        this.goblin = goblin;
        this.speed = speedIn;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse()
    {
        if(this.goblin.isOnFire() && !this.goblin.isStunned())
        {
            BlockPos blockpos = this.getClosestWaterPos(this.goblin.level, this.goblin, 5, 4);
            if(blockpos != null)
            {
                this.randPosX = (double) blockpos.getX();
                this.randPosY = (double) blockpos.getY();
                this.randPosZ = (double) blockpos.getZ();
                return true;
            }
            return this.findRandomPosition();
        }
        return false;
    }

    @Override
    public void start()
    {
        this.goblin.getNavigation().moveTo(this.randPosX, this.randPosY, this.randPosZ, this.speed);
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
        {
            return false;
        }
        else
        {
            this.randPosX = randomPos.x;
            this.randPosY = randomPos.y;
            this.randPosZ = randomPos.z;
            return true;
        }
    }

    @Nullable
    private BlockPos getClosestWaterPos(BlockGetter blockGetter, Entity entityIn, int horizontalRange, int verticalRange)
    {
        BlockPos entityPos = entityIn.blockPosition();
        int entityX = entityPos.getX();
        int entityY = entityPos.getY();
        int entityZ = entityPos.getZ();
        float range = (float) (horizontalRange * horizontalRange * verticalRange * 2);
        BlockPos randomPos = null;
        BlockPos.MutableBlockPos currentPos = new BlockPos.MutableBlockPos();
        for(int x = entityX - horizontalRange; x <= entityX + horizontalRange; ++x)
        {
            for(int y = entityY - verticalRange; y <= entityY + verticalRange; ++y)
            {
                for(int z = entityZ - horizontalRange; z <= entityZ + horizontalRange; ++z)
                {
                    currentPos.set(x, y, z);
                    if(blockGetter.getFluidState(currentPos).is(FluidTags.WATER))
                    {
                        float f1 = (float) ((x - entityX) * (x - entityX) + (y - entityY) * (y - entityY) + (z - entityZ) * (z - entityZ));
                        if(f1 < range)
                        {
                            range = f1;
                            randomPos = new BlockPos(currentPos);
                        }
                    }
                }
            }
        }
        return randomPos;
    }
}
