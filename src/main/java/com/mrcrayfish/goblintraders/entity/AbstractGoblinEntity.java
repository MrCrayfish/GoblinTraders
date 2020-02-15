package com.mrcrayfish.goblintraders.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.INPC;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public class AbstractGoblinEntity extends MobEntity implements INPC, IMerchant
{
    protected AbstractGoblinEntity(EntityType<? extends MobEntity> type, World worldIn)
    {
        super(type, worldIn);
    }

    @Override
    public void setCustomer(@Nullable PlayerEntity player)
    {

    }

    @Nullable
    @Override
    public PlayerEntity getCustomer()
    {
        return null;
    }

    @Override
    public MerchantOffers getOffers()
    {
        return null;
    }

    @Override
    public void setClientSideOffers(@Nullable MerchantOffers offers)
    {

    }

    @Override
    public void onTrade(MerchantOffer offer)
    {

    }

    @Override
    public void verifySellingItem(ItemStack stack)
    {

    }

    @Override
    public World getWorld()
    {
        return null;
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
        return null;
    }
}
