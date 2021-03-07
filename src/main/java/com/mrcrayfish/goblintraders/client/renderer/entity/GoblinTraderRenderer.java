package com.mrcrayfish.goblintraders.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrcrayfish.goblintraders.client.renderer.entity.model.GoblinTraderModel;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

/**
 * Author: MrCrayfish
 */
public class GoblinTraderRenderer extends MobRenderer<AbstractGoblinEntity, GoblinTraderModel>
{
    public GoblinTraderRenderer(EntityRendererManager renderManagerIn)
    {
        super(renderManagerIn, new GoblinTraderModel(), 0.5F);
        this.addLayer(new HeldItemLayer<>(this));
    }

    @Override
    public ResourceLocation getEntityTexture(AbstractGoblinEntity entity)
    {
        return entity.getTexture();
    }

    @Override
    public void render(AbstractGoblinEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light)
    {
        matrixStack.push();
        if(entity.isStunned())
        {
            float progress = Math.min(10F, entity.getFallCounter() + partialTicks) / 10F;
            matrixStack.rotate(Vector3f.YP.rotationDegrees(-entity.getStunRotation()));
            matrixStack.rotate(Vector3f.XP.rotationDegrees(90F * progress));
            matrixStack.rotate(Vector3f.YP.rotationDegrees(entity.getStunRotation()));
        }
        super.render(entity, 0F, partialTicks, matrixStack, renderTypeBuffer, light);
        matrixStack.pop();
    }
}
