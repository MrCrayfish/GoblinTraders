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
public class EatAppleGoal extends UseItemGoal<AbstractGoblinEntity>
{
    public EatAppleGoal(AbstractGoblinEntity entity)
    {
        super(entity, new ItemStack(Items.APPLE), SoundEvents.ENTITY_PLAYER_BURP, entity1 -> entity1.getHealth() < entity1.getMaxHealth() && entity1.getRNG().nextInt(100) == 0); //TODO change sound to a burp
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }
}
