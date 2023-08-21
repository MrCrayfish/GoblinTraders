package com.mrcrayfish.goblintraders.datagen;

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
        private final BiConsumer<EntityType<?>, LootTable.Builder> add;

        public Entity(BiConsumer<EntityType<?>, LootTable.Builder> add)
        {
            this.add = add;
        }

        @Override
        public void add(EntityType<?> type, LootTable.Builder builder)
        {
            this.add.accept(type, builder);
        }
    }
}
