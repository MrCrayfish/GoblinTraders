package com.mrcrayfish.goblintraders.trades.price;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;

public record RangedPrice(int min, int max) implements BasePrice
{
    public static final Codec<RangedPrice> CODEC = RecordCodecBuilder.create(builder -> builder.group(
        Codec.INT.fieldOf("min").forGetter(RangedPrice::min),
        Codec.INT.fieldOf("max").forGetter(RangedPrice::max)
    ).apply(builder, RangedPrice::new));

    @Override
    public int get(RandomSource rand)
    {
        return rand.nextIntBetweenInclusive(this.min, this.max);
    }
}
