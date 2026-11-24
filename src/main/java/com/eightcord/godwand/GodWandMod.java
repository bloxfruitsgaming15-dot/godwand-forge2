// GodWandMod.java — forge 1.20.1, mojang mappings
package com.eightcord.godwand;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(GodWandMod.MOD_ID)
public class GodWandMod {
    public static final String MOD_ID = "godwand";

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);

    public static final RegistryObject<GodWandItem> GOD_WAND =
            ITEMS.register("god_wand", () -> new GodWandItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<EntityType<GodBoltEntity>> GOD_BOLT =
            ENTITY_TYPES.register("god_bolt", () -> EntityType.Builder
                    .<GodBoltEntity>of(GodBoltEntity::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(MOD_ID + ":god_bolt"));

    public GodWandMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(bus);
        ENTITY_TYPES.register(bus);
    }
}
