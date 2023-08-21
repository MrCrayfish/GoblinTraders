package com.mrcrayfish.goblintraders.client.renderer.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

/**
 * Author: MrCrayfish
 */
public class GoblinTraderModel extends HierarchicalModel<AbstractGoblinEntity> implements ArmedModel, HeadedModel
{
    public final ModelPart root;
    public final ModelPart head;
    public final ModelPart hood;
    public final ModelPart body;
    public final ModelPart rightArm;
    public final ModelPart leftArm;
    public final ModelPart rightLeg;
    public final ModelPart leftLeg;
    public final ModelPart nose;
    public final ModelPart rightEar;
    public final ModelPart leftEar;
    public final ModelPart bag;

    public GoblinTraderModel(ModelPart part)
    {
        this.root = part;
        this.head = part.getChild("head");
        this.hood = part.getChild("hood");
        this.body = part.getChild("body");
        this.rightArm = part.getChild("right_arm");
        this.leftArm = part.getChild("left_arm");
        this.rightLeg = part.getChild("right_leg");
        this.leftLeg = part.getChild("left_leg");
        this.nose = this.head.getChild("nose");
        this.rightEar = this.head.getChild("right_ear");
        this.leftEar = this.head.getChild("left_ear");
        this.bag = this.body.getChild("bag");
    }

    public static LayerDefinition createBodyLayer()
    {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().addBox(-4.0F, -6.0F, -3.0F, 8.0F, 6.0F, 6.0F), PartPose.offset(0.0F, 16.0F, 0.0F));
        head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 4.0F), PartPose.offsetAndRotation(-4.0F, -3.0F, 1.0F, 0.0F, -0.785F, 0.0F));
        head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(8, 8).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 4.0F), PartPose.offsetAndRotation(4.0F, -3.0F, 1.0F, 0.0F, 0.785F, 0.0F));
        head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F), PartPose.offset(0.0F, -3.0F, -3.0F));
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(12, 12).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F), PartPose.offset(0.0F, 16.0F, 0.0F));
        body.addOrReplaceChild("bag", CubeListBuilder.create().texOffs(0, 20).addBox(-2.5F, -2.0F, 2.0F, 5.0F, 7.0F, 3.0F), PartPose.ZERO);
        root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F), PartPose.offset(-3.0F, 17.0F, 0.0F));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(38, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F), PartPose.offset(3.0F, 17.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(26, 9).addBox(-1.0F, 0.0F, -1.5F, 2.0F, 4.0F, 3.0F), PartPose.offset(-1.0F, 20.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(36, 9).addBox(-1.0F, 0.0F, -1.5F, 2.0F, 4.0F, 3.0F), PartPose.offset(1.0F, 20.0F, 0.0F));
        root.addOrReplaceChild("hood", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, -6.0F, -3.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 16.0F, 0.0F));
        return LayerDefinition.create(mesh, 46, 46);
    }

    @Override
    public ModelPart root()
    {
        return this.root;
    }

    @Override
    public void setupAnim(AbstractGoblinEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch)
    {
        float rotateFactor;
        rotateFactor = (float) entity.getDeltaMovement().lengthSqr();
        rotateFactor = rotateFactor / 0.2F;
        rotateFactor = rotateFactor * rotateFactor * rotateFactor;
        if(rotateFactor < 1.0F)
        {
            rotateFactor = 1.0F;
        }
        this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / rotateFactor;
        this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / rotateFactor;
        this.rightArm.yRot = 0.0F;
        this.rightArm.zRot = 0.0F;
        this.leftArm.yRot = 0.0F;
        this.leftArm.zRot = 0.0F;
        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / rotateFactor;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / rotateFactor;
        this.head.yRot = headYaw * ((float) Math.PI / 180F);
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.hood.copyFrom(this.head);

        if(this.attackTime > 0.0F)
        {
            ModelPart arm = this.rightArm;
            float progress = this.attackTime;
            this.body.yRot = Mth.sin(Mth.sqrt(progress) * ((float) Math.PI * 2F)) * 0.2F;
            this.rightArm.yRot += this.body.yRot;
            this.leftArm.yRot += this.body.yRot;
            this.leftArm.xRot += this.body.yRot;
            progress = 1.0F - this.attackTime;
            progress = progress * progress;
            progress = progress * progress;
            progress = 1.0F - progress;
            float f2 = Mth.sin(progress * (float) Math.PI);
            float f3 = Mth.sin(this.attackTime * (float) Math.PI) * -(this.head.xRot - 0.7F) * 0.75F;
            arm.xRot = (float) ((double) arm.xRot - ((double) f2 * 1.2D + (double) f3));
            arm.yRot += this.body.yRot * 2.0F;
            arm.zRot += Mth.sin(this.attackTime * (float) Math.PI) * -0.4F;
        }

        if(entity.isUsingItem())
        {
            double rotateX = Math.toRadians(-90F + 5F * Math.sin(ageInTicks));
            this.rightArm.xRot = (float) rotateX;
            this.leftArm.xRot = (float) rotateX;
            this.rightLeg.xRot = (float) Math.toRadians(-90F);
            this.rightLeg.yRot = (float) Math.toRadians(25F);
            this.leftLeg.xRot = (float) Math.toRadians(-90F);
            this.leftLeg.yRot = (float) Math.toRadians(-25F);
        }
        else
        {
            this.rightLeg.yRot = (float) Math.toRadians(0);
            this.leftLeg.yRot = (float) Math.toRadians(0);
        }
    }

    @Override
    public void translateToHand(HumanoidArm arm, PoseStack poseStack)
    {
        switch(arm)
        {
            case LEFT -> {
                this.leftArm.translateAndRotate(poseStack);
                poseStack.translate(-0.235, -0.15, 0.25);
                poseStack.scale(0.75F, 0.75F, 0.75F);
            }
            case RIGHT -> {
                this.rightArm.translateAndRotate(poseStack);
                poseStack.translate(0.235, -0.15, 0.25);
                poseStack.scale(0.75F, 0.75F, 0.75F);
            }
        }
    }

    @Override
    public ModelPart getHead()
    {
        return this.head;
    }
}
