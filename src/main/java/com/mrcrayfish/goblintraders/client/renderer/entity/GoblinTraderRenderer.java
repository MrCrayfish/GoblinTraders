package com.mrcrayfish.goblintraders.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mrcrayfish.goblintraders.client.renderer.entity.model.GoblinTraderModel;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public class GoblinTraderRenderer extends MobRenderer<AbstractGoblinEntity, GoblinTraderModel>
{
    public GoblinTraderRenderer(EntityRendererProvider.Context context)
    {
        super(context, new GoblinTraderModel(context.bakeLayer(GoblinModelLayers.GOBLIN_TRADER)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractGoblinEntity entity)
    {
        return entity.getTexture();
    }

    public void render(AbstractGoblinEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource source, int light)
    {
        poseStack.pushPose();
        if(entity.isUsingItem())
        {
            poseStack.translate(0, -0.15, 0);
        }
        if(entity.isStunned() && entity.isAlive())
        {
            float progress = Math.min(10F, entity.getFallCounter() + partialTicks) / 10F;
            poseStack.mulPose(Vector3f.YP.rotationDegrees(-entity.getStunRotation()));
            poseStack.mulPose(Vector3f.XP.rotationDegrees(90F * progress));
            poseStack.mulPose(Vector3f.YP.rotationDegrees(entity.getStunRotation()));
        }
        super.render(entity, 0F, partialTicks, poseStack, source, light);
        poseStack.popPose();
    }
}
