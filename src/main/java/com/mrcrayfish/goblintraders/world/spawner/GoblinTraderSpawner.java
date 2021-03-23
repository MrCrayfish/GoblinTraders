package com.mrcrayfish.goblintraders.world.spawner;

import com.mrcrayfish.goblintraders.Config;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.init.ModEntities;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class GoblinTraderSpawner
{
    private final GoblinData data;
    private final EntityType<? extends AbstractGoblinEntity> entityType;
    private int delayBeforeSpawnLogic;
    private final int traderSpawnDelay;
    private final int traderSpawnChance;
    private int currentTraderSpawnDelay;
    private int currentTraderSpawnChance;
    private int minLevel;
    private int maxLevel;

    public GoblinTraderSpawner(MinecraftServer server, String key, EntityType<? extends AbstractGoblinEntity> entityType, Config.Common.Goblin goblin)
    {
        this.data = GoblinTraderData.get(server).getGoblinData(key);
        this.entityType = entityType;
        this.delayBeforeSpawnLogic = 600;
        this.currentTraderSpawnDelay = this.data.getGoblinTraderSpawnDelay();
        this.currentTraderSpawnChance = this.data.getGoblinTraderSpawnChance();
        this.traderSpawnDelay = goblin.traderSpawnDelay.get();
        this.traderSpawnChance = goblin.traderSpawnChance.get();
        this.minLevel = Math.min(goblin.traderMinSpawnLevel.get(), goblin.traderMaxSpawnLevel.get());
        this.maxLevel = Math.max(goblin.traderMinSpawnLevel.get(), goblin.traderMaxSpawnLevel.get());
        if(this.currentTraderSpawnDelay == 0 && this.currentTraderSpawnChance == 0)
        {
            this.currentTraderSpawnDelay = this.traderSpawnDelay;
            this.currentTraderSpawnChance = this.traderSpawnChance;
            this.data.setGoblinTraderSpawnDelay(this.currentTraderSpawnDelay);
            this.data.setGoblinTraderSpawnChance(this.currentTraderSpawnChance);
        }
    }

    public int tick(World world)
    {
        if(world.getGameRules().getBoolean(GameRules.field_230128_E_))
        {
            if(--this.delayBeforeSpawnLogic <= 0)
            {
                int delay = this.traderSpawnDelay / 20;
                this.delayBeforeSpawnLogic = delay;
                this.currentTraderSpawnDelay -= delay;
                this.data.setGoblinTraderSpawnDelay(this.currentTraderSpawnDelay);
                if(this.currentTraderSpawnDelay <= 0)
                {
                    this.currentTraderSpawnDelay = this.traderSpawnDelay;
                    if(world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING))
                    {
                        int spawnChance = this.currentTraderSpawnChance;
                        this.currentTraderSpawnChance = MathHelper.clamp(this.currentTraderSpawnChance + this.traderSpawnChance, this.traderSpawnChance, 100);
                        this.data.setGoblinTraderSpawnChance(this.currentTraderSpawnChance);
                        if(world.rand.nextInt(100) <= spawnChance)
                        {
                            if(this.spawnTrader(world))
                            {
                                this.currentTraderSpawnChance = this.traderSpawnChance;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    private boolean spawnTrader(World world)
    {
        List<PlayerEntity> players = new ArrayList<>(world.getPlayers());
        if(players.isEmpty())
        {
            return false;
        }
        PlayerEntity randomPlayer = players.get(world.rand.nextInt(players.size()));
        if(randomPlayer == null)
        {
            return true;
        }
        else
        {
            BlockPos blockpos = randomPlayer.getPosition();
            BlockPos safestPos = this.getSafePositionAroundPlayer(randomPlayer.world, blockpos, 10);
            if(safestPos != null && this.isEmptyCollision(randomPlayer.world, safestPos))
            {
                if(safestPos.getY() < this.minLevel || safestPos.getY() >= this.maxLevel)
                {
                    return false;
                }
                AbstractGoblinEntity goblin = this.entityType.spawn((ServerWorld) randomPlayer.world, (CompoundNBT) null, (ITextComponent) null, (PlayerEntity) null, safestPos, SpawnReason.EVENT, false, false);
                if(goblin != null)
                {
                    this.data.setGoblinTraderId(goblin.getUniqueID());
                    goblin.setDespawnDelay(this.traderSpawnDelay);
                    goblin.setHomePosAndDistance(safestPos, 16);
                    return true;
                }
            }
            return false;
        }
    }

    @Nullable
    private BlockPos getSafePositionAroundPlayer(World world, BlockPos pos, int range)
    {
        if(range == 0)
        {
            return null;
        }
        BlockPos safestPos = null;
        for(int attempts = 0; attempts < 50; attempts++)
        {
            int posX = pos.getX() + world.rand.nextInt(range * 2) - range;
            int posY = pos.getY() + world.rand.nextInt(range) - range / 2;
            int posZ = pos.getZ() + world.rand.nextInt(range * 2) - range;
            BlockPos testPos = this.findGround(world, new BlockPos(posX, posY, posZ), range);
            if(testPos != null && WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, world, testPos, ModEntities.GOBLIN_TRADER.get()))
            {
                safestPos = testPos;
                break;
            }
        }
        return safestPos != null ? safestPos : this.getSafePositionAroundPlayer(world, pos, range / 2);
    }

    @Nullable
    private BlockPos findGround(World world, BlockPos pos, int maxDistance)
    {
        if(world.isAirBlock(pos))
        {
            BlockPos downPos = pos;
            while(World.isValid(downPos.down()) && world.isAirBlock(downPos.down()) && downPos.down().withinDistance(pos, maxDistance))
            {
                downPos = downPos.down();
            }
            if(!world.isAirBlock(downPos.down()))
            {
                return downPos;
            }
        }
        else
        {
            BlockPos upPos = pos;
            while(World.isValid(upPos.up()) && !world.isAirBlock(upPos.up()) && upPos.up().withinDistance(pos, maxDistance))
            {
                upPos = upPos.up();
            }
            if(!world.isAirBlock(upPos.down()))
            {
                return upPos;
            }
        }
        return null;
    }

    private boolean isEmptyCollision(World world, BlockPos pos)
    {
        if(!world.getBlockState(pos).getCollisionShape(world, pos).isEmpty())
        {
            return false;
        }
        return true;
    }
}
