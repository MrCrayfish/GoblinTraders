package com.mrcrayfish.goblintraders.entity.ai.goal;

import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.UseItemGoal;

import java.util.EnumSet;

/**
 * Author: MrCrayfish
 */
public class EatFavouriteFoodGoal extends UseItemGoal<AbstractGoblinEntity>
{
    public EatFavouriteFoodGoal(AbstractGoblinEntity entity)
    {
        super(entity, entity.getFavouriteFood().copy(), SoundEvents.PLAYER_BURP, entity1 -> entity1.getHealth() < entity1.getMaxHealth() && entity1.getRandom().nextInt(100) == 0 && !entity1.isStunned());
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }
}
