package com.mrcrayfish.goblintraders.world.spawner;

import com.mrcrayfish.goblintraders.Reference;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.entity.GoblinTraderEntity;
import com.mrcrayfish.goblintraders.init.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class GoblinTraderSpawner
{
    private final Random random = new Random();
    private final ServerWorld world;
    private final GoblinData data;
    private int delayBeforeSpawnLogic;
    private int traderSpawnDelay;
    private int traderSpawnChance;
    private EntityType<? extends AbstractGoblinEntity> entityType;
    private int minLevel;
    private int maxLevel;

    public GoblinTraderSpawner(ServerWorld world, GoblinData data, EntityType<? extends AbstractGoblinEntity> entityType, int minLevel, int maxLevel)
    {
        this.world = world;
        this.data = data;
        this.delayBeforeSpawnLogic = 600;
        this.traderSpawnDelay = data.getGoblinTraderSpawnDelay();
        this.traderSpawnChance = data.getGoblinTraderSpawnChance();
        this.entityType = entityType;
        this.minLevel = Math.min(minLevel, maxLevel);
        this.maxLevel = Math.max(minLevel, maxLevel);
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
        //List<PlayerEntity> players = this.world.getServer().getPlayerList().getPlayers().stream().filter(player -> player.dimension == this.world.getDimension().getType() && player.isAlive()).collect(Collectors.toList());
        List<PlayerEntity> players = new ArrayList<>(this.world.getPlayers());
        if(players.isEmpty())
        {
            return false;
        }
        PlayerEntity randomPlayer = players.get(this.world.rand.nextInt(players.size()));
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
            BlockPos blockpos = randomPlayer.func_233580_cy_();
            BlockPos safestPos = this.getSafePositionAroundPlayer(randomPlayer.world, blockpos, 10);
            if(safestPos != null && this.isEmptyCollision(randomPlayer.world, safestPos))
            {
                if(safestPos.getY() < this.minLevel || safestPos.getY() >= this.maxLevel)
                {
                    return false;
                }
                AbstractGoblinEntity goblin = this.entityType.spawn(randomPlayer.world, null, null, null, safestPos, SpawnReason.EVENT, false, false);
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
    private BlockPos getSafePositionAroundPlayer(World world, BlockPos pos, int range)
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
            BlockPos testPos = this.findGround(world, new BlockPos(posX, posY, posZ), range);
            if(testPos != null && WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, world, testPos, ModEntities.GOBLIN_TRADER))
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
        System.out.println(world.getBlockState(pos).getBlock().getRegistryName());
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

    private static List<GoblinTraderSpawner> spawners = new ArrayList<>();

    @SubscribeEvent
    public static void onServerStart(FMLServerStartedEvent event)
    {
        MinecraftServer server = event.getServer();
        GoblinTraderData traderData = GoblinTraderData.get(server);
        spawners.add(new GoblinTraderSpawner(server.getWorld(World.field_234918_g_), traderData.getGoblinData("GoblinTrader"), ModEntities.GOBLIN_TRADER, 0, 64));
        spawners.add(new GoblinTraderSpawner(server.getWorld(World.field_234919_h_), traderData.getGoblinData("VeinGoblinTrader"), ModEntities.VEIN_GOBLIN_TRADER, 0, 128));
    }

    @SubscribeEvent
    public static void onServerStart(FMLServerStoppedEvent event)
    {
        spawners.clear();
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if(event.phase != TickEvent.Phase.START)
            return;

        if(event.side != LogicalSide.SERVER)
            return;

        if(!event.world.func_234923_W_().equals(World.field_234918_g_))
            return;

        spawners.forEach(GoblinTraderSpawner::tick);
    }
}
