package com.mrcrayfish.goblintraders.entity.ai.goal;

import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;

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
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute()
    {
        if(this.goblin.isBurning() && !this.goblin.isStunned())
        {
            BlockPos blockpos = this.getClosestWaterPos(this.goblin.world, this.goblin, 5, 4);
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
    public void startExecuting()
    {
        this.goblin.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return !this.goblin.getNavigator().noPath();
    }

    private boolean findRandomPosition()
    {
        Vec3d randomPos = RandomPositionGenerator.findRandomTarget(this.goblin, 5, 4);
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
    private BlockPos getClosestWaterPos(IBlockReader worldIn, Entity entityIn, int horizontalRange, int verticalRange)
    {
        BlockPos entityPos = entityIn.getPosition();
        int entityX = entityPos.getX();
        int entityY = entityPos.getY();
        int entityZ = entityPos.getZ();
        float range = (float) (horizontalRange * horizontalRange * verticalRange * 2);
        BlockPos randomPos = null;
        BlockPos.Mutable currentPos = new BlockPos.Mutable();
        for(int x = entityX - horizontalRange; x <= entityX + horizontalRange; ++x)
        {
            for(int y = entityY - verticalRange; y <= entityY + verticalRange; ++y)
            {
                for(int z = entityZ - horizontalRange; z <= entityZ + horizontalRange; ++z)
                {
                    currentPos.setPos(x, y, z);
                    if(worldIn.getFluidState(currentPos).isTagged(FluidTags.WATER))
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
