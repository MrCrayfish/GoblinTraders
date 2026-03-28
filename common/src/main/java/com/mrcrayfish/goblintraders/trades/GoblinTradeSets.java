package com.mrcrayfish.goblintraders.trades;

import com.mrcrayfish.goblintraders.util.Utils;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.item.trading.TradeSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public final class GoblinTradeSets
{
    public static final ResourceKey<TradeSet> GOBLIN_TRADER_COMMON = createKey("goblin_trader/common");
    public static final ResourceKey<TradeSet> GOBLIN_TRADER_UNCOMMON = createKey("goblin_trader/uncommon");
    public static final ResourceKey<TradeSet> GOBLIN_TRADER_RARE = createKey("goblin_trader/rare");
    public static final ResourceKey<TradeSet> GOBLIN_TRADER_EPIC = createKey("goblin_trader/epic");
    public static final ResourceKey<TradeSet> GOBLIN_TRADER_LEGENDARY = createKey("goblin_trader/legendary");
    public static final ResourceKey<TradeSet> VEIN_GOBLIN_TRADER_COMMON = createKey("vein_goblin_trader/common");
    public static final ResourceKey<TradeSet> VEIN_GOBLIN_TRADER_UNCOMMON = createKey("vein_goblin_trader/uncommon");
    public static final ResourceKey<TradeSet> VEIN_GOBLIN_TRADER_RARE = createKey("vein_goblin_trader/rare");
    public static final ResourceKey<TradeSet> VEIN_GOBLIN_TRADER_EPIC = createKey("vein_goblin_trader/epic");
    public static final ResourceKey<TradeSet> VEIN_GOBLIN_TRADER_LEGENDARY = createKey("vein_goblin_trader/legendary");

    public static void bootstrap(BootstrapContext<TradeSet> context)
    {
        TradeSets.register(context, GOBLIN_TRADER_COMMON, GoblinTradeTags.GOBLIN_TRADER_COMMON, UniformGenerator.between(5, 8));
        TradeSets.register(context, GOBLIN_TRADER_UNCOMMON, GoblinTradeTags.GOBLIN_TRADER_UNCOMMON, UniformGenerator.between(3, 5));
        TradeSets.register(context, GOBLIN_TRADER_RARE, GoblinTradeTags.GOBLIN_TRADER_RARE, UniformGenerator.between(2, 3));
        TradeSets.register(context, GOBLIN_TRADER_EPIC, GoblinTradeTags.GOBLIN_TRADER_EPIC, UniformGenerator.between(1, 2));
        TradeSets.register(context, GOBLIN_TRADER_LEGENDARY, GoblinTradeTags.GOBLIN_TRADER_LEGENDARY, ConstantValue.exactly(1));
        TradeSets.register(context, VEIN_GOBLIN_TRADER_COMMON, GoblinTradeTags.VEIN_GOBLIN_TRADER_COMMON, UniformGenerator.between(5, 8));
        TradeSets.register(context, VEIN_GOBLIN_TRADER_UNCOMMON, GoblinTradeTags.VEIN_GOBLIN_TRADER_UNCOMMON, UniformGenerator.between(3, 5));
        TradeSets.register(context, VEIN_GOBLIN_TRADER_RARE, GoblinTradeTags.VEIN_GOBLIN_TRADER_RARE, UniformGenerator.between(2, 3));
        TradeSets.register(context, VEIN_GOBLIN_TRADER_EPIC, GoblinTradeTags.VEIN_GOBLIN_TRADER_EPIC, UniformGenerator.between(1, 2));
        TradeSets.register(context, VEIN_GOBLIN_TRADER_LEGENDARY, GoblinTradeTags.VEIN_GOBLIN_TRADER_LEGENDARY, ConstantValue.exactly(1));
    }

    public static ResourceKey<TradeSet> createKey(String name)
    {
        return ResourceKey.create(Registries.TRADE_SET, Utils.id(name));
    }
}
