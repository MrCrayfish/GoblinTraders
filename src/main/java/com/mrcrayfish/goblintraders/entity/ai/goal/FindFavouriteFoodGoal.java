package com.mrcrayfish.goblintraders.entity.ai.goal;

import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.pathfinder.Path;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class FindFavouriteFoodGoal extends Goal
{
    private ItemEntity itemEntity;
    private AbstractGoblinEntity entity;

    public FindFavouriteFoodGoal(AbstractGoblinEntity entity)
    {
        this.entity = entity;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse()
    {
        this.findFavouriteFood();
        return this.itemEntity != null && this.itemEntity.isAlive() && this.entity.getNavigation().createPath(this.itemEntity, 0) != null && !this.entity.isStunned();
    }

    @Override
    public void tick()
    {
        if(this.entity.isStunned())
            return;

        this.entity.getLookControl().setLookAt(this.itemEntity, 10.0F, (float) this.entity.getHeadRotSpeed());
        this.entity.getNavigation().stop();
        Path path = this.entity.getNavigation().createPath(this.itemEntity, 0);
        if(path != null) this.entity.getNavigation().moveTo(path, 0.4F);
        if(this.entity.distanceTo(this.itemEntity) <= 1.0D && this.itemEntity.isAlive())
        {
            this.itemEntity.remove(Entity.RemovalReason.KILLED);
            this.entity.level.playSound(null, this.itemEntity.getX(), this.itemEntity.getY(), this.itemEntity.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 1.0F, 0.75F);
        }
    }

    @Override
    public boolean canContinueToUse()
    {
        return this.itemEntity.isAlive() && this.entity.getNavigation().createPath(this.itemEntity, 0) != null;
    }

    @Nullable
    private void findFavouriteFood()
    {
        List<ItemEntity> players = this.entity.level.getEntitiesOfClass(ItemEntity.class, this.entity.getBoundingBox().inflate(10), itemEntity -> itemEntity.getItem().getItem() == this.entity.getFavouriteFood().getItem());
        if(players.size() > 0)
        {
            this.itemEntity = players.stream().min(Comparator.comparing(this.entity::distanceTo)).get();
        }
    }
}
