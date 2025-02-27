package com.mrcrayfish.goblintraders.entity;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class GoblinRotationControl extends BodyRotationControl
{
    private final AbstractGoblinEntity goblin;
    private float lastHeadRotation;

    public GoblinRotationControl(AbstractGoblinEntity goblin)
    {
        super(goblin);
        this.goblin = goblin;
    }

    @Override
    public void clientTick()
    {
        if(!this.goblin.isSitting())
        {
            super.clientTick();
            return;
        }

        if(Math.abs(this.goblin.yHeadRot - this.goblin.yBodyRot) > this.goblin.getMaxHeadYRot())
        {
            this.goblin.yBodyRot = Mth.rotateIfNecessary(this.goblin.yBodyRot, this.goblin.yHeadRot, (float) this.goblin.getMaxHeadYRot());
        }
    }
}
