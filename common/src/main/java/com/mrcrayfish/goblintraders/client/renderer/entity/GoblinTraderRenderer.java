package com.mrcrayfish.goblintraders.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrcrayfish.goblintraders.client.renderer.entity.model.GoblinTraderModel;
import com.mrcrayfish.goblintraders.client.renderer.entity.state.GoblinRenderState;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.phys.Vec3;

/**
 * Author: MrCrayfish
 */
public class GoblinTraderRenderer extends MobRenderer<AbstractGoblinEntity, GoblinRenderState, GoblinTraderModel>
{
    public GoblinTraderRenderer(EntityRendererProvider.Context context)
    {
        super(context, new GoblinTraderModel(context.bakeLayer(GoblinModelLayers.GOBLIN_TRADER)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this));
    }

    @Override
    public GoblinRenderState createRenderState()
    {
        return new GoblinRenderState();
    }

    @Override
    public void extractRenderState(AbstractGoblinEntity goblin, GoblinRenderState state, float partialTick)
    {
        super.extractRenderState(goblin, state, partialTick);
        ArmedEntityRenderState.extractArmedEntityRenderState(goblin, state, this.itemModelResolver);
        state.sitting = goblin.isSitting();
        state.usingItem = goblin.isUsingItem();
        state.attackTime = goblin.getAttackAnim(partialTick);
        state.holdingItem = !goblin.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
        state.texture = goblin.getTexture();
        state.headTilt = goblin.getHeadTilt(partialTick);
        state.armAngle = goblin.getArmAngle(partialTick);
        state.stunned = goblin.isStunned();
        state.stunRot = goblin.getStunRotation();
        state.stunCounter = goblin.getFallCounter() + partialTick;
        state.alive = goblin.isAlive();
    }

    @Override
    public ResourceLocation getTextureLocation(GoblinRenderState state)
    {
        return state.texture;
    }

    @Override
    public void submit(GoblinRenderState renderState, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraState)
    {
        stack.pushPose();
        if(renderState.usingItem || renderState.sitting)
        {
            stack.translate(0, -0.17, 0);
        }
        if(renderState.stunned && renderState.alive)
        {
            float progress = Math.min(10F, renderState.stunCounter) / 10F;
            stack.mulPose(Axis.YP.rotationDegrees(-renderState.stunRot));
            stack.mulPose(Axis.XP.rotationDegrees(90F * progress));
            stack.mulPose(Axis.YP.rotationDegrees(renderState.stunRot));
        }
        super.submit(renderState, stack, collector, cameraState);
        stack.popPose();
    }
}
