package com.mrcrayfish.goblintraders.spawner;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.goblintraders.Config;
import com.mrcrayfish.goblintraders.core.ModEntities;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.entity.GoblinTrader;
import com.mrcrayfish.goblintraders.entity.VeinGoblinTrader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Author: MrCrayfish
 */
public class GoblinTraderSpawner extends SavedData
{
    private static final SpawnProperties<GoblinTrader> GOBLIN_TRADER_PROPERTIES = new SpawnProperties<>(ModEntities.GOBLIN_TRADER::get, () -> Config.ENTITIES.goblinTrader);
    private static final SpawnProperties<VeinGoblinTrader> VEIN_GOBLIN_TRADER_PROPERTIES = new SpawnProperties<>(ModEntities.VEIN_GOBLIN_TRADER::get, () -> Config.ENTITIES.veinGoblinTrader);

    private static final Codec<GoblinTraderSpawner> GOBLIN_TRADER_SPAWNER_CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.INT.fieldOf("RunDelay").forGetter(spawner -> spawner.runDelay),
        Codec.INT.fieldOf("SpawnChance").forGetter(spawner -> spawner.spawnChance)
    ).apply(instance, (runDelay, spawnChance) -> {
        return new GoblinTraderSpawner(GOBLIN_TRADER_PROPERTIES, runDelay, spawnChance);
    }));

    private static final Codec<GoblinTraderSpawner> VEIN_GOBLIN_TRADER_SPAWNER_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("RunDelay").forGetter(spawner -> spawner.runDelay),
            Codec.INT.fieldOf("SpawnChance").forGetter(spawner -> spawner.spawnChance)
    ).apply(instance, (runDelay, spawnChance) -> {
        return new GoblinTraderSpawner(VEIN_GOBLIN_TRADER_PROPERTIES, runDelay, spawnChance);
    }));

    @SuppressWarnings("DataFlowIssue") // NeoForge and Fabric patch DataFixTypes to allow null
    private static final SavedDataType<@NotNull GoblinTraderSpawner> TYPE_GOBLIN_TRADER = new SavedDataType<>("goblintraders_goblin_trader_spawner", () -> {
        return new GoblinTraderSpawner(GOBLIN_TRADER_PROPERTIES);
    }, GOBLIN_TRADER_SPAWNER_CODEC, null);

    @SuppressWarnings("DataFlowIssue") // NeoForge and Fabric patch DataFixTypes to allow null
    private static final SavedDataType<@NotNull GoblinTraderSpawner> TYPE_VEIN_GOBLIN_TRADER = new SavedDataType<>("goblintraders_vein_goblin_trader_spawner", () -> {
        return new GoblinTraderSpawner(VEIN_GOBLIN_TRADER_PROPERTIES);
    }, VEIN_GOBLIN_TRADER_SPAWNER_CODEC, null);

    private static final int SAFE_POSITION_ATTEMPTS = 50;
    private static final int MIN_SPAWN_DISTANCE = 5;
    private static final int GROUND_SEARCH_DISTANCE = 5;
    private static final int SAVE_INTERVAL = 200;

    private final EntityType<? extends @NotNull AbstractGoblinEntity> type;
    private final IGoblinData data;
    private int runDelay;
    private int spawnChance;

    public <T extends AbstractGoblinEntity> GoblinTraderSpawner(SpawnProperties<T> properties)
    {
        this.type = properties.type.get();
        this.data = properties.goblinData.get();
        this.runDelay = this.data.getSpawnDelay();
        this.spawnChance = this.data.getSpawnChance();
    }

    public <T extends AbstractGoblinEntity> GoblinTraderSpawner(SpawnProperties<T> properties, int runDelay, int spawnChance)
    {
        this.type = properties.type.get();
        this.data = properties.goblinData.get();
        this.runDelay = runDelay;
        this.spawnChance = spawnChance;
    }

    public void serverTick(ServerLevel level)
    {
        if(!level.getGameRules().get(GameRules.SPAWN_WANDERING_TRADERS))
            return;

        this.runDelay--;

        if(this.runDelay % SAVE_INTERVAL == 0)
            this.setDirty();

        if(this.runDelay > 0)
            return;

        this.runDelay = this.data.getSpawnInterval();
        if(!level.getGameRules().get(GameRules.SPAWN_MOBS))
            return;

        double randomChance = level.getRandom().nextDouble();
        if((this.spawnChance / 100.0) < randomChance)
        {
            this.spawnChance = Math.min(this.spawnChance + this.data.getSpawnChance(), 100);
            this.setDirty();
            return;
        }

        if(this.spawnGoblin(level))
        {
            this.runDelay = this.data.getSpawnDelay();
            this.spawnChance = this.data.getSpawnChance();
            this.setDirty();
        }
    }

    private boolean spawnGoblin(ServerLevel level)
    {
        ServerPlayer player = level.getRandomPlayer();
        if(player == null)
            return false;

        BlockPos pos = this.createSpawnPosition(level, player.blockPosition(), 16);
        if(pos == null)
            return false;

        Holder<Biome> biome = level.getBiome(pos);
        if(biome.is(Biomes.THE_VOID) || biome.is(Biomes.DEEP_DARK))
            return false;

        if(pos.getY() < this.data.getMinSpawnYLevel() || pos.getY() >= this.data.getMaxSpawnYLevel())
            return false;

        AbstractGoblinEntity goblin = this.type.spawn(level, pos, EntitySpawnReason.EVENT);
        if(goblin == null)
            return false;

        this.runDelay = this.data.getSpawnDelay();
        goblin.setDespawnDelay(this.data.getDespawnDelay());
        goblin.setHomeTo(pos, 16);
        this.setDirty();
        return true;
    }

    @Nullable
    private BlockPos createSpawnPosition(ServerLevel level, BlockPos center, int range)
    {
        for(int i = 0; i < SAFE_POSITION_ATTEMPTS; i++)
        {
            int posX = center.getX() + this.createRandomSpawnableDistance(level, range);
            int posY = center.getY() + this.createRandomSpawnableDistance(level, range);
            int posZ = center.getZ() + this.createRandomSpawnableDistance(level, range);
            BlockPos pos = this.findGround(level, new BlockPos(posX, posY, posZ));
            if(pos != null && !pos.closerThan(center, MIN_SPAWN_DISTANCE) && SpawnPlacements.isSpawnPositionOk(this.type, level, pos))
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
    private int createRandomSpawnableDistance(ServerLevel level, int maxSpawnDistance)
    {
        RandomSource random = level.getRandom();
        int direction = random.nextInt(2) == 0 ? 1 : -1;
        int spawnableRange = Math.max(maxSpawnDistance - MIN_SPAWN_DISTANCE, 0);
        return (MIN_SPAWN_DISTANCE + random.nextIntBetweenInclusive(0, spawnableRange)) * direction;
    }

    @Nullable
    private BlockPos findGround(ServerLevel level, BlockPos pos)
    {
        boolean colliding = this.canCollide(level, pos);

        // Search downwards for ground
        BlockPos testPos = pos.below();
        for(int i = 0; i < GROUND_SEARCH_DISTANCE && Level.isInSpawnableBounds(testPos); i++)
        {
            if(!this.canCollide(level, testPos))
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
        colliding = this.canCollide(level, pos);
        testPos = pos.above();
        for(int i = 0; i < GROUND_SEARCH_DISTANCE && Level.isInSpawnableBounds(testPos); i++)
        {
            if(this.canCollide(level, testPos))
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

    private boolean canCollide(ServerLevel level, BlockPos pos)
    {
        return !level.getBlockState(pos).getCollisionShape(level, pos).isEmpty();
    }

    public GoblinTraderSpawner load(CompoundTag tag)
    {
        this.runDelay = tag.getInt("RunDelay").orElse(this.data.getSpawnDelay());
        this.spawnChance = tag.getInt("SpawnChance").orElse(this.data.getSpawnChance());
        return this;
    }

    public CompoundTag save()
    {
        CompoundTag tag = new CompoundTag();
        tag.putInt("RunDelay", this.runDelay);
        tag.putInt("SpawnChance", this.spawnChance);
        return tag;
    }

    public static Optional<GoblinTraderSpawner> getGoblinTraderSpawner(MinecraftServer server)
    {
        return getTraderSpawner(server, Level.OVERWORLD, TYPE_GOBLIN_TRADER);
    }

    public static Optional<GoblinTraderSpawner> getVeinGoblinTraderSpawner(MinecraftServer server)
    {
        return getTraderSpawner(server, Level.NETHER, TYPE_VEIN_GOBLIN_TRADER);
    }

    private static Optional<GoblinTraderSpawner> getTraderSpawner(MinecraftServer server, ResourceKey<Level> key, SavedDataType<GoblinTraderSpawner> type)
    {
        ServerLevel level = server.getLevel(key);
        if(level != null)
        {
            return Optional.of(level.getDataStorage().computeIfAbsent(type));
        }
        return Optional.empty();
    }

    public record SpawnProperties<T extends AbstractGoblinEntity>(Supplier<EntityType<T>> type, Supplier<IGoblinData> goblinData) {}
}
