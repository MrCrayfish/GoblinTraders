package com.mrcrayfish.goblintraders.entity;

import com.mrcrayfish.goblintraders.Config;
import com.mrcrayfish.goblintraders.trades.GoblinTradeSets;
import com.mrcrayfish.goblintraders.util.Utils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;

/**
 * Author: MrCrayfish
 */
public class VeinGoblinTrader extends AbstractGoblinEntity
{
    private static final Identifier TEXTURE = Utils.id("textures/entity/vein_goblin_trader.png");

    public VeinGoblinTrader(EntityType<VeinGoblinTrader> type, Level level)
    {
        super(type, level);
    }

    @Override
    public Identifier getTexture()
    {
        return TEXTURE;
    }

    @Override
    protected void populateTradeData(ServerLevel level)
    {
        MerchantOffers offers = this.getOffers();
        this.addOffersFromTradeSet(level, offers, GoblinTradeSets.VEIN_GOBLIN_TRADER_COMMON);
        this.addOffersFromTradeSet(level, offers, GoblinTradeSets.VEIN_GOBLIN_TRADER_UNCOMMON);
        this.addOffersFromTradeSet(level, offers, GoblinTradeSets.VEIN_GOBLIN_TRADER_RARE);
        this.addOffersFromTradeSet(level, offers, GoblinTradeSets.VEIN_GOBLIN_TRADER_EPIC);
        this.addOffersFromTradeSet(level, offers, GoblinTradeSets.VEIN_GOBLIN_TRADER_LEGENDARY);
    }

    @Override
    public ItemStack getFavouriteFood()
    {
        return new ItemStack(Items.CARROT);
    }

    @Override
    public void aiStep()
    {
        if(this.isClientSide() && this.tickCount % 2 == 0)
        {
            this.level().addParticle(ParticleTypes.FLAME, this.getX() - 0.5 + this.getRandom().nextDouble(), this.getY() + 0.5 - 0.5 + this.getRandom().nextDouble(), this.getZ() - 0.5 + this.getRandom().nextDouble(), 0, 0, 0);
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
        return Config.ENTITIES.veinGoblinTrader.restockDelay.get();
    }

    @Override
    public boolean canAttackBack()
    {
        return Config.ENTITIES.veinGoblinTrader.canAttackBack.get();
    }

    @Override
    public int getAmbientSoundInterval()
    {
        return Config.ENTITIES.veinGoblinTrader.gruntNoiseInterval.get();
    }
}
