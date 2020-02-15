package com.mrcrayfish.goblintraders.entity;

import com.mrcrayfish.goblintraders.Reference;
import com.mrcrayfish.goblintraders.init.ModEntities;
import net.minecraft.item.MerchantOffers;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class GoblinTraderEntity extends AbstractGoblinEntity
{
    public GoblinTraderEntity(World worldIn)
    {
        super(ModEntities.GOBLIN_TRADER, worldIn);
    }

    @Override
    public ResourceLocation getTexture()
    {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/goblin_trader.png");
    }

    @Override
    protected void populateTradeData()
    {
        MerchantOffers offers = this.getOffers();
        this.addTrades(offers, GoblinTrades.GOBLIN_TRADER.get(BASE_TRADES), Math.max(4, this.rand.nextInt(8) + 1));
        this.addTrades(offers, GoblinTrades.GOBLIN_TRADER.get(RARE_TRADES), Math.max(1, this.rand.nextInt(4) + 1));
    }
}
