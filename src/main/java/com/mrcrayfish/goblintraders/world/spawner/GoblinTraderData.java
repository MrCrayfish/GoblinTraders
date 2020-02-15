package com.mrcrayfish.goblintraders.world.spawner;

import com.mrcrayfish.goblintraders.Reference;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public class GoblinTraderData extends WorldSavedData
{
    private static final String DATA_NAME = Reference.MOD_ID + "_goblin_trader";

    private int goblinTraderSpawnDelay;
    private int goblinTraderSpawnChance;
    private UUID goblinTraderId;

    public GoblinTraderData()
    {
        super(DATA_NAME);
    }

    public GoblinTraderData(String name)
    {
        super(name);
    }

    public void setGoblinTraderSpawnDelay(int goblinTraderSpawnDelay)
    {
        this.goblinTraderSpawnDelay = goblinTraderSpawnDelay;
        this.setDirty(true);
    }

    public void setGoblinTraderSpawnChance(int goblinTraderSpawnChance)
    {
        this.goblinTraderSpawnChance = goblinTraderSpawnChance;
        this.setDirty(true);
    }

    public void setGoblinTraderId(UUID goblinTraderId)
    {
        this.goblinTraderId = goblinTraderId;
        this.setDirty(true);
    }

    public int getGoblinTraderSpawnDelay()
    {
        return goblinTraderSpawnDelay;
    }

    public int getGoblinTraderSpawnChance()
    {
        return goblinTraderSpawnChance;
    }

    public UUID getGoblinTraderId()
    {
        return goblinTraderId;
    }

    @Override
    public void read(CompoundNBT compound)
    {
        if(compound.contains("GoblinTraderSpawnDelay", Constants.NBT.TAG_INT))
        {
            this.goblinTraderSpawnDelay = compound.getInt("GoblinTraderSpawnDelay");
        }
        if(compound.contains("GoblinTraderSpawnChance", Constants.NBT.TAG_INT))
        {
            this.goblinTraderSpawnChance = compound.getInt("GoblinTraderSpawnChance");
        }
        if(compound.hasUniqueId("GoblinTraderId"))
        {
            this.goblinTraderId = compound.getUniqueId("GoblinTraderId");
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        compound.putInt("GoblinTraderSpawnDelay", this.goblinTraderSpawnDelay);
        compound.putInt("GoblinTraderSpawnChance", this.goblinTraderSpawnChance);
        if(this.goblinTraderId != null)
        {
            compound.putUniqueId("GoblinTraderId", this.goblinTraderId);
        }
        return compound;
    }

    public static GoblinTraderData get(MinecraftServer server)
    {
        ServerWorld world = server.getWorld(DimensionType.OVERWORLD);
        return world.getSavedData().getOrCreate(GoblinTraderData::new, DATA_NAME);
    }
}
