package com.mrcrayfish.goblintraders.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrcrayfish.goblintraders.client.renderer.entity.model.GoblinTraderModel;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;

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
        if(entity.isHandActive())
        {
            matrixStack.translate(0, -0.15, 0);
        }
        if(entity.isStunned() && entity.isAlive())
        {
            float progress = Math.min(10F, entity.getFallCounter() + partialTicks) / 10F;
            matrixStack.rotate(Vector3f.field_229181_d_.func_229187_a_(-entity.getStunRotation()));
            matrixStack.rotate(Vector3f.field_229179_b_.func_229187_a_(90F * progress));
            matrixStack.rotate(Vector3f.field_229181_d_.func_229187_a_(entity.getStunRotation()));
        }
        super.render(entity, 0F, partialTicks, matrixStack, renderTypeBuffer, light);
        matrixStack.pop();
    }
}
