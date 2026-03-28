package com.mrcrayfish.goblintraders;

import com.mrcrayfish.goblintraders.core.ModEntities;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

/**
 * Author: MrCrayfish
 */
@Mod(Constants.MOD_ID)
public class GoblinTraders
{
    public GoblinTraders(IEventBus bus)
    {
        bus.addListener(this::onCommonSetup);
        bus.addListener(this::onEntityAttributeCreation);
        bus.addListener(this::onRegisterSpawnPlacements);
    }

    private void onCommonSetup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(Bootstrap::init);
    }

    private void onEntityAttributeCreation(EntityAttributeCreationEvent event)
    {
        event.put(ModEntities.GOBLIN_TRADER.get(), AbstractGoblinEntity.createAttributes().build());
        event.put(ModEntities.VEIN_GOBLIN_TRADER.get(), AbstractGoblinEntity.createAttributes().build());
    }

    public void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent event)
    {
        event.register(ModEntities.GOBLIN_TRADER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.VEIN_GOBLIN_TRADER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}
