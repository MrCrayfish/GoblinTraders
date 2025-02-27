package com.mrcrayfish.goblintraders.trades.type;

import com.mojang.serialization.Codec;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.trades.TradeManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.trading.MerchantOffer;

/**
 * Author: MrCrayfish
 */
public interface BaseTrade
{
    Codec<BaseTrade> CODEC = ResourceLocation.CODEC.dispatch(BaseTrade::getId, id -> TradeManager.instance().getTradeCodec(id));

    ResourceLocation getId();

    MerchantOffer createVanillaOffer(AbstractGoblinEntity abstractGoblinEntity, RandomSource random);
}
