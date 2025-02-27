package com.mrcrayfish.goblintraders.entity.ai.goal;

import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
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
    private final AbstractGoblinEntity goblin;

    public FindFavouriteFoodGoal(AbstractGoblinEntity goblin)
    {
        this.goblin = goblin;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse()
    {
        this.findFavouriteFood();

        if(this.itemEntity == null || !this.itemEntity.isAlive())
            return false;

        Path path = this.goblin.getNavigation().createPath(this.itemEntity, 0);
        if(path == null || !path.canReach())
            return false;

        return !this.goblin.isStunned();
    }

    @Override
    public void tick()
    {
        if(this.goblin.isStunned())
            return;

        this.goblin.getLookControl().setLookAt(this.itemEntity, 10.0F, (float) this.goblin.getHeadRotSpeed());
        this.goblin.getNavigation().stop();
        Path path = this.goblin.getNavigation().createPath(this.itemEntity, 0);
        if(path != null) this.goblin.getNavigation().moveTo(path, 0.4F);
        if(this.goblin.distanceTo(this.itemEntity) <= 1.0D && this.itemEntity.isAlive())
        {
            this.itemEntity.remove(Entity.RemovalReason.KILLED);
            this.goblin.level().playSound(null, this.itemEntity.getX(), this.itemEntity.getY(), this.itemEntity.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 1.0F, 0.75F);
            this.goblin.setItemSlot(EquipmentSlot.MAINHAND, this.goblin.getFavouriteFood().copy());
        }
    }

    @Override
    public boolean canContinueToUse()
    {
        return this.itemEntity.isAlive() && this.goblin.getNavigation().createPath(this.itemEntity, 0) != null && this.goblin.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
    }

    private void findFavouriteFood()
    {
        List<ItemEntity> players = this.goblin.level().getEntitiesOfClass(ItemEntity.class, this.goblin.getBoundingBox().inflate(10), itemEntity -> itemEntity.getItem().getItem() == this.goblin.getFavouriteFood().getItem());
        if(!players.isEmpty())
        {
            this.itemEntity = players.stream().min(Comparator.comparing(this.goblin::distanceTo)).get();
        }
    }
}
