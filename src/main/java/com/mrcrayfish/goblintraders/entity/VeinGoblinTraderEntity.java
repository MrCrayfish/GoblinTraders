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
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class VeinGoblinTraderEntity extends AbstractGoblinEntity
{
    public VeinGoblinTraderEntity(World worldIn)
    {
        super(ModEntities.VEIN_GOBLIN_TRADER.get(), worldIn);
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
        EntityTrades trades = TradeManager.instance().getTrades(ModEntities.VEIN_GOBLIN_TRADER.get());
        if(trades != null)
        {
            Map<TradeRarity, List<VillagerTrades.ITrade>> tradeMap = trades.getTradeMap();
            this.addTrades(offers, tradeMap.get(TradeRarity.COMMON), tradeMap.get(TradeRarity.COMMON).size(), true);
            this.addTrades(offers, tradeMap.get(TradeRarity.UNCOMMON), Math.max(3, this.rand.nextInt(5) + 1), true);
            this.addTrades(offers, tradeMap.get(TradeRarity.RARE), Math.max(2, this.rand.nextInt(3) + 1), true);
        }
    }

    @Override
    public ItemStack getFavouriteFood()
    {
        return new ItemStack(Items.CARROT);
    }

    @Override
    public void livingTick()
    {
        super.livingTick();
        if(this.world.isRemote && this.ticksExisted % 2 == 0)
        {
            if(this.getPosX() != this.prevPosX || this.getPosY() != this.prevPosY || this.getPosZ() != this.prevPosZ)
            {
                //this.world.addParticle(ParticleTypes.FLAME, this.getPosX() - 0.1 + 0.2 * this.rand.nextDouble(), this.getPosY() + 0.25 - 0.1 + 0.2 * this.rand.nextDouble(), this.getPosZ() - 0.1 + 0.2 * this.rand.nextDouble(), 0, 0, 0);
            }
            this.world.addParticle(ParticleTypes.FLAME, this.getPosX() - 0.5 + 1.0 * this.rand.nextDouble(), this.getPosY() + 0.5 - 0.5 + 1.0 * this.rand.nextDouble(), this.getPosZ() - 0.5 + 1.0 * this.rand.nextDouble(), 0, 0, 0);
        }
    }
}
