package com.mrcrayfish.goblintraders.mixin;

import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.init.ModStats;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MerchantResultSlot;
import net.minecraft.world.item.trading.Merchant;
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
    private Merchant merchant;

    @ModifyArg(method = "onTake", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;awardStat(Lnet/minecraft/resources/ResourceLocation;)V"))
    private ResourceLocation modifyAddStat(ResourceLocation stat)
    {
        if(this.merchant instanceof AbstractGoblinEntity)
        {
            return ModStats.TRADE_WITH_GOBLIN;
        }
        return stat;
    }
}
