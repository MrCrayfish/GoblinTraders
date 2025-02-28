package com.mrcrayfish.goblintraders.entity;

import com.mrcrayfish.goblintraders.Config;
import com.mrcrayfish.goblintraders.core.ModSounds;
import com.mrcrayfish.goblintraders.entity.ai.goal.TradeWithPlayerGoal;
import com.mrcrayfish.goblintraders.entity.ai.goal.*;
import com.mrcrayfish.goblintraders.inventory.GoblinMerchantMenu;
import com.mrcrayfish.goblintraders.trades.GoblinOffers;
import com.mrcrayfish.goblintraders.trades.type.BaseTrade;
import com.mrcrayfish.goblintraders.util.Utils;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Author: MrCrayfish
 */
public abstract class AbstractGoblinEntity extends TraderCreatureEntity implements Npc
{
    public static final EntityDataAccessor<Boolean> STUNNED = SynchedEntityData.defineId(AbstractGoblinEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Float> STUN_ROTATION = SynchedEntityData.defineId(AbstractGoblinEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(AbstractGoblinEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> CURIOUS = SynchedEntityData.defineId(AbstractGoblinEntity.class, EntityDataSerializers.BOOLEAN);

    private @Nullable Player customer;
    private @Nullable MerchantOffers offers;
    private final Set<UUID> tradedCustomers = new HashSet<>();

    private int stunDelay;
    private int despawnDelay = 24000;
    private int fallCounter;
    private int restockDelay;
    private float headTilt;
    private float headTiltO;
    private float armAngle;
    private float armAngleO;
    private int curiousTime;

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
        this.goalSelector.addGoal(5, new EatFavouriteFoodGoal(this));
        this.goalSelector.addGoal(6, new FindFavouriteFoodGoal(this));
        this.goalSelector.addGoal(7, new GoblinTemptGoal(this, 0.4D, Ingredient.of(this.getFavouriteFood()), false));
        this.goalSelector.addGoal(8, new FollowPotentialCustomerGoal(this));
        this.goalSelector.addGoal(9, new SitAndLookGoal(this));
        this.goalSelector.addGoal(10, new WaterAvoidingRandomStrollGoal(this, 0.4D));
        this.goalSelector.addGoal(11, new MoveTowardsRestrictionGoal(this, 0.4D));
        this.goalSelector.addGoal(12, new InteractGoal(this, Player.class, 4.0F, 1.0F));
        this.goalSelector.addGoal(13, new LookAtPlayerGoal(this, Mob.class, 8.0F));
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
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(STUNNED, false);
        builder.define(STUN_ROTATION, 0F);
        builder.define(SITTING, false);
        builder.define(CURIOUS, false);
    }

    public abstract ResourceLocation getTexture();

    public int getFallCounter()
    {
        return this.fallCounter;
    }

    @Override
    public ItemStack eat(Level level, ItemStack stack, FoodProperties properties)
    {
        if(stack.getItem() == this.getFavouriteFood().getItem())
        {
            this.setHealth(this.getHealth() + properties.nutrition());
        }
        return super.eat(level, stack, properties);
    }

    @Override
    public void baseTick()
    {
        this.headTiltO = this.headTilt;
        this.armAngleO = this.armAngle;

        super.baseTick();
        this.updateSwingTime(); //TODO test
        if(this.stunDelay > 0)
        {
            this.stunDelay--;
            if(this.stunDelay == 0)
            {
                this.entityData.set(STUNNED, false);
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.ENTITY_GOBLIN_TRADER_ANNOYED_GRUNT.get(), SoundSource.NEUTRAL, 1.0F, 0.9F + this.getRandom().nextFloat() * 0.2F);
            }
        }
        if(!this.level().isClientSide() && !Config.ENTITIES.preventDespawnIfNamed.get() && !this.isPersistenceRequired())
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
        if(!this.level().isClientSide() && this.getMaxRestockDelay() != -1)
        {
            if(++this.restockDelay >= this.getMaxRestockDelay())
            {
                this.getOffers().forEach(MerchantOffer::resetUses);
                this.restockDelay = 0;
                this.resendOffers();
            }
        }

        if(!this.level().isClientSide() && --this.curiousTime <= 0)
        {
            this.setCurious(false);
        }

        float targetTilt = this.isCurious() ? 20 : 0;
        this.headTilt = Mth.lerp(0.35F, this.headTilt, targetTilt);

        float targetAngle = this.getTargetArmAngle();
        this.armAngle = Mth.lerp(0.35F, this.armAngle, targetAngle);
    }

    private void resendOffers()
    {
        MerchantOffers offers = this.getOffers();
        Player player = this.getTradingPlayer();
        if(player != null && !offers.isEmpty())
        {
            player.sendMerchantOffers(player.containerMenu.containerId, offers, 0, 0, false, this.canRestock());
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

    protected void addTrades(MerchantOffers offers, @Nullable List<BaseTrade> trades, int max, boolean shuffle)
    {
        if(trades == null)
            return;
        List<Integer> randomIndexes = IntStream.range(0, trades.size()).boxed().collect(Collectors.toList());
        if(shuffle) Collections.shuffle(randomIndexes);
        randomIndexes = randomIndexes.subList(0, Math.min(trades.size(), max));
        for(Integer index : randomIndexes)
        {
            BaseTrade trade = trades.get(index);
            MerchantOffer offer = trade.createVanillaOffer(this, this.getRandom());
            if(offer != null)
            {
                offers.add(offer);
            }
        }
    }

    @Override
    public void openTradingScreen(Player player, Component title, int level)
    {
        OptionalInt id = player.openMenu(new SimpleMenuProvider((windowId, playerInventory, player1) -> {
            return new GoblinMerchantMenu(windowId, playerInventory, this);
        }, title));
        if(id.isPresent())
        {
            MerchantOffers offers = this.getOffers();
            if(!offers.isEmpty())
            {
                player.sendMerchantOffers(id.getAsInt(), offers, level, 0, false, this.canRestock());
            }
        }
    }

    @Override
    public boolean canRestock()
    {
        return true;
    }

    @Override
    public void overrideOffers(@Nullable MerchantOffers offers) {}

    @Override
    public void notifyTrade(MerchantOffer offer)
    {
        offer.increaseUses();
        if(this.customer != null)
        {
            this.tradedCustomers.add(this.customer.getUUID());
        }
        if(this.level() instanceof ServerLevel serverLevel)
        {
            ExperienceOrb.award(serverLevel, this.getPosition(1F), offer.getXp());
        }
    }

    @Override
    public void notifyTradeUpdated(ItemStack stack)
    {

    }

    @Override
    public boolean isClientSide()
    {
        return this.level().isClientSide();
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
        else if(this.getFavouriteFood().is(heldItem.getItem()))
        {
            if(this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty())
            {
                this.setItemSlot(EquipmentSlot.MAINHAND, heldItem.copyWithCount(1));
                heldItem.shrink(1);
            }
            return InteractionResult.sidedSuccess(this.isClientSide());
        }
        else if(this.isAlive() && !this.hasCustomer() && !this.isBaby() && (this.fireImmune() || !this.isOnFire()) && !this.isStunned()) //TODO check for egg
        {
            if(this.getOffers().isEmpty())
            {
                return InteractionResult.sidedSuccess(this.isClientSide());
            }
            else if(!this.isClientSide() && (this.getLastHurtByMob() == null || this.getLastHurtByMob() != player))
            {
                this.setTradingPlayer(player);
                this.openTradingScreen(player, Objects.requireNonNull(this.getDisplayName()), 1);
            }
            return InteractionResult.sidedSuccess(this.isClientSide());
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
                this.playSound(this.getDrinkingSound(stack), 0.5F, this.level().getRandom().nextFloat() * 0.1F + 0.9F);
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
            if(this.level() instanceof ServerLevel serverLevel)
            {
                serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), frontPosition.x, frontPosition.y, frontPosition.z, 1, motion.x, motion.y + 0.05D, motion.z, 0.0D);
            }
            else
            {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, stack), frontPosition.x, frontPosition.y, frontPosition.z, motion.x, motion.y + 0.05D, motion.z);
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
        if(attacked)
        {
            this.setCurious(false);
            this.setSitting(false);
            if(source.getEntity() instanceof Player)
            {
                this.getNavigation().stop();
                this.entityData.set(STUNNED, true);
                this.entityData.set(STUN_ROTATION, this.getStunRotation(source.getEntity()));
                this.goalSelector.getAvailableGoals().forEach(WrappedGoal::stop);
                this.stunDelay = 20;
            }
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
        if(compound.contains("TradedCustomers", Tag.TAG_LIST))
        {
            this.tradedCustomers.clear();
            ListTag list = compound.getList("TradedCustomers", Tag.TAG_STRING);
            list.forEach(tag -> {
                if(tag instanceof StringTag s) {
                    UUID id = Utils.parseUuid(s.getAsString());
                    if(id != null) {
                        this.tradedCustomers.add(id);
                    }
                }
            });
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound)
    {
        super.addAdditionalSaveData(compound);
        MerchantOffers offers = this.getOffers();
        if(!offers.isEmpty())
        {
            MerchantOffers.CODEC.encodeStart(NbtOps.INSTANCE, offers).result()
                .ifPresent(tag -> {
                    compound.put("Offers", tag);
                });
        }
        compound.putInt("DespawnDelay", this.despawnDelay);
        compound.putInt("RestockDelay", this.restockDelay);

        if(!this.tradedCustomers.isEmpty())
        {
            ListTag list = new ListTag();
            this.tradedCustomers.forEach(id -> {
                list.add(StringTag.valueOf(id.toString()));
            });
            compound.put("TradedCustomers", list);
        }
    }

    private void handleDespawn()
    {
        if(!this.hasCustomer() && --this.despawnDelay <= 0)
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
        return Monster.createMobAttributes().add(Attributes.MAX_HEALTH, 20F).add(Attributes.MOVEMENT_SPEED, 0.6D);
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

    public void setSitting(boolean sitting)
    {
        this.entityData.set(SITTING, sitting);
    }

    public boolean isSitting()
    {
        return this.entityData.get(SITTING);
    }

    public void setCurious(boolean curious)
    {
        this.entityData.set(CURIOUS, curious);
        this.curiousTime = curious ? 100 : 0;
    }

    public void resetCurious()
    {
        this.entityData.set(CURIOUS, false);
        this.curiousTime = 0;
    }

    public boolean isCurious()
    {
        return this.entityData.get(CURIOUS);
    }

    @Override
    protected float getMaxHeadRotationRelativeToBody()
    {
        return 90F;
    }

    @Override
    protected BodyRotationControl createBodyControl()
    {
        return new GoblinRotationControl(this);
    }

    public float getHeadTilt(float partial)
    {
        return Mth.lerp(partial, this.headTiltO, this.headTilt);
    }

    @Override
    public double getEyeY()
    {
        return !this.isSitting() ? super.getEyeY() : super.getEyeY() - 0.17;
    }

    public float getArmAngle(float partial)
    {
        return Mth.lerp(partial, this.armAngleO, this.armAngle);
    }

    private float getTargetArmAngle()
    {
        if(this.isCurious() && !this.isSitting())
            return 110;

        if(!this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty())
            return 80;

        return 0;
    }
}
