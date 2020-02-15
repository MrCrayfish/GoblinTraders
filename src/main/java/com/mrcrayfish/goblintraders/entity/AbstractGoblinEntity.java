package com.mrcrayfish.goblintraders.entity;

import com.google.common.collect.Sets;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.INPC;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Author: MrCrayfish
 */
public abstract class AbstractGoblinEntity extends MobEntity implements INPC, IMerchant
{
    protected static final int BASE_TRADES = 0;
    protected static final int RARE_TRADES = 1;

    @Nullable
    private PlayerEntity customer;
    @Nullable
    private MerchantOffers offers;

    protected AbstractGoblinEntity(EntityType<? extends MobEntity> type, World worldIn)
    {
        super(type, worldIn);
    }

    public abstract ResourceLocation getTexture();

    @Override
    public void setCustomer(@Nullable PlayerEntity player)
    {
        this.customer = player;
    }

    @Nullable
    @Override
    public PlayerEntity getCustomer()
    {
        return this.customer;
    }

    public boolean hasCustomer()
    {
        return this.customer != null;
    }

    @Override
    public MerchantOffers getOffers()
    {
        if(this.offers == null)
        {
            this.offers = new MerchantOffers();
            this.populateTradeData();
        }
        return this.offers;
    }

    protected void addOffer(MerchantOffer offer)
    {

    }

    protected abstract void populateTradeData();

    protected void addTrades(MerchantOffers offers, @Nullable VillagerTrades.ITrade[] trades, int max)
    {
        if(trades == null)
            return;
        List<Integer> randomIndexes = IntStream.range(0, trades.length).boxed().collect(Collectors.toList());
        Collections.shuffle(randomIndexes);
        randomIndexes = randomIndexes.subList(0, Math.min(trades.length, max));
        for(Integer index : randomIndexes)
        {
            VillagerTrades.ITrade trade = trades[index];
            MerchantOffer offer = trade.getOffer(this, this.rand);
            if(offer != null)
            {
                offers.add(offer);
            }
        }
    }

    @Override
    public void setClientSideOffers(@Nullable MerchantOffers offers)
    {

    }

    @Override
    public void onTrade(MerchantOffer offer)
    {
        offer.increaseUses();
    }

    @Override
    public void verifySellingItem(ItemStack stack)
    {

    }

    @Override
    public World getWorld()
    {
        return this.world;
    }

    @Override
    public int getXp()
    {
        return 0;
    }

    @Override
    public void setXP(int xpIn)
    {

    }

    @Override
    public boolean func_213705_dZ()
    {
        return false;
    }

    @Override
    public SoundEvent getYesSound()
    {
        return SoundEvents.ENTITY_VILLAGER_YES;
    }

    @Override
    protected boolean processInteract(PlayerEntity player, Hand hand)
    {
        ItemStack heldItem = player.getHeldItem(hand);
        if(heldItem.getItem() == Items.NAME_TAG)
        {
            heldItem.interactWithEntity(player, this, hand);
            return true;
        }
        else if(this.isAlive() && !this.hasCustomer() && !this.isChild()) //TODO check for egg
        {
            if(this.getOffers().isEmpty())
            {
                return super.processInteract(player, hand);
            }
            else if(!this.world.isRemote)
            {
                this.setCustomer(player);
                this.openMerchantContainer(player, this.getDisplayName(), 1);
            }
            return true;
        }
        return super.processInteract(player, hand);
    }
}
