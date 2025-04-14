package com.mrcrayfish.goblintraders.trades;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.goblintraders.trades.price.BasePrice;
import com.mrcrayfish.goblintraders.trades.price.ConstantPrice;
import com.mrcrayfish.goblintraders.trades.price.RangedPrice;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.level.ItemLike;

@SuppressWarnings("deprecation")
public record TradeCost(Holder<Item> item, BasePrice count, DataComponentExactPredicate components)
{
    public static final Codec<TradeCost> CODEC = RecordCodecBuilder.create(builder -> builder.group(
        Item.CODEC.fieldOf("id").forGetter(TradeCost::item),
        BasePrice.CODEC.fieldOf("count").orElse(new ConstantPrice(1)).forGetter(TradeCost::count),
            DataComponentExactPredicate.CODEC.optionalFieldOf("components", DataComponentExactPredicate.EMPTY).forGetter(TradeCost::components)
    ).apply(builder, TradeCost::new));

    public TradeCost(ItemLike item)
    {
        this(item.asItem().builtInRegistryHolder(), new ConstantPrice(1), DataComponentExactPredicate.EMPTY);
    }

    public TradeCost(ItemLike item, int count)
    {
        this(item.asItem().builtInRegistryHolder(), new ConstantPrice(count), DataComponentExactPredicate.EMPTY);
    }

    public TradeCost(ItemLike item, int count, DataComponentExactPredicate components)
    {
        this(item.asItem().builtInRegistryHolder(), new ConstantPrice(count), components);
    }

    public TradeCost(ItemLike item, int min, int max)
    {
        this(item.asItem().builtInRegistryHolder(), new RangedPrice(min, max), DataComponentExactPredicate.EMPTY);
    }

    public TradeCost(ItemLike item, int min, int max, DataComponentExactPredicate components)
    {
        this(item.asItem().builtInRegistryHolder(), new RangedPrice(min, max), components);
    }

    public ItemCost createVanillaCost(RandomSource random)
    {
        return new ItemCost(this.item, this.count.get(random), this.components);
    }
}
