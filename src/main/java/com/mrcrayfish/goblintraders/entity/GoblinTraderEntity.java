package com.mrcrayfish.goblintraders.entity;

import com.mrcrayfish.goblintraders.Reference;
import com.mrcrayfish.goblintraders.init.ModEntities;
import com.mrcrayfish.goblintraders.trades.EntityTrades;
import com.mrcrayfish.goblintraders.trades.TradeManager;
import com.mrcrayfish.goblintraders.trades.TradeRarity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffers;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class GoblinTraderEntity extends AbstractGoblinEntity
{
    public GoblinTraderEntity(World worldIn)
    {
        super(ModEntities.GOBLIN_TRADER.get(), worldIn);
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
        EntityTrades trades = TradeManager.instance().getTrades(ModEntities.GOBLIN_TRADER.get());
        if(trades != null)
        {
            Map<TradeRarity, List<VillagerTrades.ITrade>> tradeMap = trades.getTradeMap();
            this.addTrades(offers, tradeMap.get(TradeRarity.COMMON), tradeMap.get(TradeRarity.COMMON).size(), true);
            this.addTrades(offers, tradeMap.get(TradeRarity.UNCOMMON), Math.max(3, this.rand.nextInt(5) + 1), true);
            this.addTrades(offers, tradeMap.get(TradeRarity.RARE), Math.max(3, this.rand.nextInt(4) + 1), true);
        }
    }

    @Override
    public ItemStack getFavouriteFood()
    {
        return new ItemStack(Items.APPLE);
    }
}
