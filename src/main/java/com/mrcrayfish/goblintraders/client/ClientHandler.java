package com.mrcrayfish.goblintraders.client;

import com.mrcrayfish.goblintraders.client.renderer.entity.GoblinTraderRenderer;
import com.mrcrayfish.goblintraders.init.ModEntities;
import com.mrcrayfish.goblintraders.init.ModItems;
import com.mrcrayfish.goblintraders.item.SupplierSpawnEggItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

/**
 * Author: MrCrayfish
 */
@OnlyIn(Dist.CLIENT)
public class ClientHandler
{
    public static void setup()
    {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.GOBLIN_TRADER.get(), GoblinTraderRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.VEIN_GOBLIN_TRADER.get(), GoblinTraderRenderer::new);
    }
}
