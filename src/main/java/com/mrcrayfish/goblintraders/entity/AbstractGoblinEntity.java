package com.mrcrayfish.goblintraders.entity;

import com.mrcrayfish.goblintraders.entity.ai.goal.AttackRevengeTargetGoal;
import com.mrcrayfish.goblintraders.entity.ai.goal.EatFavouriteFoodGoal;
import com.mrcrayfish.goblintraders.entity.ai.goal.FindFavouriteFoodGoal;
import com.mrcrayfish.goblintraders.entity.ai.goal.FollowPotentialCustomerGoal;
import com.mrcrayfish.goblintraders.entity.ai.goal.LookAtCustomerGoal;
import com.mrcrayfish.goblintraders.entity.ai.goal.TradeWithPlayerGoal;
import com.mrcrayfish.goblintraders.init.ModSounds;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.INPC;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookAtWithoutMovingGoal;
import net.minecraft.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Author: MrCrayfish
 */
public abstract class AbstractGoblinEntity extends TraderCreatureEntity implements INPC
{
    public static final DataParameter<Boolean> STUNNED = EntityDataManager.createKey(AbstractGoblinEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Float> STUN_ROTATION = EntityDataManager.createKey(AbstractGoblinEntity.class, DataSerializers.FLOAT);

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
        this.goalSelector.addGoal(6, new FindFavouriteFoodGoal(this));
        this.goalSelector.addGoal(7, new EatFavouriteFoodGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 0.4D));
        this.goalSelector.addGoal(8, new MoveTowardsRestrictionGoal(this, 0.4D));
        this.goalSelector.addGoal(9, new LookAtWithoutMovingGoal(this, PlayerEntity.class, 4.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
    }

    @Override
    protected void updateMovementGoalFlags()
    {
        super.updateMovementGoalFlags();
        if(this.isStunned())
        {
            this.goalSelector.setFlag(Goal.Flag.MOVE, true);
            this.goalSelector.setFlag(Goal.Flag.JUMP, true);
            this.goalSelector.setFlag(Goal.Flag.LOOK, true);
        }
    }

    @Override
    protected void registerData()
    {
        super.registerData();
        this.dataManager.register(STUNNED, false);
        this.dataManager.register(STUN_ROTATION, 0F);
    }

    public abstract ResourceLocation getTexture();

    public int getFallCounter()
    {
        return this.fallCounter;
    }

    @Override
    public ItemStack onFoodEaten(World world, ItemStack stack)
    {
        if(stack.getItem() == this.getFavouriteFood().getItem() && stack.getItem().getFood() != null)
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
                this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), ModSounds.ENTITY_GOBLIN_TRADER_ANNOYED_GRUNT.get(), SoundCategory.NEUTRAL, 1.0F, 0.9F + this.getRNG().nextFloat() * 0.2F);
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

    protected void addTrades(MerchantOffers offers, @Nullable List<VillagerTrades.ITrade> trades, int max)
    {
        if(trades == null)
            return;
        List<Integer> randomIndexes = IntStream.range(0, trades.size()).boxed().collect(Collectors.toList());
        Collections.shuffle(randomIndexes);
        randomIndexes = randomIndexes.subList(0, Math.min(trades.size(), max));
        for(Integer index : randomIndexes)
        {
            VillagerTrades.ITrade trade = trades.get(index);
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
    public boolean hasXPBar()
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
    protected ActionResultType func_230254_b_(PlayerEntity player, Hand hand)
    {
        ItemStack heldItem = player.getHeldItem(hand);
        if(heldItem.getItem() == Items.NAME_TAG)
        {
            heldItem.interactWithEntity(player, this, hand);
            return ActionResultType.SUCCESS;
        }
        else if(this.isAlive() && !this.hasCustomer() && !this.isChild()) //TODO check for egg
        {
            if(this.getOffers().isEmpty())
            {
                return super.func_230254_b_(player, hand);
            }
            else if(!this.world.isRemote && (this.getRevengeTarget() == null || this.getRevengeTarget() != player))
            {
                this.setCustomer(player);
                this.openMerchantContainer(player, this.getDisplayName(), 1);
            }
            return ActionResultType.SUCCESS;
        }
        return super.func_230254_b_(player, hand);
    }

    @Override
    protected void triggerItemUseEffects(ItemStack stack, int count)
    {
        if(!stack.isEmpty() && this.isHandActive())
        {
            if(stack.getUseAction() == UseAction.DRINK)
            {
                this.playSound(this.getDrinkSound(stack), 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
            }
            if(stack.getUseAction() == UseAction.EAT)
            {
                this.spawnFoodParticles(stack, count);
                this.playSound(this.getEatSound(stack), 0.5F + 0.5F * (float) this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            }
        }
    }

    /**
     * A custom implementation that fixes the position of the particles
     */
    protected void spawnFoodParticles(ItemStack stack, int count)
    {
        for(int i = 0; i < count; ++i)
        {
            Vector3d frontPosition = Vector3d.fromPitchYaw(0F, this.renderYawOffset).scale(0.25);
            frontPosition = frontPosition.add(0, 0.35, 0);
            frontPosition = frontPosition.add(this.getPositionVec());
            Vector3d motion = new Vector3d(this.rand.nextDouble() * 0.2 - 0.1, 0.1, this.rand.nextDouble() * 0.2 - 0.1);
            if(this.world instanceof ServerWorld)
            {
                ((ServerWorld) this.world).spawnParticle(new ItemParticleData(ParticleTypes.ITEM, stack), frontPosition.x, frontPosition.y, frontPosition.z, 1, motion.x, motion.y + 0.05D, motion.z, 0.0D);
            }
            else
            {
                this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, stack), frontPosition.x, frontPosition.y, frontPosition.z, motion.x, motion.y + 0.05D, motion.z);
            }
        }
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
            this.getNavigator().clearPath();
            this.dataManager.set(STUNNED, true);
            this.dataManager.set(STUN_ROTATION, this.getStunRotation(source.getImmediateSource()));
            this.goalSelector.getRunningGoals().forEach(PrioritizedGoal::resetTask);
            this.stunDelay = 20;
        }
        return attacked;
    }

    private float getStunRotation(@Nullable Entity entity)
    {
        return entity != null ? entity.rotationYaw : 0F;
    }

    public int getStunDelay()
    {
        return this.stunDelay;
    }

    public void setDespawnDelay(int despawnDelay)
    {
        this.despawnDelay = despawnDelay;
    }

    public int getDespawnDelay()
    {
        return this.despawnDelay;
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
        return ModSounds.ENTITY_GOBLIN_TRADER_IDLE_GRUNT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return ModSounds.ENTITY_GOBLIN_TRADER_IDLE_GRUNT.get();
    }

    public abstract ItemStack getFavouriteFood();

    public static AttributeModifierMap.MutableAttribute prepareAttributes()
    {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 20D) // MAX_HEALTH
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.7D); // MOVEMENT_SPEED
    }

    public boolean isStunned()
    {
        return this.dataManager.get(STUNNED);
    }

    public float getStunRotation()
    {
        return this.dataManager.get(STUN_ROTATION);
    }
}
