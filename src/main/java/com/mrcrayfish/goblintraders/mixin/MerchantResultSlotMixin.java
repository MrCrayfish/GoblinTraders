package com.mrcrayfish.goblintraders.mixin;

import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.init.ModStats;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.inventory.container.MerchantResultSlot;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * Author: MrCrayfish
 */
@Mixin(MerchantResultSlot.class)
public class MerchantResultSlotMixin
{
    @Shadow
    @Final
    private IMerchant merchant;

    @ModifyArg(method = "onTake", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addStat(Lnet/minecraft/util/ResourceLocation;)V"))
    private ResourceLocation modifyAddStat(ResourceLocation stat)
    {
        if(this.merchant instanceof AbstractGoblinEntity)
        {
            return ModStats.TRADE_WITH_GOBLIN;
        }
        return stat;
    }
}
