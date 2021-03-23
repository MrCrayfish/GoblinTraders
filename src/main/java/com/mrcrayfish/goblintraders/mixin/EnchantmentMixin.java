package com.mrcrayfish.goblintraders.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
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

    @Inject(method = "getDisplayName", at = @At(value = "HEAD"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void append(int level, CallbackInfoReturnable<ITextComponent> cir)
    {
        this.enchantment = (Enchantment) (Object) this;
        this.level = level;
    }

    @ModifyVariable(method = "getDisplayName", at = @At(value = "RETURN"), index = 2, ordinal = 0)
    private ITextComponent append(ITextComponent component)
    {
        if(this.level > this.enchantment.getMaxLevel() && !this.enchantment.isCurse())
        {
            return component.applyTextStyle(TextFormatting.LIGHT_PURPLE);
        }
        return component;
    }
}
