package com.mrcrayfish.goblintraders.mixin;

import com.mrcrayfish.goblintraders.core.ModMenuTypes;
import com.mrcrayfish.goblintraders.inventory.GoblinMerchantMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Author: MrCrayfish
 */
@Mixin(AbstractContainerMenu.class)
public class AbstractContainerMenuMixin
{
    @Final
    @Mutable
    @Shadow
    private MenuType<?> menuType;

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void goblinTraders$onPostInit(@Nullable MenuType<?> type, int windowId, CallbackInfo ci)
    {
        AbstractContainerMenu menu = (AbstractContainerMenu) (Object) this;
        if(menu instanceof GoblinMerchantMenu)
        {
            this.menuType = ModMenuTypes.GOBLIN_MERCHANT.get();
        }
    }
}
