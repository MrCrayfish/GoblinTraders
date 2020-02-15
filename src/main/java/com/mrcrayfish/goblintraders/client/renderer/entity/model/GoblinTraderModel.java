package com.mrcrayfish.goblintraders.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

/**
 * Author: MrCrayfish
 */
public class GoblinTraderModel extends SegmentedModel<AbstractGoblinEntity>
{
    private ModelRenderer box;

    public GoblinTraderModel()
    {
       this.box = new ModelRenderer(this);
       this.box.addBox(16.0F, 16.0F, 16.0F, 0F, 0F, 0F, 0F);
    }

    @Override
    public Iterable<ModelRenderer> func_225601_a_()
    {
        return ImmutableList.of(this.box);
    }

    @Override
    public void render(AbstractGoblinEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_)
    {

    }
}
