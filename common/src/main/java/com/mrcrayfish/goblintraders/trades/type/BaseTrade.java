package com.mrcrayfish.goblintraders.trades.type;

import com.mojang.serialization.Codec;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.trades.TradeManager;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.trading.MerchantOffer;

/**
 * Author: MrCrayfish
 */
public interface BaseTrade
{
    Codec<BaseTrade> CODEC = Identifier.CODEC.dispatch(BaseTrade::getId, id -> TradeManager.instance().getTradeCodec(id));

    Identifier getId();

    MerchantOffer createVanillaOffer(AbstractGoblinEntity goblin, RandomSource random);
}
