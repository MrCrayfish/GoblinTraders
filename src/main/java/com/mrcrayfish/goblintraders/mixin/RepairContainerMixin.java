package com.mrcrayfish.goblintraders.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Iterator;
import java.util.Map;

/**
 * Fixes combining tools in an anvil reducing enchantment level to it's max level when the
 * level of the enchantment is higher than it's max level. For example, combining level five
 * efficiency pickaxe with a level six efficiency pickaxe (which is higher than the max) will
 * keep the enchantment at level six instead of changing to it's max level of five.
 *
 * Author: MrCrayfish
 */
@Mixin(RepairContainer.class)
public class RepairContainerMixin
{
    private int maxLevel;

    @SuppressWarnings("unchecked")
    @Inject(method = "updateRepairOutput", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;canApply(Lnet/minecraft/item/ItemStack;)Z", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    private void beforeCanApply(CallbackInfo ci, ItemStack leftOriginal, int enchantCost, int repairCost, int renameCost, ItemStack leftCopy, ItemStack rightOriginal, Map leftEnchantments, boolean enchantingItem, Map rightEnchantments, boolean combinedEnchants, boolean invalidRepair, Iterator var12, Enchantment enchantment, int leftEnchantmentLevel, int combinedEnchantmentLevel)
    {
        int maxLevel = this.getEnchantmentLevel(enchantment);
        int leftLevel = (int) leftEnchantments.getOrDefault(enchantment, 0);
        int rightLevel = (int) rightEnchantments.get(enchantment);
        this.maxLevel = Math.max(rightLevel, leftLevel);
        if(leftLevel == rightLevel && leftLevel < maxLevel)
        {
            this.maxLevel = rightLevel + 1;
        }
    }

    // Prevents mixin from targeting getMaxLevel
    private int getEnchantmentLevel(Enchantment enchantment)
    {
        return enchantment.getMaxLevel();
    }

    @ModifyArgs(method = "updateRepairOutput", at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", ordinal = 0))
    private void afterSetMaxLevel(Args args)
    {
        args.set(1, this.maxLevel);
    }
}
