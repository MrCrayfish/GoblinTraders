package com.mrcrayfish.goblintraders.entity;

import com.mrcrayfish.goblintraders.Config;
import com.mrcrayfish.goblintraders.Reference;
import com.mrcrayfish.goblintraders.init.ModEntities;
import com.mrcrayfish.goblintraders.trades.EntityTrades;
import com.mrcrayfish.goblintraders.trades.TradeManager;
import com.mrcrayfish.goblintraders.trades.TradeRarity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class GoblinTraderEntity extends AbstractGoblinEntity
{
    public GoblinTraderEntity(Level level)
    {
        super(ModEntities.GOBLIN_TRADER.get(), level);
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
        EntityTrades entityTrades = TradeManager.instance().getTrades(ModEntities.GOBLIN_TRADER.get());
        if(entityTrades != null)
        {
            Map<TradeRarity, List<VillagerTrades.ItemListing>> tradeMap = entityTrades.getTradeMap();
            for(TradeRarity rarity : TradeRarity.values())
            {
                List<VillagerTrades.ItemListing> trades = tradeMap.get(rarity);
                int min = rarity.getMaximum().apply(trades, this.getRandom());
                int max = rarity.getMaximum().apply(trades, this.getRandom());
                this.addTrades(offers, trades, Math.max(min, max), rarity.shouldShuffle());
            }
        }
    }

    @Override
    public ItemStack getFavouriteFood()
    {
        return new ItemStack(Items.APPLE);
    }

    @Override
    protected int getMaxRestockDelay()
    {
        return Config.COMMON.goblinTrader.restockDelay.get();
    }

    @Override
    public boolean canAttackBack()
    {
        return Config.COMMON.goblinTrader.canAttackBack.get();
    }

    @Override
    public int getAmbientSoundInterval()
    {
        return Config.COMMON.goblinTrader.gruntNoiseInterval.get();
    }
}
