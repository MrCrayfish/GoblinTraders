package com.mrcrayfish.goblintraders.entity;

import com.mrcrayfish.goblintraders.Config;
import com.mrcrayfish.goblintraders.core.ModSounds;
import com.mrcrayfish.goblintraders.entity.ai.goal.AttackRevengeTargetGoal;
import com.mrcrayfish.goblintraders.entity.ai.goal.EatFavouriteFoodGoal;
import com.mrcrayfish.goblintraders.entity.ai.goal.FindFavouriteFoodGoal;
import com.mrcrayfish.goblintraders.entity.ai.goal.FirePanicGoal;
import com.mrcrayfish.goblintraders.entity.ai.goal.FollowPotentialCustomerGoal;
import com.mrcrayfish.goblintraders.entity.ai.goal.LookAtCustomerGoal;
import com.mrcrayfish.goblintraders.entity.ai.goal.TradeWithPlayerGoal;
import com.mrcrayfish.goblintraders.trades.GoblinOffers;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.InteractGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

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
public abstract class AbstractGoblinEntity extends TraderCreatureEntity implements Npc
{
    public static final EntityDataAccessor<Boolean> STUNNED = SynchedEntityData.defineId(AbstractGoblinEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Float> STUN_ROTATION = SynchedEntityData.defineId(AbstractGoblinEntity.class, EntityDataSerializers.FLOAT);

    @Nullable
    private Player customer;
    private Set<UUID> tradedCustomers = new HashSet<>();
    @Nullable
    private MerchantOffers offers;

    private int stunDelay;
    private int despawnDelay;
    private int fallCounter;
    private int restockDelay;

    protected AbstractGoblinEntity(EntityType<? extends TraderCreatureEntity> type, Level level)
    {
        super(type, level);
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new FirePanicGoal(this, 0.5F));
        this.goalSelector.addGoal(2, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(3, new LookAtCustomerGoal(this));
        this.goalSelector.addGoal(4, new AttackRevengeTargetGoal(this));
        this.goalSelector.addGoal(5, new FollowPotentialCustomerGoal(this));
        this.goalSelector.addGoal(6, new FindFavouriteFoodGoal(this));
        this.goalSelector.addGoal(7, new TemptGoal(this, 0.4D, Ingredient.of(this.getFavouriteFood()), false));
        this.goalSelector.addGoal(8, new EatFavouriteFoodGoal(this));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.4D));
        this.goalSelector.addGoal(9, new MoveTowardsRestrictionGoal(this, 0.4D));
        this.goalSelector.addGoal(10, new InteractGoal(this, Player.class, 4.0F, 1.0F));
        this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }

    @Override
    protected void updateControlFlags()
    {
        super.updateControlFlags();
        if(this.isStunned())
        {
            this.goalSelector.setControlFlag(Goal.Flag.MOVE, true);
            this.goalSelector.setControlFlag(Goal.Flag.JUMP, true);
            this.goalSelector.setControlFlag(Goal.Flag.LOOK, true);
        }
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(STUNNED, false);
        this.entityData.define(STUN_ROTATION, 0F);
    }

    public abstract ResourceLocation getTexture();

    public int getFallCounter()
    {
        return this.fallCounter;
    }

    @Override
    public ItemStack eat(Level level, ItemStack stack)
    {
        if(stack.getItem() == this.getFavouriteFood().getItem() && stack.getItem().getFoodProperties() != null)
        {
            this.setHealth(this.getHealth() + stack.getItem().getFoodProperties().getNutrition());
        }
        return super.eat(level, stack);
    }

    @Override
    public void baseTick()
    {
        super.baseTick();
        this.updateSwingTime(); //TODO test
        if(this.stunDelay > 0)
        {
            this.stunDelay--;
            if(this.stunDelay == 0)
            {
                this.entityData.set(STUNNED, false);
                this.level.playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.ENTITY_GOBLIN_TRADER_ANNOYED_GRUNT.get(), SoundSource.NEUTRAL, 1.0F, 0.9F + this.getRandom().nextFloat() * 0.2F);
            }
        }
        if(!this.level.isClientSide() && (!Config.ENTITIES.preventDespawnIfNamed.get() || !this.isPersistenceRequired()))
        {
            this.handleDespawn();
        }
        else if(this.entityData.get(STUNNED))
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
        if(!this.level.isClientSide() && this.getMaxRestockDelay() != -1)
        {
            if(++this.restockDelay >= this.getMaxRestockDelay())
            {
                this.getOffers().forEach(MerchantOffer::resetUses);
                this.restockDelay = 0;
            }
        }
    }

    @Override
    public void setTradingPlayer(@Nullable Player player)
    {
        this.customer = player;
    }

    @Nullable
    @Override
    public Player getTradingPlayer()
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
            this.offers = new GoblinOffers();
            this.populateTradeData();
        }
        return this.offers;
    }

    protected abstract void populateTradeData();

    protected void addTrades(MerchantOffers offers, @Nullable List<VillagerTrades.ItemListing> trades, int max, boolean shuffle)
    {
        if(trades == null)
            return;
        List<Integer> randomIndexes = IntStream.range(0, trades.size()).boxed().collect(Collectors.toList());
        if(shuffle) Collections.shuffle(randomIndexes);
        randomIndexes = randomIndexes.subList(0, Math.min(trades.size(), max));
        for(Integer index : randomIndexes)
        {
            VillagerTrades.ItemListing trade = trades.get(index);
            MerchantOffer offer = trade.getOffer(this, this.getRandom());
            if(offer != null)
            {
                offers.add(offer);
            }
        }
    }

    @Override
    public void overrideOffers(@Nullable MerchantOffers offers)
    {

    }

    @Override
    public void notifyTrade(MerchantOffer offer)
    {
        offer.increaseUses();
        if(this.customer != null)
        {
            this.tradedCustomers.add(this.customer.getUUID());
        }
        if(this.level instanceof ServerLevel)
        {
            ExperienceOrb.award((ServerLevel) this.level, this.getPosition(1F), offer.getXp());
        }
    }

    @Override
    public void notifyTradeUpdated(ItemStack stack)
    {

    }

    @Override
    public Level getLevel()
    {
        return this.level;
    }

    @Override
    public boolean isClientSide()
    {
        return this.getLevel().isClientSide;
    }

    @Override
    public int getVillagerXp()
    {
        return 0;
    }

    @Override
    public void overrideXp(int xpIn) {}

    @Override
    public boolean showProgressBar()
    {
        return false;
    }

    @Override
    public SoundEvent getNotifyTradeSound()
    {
        return SoundEvents.VILLAGER_YES;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer)
    {
        return false;
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand)
    {
        ItemStack heldItem = player.getItemInHand(hand);
        if(heldItem.getItem() == Items.NAME_TAG)
        {
            heldItem.interactLivingEntity(player, this, hand);
            return InteractionResult.SUCCESS;
        }
        else if(this.isAlive() && !this.hasCustomer() && !this.isBaby() && (this.fireImmune() || !this.isOnFire()) && !this.isStunned()) //TODO check for egg
        {
            if(this.getOffers().isEmpty())
            {
                return InteractionResult.sidedSuccess(this.level.isClientSide());
            }
            else if(!this.level.isClientSide() && (this.getLastHurtByMob() == null || this.getLastHurtByMob() != player))
            {
                this.setTradingPlayer(player);
                this.openTradingScreen(player, this.getDisplayName(), 1);
            }
            return InteractionResult.sidedSuccess(this.level.isClientSide());
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected void triggerItemUseEffects(ItemStack stack, int count)
    {
        if(!stack.isEmpty() && this.isUsingItem())
        {
            if(stack.getUseAnimation() == UseAnim.DRINK)
            {
                this.playSound(this.getDrinkingSound(stack), 0.5F, this.level.getRandom().nextFloat() * 0.1F + 0.9F);
            }
            if(stack.getUseAnimation() == UseAnim.EAT)
            {
                this.spawnFoodParticles(stack, count);
                this.playSound(this.getEatingSound(stack), 0.5F + 0.5F * (float) this.getRandom().nextInt(2), (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F);
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
            Vec3 frontPosition = Vec3.directionFromRotation(0F, this.yBodyRot).scale(0.25);
            frontPosition = frontPosition.add(0, 0.35, 0);
            frontPosition = frontPosition.add(this.position());
            Vec3 motion = new Vec3(this.getRandom().nextDouble() * 0.2 - 0.1, 0.1, this.getRandom().nextDouble() * 0.2 - 0.1);
            if(this.level instanceof ServerLevel)
            {
                ((ServerLevel) this.level).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), frontPosition.x, frontPosition.y, frontPosition.z, 1, motion.x, motion.y + 0.05D, motion.z, 0.0D);
            }
            else
            {
                this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack), frontPosition.x, frontPosition.y, frontPosition.z, motion.x, motion.y + 0.05D, motion.z);
            }
        }
    }

    public boolean isPreviousCustomer(Player player)
    {
        return this.tradedCustomers.contains(player.getUUID());
    }

    @Override
    public boolean hurt(DamageSource source, float amount)
    {
        boolean attacked = super.hurt(source, amount);
        if(attacked && source.getEntity() instanceof Player)
        {
            this.getNavigation().stop();
            this.entityData.set(STUNNED, true);
            this.entityData.set(STUN_ROTATION, this.getStunRotation(source.getEntity()));
            this.goalSelector.getRunningGoals().forEach(WrappedGoal::stop); //TODO test
            this.stunDelay = 20;
        }
        return attacked;
    }

    private float getStunRotation(@Nullable Entity entity)
    {
        return entity != null ? entity.getYRot() : 0F;
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
    public void readAdditionalSaveData(CompoundTag compound)
    {
        super.readAdditionalSaveData(compound);
        if(compound.contains("Offers", 10))
        {
            this.offers = new GoblinOffers(compound.getCompound("Offers"));
        }
        if(compound.contains("DespawnDelay", Tag.TAG_INT))
        {
            this.despawnDelay = compound.getInt("DespawnDelay");
        }
        if(compound.contains("RestockDelay", Tag.TAG_INT))
        {
            this.restockDelay = compound.getInt("RestockDelay");
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound)
    {
        super.addAdditionalSaveData(compound);
        MerchantOffers offers = this.getOffers();
        if(!offers.isEmpty())
        {
            compound.put("Offers", offers.createTag());
        }
        compound.putInt("DespawnDelay", this.despawnDelay);
        compound.putInt("RestockDelay", this.restockDelay);
    }

    private void handleDespawn()
    {
        if(this.despawnDelay > 0 && !this.hasCustomer() && --this.despawnDelay == 0)
        {
            this.remove(RemovalReason.KILLED);
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
    protected SoundEvent getHurtSound(DamageSource damageSource)
    {
        return ModSounds.ENTITY_GOBLIN_TRADER_IDLE_GRUNT.get();
    }

    public abstract ItemStack getFavouriteFood();

    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMobAttributes().add(Attributes.MAX_HEALTH, 20F).add(Attributes.MOVEMENT_SPEED, 0.7D);
    }

    public boolean isStunned()
    {
        return this.entityData.get(STUNNED);
    }

    public float getStunRotation()
    {
        return this.entityData.get(STUN_ROTATION);
    }

    protected abstract int getMaxRestockDelay();

    public abstract boolean canAttackBack();

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob)
    {
        return null;
    }
}
