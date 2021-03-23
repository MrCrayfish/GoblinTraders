package com.mrcrayfish.goblintraders.world.spawner;

import com.mrcrayfish.goblintraders.Reference;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class GoblinTraderData extends WorldSavedData
{
    private static final String DATA_NAME = Reference.MOD_ID + "_goblin_trader";

    private Map<String, GoblinData> data = new HashMap<>();

    public GoblinTraderData()
    {
        super(DATA_NAME);
    }

    public GoblinTraderData(String name)
    {
        super(name);
    }

    public GoblinData getGoblinData(String key)
    {
        return this.data.computeIfAbsent(key, s -> new GoblinData(this));
    }

    @Override
    public void read(CompoundNBT compound)
    {
        if(compound.contains("GoblinTraderSpawnDelay", Constants.NBT.TAG_INT))
        {
            this.getGoblinData("GoblinTrader").setGoblinTraderSpawnDelay(compound.getInt("GoblinTraderSpawnDelay"));
        }
        if(compound.contains("GoblinTraderSpawnChance", Constants.NBT.TAG_INT))
        {
            this.getGoblinData("GoblinTrader").setGoblinTraderSpawnChance(compound.getInt("GoblinTraderSpawnChance"));
        }
        if(compound.hasUniqueId("GoblinTraderId"))
        {
            this.getGoblinData("GoblinTrader").setGoblinTraderId(compound.getUniqueId("GoblinTraderId"));
        }
        if(compound.contains("Data", Constants.NBT.TAG_LIST))
        {
            this.data.clear();
            ListNBT list = compound.getList("Data", Constants.NBT.TAG_COMPOUND);
            list.forEach(nbt -> {
                CompoundNBT goblinTag = (CompoundNBT) nbt;
                String key = goblinTag.getString("Key");
                GoblinData data = new GoblinData(this);
                data.read(goblinTag);
                this.data.put(key, data);
            });
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        ListNBT list = new ListNBT();
        this.data.forEach((s, goblinData) -> {
            CompoundNBT goblinTag = new CompoundNBT();
            goblinData.write(goblinTag);
            goblinTag.putString("Key", s);
            list.add(goblinTag);
        });
        compound.put("Data", list);
        return compound;
    }

    public static GoblinTraderData get(MinecraftServer server)
    {
        ServerWorld world = server.getWorld(DimensionType.OVERWORLD);
        return world.getSavedData().getOrCreate(GoblinTraderData::new, DATA_NAME);
    }
}
