package com.mrcrayfish.goblintraders.entity;

import com.mrcrayfish.goblintraders.Config;
import com.mrcrayfish.goblintraders.Reference;
import com.mrcrayfish.goblintraders.init.ModEntities;
import com.mrcrayfish.goblintraders.trades.EntityTrades;
import com.mrcrayfish.goblintraders.trades.TradeManager;
import com.mrcrayfish.goblintraders.trades.TradeRarity;
import net.minecraft.core.particles.ParticleTypes;
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
public class VeinGoblinTraderEntity extends AbstractGoblinEntity
{
    public VeinGoblinTraderEntity(Level level)
    {
        super(ModEntities.VEIN_GOBLIN_TRADER.get(), level);
    }

    @Override
    public ResourceLocation getTexture()
    {
        return new ResourceLocation(Reference.MOD_ID, "textures/entity/vein_goblin_trader.png");
    }

    @Override
    protected void populateTradeData()
    {
        MerchantOffers offers = this.getOffers();
        EntityTrades entityTrades = TradeManager.instance().getTrades(ModEntities.VEIN_GOBLIN_TRADER.get());
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
        return new ItemStack(Items.CARROT);
    }

    @Override
    public void aiStep()
    {
        if(this.level.isClientSide() && this.tickCount % 2 == 0)
        {
            this.level.addParticle(ParticleTypes.FLAME, this.getX() - 0.5 + this.getRandom().nextDouble(), this.getY() + 0.5 - 0.5 + this.getRandom().nextDouble(), this.getZ() - 0.5 + this.getRandom().nextDouble(), 0, 0, 0);
        }
        super.aiStep();
    }

    @Override
    public boolean fireImmune()
    {
        return true;
    }

    @Override
    protected int getMaxRestockDelay()
    {
        return Config.COMMON.veinGoblinTrader.restockDelay.get();
    }

    @Override
    public boolean canAttackBack()
    {
        return Config.COMMON.veinGoblinTrader.canAttackBack.get();
    }

    @Override
    public int getAmbientSoundInterval()
    {
        return Config.COMMON.veinGoblinTrader.gruntNoiseInterval.get();
    }
}
