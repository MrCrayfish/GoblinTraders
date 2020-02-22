package com.mrcrayfish.goblintraders.world.spawner;

import com.mrcrayfish.goblintraders.Reference;
import com.mrcrayfish.goblintraders.entity.GoblinTraderEntity;
import com.mrcrayfish.goblintraders.init.ModEntities;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class GoblinTraderSpawner
{
    private final Random random = new Random();
    private final ServerWorld world;
    private final GoblinTraderData data;
    private int delayBeforeSpawnLogic;
    private int traderSpawnDelay;
    private int traderSpawnChance;

    public GoblinTraderSpawner(ServerWorld world, GoblinTraderData data)
    {
        this.world = world;
        this.data = data;
        this.delayBeforeSpawnLogic = 600;
        this.traderSpawnDelay = data.getGoblinTraderSpawnDelay();
        this.traderSpawnChance = data.getGoblinTraderSpawnChance();
        if(this.traderSpawnDelay == 0 && this.traderSpawnChance == 0)
        {
            this.traderSpawnDelay = 24000;
            this.traderSpawnChance = 25;
            data.setGoblinTraderSpawnDelay(this.traderSpawnDelay);
            data.setGoblinTraderSpawnChance(this.traderSpawnChance);
        }
    }

    private void tick()
    {
        if(this.world.getGameRules().getBoolean(GameRules.field_230128_E_))
        {
            if(--this.delayBeforeSpawnLogic <= 0)
            {
                this.delayBeforeSpawnLogic = 1200;
                this.traderSpawnDelay -= 1200;
                this.data.setGoblinTraderSpawnDelay(this.traderSpawnDelay);
                if(this.traderSpawnDelay <= 0)
                {
                    this.traderSpawnDelay = 24000;
                    if(this.world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING))
                    {
                        int i = this.traderSpawnChance;
                        this.traderSpawnChance = MathHelper.clamp(this.traderSpawnChance + 25, 25, 75);
                        this.data.setGoblinTraderSpawnChance(this.traderSpawnChance);
                        if(this.random.nextInt(100) <= i)
                        {
                            if(this.spawnTrader())
                            {
                                this.traderSpawnChance = 25;
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean spawnTrader()
    {
        PlayerEntity randomPlayer = this.world.getRandomPlayer();
        if(randomPlayer == null)
        {
            return true;
        }
        else if (this.random.nextInt(5) != 0)
        {
            return false;
        }
        else
        {
            BlockPos blockpos = randomPlayer.getPosition();
            BlockPos safestPos = this.getSafePositionAroundPlayer(blockpos, 10);
            if(safestPos != null && this.isEmptyCollision(safestPos))
            {
                if(safestPos.getY() >= 64)
                {
                    return false;
                }
                GoblinTraderEntity goblin = ModEntities.GOBLIN_TRADER.spawn(this.world, null, null, null, safestPos, SpawnReason.EVENT, false, false);
                if(goblin != null)
                {
                    this.data.setGoblinTraderId(goblin.getUniqueID());
                    goblin.setDespawnDelay(24000);
                    goblin.setHomePosAndDistance(safestPos, 16);
                    return true;
                }
            }
            return false;
        }
    }

    @Nullable
    private BlockPos getSafePositionAroundPlayer(BlockPos pos, int range)
    {
        if(range == 0)
        {
            return null;
        }
        BlockPos safestPos = null;
        for(int attempts = 0; attempts < 50; attempts++)
        {
            int posX = pos.getX() + this.random.nextInt(range * 2) - range;
            int posY = pos.getY() + this.random.nextInt(range) - range / 2;
            int posZ = pos.getZ() + this.random.nextInt(range * 2) - range;
            BlockPos testPos = this.findGround(new BlockPos(posX, posY, posZ), range);
            if(testPos != null && WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, this.world, testPos, ModEntities.GOBLIN_TRADER))
            {
                safestPos = testPos;
                break;
            }
        }
        return safestPos != null ? safestPos : this.getSafePositionAroundPlayer(pos, range / 2);
    }

    @Nullable
    private BlockPos findGround(BlockPos pos, int maxDistance)
    {
        if(this.world.isAirBlock(pos))
        {
            BlockPos downPos = pos;
            while(World.isValid(downPos.down()) && this.world.isAirBlock(downPos.down()) && downPos.down().withinDistance(pos, maxDistance))
            {
                downPos = downPos.down();
            }
            if(!this.world.isAirBlock(downPos.down()))
            {
                return downPos;
            }
        }
        else
        {
            BlockPos upPos = pos;
            while(World.isValid(upPos.up()) && !this.world.isAirBlock(upPos.up()) && upPos.up().withinDistance(pos, maxDistance))
            {
                upPos = upPos.up();
            }
            if(!this.world.isAirBlock(upPos.down()))
            {
                return upPos;
            }
        }
        return null;
    }

    private boolean isEmptyCollision(BlockPos pos)
    {
        if(!this.world.getBlockState(pos).getCollisionShape(this.world, pos).isEmpty())
        {
            return false;
        }
        return true;
    }

    public static GoblinTraderSpawner overworldSpawner;

    @SubscribeEvent
    public static void onServerStart(FMLServerStartedEvent event)
    {
        MinecraftServer server = event.getServer();
        overworldSpawner = new GoblinTraderSpawner(server.getWorld(DimensionType.OVERWORLD), GoblinTraderData.get(server));
    }

    @SubscribeEvent
    public static void onServerStart(FMLServerStoppedEvent event)
    {
        overworldSpawner = null;
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if(event.phase != TickEvent.Phase.START)
            return;

        if(event.side != LogicalSide.SERVER)
            return;

        if(!event.world.getDimension().isSurfaceWorld())
            return;

        overworldSpawner.tick();
    }
}
