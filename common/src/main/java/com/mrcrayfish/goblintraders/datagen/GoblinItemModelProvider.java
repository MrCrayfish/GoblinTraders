package com.mrcrayfish.goblintraders.datagen;

import com.mrcrayfish.framework.api.datagen.FrameworkGenerator;
import com.mrcrayfish.goblintraders.core.ModItems;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
public class GoblinItemModelProvider extends FrameworkGenerator
{
    public GoblinItemModelProvider(Map<Block, BlockModelDefinitionGenerator> generators, Map<Item, ClientItem> items, Map<ResourceLocation, ModelInstance> models)
    {
        super(generators, items, models);
    }

    @Override
    public void generate()
    {
        this.flatItemModel(ModItems.GOBLIN_TRADER_SPAWN_EGG.get());
        this.flatItemModel(ModItems.VEIN_GOBLIN_TRADER_SPAWN_EGG.get());
    }
}
