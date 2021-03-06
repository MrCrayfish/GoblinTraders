package com.mrcrayfish.goblintraders.item;

import com.google.common.collect.ImmutableList;
import com.mrcrayfish.goblintraders.mixin.SpawnEggItemMixin;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;

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
    public EntityType<?> getType(CompoundNBT tag)
    {
        return this.typeSupplier.get();
    }

    public static void updateEggMap()
    {
        SpawnEggItemMixin.getEggMap().remove(null); //Removes null key since null was passed to super constructor
        IDispenseItemBehavior eggDispenseBehaviour = (source, stack) -> {
            Direction direction = source.getBlockState().get(DispenserBlock.FACING);
            EntityType<?> type = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
            type.spawn(source.getWorld(), stack, null, source.getBlockPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
            stack.shrink(1);
            return stack;
        };
        EGGS.forEach(spawnEggItem -> {
            SpawnEggItemMixin.getEggMap().put(spawnEggItem.typeSupplier.get(), spawnEggItem);
            DispenserBlock.registerDispenseBehavior(spawnEggItem, eggDispenseBehaviour);
        });
    }

    public static List<SupplierSpawnEggItem> getSupplierEggs()
    {
        return ImmutableList.copyOf(EGGS);
    }
}
