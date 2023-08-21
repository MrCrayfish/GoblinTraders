package com.mrcrayfish.goblintraders.spawner;

import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.saveddata.SavedData;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class GoblinTraderSpawner extends SavedData
{
    private static final Map<EntityType<?>, SpawnData> SPAWN_DATA = new HashMap<>();
    private static final int SAFE_POSITION_ATTEMPTS = 50;
    private static final int MIN_SPAWN_DISTANCE = 5;
    private static final int GROUND_SEARCH_DISTANCE = 5;

    private final MinecraftServer server;
    private final ServerLevel level;
    private final EntityType<? extends AbstractGoblinEntity> type;
    private final IGoblinData data;
    private int runDelay;
    private int spawnChance;

    public GoblinTraderSpawner(MinecraftServer server, ServerLevel level, EntityType<? extends AbstractGoblinEntity> type, IGoblinData data)
    {
        this.server = server;
        this.level = level;
        this.type = type;
        this.data = data;
        this.runDelay = data.getSpawnDelay();
        this.spawnChance = data.getSpawnChance();
    }

    public void serverTick()
    {
        if(!this.level.getGameRules().getBoolean(GameRules.RULE_DO_TRADER_SPAWNING))
            return;

        this.runDelay--;
        if(this.runDelay > 0)
            return;

        this.runDelay = this.data.getSpawnInterval();
        if(!this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING))
            return;

        double randomChance = this.level.getRandom().nextDouble();
        if((this.spawnChance / 100.0) < randomChance)
        {
            this.spawnChance = Math.min(this.spawnChance + this.data.getSpawnChance(), 100);
            return;
        }

        if(this.spawnGoblin())
        {
            this.runDelay = this.data.getSpawnDelay();
            this.spawnChance = this.data.getSpawnChance();
        }
    }

    private boolean spawnGoblin()
    {
        ServerPlayer player = this.level.getRandomPlayer();
        if(player == null)
            return false;

        BlockPos pos = this.createSpawnPosition(player.blockPosition(), 16);
        if(pos == null)
            return false;

        if(this.level.getBiome(pos).is(Biomes.THE_VOID))
            return false;

        if(pos.getY() < this.data.getMinSpawnYLevel() || pos.getY() >= this.data.getMaxSpawnYLevel())
            return false;

        AbstractGoblinEntity goblin = this.type.spawn(this.level, pos, MobSpawnType.EVENT);
        if(goblin == null)
            return false;

        this.runDelay = this.data.getSpawnDelay();
        goblin.restrictTo(pos, 16);
        return true;
    }

    @Nullable
    private BlockPos createSpawnPosition(BlockPos center, int range)
    {
        for(int i = 0; i < SAFE_POSITION_ATTEMPTS; i++)
        {
            int posX = center.getX() + this.createRandomSpawnableDistance(range);
            int posY = center.getY() + this.createRandomSpawnableDistance(range);
            int posZ = center.getZ() + this.createRandomSpawnableDistance(range);
            BlockPos pos = this.findGround(new BlockPos(posX, posY, posZ));
            if(pos != null && !pos.closerThan(center, MIN_SPAWN_DISTANCE) && NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND, this.level, pos, this.type))
            {
                return pos;
            }
        }
        return null;
    }

    /**
     * Creates a random distance within the spawnable distance, which is based on the minimum spawn
     * distance and the max spawn distance.
     *
     * @param maxSpawnDistance
     * @return a random distance between the valid range
     */
    private int createRandomSpawnableDistance(int maxSpawnDistance)
    {
        RandomSource random = this.level.getRandom();
        int direction = random.nextInt(2) == 0 ? 1 : -1;
        int spawnableRange = Math.max(maxSpawnDistance - MIN_SPAWN_DISTANCE, 0);
        return (MIN_SPAWN_DISTANCE + random.nextIntBetweenInclusive(0, spawnableRange)) * direction;
    }

    @Nullable
    private BlockPos findGround(BlockPos pos)
    {
        boolean colliding = this.canCollide(pos);

        // Search downwards for ground
        BlockPos testPos = pos.below();
        for(int i = 0; i < GROUND_SEARCH_DISTANCE && Level.isInSpawnableBounds(testPos); i++)
        {
            if(!this.canCollide(testPos))
            {
                colliding = false;
                testPos = testPos.below();
            }
            else if(!colliding)
            {
                return testPos.above();
            }
        }

        // Search upwards for ground
        colliding = this.canCollide(pos);
        testPos = pos.above();
        for(int i = 0; i < GROUND_SEARCH_DISTANCE && Level.isInSpawnableBounds(testPos); i++)
        {
            if(this.canCollide(testPos))
            {
                colliding = true;
                testPos = testPos.above();
            }
            else if(colliding)
            {
                return testPos;
            }
        }
        return null;
    }

    private boolean canCollide(BlockPos pos)
    {
        return !this.level.getBlockState(pos).getCollisionShape(this.level, pos).isEmpty();
    }

    public GoblinTraderSpawner load(CompoundTag tag)
    {
        if(tag.contains("RunDelay", Tag.TAG_INT))
        {
            this.runDelay = tag.getInt("RunDelay");
        }
        if(tag.contains("SpawnChance", Tag.TAG_INT))
        {
            this.spawnChance = tag.getInt("SpawnChance");
        }
        return this;
    }

    @Override
    public CompoundTag save(CompoundTag tag)
    {
        tag.putInt("RunDelay", this.runDelay);
        tag.putInt("SpawnChance", this.spawnChance);
        return tag;
    }

    public static Optional<GoblinTraderSpawner> get(MinecraftServer server, EntityType<? extends AbstractGoblinEntity> type)
    {
        SpawnData data = SPAWN_DATA.get(type);
        if(data != null)
        {
            ServerLevel level = server.getLevel(data.levelKey());
            if(level != null)
            {
                String storageKey = BuiltInRegistries.ENTITY_TYPE.getKey(type) + "_spawner";
                return Optional.of(level.getDataStorage().computeIfAbsent(tag -> {
                    return new GoblinTraderSpawner(server, level, type, data.goblinData().get()).load(tag);
                }, () -> {
                    return new GoblinTraderSpawner(server, level, type, data.goblinData().get());
                }, storageKey));
            }
        }
        return Optional.empty();
    }

    public static void register(EntityType<? extends AbstractGoblinEntity> type, ResourceKey<Level> levelKey, Supplier<IGoblinData> goblinData)
    {
        SPAWN_DATA.putIfAbsent(type, new SpawnData(levelKey, goblinData));
    }

    private record SpawnData(ResourceKey<Level> levelKey, Supplier<IGoblinData> goblinData) {}
}
