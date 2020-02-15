package com.mrcrayfish.goblintraders.client;

import com.mrcrayfish.goblintraders.client.renderer.entity.GoblinTraderRenderer;
import com.mrcrayfish.goblintraders.init.ModEntities;
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
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.GOBLIN_TRADER, GoblinTraderRenderer::new);
    }
}
