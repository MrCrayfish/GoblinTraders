package com.mrcrayfish.goblintraders.entity.ai.goal;

import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.UseItemGoal;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvents;

import java.util.EnumSet;

/**
 * Author: MrCrayfish
 */
public class EatFavouriteFoodGoal extends UseItemGoal<AbstractGoblinEntity>
{
    public EatFavouriteFoodGoal(AbstractGoblinEntity entity)
    {
        super(entity, entity.getFavouriteFood().copy(), SoundEvents.ENTITY_PLAYER_BURP, entity1 -> entity1.getHealth() < entity1.getMaxHealth() && entity1.getRNG().nextInt(100) == 0 && !entity1.isStunned());
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }
}
