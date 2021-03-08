package com.mrcrayfish.goblintraders.mixin;

import net.minecraft.inventory.container.RepairContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Author: MrCrayfish
 */
@Mixin(RepairContainer.class)
public class RepairContainerMixin
{
    @Inject(method = "updateRepairOutput", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;canApply(Lnet/minecraft/item/ItemStack;)Z"), locals = LocalCapture.PRINT)
    private void beforeCanApply(CallbackInfo ci)
    {

    }
}
