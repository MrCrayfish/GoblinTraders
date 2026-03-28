package com.mrcrayfish.goblintraders.loot_functions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;

public class IncreaseDurabilityFunction extends LootItemConditionalFunction
{
    public static final MapCodec<IncreaseDurabilityFunction> MAP_CODEC = RecordCodecBuilder.mapCodec(builder ->
        commonFields(builder).and(Codec.DOUBLE.fieldOf("durability_scale").forGetter((f) -> f.durabilityScale))
    .apply(builder, IncreaseDurabilityFunction::new));

    private final double durabilityScale;

    private IncreaseDurabilityFunction(List<LootItemCondition> predicates, double durabilityScale)
    {
        super(predicates);
        this.durabilityScale = durabilityScale;
    }

    public MapCodec<IncreaseDurabilityFunction> codec()
    {
        return MAP_CODEC;
    }

    public ItemStack run(ItemStack stack, LootContext context)
    {
        stack.set(DataComponents.MAX_DAMAGE, (int) (stack.getMaxDamage() * this.durabilityScale));
        ItemStack.validateStrict(stack);
        return stack;
    }

    public static LootItemConditionalFunction.Builder<?> apply(double durabilityScale)
    {
        return simpleBuilder(conditions -> new IncreaseDurabilityFunction(conditions, durabilityScale));
    }
}