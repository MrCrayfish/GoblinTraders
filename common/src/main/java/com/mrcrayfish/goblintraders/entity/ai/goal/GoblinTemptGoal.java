package com.mrcrayfish.goblintraders.entity.ai.goal;

import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Predicate;

public class GoblinTemptGoal extends TemptGoal
{
    private final AbstractGoblinEntity goblin;

    public GoblinTemptGoal(AbstractGoblinEntity goblin, double speedModifier, Ingredient ingredient, boolean canScare)
    {
        super(goblin, speedModifier, ingredient, canScare);
        this.goblin = goblin;
    }

    @Override
    public boolean canContinueToUse()
    {
        return super.canContinueToUse() && this.goblin.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
    }

    @Override
    public void tick()
    {
        super.tick();

        if(this.goblin.getNavigation().isDone())
        {
            this.goblin.setCurious(true);
        }
        else
        {
            this.goblin.setCurious(false);
        }
    }

    @Override
    public void stop()
    {
        super.stop();
        this.goblin.setCurious(false);
    }
}
