package com.mrcrayfish.goblintraders.trades;

import com.google.gson.JsonObject;
import com.mrcrayfish.goblintraders.trades.type.ITradeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerTrades;

/**
 * Author: MrCrayfish
 */
public abstract class TradeSerializer<T extends ITradeType<? extends VillagerTrades.ItemListing>>
{
    private ResourceLocation id;

    public TradeSerializer(ResourceLocation id)
    {
        this.id = id;
    }

    public ResourceLocation getId()
    {
        return this.id;
    }

    public abstract T deserialize(JsonObject object);

    public JsonObject serialize(T trade)
    {
        JsonObject object = new JsonObject();
        object.addProperty("type", this.id.toString());
        return object;
    }
}
