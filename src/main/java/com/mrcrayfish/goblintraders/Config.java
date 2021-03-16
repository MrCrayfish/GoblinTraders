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
        public final ForgeConfigSpec.IntValue goblinTraderSpawnChance;
        public final ForgeConfigSpec.IntValue goblinTraderSpawnDelay;
        public final ForgeConfigSpec.IntValue goblinTraderMinSpawnLevel;
        public final ForgeConfigSpec.IntValue goblinTraderMaxSpawnLevel;
        public final ForgeConfigSpec.IntValue veinGoblinTraderSpawnChance;
        public final ForgeConfigSpec.IntValue veinGoblinTraderSpawnDelay;
        public final ForgeConfigSpec.IntValue veinGoblinTraderMinSpawnLevel;
        public final ForgeConfigSpec.IntValue veinGoblinTraderMaxSpawnLevel;

        Common(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Common configuration settings").push("common");
            this.goblinTraderSpawnChance = builder
                    .comment("The chance out of one hundred that the Goblin Trader will spawn in the over world")
                    .defineInRange("goblinTraderSpawnChance", 25, 1, 100);
            this.goblinTraderSpawnDelay = builder
                    .comment("The amount of ticks before the Goblin Trader will spawn again")
                    .defineInRange("goblinTraderSpawnDelay", 24000, 0, Integer.MAX_VALUE);
            this.goblinTraderMinSpawnLevel = builder
                    .comment("The minimum level the Goblin Trader can spawn")
                    .defineInRange("goblinTraderMinSpawnLevel", 0, 0, 256);
            this.goblinTraderMaxSpawnLevel = builder
                    .comment("The maximum level the Goblin Trader can spawn")
                    .defineInRange("goblinTraderMaxSpawnLevel", 64, 0, 256);
            this.veinGoblinTraderSpawnChance = builder
                    .comment("The chance out of one hundred that the Vein Goblin Trader will spawn in the over world")
                    .defineInRange("veinGoblinTraderSpawnChance", 25, 1, 100);
            this.veinGoblinTraderSpawnDelay = builder
                    .comment("The amount of ticks before the Vein Goblin Trader will spawn again")
                    .defineInRange("veinGoblinTraderSpawnDelay", 24000, 0, Integer.MAX_VALUE);
            this.veinGoblinTraderMinSpawnLevel = builder
                    .comment("The minimum level the Goblin Trader can spawn")
                    .defineInRange("veinGoblinTraderMinSpawnLevel", 0, 0, 256);
            this.veinGoblinTraderMaxSpawnLevel = builder
                    .comment("The maximum level the Goblin Trader can spawn")
                    .defineInRange("veinGoblinTraderMaxSpawnLevel", 128, 0, 256);
            builder.pop();
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
