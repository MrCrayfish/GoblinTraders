package com.mrcrayfish.goblintraders.trades;

import com.google.gson.JsonObject;
import com.mrcrayfish.goblintraders.trades.type.ITradeType;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.util.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public abstract class TradeSerializer<T extends ITradeType<? extends VillagerTrades.ITrade>>
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
