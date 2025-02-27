package com.mrcrayfish.goblintraders.trades.price;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;

public interface BasePrice
{
    Codec<BasePrice> CODEC = Codec.either(ConstantPrice.CODEC, RangedPrice.CODEC).xmap(Either::unwrap, number -> {
        if(number instanceof ConstantPrice fixed) {
            return Either.left(fixed);
        } else if(number instanceof RangedPrice ranged) {
            return Either.right(ranged);
        }
        throw new UnsupportedOperationException();
    });

    int get(RandomSource rand);
}
