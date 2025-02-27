package com.mrcrayfish.goblintraders.trades.price;

import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;

public record ConstantPrice(int value) implements BasePrice
{
    public static final Codec<ConstantPrice> CODEC = Codec.INT.xmap(ConstantPrice::new, ConstantPrice::value);

    @Override
    public int get(RandomSource rand)
    {
        return this.value;
    }
}
