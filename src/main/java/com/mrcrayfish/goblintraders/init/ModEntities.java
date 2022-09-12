package com.mrcrayfish.goblintraders.init;

import com.mrcrayfish.goblintraders.Reference;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.entity.GoblinTraderEntity;
import com.mrcrayfish.goblintraders.entity.VeinGoblinTraderEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;

import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public class ModEntities implements ModInitializer
{
    public static final EntityType<GoblinTraderEntity> GOBLIN_TRADER = Registry.register(
            Registry.ENTITY_TYPE,
            new ResourceLocation(Reference.MOD_ID, "goblin_trader"),
            new FabricEntityTypeBuilder.Living<GoblinTraderEntity>(MobCategory.CREATURE, GoblinTraderEntity::new){}
                    .dimensions(EntityDimensions.fixed(0.5F, 1.0F))
                    .defaultAttributes(AbstractGoblinEntity::createAttributes)
                    .build()
    );

    public static final EntityType<VeinGoblinTraderEntity> VEIN_GOBLIN_TRADER = Registry.register(
            Registry.ENTITY_TYPE,
            new ResourceLocation(Reference.MOD_ID, "vein_goblin_trader"),
            new FabricEntityTypeBuilder.Living<VeinGoblinTraderEntity>(MobCategory.CREATURE, VeinGoblinTraderEntity::new){}
                    .dimensions(EntityDimensions.fixed(0.5F, 1.0F))
                    .defaultAttributes(AbstractGoblinEntity::createAttributes)
                    .build()
    );

    @Override
    public void onInitialize()
    {

    }
}
