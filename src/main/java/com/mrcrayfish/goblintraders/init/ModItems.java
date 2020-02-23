package com.mrcrayfish.goblintraders.init;

import com.mrcrayfish.goblintraders.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems
{
    private static final List<Item> ITEMS = new ArrayList<>();

    public static final Item GOBLIN_TRADER_SPAWN_EGG = build(new ResourceLocation(Reference.MOD_ID, "goblin_trader_spawn_egg"), new SpawnEggItem(ModEntities.GOBLIN_TRADER, 0x4da744, 0x316f5d, new Item.Properties().group(ItemGroup.MISC)));

    private static Item build(ResourceLocation id, Item item)
    {
        item.setRegistryName(id);
        ITEMS.add(item);
        return item;
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void registerTypes(final RegistryEvent.Register<Item> event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();
        ITEMS.forEach(registry::register);
    }
}
