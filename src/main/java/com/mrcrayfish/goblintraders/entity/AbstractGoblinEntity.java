package com.mrcrayfish.goblintraders.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mrcrayfish.goblintraders.entity.ai.goal.AttackRevengeTargetGoal;
import com.mrcrayfish.goblintraders.entity.ai.goal.FollowPotentialCustomerGoal;
import com.mrcrayfish.goblintraders.entity.ai.goal.LookAtCustomerGoal;
import com.mrcrayfish.goblintraders.entity.ai.goal.TradeWithPlayerGoal;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.INPC;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Author: MrCrayfish
 */
public abstract class AbstractGoblinEntity extends CreatureEntity implements INPC, IMerchant
{
    protected static final int BASE_TRADES = 0;
    protected static final int RARE_TRADES = 1;

    private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.INTERACTION_TARGET);

    @Nullable
    private PlayerEntity customer;
    private Set<UUID> tradedCustomers = new HashSet<>();
    @Nullable
    private MerchantOffers offers;

    private int stunDelay;

    protected AbstractGoblinEntity(EntityType<? extends CreatureEntity> type, World worldIn)
    {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(2, new LookAtCustomerGoal(this));
        this.goalSelector.addGoal(3, new AttackRevengeTargetGoal(this));
        this.goalSelector.addGoal(4, new FollowPotentialCustomerGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.4D));
        this.goalSelector.addGoal(7, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 4.0F, 1.0F));
        this.goalSelector.addGoal(8, new LookAtGoal(this, MobEntity.class, 8.0F));
    }

    public abstract ResourceLocation getTexture();

    @Override
    public void livingTick()
    {
        super.livingTick();
        this.updateArmSwingProgress();
        if(this.stunDelay > 0)
        {
            this.stunDelay--;
        }
    }

    @Override
    public void setCustomer(@Nullable PlayerEntity player)
    {
        if(player == null && this.customer != null && !this.isPreviousCustomer(this.customer))
        {
            if(this.rand.nextInt(5) == 0)
            {
                this.customer.attackEntityFrom(DamageSource.causeMobDamage(this), 0.5F);
                this.swingArm(Hand.MAIN_HAND);
            }
        }
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
        if(this.customer != null)
        {
            this.tradedCustomers.add(this.customer.getUniqueID());
        }
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
    public boolean canDespawn(double distanceToClosestPlayer)
    {
        return false;
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
            else if(!this.world.isRemote && (this.getRevengeTarget() == null || this.getRevengeTarget() != player))
            {
                this.setCustomer(player);
                this.openMerchantContainer(player, this.getDisplayName(), 1);
            }
            return true;
        }
        return super.processInteract(player, hand);
    }

    public boolean isPreviousCustomer(PlayerEntity player)
    {
        return this.tradedCustomers.contains(player.getUniqueID());
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        boolean attacked = super.attackEntityFrom(source, amount);
        if(attacked)
        {
            this.goalSelector.getRunningGoals().forEach(PrioritizedGoal::resetTask);
            this.stunDelay = 20;
        }
        return attacked;
    }

    public int getStunDelay()
    {
        return stunDelay;
    }
}
