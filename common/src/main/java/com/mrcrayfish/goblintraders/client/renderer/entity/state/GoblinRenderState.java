package com.mrcrayfish.goblintraders.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class GoblinRenderState extends ArmedEntityRenderState
{
    public boolean sitting;
    public boolean usingItem;
    public float attackTime;
    public boolean holdingItem;
    public Identifier texture;
    public float headTilt;
    public float armAngle;
    public boolean stunned;
    public float stunRot;
    public float stunCounter;
    public boolean alive;
}
