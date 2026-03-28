package com.mrcrayfish.goblintraders.trades;

import com.mrcrayfish.goblintraders.util.Utils;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.trading.VillagerTrade;

public final class GoblinTradeTags
{
    public static final TagKey<VillagerTrade> GOBLIN_TRADER_COMMON = createKey("goblin_trader/common");
    public static final TagKey<VillagerTrade> GOBLIN_TRADER_UNCOMMON = createKey("goblin_trader/uncommon");
    public static final TagKey<VillagerTrade> GOBLIN_TRADER_RARE = createKey("goblin_trader/rare");
    public static final TagKey<VillagerTrade> GOBLIN_TRADER_EPIC = createKey("goblin_trader/epic");
    public static final TagKey<VillagerTrade> GOBLIN_TRADER_LEGENDARY = createKey("goblin_trader/legendary");
    public static final TagKey<VillagerTrade> VEIN_GOBLIN_TRADER_COMMON = createKey("vein_goblin_trader/common");
    public static final TagKey<VillagerTrade> VEIN_GOBLIN_TRADER_UNCOMMON = createKey("vein_goblin_trader/uncommon");
    public static final TagKey<VillagerTrade> VEIN_GOBLIN_TRADER_RARE = createKey("vein_goblin_trader/rare");
    public static final TagKey<VillagerTrade> VEIN_GOBLIN_TRADER_EPIC = createKey("vein_goblin_trader/epic");
    public static final TagKey<VillagerTrade> VEIN_GOBLIN_TRADER_LEGENDARY = createKey("vein_goblin_trader/legendary");

    private static TagKey<VillagerTrade> createKey(String name)
    {
        return TagKey.create(Registries.VILLAGER_TRADE, Utils.id(name));
    }
}
