package com.mrcrayfish.goblintraders.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

/**
 * Author: MrCrayfish
 */
public class GoblinTraderModel extends SegmentedModel<AbstractGoblinEntity> implements IHasArm, IHasHead
{
    public ModelRenderer head;
    public ModelRenderer hood;
    public ModelRenderer body;
    public ModelRenderer rightArm;
    public ModelRenderer leftArm;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;
    public ModelRenderer nose;
    public ModelRenderer rightEar;
    public ModelRenderer leftEar;
    public ModelRenderer bag;
    private final ImmutableList<ModelRenderer> parts;

    public GoblinTraderModel()
    {
        this.textureWidth = 46;
        this.textureHeight = 46;
        this.rightEar = new ModelRenderer(this, 0, 8);
        this.rightEar.setRotationPoint(-4.0F, -3.0F, 1.0F);
        this.rightEar.addBox(0.0F, -2.0F, 0.0F, 0, 4, 4, 0.0F);
        this.setRotateAngle(rightEar, 0.0F, -0.7853981633974483F, 0.0F);
        this.leftEar = new ModelRenderer(this, 8, 8);
        this.leftEar.setRotationPoint(4.0F, -3.0F, 1.0F);
        this.leftEar.addBox(0.0F, -2.0F, 0.0F, 0, 4, 4, 0.0F);
        this.setRotateAngle(leftEar, 0.0F, 0.7853981633974483F, 0.0F);
        this.rightLeg = new ModelRenderer(this, 26, 9);
        this.rightLeg.setRotationPoint(-1.0F, 20.0F, 0.0F);
        this.rightLeg.addBox(-1.0F, 0.0F, -1.5F, 2, 4, 3, 0.0F);
        this.rightArm = new ModelRenderer(this, 30, 0);
        this.rightArm.setRotationPoint(-3.0F, 17.0F, 0.0F);
        this.rightArm.addBox(-1.0F, -1.0F, -1.0F, 2, 5, 2, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.head.addBox(-4.0F, -6.0F, -3.0F, 8, 6, 6, 0.0F);
        this.body = new ModelRenderer(this, 12, 12);
        this.body.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.body.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
        this.nose = new ModelRenderer(this, 22, 0);
        this.nose.setRotationPoint(0.0F, -3.0F, -3.0F);
        this.nose.addBox(-1.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
        this.hood = new ModelRenderer(this, 0, 32);
        this.hood.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.hood.addBox(-4.0F, -6.0F, -3.0F, 8, 8, 6, 0.5F);
        this.bag = new ModelRenderer(this, 0, 20);
        this.bag.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bag.addBox(-2.5F, -2.0F, 2.0F, 5, 7, 3, 0.0F);
        this.leftArm = new ModelRenderer(this, 38, 0);
        this.leftArm.setRotationPoint(3.0F, 17.0F, 0.0F);
        this.leftArm.addBox(-1.0F, -1.0F, -1.0F, 2, 5, 2, 0.0F);
        this.leftLeg = new ModelRenderer(this, 36, 9);
        this.leftLeg.setRotationPoint(1.0F, 20.0F, 0.0F);
        this.leftLeg.addBox(-1.0F, 0.0F, -1.5F, 2, 4, 3, 0.0F);
        this.head.addChild(this.rightEar);
        this.head.addChild(this.leftEar);
        this.head.addChild(this.nose);
        this.body.addChild(this.bag);
        this.parts = ImmutableList.of(this.head, this.hood, this.body, this.leftArm, this.rightArm, this.leftLeg, this.rightLeg);
    }

    private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public Iterable<ModelRenderer> func_225601_a_()
    {
        return this.parts;
    }

    @Override
    public void render(AbstractGoblinEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch)
    {
        float rotateFactor;
        rotateFactor = (float) entity.getMotion().lengthSquared();
        rotateFactor = rotateFactor / 0.2F;
        rotateFactor = rotateFactor * rotateFactor * rotateFactor;
        if(rotateFactor < 1.0F)
        {
            rotateFactor = 1.0F;
        }
        this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / rotateFactor;
        this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / rotateFactor;
        this.rightArm.rotateAngleY = 0.0F;
        this.rightArm.rotateAngleZ = 0.0F;
        this.leftArm.rotateAngleY = 0.0F;
        this.leftArm.rotateAngleZ = 0.0F;
        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / rotateFactor;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / rotateFactor;
        this.head.rotateAngleY = headYaw * ((float) Math.PI / 180F);
        this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.hood.copyModelAngles(this.head);

        if(this.swingProgress > 0.0F)
        {
            ModelRenderer arm = this.rightArm;
            float progress = this.swingProgress;
            this.body.rotateAngleY = MathHelper.sin(MathHelper.sqrt(progress) * ((float)Math.PI * 2F)) * 0.2F;
            this.rightArm.rotateAngleY += this.body.rotateAngleY;
            this.leftArm.rotateAngleY += this.body.rotateAngleY;
            this.leftArm.rotateAngleX += this.body.rotateAngleY;
            progress = 1.0F - this.swingProgress;
            progress = progress * progress;
            progress = progress * progress;
            progress = 1.0F - progress;
            float f2 = MathHelper.sin(progress * (float)Math.PI);
            float f3 = MathHelper.sin(this.swingProgress * (float)Math.PI) * -(this.head.rotateAngleX - 0.7F) * 0.75F;
            arm.rotateAngleX = (float)((double)arm.rotateAngleX - ((double)f2 * 1.2D + (double)f3));
            arm.rotateAngleY += this.body.rotateAngleY * 2.0F;
            arm.rotateAngleZ += MathHelper.sin(this.swingProgress * (float)Math.PI) * -0.4F;
        }

        if(entity.isHandActive())
        {
            this.rightArm.rotateAngleX = (float) Math.toRadians(-90F + 5F * Math.sin(ageInTicks));
            this.leftArm.rotateAngleX = (float) Math.toRadians(-90F + 5F * Math.sin(ageInTicks));
            this.rightLeg.rotateAngleX = (float) Math.toRadians(-90F);
            this.rightLeg.rotateAngleY = (float) Math.toRadians(25F);
            this.leftLeg.rotateAngleX = (float) Math.toRadians(-90F);
            this.leftLeg.rotateAngleY = (float) Math.toRadians(-25F);
        }
        else
        {
            this.rightLeg.rotateAngleY = (float) Math.toRadians(0);
            this.leftLeg.rotateAngleY = (float) Math.toRadians(0);
        }
    }

    @Override
    public void func_225599_a_(HandSide handSide, MatrixStack matrixStack)
    {
        switch(handSide)
        {
            case LEFT:
                this.leftArm.setAnglesAndRotation(matrixStack);
                matrixStack.translate(-0.235, -0.15, 0.25);
                matrixStack.scale(0.75F, 0.75F, 0.75F);
                break;
            case RIGHT:
                this.rightArm.setAnglesAndRotation(matrixStack);
                matrixStack.translate(0.235, -0.15, 0.25);
                matrixStack.scale(0.75F, 0.75F, 0.75F);
                break;
        }
    }

    @Override
    public ModelRenderer getModelHead()
    {
        return this.head;
    }
}
