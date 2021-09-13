package com.mrcrayfish.goblintraders.init;

import com.mrcrayfish.goblintraders.Reference;
import com.mrcrayfish.goblintraders.entity.AbstractGoblinEntity;
import com.mrcrayfish.goblintraders.entity.GoblinTraderEntity;
import com.mrcrayfish.goblintraders.entity.VeinGoblinTraderEntity;
import com.mrcrayfish.goblintraders.item.SupplierSpawnEggItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;

/**
 * Author: MrCrayfish
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities
{
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, Reference.MOD_ID);

    public static final RegistryObject<EntityType<GoblinTraderEntity>> GOBLIN_TRADER = build("goblin_trader", GoblinTraderEntity::new, 0.5F, 1.0F);
    public static final RegistryObject<EntityType<VeinGoblinTraderEntity>> VEIN_GOBLIN_TRADER = build("vein_goblin_trader", VeinGoblinTraderEntity::new, 0.5F, 1.0F);

    private static <T extends Entity> RegistryObject<EntityType<T>> build(String id, Function<Level, T> function, float width, float height)
    {
        EntityType<T> type = EntityType.Builder.<T>of((entityType, world) -> function.apply(world), MobCategory.CREATURE).sized(width, height).setCustomClientFactory((spawnEntity, world) -> function.apply(world)).build(id);
        return REGISTER.register(id, () -> type);
    }

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event)
    {
        event.put(GOBLIN_TRADER.get(), AbstractGoblinEntity.createAttributes().build());
        event.put(VEIN_GOBLIN_TRADER.get(), AbstractGoblinEntity.createAttributes().build());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void register(RegistryEvent.Register<EntityType<?>> event)
    {
        SupplierSpawnEggItem.updateEggMap();
    }
}
