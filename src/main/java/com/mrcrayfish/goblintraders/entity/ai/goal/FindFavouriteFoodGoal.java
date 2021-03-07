package com.mrcrayfish.goblintraders.entity.ai.goal;

import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

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
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean shouldExecute()
    {
        this.findFavouriteFood();
        return this.itemEntity != null && this.itemEntity.isAlive() && this.entity.getNavigator().getPathToEntity(this.itemEntity, 0) != null && !this.entity.isStunned();
    }

    @Override
    public void tick()
    {
        if(this.entity.isStunned())
            return;

        this.entity.getLookController().setLookPositionWithEntity(this.itemEntity, 10.0F, (float) this.entity.getVerticalFaceSpeed());
        this.entity.getNavigator().clearPath();
        Path path = this.entity.getNavigator().getPathToEntity(this.itemEntity, 0);
        if(path != null) this.entity.getNavigator().setPath(path, 0.5F);
        if(this.entity.getDistance(this.itemEntity) <= 1.0D && this.itemEntity.isAlive())
        {
            this.itemEntity.remove();
            this.entity.world.playSound(null, this.itemEntity.getPosX(), this.itemEntity.getPosY(), this.itemEntity.getPosZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.NEUTRAL, 1.0F, 0.75F);
        }
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return this.itemEntity.isAlive() && this.entity.getNavigator().getPathToEntity(this.itemEntity, 0) != null;
    }

    @Nullable
    private void findFavouriteFood()
    {
        List<ItemEntity> players = this.entity.world.getEntitiesWithinAABB(ItemEntity.class, this.entity.getBoundingBox().grow(10), itemEntity -> itemEntity.getItem().getItem() == this.entity.getFavouriteFood().getItem());
        if(players.size() > 0)
        {
            this.itemEntity = players.stream().min(Comparator.comparing(this.entity::getDistance)).get();
        }
    }
}
