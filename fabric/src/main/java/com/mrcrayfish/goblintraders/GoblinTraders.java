package com.mrcrayfish.goblintraders;

import com.mrcrayfish.framework.FrameworkSetup;
import com.mrcrayfish.goblintraders.core.ModEntities;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.trades.TradeManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

/**
 * Author: MrCrayfish
 */
public class GoblinTraders implements ModInitializer
{
    public GoblinTraders()
    {
        FrameworkSetup.run();
    }

    @Override
    public void onInitialize()
    {
        Bootstrap.init();
        ResourceLoader.get(PackType.SERVER_DATA).registerReloader(TradeManager.ID, TradeManager.instance());
        FabricDefaultAttributeRegistry.register(ModEntities.GOBLIN_TRADER.get(), AbstractGoblinEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.VEIN_GOBLIN_TRADER.get(), AbstractGoblinEntity.createAttributes());
        SpawnPlacements.register(ModEntities.GOBLIN_TRADER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacements.register(ModEntities.VEIN_GOBLIN_TRADER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
    }
}
