package com.mrcrayfish.goblintraders.item;

import com.google.common.collect.ImmutableList;
import com.mrcrayfish.goblintraders.mixin.SpawnEggItemMixin;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.DispenserBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class SupplierSpawnEggItem extends SpawnEggItem
{
    private static final List<SupplierSpawnEggItem> EGGS = new ArrayList<>();

    private final Supplier<EntityType<?>> typeSupplier;

    public SupplierSpawnEggItem(Supplier<EntityType<?>> typeSupplier, int primaryColorIn, int secondaryColorIn, Properties builder)
    {
        super(null, primaryColorIn, secondaryColorIn, builder);
        this.typeSupplier = typeSupplier;
        EGGS.add(this);
    }

    @Override
    public EntityType<?> getType(CompoundTag tag)
    {
        return this.typeSupplier.get();
    }

    public static void updateEggMap()
    {
        SpawnEggItemMixin.getEggMap().remove(null); //Removes null key since null was passed to super constructor
        DispenseItemBehavior eggDispenseBehaviour = (source, stack) -> {
            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            EntityType<?> type = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
            type.spawn(source.getLevel(), stack, null, source.getPos().relative(direction), MobSpawnType.DISPENSER, direction != Direction.UP, false);
            stack.shrink(1);
            return stack;
        };
        EGGS.forEach(spawnEggItem -> {
            SpawnEggItemMixin.getEggMap().put(spawnEggItem.typeSupplier.get(), spawnEggItem);
            DispenserBlock.registerBehavior(spawnEggItem, eggDispenseBehaviour);
        });
    }

    public static List<SupplierSpawnEggItem> getSupplierEggs()
    {
        return ImmutableList.copyOf(EGGS);
    }
}
