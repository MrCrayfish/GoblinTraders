package com.mrcrayfish.goblintraders.client.renderer.entity;

import com.mrcrayfish.goblintraders.client.renderer.entity.model.GoblinTraderModel;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
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
}
