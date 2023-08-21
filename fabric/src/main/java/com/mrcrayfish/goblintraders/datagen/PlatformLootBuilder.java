package com.mrcrayfish.goblintraders.datagen;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.function.BiConsumer;

/**
 * Author: MrCrayfish
 */
public class PlatformLootBuilder
{
    public static class Entity implements LootBuilder.Entity
    {
        private final BiConsumer<ResourceLocation, LootTable.Builder> consumer;

        public Entity(BiConsumer<ResourceLocation, LootTable.Builder> consumer)
        {
            this.consumer = consumer;
        }

        @Override
        public void add(EntityType<?> type, LootTable.Builder builder)
        {
            this.consumer.accept(EntityType.getKey(type), builder);
        }
    }
}
