package com.mrcrayfish.goblintraders.client.renderer.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrcrayfish.goblintraders.client.renderer.entity.state.GoblinRenderState;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Author: MrCrayfish
 */
public class GoblinTraderModel extends EntityModel<GoblinRenderState> implements ArmedModel, HeadedModel
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
        super(part);
        this.root = part;
        this.body = part.getChild("body");
        this.head = part.getChild("head");
        this.hood = this.head.getChild("hood");
        this.rightArm = this.body.getChild("right_arm");
        this.leftArm = this.body.getChild("left_arm");
        this.rightLeg = this.body.getChild("right_leg");
        this.leftLeg = this.body.getChild("left_leg");
        this.nose = this.head.getChild("nose");
        this.rightEar = this.head.getChild("right_ear");
        this.leftEar = this.head.getChild("left_ear");
        this.bag = this.body.getChild("bag");
    }

    public static LayerDefinition createBodyLayer()
    {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(12, 12).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));
        body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 1.0F, 0.0F));
        body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(38, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 1.0F, 0.0F));
        body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(26, 9).addBox(-1.0F, 0.0F, -1.5F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 4.0F, 0.0F));
        body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(36, 9).addBox(-1.0F, 0.0F, -1.5F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 4.0F, 0.0F));
        body.addOrReplaceChild("bag", CubeListBuilder.create().texOffs(0, 20).addBox(-2.5F, -3.0F, 0.0F, 5.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 2.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -6.0F, -3.0F, 8.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));
        head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -3.0F));
        head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -3.0F, 1.0F, 0.0F, -0.7854F, 0.0F));
        head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(8, 8).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -3.0F, 1.0F, 0.0F, 0.7854F, 0.0F));
        head.addOrReplaceChild("hood", CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, -14.0F, -9.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 8.0F, 6.0F));

        return LayerDefinition.create(mesh, 46, 46);
    }

    @Override
    public void setupAnim(GoblinRenderState state)
    {
        super.setupAnim(state);

        float walkPos = state.walkAnimationPos;
        float walkSpeed = state.walkAnimationSpeed;

        if(!state.holdingItem)
        {
            this.rightArm.xRot = Mth.cos(walkPos * 0.6662F + (float) Math.PI) * 2.0F * walkSpeed * 0.5F;
            this.leftArm.xRot = Mth.cos(walkPos * 0.6662F) * 2.0F * walkSpeed * 0.5F;
        }
        this.rightLeg.xRot = Mth.cos(walkPos * 0.6662F) * 1.4F * walkSpeed;
        this.leftLeg.xRot = Mth.cos(walkPos * 0.6662F + (float) Math.PI) * 1.4F * walkSpeed;

        this.rightArm.xRot -= (float) Math.toRadians(state.armAngle);
        this.leftArm.xRot -= (float) Math.toRadians(state.armAngle);

        Quaternionf quaternionYaw = new Quaternionf().rotationY(org.joml.Math.toRadians(state.yRot));
        Quaternionf quaternionPitch = new Quaternionf().rotationX(org.joml.Math.toRadians(state.xRot));
        Quaternionf quaternionRoll = new Quaternionf().rotationZ(org.joml.Math.toRadians(state.headTilt));
        Quaternionf finalRotation = new Quaternionf(quaternionPitch).mul(quaternionYaw).mul(quaternionRoll);
        Vector3f euler = new Vector3f();
        finalRotation.getEulerAnglesZXY(euler);
        this.head.xRot = euler.x;
        this.head.yRot = euler.y;
        this.head.zRot = euler.z;

        if(state.attackTime > 0.0F)
        {
            ModelPart arm = this.rightArm;
            float progress = state.attackTime;
            this.body.yRot = Mth.sin(Mth.sqrt(progress) * ((float) Math.PI * 2F)) * 0.2F;
            this.rightArm.yRot += this.body.yRot;
            this.leftArm.yRot += this.body.yRot;
            this.leftArm.xRot += this.body.yRot;
            progress = 1.0F - state.attackTime;
            progress = progress * progress;
            progress = progress * progress;
            progress = 1.0F - progress;
            float f2 = Mth.sin(progress * (float) Math.PI);
            float f3 = Mth.sin(state.attackTime * (float) Math.PI) * -(this.head.xRot - 0.7F) * 0.75F;
            arm.xRot = (float) ((double) arm.xRot - ((double) f2 * 1.2D + (double) f3));
            arm.yRot += this.body.yRot * 2.0F;
            arm.zRot += Mth.sin(state.attackTime * (float) Math.PI) * -0.4F;
        }

        if(state.sitting || state.usingItem)
        {
            this.rightLeg.xRot = (float) Math.toRadians(-90F);
            this.rightLeg.yRot = (float) Math.toRadians(30F);
            this.leftLeg.xRot = (float) Math.toRadians(-90F);
            this.leftLeg.yRot = (float) Math.toRadians(-30F);

            if(state.usingItem)
            {
                double rotateX = Math.toRadians(-90F + 5F * Math.sin(state.ageInTicks));
                this.rightArm.xRot = (float) rotateX;
                this.leftArm.xRot = (float) rotateX;
            }
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
                this.body.translateAndRotate(poseStack);
                this.leftArm.translateAndRotate(poseStack);
                poseStack.translate(-0.235, -0.15, 0.25);
                poseStack.scale(0.75F, 0.75F, 0.75F);
            }
            case RIGHT -> {
                this.body.translateAndRotate(poseStack);
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
