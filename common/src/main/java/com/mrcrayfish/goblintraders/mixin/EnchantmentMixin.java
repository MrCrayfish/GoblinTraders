package com.mrcrayfish.goblintraders.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Author: MrCrayfish
 */
@Mixin(Enchantment.class)
public class EnchantmentMixin
{
    @Unique
    private Enchantment goblinTraders$enchantment;

    @Unique
    private int goblinTraders$level;

    @Inject(method = "getFullname", at = @At(value = "HEAD"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void goblinTradersAppend(int level, CallbackInfoReturnable<Component> cir)
    {
        this.goblinTraders$enchantment = (Enchantment) (Object) this;
        this.goblinTraders$level = level;
    }

    @ModifyVariable(method = "getFullname", at = @At(value = "RETURN"), index = 2, ordinal = 0)
    private MutableComponent goblinTradersAppend(MutableComponent component)
    {
        if(this.goblinTraders$level > this.goblinTraders$enchantment.getMaxLevel() && !this.goblinTraders$enchantment.isCurse())
        {
            return component.withStyle(ChatFormatting.LIGHT_PURPLE);
        }
        return component;
    }
}
