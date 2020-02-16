package com.mrcrayfish.goblintraders.entity;

import com.google.common.collect.ImmutableList;
import com.mrcrayfish.goblintraders.entity.ai.goal.LookAtCustomerGoal;
import com.mrcrayfish.goblintraders.entity.ai.goal.TradeWithPlayerGoal;
import com.mrcrayfish.goblintraders.entity.ai.goal.*;
import com.mrcrayfish.goblintraders.init.ModSounds;
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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

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

    public static final DataParameter<Boolean> STUNNED = EntityDataManager.createKey(AbstractGoblinEntity.class, DataSerializers.BOOLEAN);

    private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.INTERACTION_TARGET);

    @Nullable
    private PlayerEntity customer;
    private Set<UUID> tradedCustomers = new HashSet<>();
    @Nullable
    private MerchantOffers offers;

    private int stunDelay;
    private int despawnDelay;
    private int fallCounter;

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
        this.goalSelector.addGoal(5, new FollowPotentialCustomerGoal(this));
        this.goalSelector.addGoal(6, new FindAppleGoal(this));
        this.goalSelector.addGoal(7, new EatAppleGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 0.4D));
        this.goalSelector.addGoal(8, new MoveTowardsRestrictionGoal(this, 0.4D));
        this.goalSelector.addGoal(9, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 4.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
    }

    @Override
    protected void registerData()
    {
        super.registerData();
        this.dataManager.register(STUNNED, false);
    }

    public abstract ResourceLocation getTexture();

    public int getFallCounter()
    {
        return fallCounter;
    }

    @Override
    public ItemStack onFoodEaten(World world, ItemStack stack)
    {
        if(stack.getItem() == Items.APPLE && stack.getItem().getFood() != null)
        {
            this.setHealth(this.getHealth() + stack.getItem().getFood().getHealing());
        }
        return super.onFoodEaten(world, stack);
    }

    @Override
    public void livingTick()
    {
        super.livingTick();
        this.updateArmSwingProgress();
        if(this.stunDelay > 0)
        {
            this.stunDelay--;
            if(this.stunDelay == 0)
            {
                this.dataManager.set(STUNNED, false);
                this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), ModSounds.ENTITY_GOBLIN_TRADER_ANNOYED_GRUNT, SoundCategory.NEUTRAL, 1.0F, 0.9F + this.getRNG().nextFloat() * 0.2F);
            }
        }
        if(!this.world.isRemote)
        {
            this.handleDespawn();
        }
        else if(this.dataManager.get(STUNNED))
        {
            if(this.fallCounter < 10)
            {
                this.fallCounter++;
            }
        }
        else
        {
            this.fallCounter = 0;
        }
    }

    @Override
    public void setCustomer(@Nullable PlayerEntity player)
    {
        if(player == null && this.customer != null && !this.isPreviousCustomer(this.customer))
        {
            if(this.rand.nextInt(5) == 0)
            {
                this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), ModSounds.ENTITY_GOBLIN_TRADER_ANNOYED_GRUNT, SoundCategory.NEUTRAL, 1.0F, 0.9F + this.getRNG().nextFloat() * 0.2F);
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
            this.dataManager.set(STUNNED, true);
            this.goalSelector.getRunningGoals().forEach(PrioritizedGoal::resetTask);
            this.stunDelay = 20;
        }
        return attacked;
    }

    public int getStunDelay()
    {
        return stunDelay;
    }

    public void setDespawnDelay(int despawnDelay)
    {
        this.despawnDelay = despawnDelay;
    }

    public int getDespawnDelay()
    {
        return despawnDelay;
    }

    @Override
    public void readAdditional(CompoundNBT compound)
    {
        super.readAdditional(compound);
        if(compound.contains("Offers", 10))
        {
            this.offers = new MerchantOffers(compound.getCompound("Offers"));
        }
        if(compound.contains("DespawnDelay", Constants.NBT.TAG_INT))
        {
            this.despawnDelay = compound.getInt("DespawnDelay");
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound)
    {
        super.writeAdditional(compound);
        MerchantOffers merchantoffers = this.getOffers();
        if(!merchantoffers.isEmpty())
        {
            compound.put("Offers", merchantoffers.write());
        }
        compound.putInt("DespawnDelay", this.despawnDelay);
    }

    private void handleDespawn()
    {
        if(this.despawnDelay > 0 && !this.hasCustomer() && --this.despawnDelay == 0)
        {
            this.remove();
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound()
    {
        return ModSounds.ENTITY_GOBLIN_TRADER_IDLE_GRUNT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return ModSounds.ENTITY_GOBLIN_TRADER_IDLE_GRUNT;
    }
}
