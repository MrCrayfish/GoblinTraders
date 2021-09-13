package com.mrcrayfish.goblintraders.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
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
    private Enchantment enchantment;
    private int level;

    @Inject(method = "getFullname", at = @At(value = "HEAD"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void append(int level, CallbackInfoReturnable<Component> cir)
    {
        this.enchantment = (Enchantment) (Object) this;
        this.level = level;
    }

    @ModifyVariable(method = "getFullname", at = @At(value = "RETURN"), index = 2, ordinal = 0)
    private MutableComponent append(MutableComponent component)
    {
        if(this.level > this.enchantment.getMaxLevel() && !this.enchantment.isCurse())
        {
            return component.withStyle(ChatFormatting.LIGHT_PURPLE);
        }
        return component;
    }
}
