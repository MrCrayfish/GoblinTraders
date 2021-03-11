package com.mrcrayfish.goblintraders.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
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

    @Inject(method = "getDisplayName", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/text/IFormattableTextComponent;append(Lnet/minecraft/util/text/ITextComponent;)Lnet/minecraft/util/text/IFormattableTextComponent;", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    private void append(int level, CallbackInfoReturnable<ITextComponent> cir)
    {
        this.enchantment = (Enchantment) (Object) this;
        this.level = level;
    }

    @Redirect(method = "getDisplayName", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/text/IFormattableTextComponent;append(Lnet/minecraft/util/text/ITextComponent;)Lnet/minecraft/util/text/IFormattableTextComponent;", ordinal = 0))
    private IFormattableTextComponent append(IFormattableTextComponent textComponent, ITextComponent sibling)
    {
        if(this.level > this.enchantment.getMaxLevel())
        {
            return textComponent.append(sibling).mergeStyle(TextFormatting.LIGHT_PURPLE);
        }
        return textComponent.append(sibling);
    }
}
