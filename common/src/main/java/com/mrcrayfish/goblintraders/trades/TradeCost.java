package com.mrcrayfish.goblintraders.trades;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.goblintraders.trades.price.BasePrice;
import com.mrcrayfish.goblintraders.trades.price.ConstantPrice;
import com.mrcrayfish.goblintraders.trades.price.RangedPrice;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.level.ItemLike;

@SuppressWarnings("deprecation")
public record TradeCost(Holder<Item> item, BasePrice count, DataComponentPredicate components)
{
    public static final Codec<TradeCost> CODEC = RecordCodecBuilder.create(builder -> builder.group(
        ItemStack.ITEM_NON_AIR_CODEC.fieldOf("id").forGetter(TradeCost::item),
        BasePrice.CODEC.fieldOf("count").orElse(new ConstantPrice(1)).forGetter(TradeCost::count),
        DataComponentPredicate.CODEC.optionalFieldOf("components", DataComponentPredicate.EMPTY).forGetter(TradeCost::components)
    ).apply(builder, TradeCost::new));

    public TradeCost(ItemLike item)
    {
        this(item.asItem().builtInRegistryHolder(), new ConstantPrice(1), DataComponentPredicate.EMPTY);
    }

    public TradeCost(ItemLike item, int count)
    {
        this(item.asItem().builtInRegistryHolder(), new ConstantPrice(count), DataComponentPredicate.EMPTY);
    }

    public TradeCost(ItemLike item, int count, DataComponentPredicate components)
    {
        this(item.asItem().builtInRegistryHolder(), new ConstantPrice(count), components);
    }

    public TradeCost(ItemLike item, int min, int max)
    {
        this(item.asItem().builtInRegistryHolder(), new RangedPrice(min, max), DataComponentPredicate.EMPTY);
    }

    public TradeCost(ItemLike item, int min, int max, DataComponentPredicate components)
    {
        this(item.asItem().builtInRegistryHolder(), new RangedPrice(min, max), components);
    }

    public ItemCost createVanillaCost(RandomSource random)
    {
        return new ItemCost(this.item, this.count.get(random), this.components);
    }
}
