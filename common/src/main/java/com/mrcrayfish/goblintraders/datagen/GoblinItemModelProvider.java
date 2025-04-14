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
        this.createSpawnEgg(ModItems.GOBLIN_TRADER_SPAWN_EGG.get(), 0x4DA744, 0x316F5D);
        this.createSpawnEgg(ModItems.VEIN_GOBLIN_TRADER_SPAWN_EGG.get(), 0xF3982E, 0xF45B1F);
    }

    private void createSpawnEgg(Item item, int primaryColour, int secondaryColour)
    {
        ResourceLocation spawnEggLocation = ModelLocationUtils.decorateItemModelLocation("template_spawn_egg");
        ItemModel.Unbaked unbaked = ItemModelUtils.tintedModel(spawnEggLocation, ItemModelUtils.constantTint(primaryColour), ItemModelUtils.constantTint(secondaryColour));
        this.items.put(item, this.createClientItem(unbaked));
    }
}
