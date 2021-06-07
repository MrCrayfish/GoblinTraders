package com.mrcrayfish.goblintraders.world.spawner;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants;

import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public class GoblinData
{
    private GoblinTraderData data;
    private int goblinTraderSpawnDelay;
    private int goblinTraderSpawnChance;

    public GoblinData(GoblinTraderData data)
    {
        this.data = data;
    }

    public void setGoblinTraderSpawnDelay(int goblinTraderSpawnDelay)
    {
        this.goblinTraderSpawnDelay = goblinTraderSpawnDelay;
        this.data.setDirty(true);
    }

    public void setGoblinTraderSpawnChance(int goblinTraderSpawnChance)
    {
        this.goblinTraderSpawnChance = goblinTraderSpawnChance;
        this.data.setDirty(true);
    }

    public int getGoblinTraderSpawnDelay()
    {
        return goblinTraderSpawnDelay;
    }

    public int getGoblinTraderSpawnChance()
    {
        return goblinTraderSpawnChance;
    }

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
    }

    public CompoundNBT write(CompoundNBT compound)
    {
        compound.putInt("GoblinTraderSpawnDelay", this.goblinTraderSpawnDelay);
        compound.putInt("GoblinTraderSpawnChance", this.goblinTraderSpawnChance);
        return compound;
    }
}
