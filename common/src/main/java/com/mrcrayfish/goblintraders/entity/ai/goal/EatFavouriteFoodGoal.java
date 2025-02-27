package com.mrcrayfish.goblintraders.entity.ai.goal;

import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.UseItemGoal;

import java.util.EnumSet;

/**
 * Author: MrCrayfish
 */
public class EatFavouriteFoodGoal extends UseItemGoal<AbstractGoblinEntity>
{
    public EatFavouriteFoodGoal(AbstractGoblinEntity goblin)
    {
        super(goblin, goblin.getFavouriteFood().copy(), SoundEvents.PLAYER_BURP, e -> {
            if(e.getItemBySlot(EquipmentSlot.MAINHAND).is(goblin.getFavouriteFood().getItem())) {
                return e.getRandom().nextInt(40) == 0 && !e.isStunned();
            }
            return e.getHealth() < e.getMaxHealth() && e.getRandom().nextInt(100) == 0 && !e.isStunned();
        });
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }
}
