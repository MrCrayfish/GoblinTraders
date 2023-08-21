package com.mrcrayfish.goblintraders.spawner;

/**
 * Author: MrCrayfish
 */
public interface IGoblinData
{
    int getSpawnChance();

    int getSpawnDelay();

    int getSpawnInterval();

    int getMinSpawnYLevel();

    int getMaxSpawnYLevel();

    int getRestockDelay();

    boolean canAttackBack();

    int getGruntNoiseInterval();
}
