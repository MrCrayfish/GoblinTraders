package com.mrcrayfish.goblintraders.entity;

import com.mrcrayfish.goblintraders.Config;
import com.mrcrayfish.goblintraders.trades.GoblinTradeSets;
import com.mrcrayfish.goblintraders.util.Utils;
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
public class GoblinTrader extends AbstractGoblinEntity
{
    private static final Identifier TEXTURE = Utils.id("textures/entity/goblin_trader.png");

    public GoblinTrader(EntityType<GoblinTrader> type, Level level)
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
        this.addOffersFromTradeSet(level, offers, GoblinTradeSets.GOBLIN_TRADER_COMMON);
        this.addOffersFromTradeSet(level, offers, GoblinTradeSets.GOBLIN_TRADER_UNCOMMON);
        this.addOffersFromTradeSet(level, offers, GoblinTradeSets.GOBLIN_TRADER_RARE);
        this.addOffersFromTradeSet(level, offers, GoblinTradeSets.GOBLIN_TRADER_EPIC);
        this.addOffersFromTradeSet(level, offers, GoblinTradeSets.GOBLIN_TRADER_LEGENDARY);
    }

    @Override
    public ItemStack getFavouriteFood()
    {
        return new ItemStack(Items.APPLE);
    }

    @Override
    protected int getMaxRestockDelay()
    {
        return Config.ENTITIES.goblinTrader.restockDelay.get();
    }

    @Override
    public boolean canAttackBack()
    {
        return Config.ENTITIES.goblinTrader.canAttackBack.get();
    }

    @Override
    public int getAmbientSoundInterval()
    {
        return Config.ENTITIES.goblinTrader.gruntNoiseInterval.get();
    }
}
