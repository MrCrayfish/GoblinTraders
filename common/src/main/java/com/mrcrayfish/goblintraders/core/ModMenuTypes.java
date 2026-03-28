package com.mrcrayfish.goblintraders.core;

import com.mrcrayfish.framework.api.registry.RegistryContainer;
import com.mrcrayfish.framework.api.registry.RegistryEntry;
import com.mrcrayfish.goblintraders.inventory.GoblinMerchantMenu;
import com.mrcrayfish.goblintraders.util.Utils;
import net.minecraft.world.inventory.MenuType;

/**
 * Author: MrCrayfish
 */
@RegistryContainer
public class ModMenuTypes
{
    public static final RegistryEntry<MenuType<GoblinMerchantMenu>> GOBLIN_MERCHANT = RegistryEntry.menuType(Utils.id("goblin_merchant"), GoblinMerchantMenu::new);
}
