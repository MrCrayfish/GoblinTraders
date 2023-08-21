package com.mrcrayfish.goblintraders.item;

import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.SpawnEggItem;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class GoblinSpawnEggItem extends SpawnEggItem
{
    private final Supplier<EntityType<? extends AbstractGoblinEntity>> type;

    @SuppressWarnings("DataFlowIssue")
    public GoblinSpawnEggItem(Supplier<EntityType<? extends AbstractGoblinEntity>> type, int primaryColour, int secondaryColour, Properties properties)
    {
        super(null, primaryColour, secondaryColour, properties);
        this.type = type;
    }

    @Override
    public EntityType<?> getType(@Nullable CompoundTag tag)
    {
        return this.type.get();
    }

    @Override
    public FeatureFlagSet requiredFeatures()
    {
        return this.type.get().requiredFeatures();
    }
}
