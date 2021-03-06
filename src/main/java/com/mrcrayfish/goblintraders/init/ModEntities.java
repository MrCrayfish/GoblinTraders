package com.mrcrayfish.goblintraders.init;

import com.mrcrayfish.goblintraders.Reference;
import com.mrcrayfish.goblintraders.entity.GoblinTraderEntity;
import com.mrcrayfish.goblintraders.entity.VeinGoblinTraderEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
public class ModEntities
{
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, Reference.MOD_ID);

    public static final RegistryObject<EntityType<GoblinTraderEntity>> GOBLIN_TRADER = build("goblin_trader", GoblinTraderEntity::new, 0.5F, 1.0F);
    public static final RegistryObject<EntityType<VeinGoblinTraderEntity>> VEIN_GOBLIN_TRADER = build("vein_goblin_trader", VeinGoblinTraderEntity::new, 0.5F, 1.0F);

    private static <T extends Entity> RegistryObject<EntityType<T>> build(String id, Function<World, T> function, float width, float height)
    {
        EntityType<T> type = EntityType.Builder.<T>create((entityType, world) -> function.apply(world), EntityClassification.CREATURE).size(width, height).setCustomClientFactory((spawnEntity, world) -> function.apply(world)).build(id.toString());
        return REGISTER.register(id, () -> type);
    }

    public static void registerEntityTypeAttributes()
    {
        GlobalEntityTypeAttributes.put(GOBLIN_TRADER.get(), GoblinTraderEntity.prepareAttributes().create());
        GlobalEntityTypeAttributes.put(VEIN_GOBLIN_TRADER.get(), VeinGoblinTraderEntity.prepareAttributes().create());
    }
}
