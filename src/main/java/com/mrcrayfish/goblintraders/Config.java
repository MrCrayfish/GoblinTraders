package com.mrcrayfish.goblintraders;

import com.mrcrayfish.framework.api.config.BoolProperty;
import com.mrcrayfish.framework.api.config.ConfigProperty;
import com.mrcrayfish.framework.api.config.DoubleProperty;
import com.mrcrayfish.framework.api.config.FrameworkConfig;
import com.mrcrayfish.framework.api.config.IntProperty;
import com.mrcrayfish.goblintraders.trades.IRaritySettings;
import com.mrcrayfish.goblintraders.trades.TradeRarity;

/**
 * Author: MrCrayfish
 */
public final class Config
{
    @FrameworkConfig(id = Reference.MOD_ID, name = "entities")
    public static final Entities ENTITIES = new Entities();

    public static class Entities
    {
        @ConfigProperty(name = "goblinTrader")
        public final Goblin goblinTrader;

        @ConfigProperty(name = "veinGoblinTrader")
        public final Goblin veinGoblinTrader;

        @ConfigProperty(name = "preventDespawnIfNamed", comment = "If true, prevents the trader from despawning if named")
        public final BoolProperty preventDespawnIfNamed = BoolProperty.create(true);

        private Entities()
        {
            this.goblinTrader = new Goblin(25, 24000, -64, 50);
            this.veinGoblinTrader = new Goblin(25, 24000, 0, 128);
        }

        public static class Goblin
        {
            @ConfigProperty(name = "traderSpawnChance", comment = "The chance out of one hundred that the trader will spawn in the over world")
            public final IntProperty traderSpawnChance;

            //TODO look into issue where high delay will still be present even if this value is lowered
            @ConfigProperty(name = "traderSpawnDelay", comment = "The amount of ticks before the trader will spawn again")
            public final IntProperty traderSpawnDelay;

            @ConfigProperty(name = "traderMinSpawnLevel", comment = "The minimum level the trader can spawn", worldRestart = true)
            public final IntProperty traderMinSpawnLevel;

            @ConfigProperty(name = "traderMaxSpawnLevel", comment = "The maximum level the trader can spawn", worldRestart = true)
            public final IntProperty traderMaxSpawnLevel;

            @ConfigProperty(name = "restockDelay", comment = "The amount of ticks before the trader will replenish it's trades. Set to -1 to disable restocking")
            public final IntProperty restockDelay;

            @ConfigProperty(name = "canAttackBack", comment = "If true, the goblin will try to hit back a player or mob that hit them")
            public final BoolProperty canAttackBack;

            @ConfigProperty(name = "gruntNoiseInterval", comment = "Goblins will make a grunt noise while walking around. If you find it happening too often, you can increase the interval. Value is represented in ticks.")
            public final IntProperty gruntNoiseInterval;

            @ConfigProperty(name = "trades")
            public final Trades trades = new Trades();

            private Goblin(int spawnChance, int spawnDelay, int minLevel, int maxLevel)
            {
                this.traderSpawnChance = IntProperty.create(spawnChance, 1, 100);
                this.traderSpawnDelay = IntProperty.create(spawnDelay, 0, Integer.MAX_VALUE);
                this.traderMinSpawnLevel = IntProperty.create(minLevel, -64, 320);
                this.traderMaxSpawnLevel = IntProperty.create(maxLevel, -64, 320);
                this.restockDelay = IntProperty.create(48000, -1, Integer.MAX_VALUE);
                this.canAttackBack = BoolProperty.create(true);
                this.gruntNoiseInterval = IntProperty.create(80, 1, 1000);
            }

            public static class Trades
            {
                @ConfigProperty(name = "common")
                public final Trade common = new Trade(5, 8, 1.0);

                @ConfigProperty(name = "uncommon")
                public final Trade uncommon = new Trade(3, 5, 1.0);

                @ConfigProperty(name = "rare")
                public final Trade rare = new Trade(3, 4, 1.0);

                @ConfigProperty(name = "epic")
                public final Trade epic = new Trade(0, 2, 1.0);

                @ConfigProperty(name = "legendary")
                public final Trade legendary = new Trade(1, 1, 0.1);

                public IRaritySettings getSettings(TradeRarity rarity)
                {
                    return switch(rarity)
                    {
                        case COMMON -> this.common;
                        case UNCOMMON -> this.uncommon;
                        case RARE -> this.rare;
                        case EPIC -> this.epic;
                        case LEGENDARY -> this.legendary;
                    };
                }

                public static class Trade implements IRaritySettings
                {
                    @ConfigProperty(name = "minAmount", comment = "The minimum amount of trades that a golbin will have.")
                    public final IntProperty minAmount;

                    @ConfigProperty(name = "manAmount", comment = "The maximum amount of trades that a golbin can have.")
                    public final IntProperty maxAmount;

                    @ConfigProperty(name = "includeChance", comment = "The chance this trade rarity will be included in the goblin's trades")
                    public final DoubleProperty includeChance;

                    public Trade(int min, int max, double includeChance)
                    {
                        this.minAmount = IntProperty.create(min, 0, Integer.MAX_VALUE);
                        this.maxAmount = IntProperty.create(max, 0, Integer.MAX_VALUE);
                        this.includeChance = DoubleProperty.create(includeChance, 0.0, 1.0);
                    }

                    @Override
                    public int getMinValue()
                    {
                        return this.minAmount.get();
                    }

                    @Override
                    public int getMaxValue()
                    {
                        return this.maxAmount.get();
                    }

                    @Override
                    public double includeChance()
                    {
                        return this.includeChance.get();
                    }
                }
            }
        }
    }
}
