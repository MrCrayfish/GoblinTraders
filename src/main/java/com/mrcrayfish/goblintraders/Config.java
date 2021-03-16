package com.mrcrayfish.goblintraders;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Author: MrCrayfish
 */
public class Config
{
    public static class Common
    {
        public final Goblin goblinTrader;
        public final Goblin veinGoblinTrader;

        Common(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Common configuration settings").push("common");
            this.goblinTrader = new Goblin(builder, "Goblin Trader", "goblin_trader", 25, 24000, 0, 64);
            this.veinGoblinTrader = new Goblin(builder, "Vein Goblin Trader", "vein_goblin_trader", 25, 24000, 0, 128);
            builder.pop();
        }

        public static class Goblin
        {
            public final ForgeConfigSpec.IntValue traderSpawnChance;
            public final ForgeConfigSpec.IntValue traderSpawnDelay;
            public final ForgeConfigSpec.IntValue traderMinSpawnLevel;
            public final ForgeConfigSpec.IntValue traderMaxSpawnLevel;

            Goblin(ForgeConfigSpec.Builder builder, String name, String key, int spawnChance, int spawnDelay, int minLevel, int maxLevel)
            {
                builder.comment(name + " settings").push(key);
                this.traderSpawnChance = builder
                        .comment("The chance out of one hundred that the trader will spawn in the over world")
                        .defineInRange("traderSpawnChance", spawnChance, 1, 100);
                this.traderSpawnDelay = builder
                        .comment("The amount of ticks before the trader will spawn again")
                        .defineInRange("traderSpawnDelay", spawnDelay, 0, Integer.MAX_VALUE);
                this.traderMinSpawnLevel = builder
                        .comment("The minimum level the trader can spawn")
                        .defineInRange("traderMinSpawnLevel", minLevel, 0, 256);
                this.traderMaxSpawnLevel = builder
                        .comment("The maximum level the trader can spawn")
                        .defineInRange("traderMaxSpawnLevel", maxLevel, 0, 256);
                builder.pop();
            }
        }
    }

    static final ForgeConfigSpec commonSpec;
    public static final Config.Common COMMON;

    static
    {
        final Pair<Common, ForgeConfigSpec> commonPair = new ForgeConfigSpec.Builder().configure(Config.Common::new);
        commonSpec = commonPair.getRight();
        COMMON = commonPair.getLeft();
    }
}
