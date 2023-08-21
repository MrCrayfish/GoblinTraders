package com.mrcrayfish.goblintraders.datagen;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootTable;

/**
 * Author: MrCrayfish
 */
public class LootBuilder
{
    public interface Entity
    {
        /**
         * Adds a loot table to an entity with full customisation using a builder.
         *
         * @param type the entity type to target for the loot table
         */
        void add(EntityType<?> type, LootTable.Builder builder);
    }
}
