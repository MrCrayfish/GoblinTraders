package com.mrcrayfish.goblintraders.world.spawner;

import com.mrcrayfish.goblintraders.Reference;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.common.util.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public class GoblinTraderData extends SavedData
{
    private static final String DATA_NAME = Reference.MOD_ID + "_goblin_trader";

    private final Map<String, GoblinData> data = new HashMap<>();

    public GoblinTraderData(CompoundTag tag)
    {
        this.read(tag);
    }

    public GoblinData getGoblinData(String key)
    {
        return this.data.computeIfAbsent(key, s -> new GoblinData(this));
    }

    public void read(CompoundTag tag)
    {
        if(tag.contains("GoblinTraderSpawnDelay", Constants.NBT.TAG_INT))
        {
            this.getGoblinData("GoblinTrader").setGoblinTraderSpawnDelay(tag.getInt("GoblinTraderSpawnDelay"));
        }
        if(tag.contains("GoblinTraderSpawnChance", Constants.NBT.TAG_INT))
        {
            this.getGoblinData("GoblinTrader").setGoblinTraderSpawnChance(tag.getInt("GoblinTraderSpawnChance"));
        }
        if(tag.contains("Data", Constants.NBT.TAG_LIST))
        {
            this.data.clear();
            ListTag list = tag.getList("Data", Constants.NBT.TAG_COMPOUND);
            list.forEach(nbt -> {
                CompoundTag goblinTag = (CompoundTag) nbt;
                String key = goblinTag.getString("Key");
                GoblinData data = new GoblinData(this);
                data.read(goblinTag);
                this.data.put(key, data);
            });
        }
    }

    @Override
    public CompoundTag save(CompoundTag compound)
    {
        ListTag list = new ListTag();
        this.data.forEach((s, goblinData) -> {
            CompoundTag goblinTag = new CompoundTag();
            goblinData.write(goblinTag);
            goblinTag.putString("Key", s);
            list.add(goblinTag);
        });
        compound.put("Data", list);
        return compound;
    }

    public static GoblinTraderData get(MinecraftServer server)
    {
        ServerLevel level = server.getLevel(Level.OVERWORLD);
        return level.getDataStorage().get(GoblinTraderData::new, DATA_NAME);
    }
}
