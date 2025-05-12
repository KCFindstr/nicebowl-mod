package com.rabimimi.nicebowl.components;

import com.rabimimi.nicebowl.NiceBowlMod;
import com.rabimimi.nicebowl.utils.PlayerData;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;

public class ComponentRegistry {
  public static final DeferredRegister<ComponentType<?>> COMPONENTS = DeferredRegister.create(
      NiceBowlMod.MOD_ID, RegistryKeys.DATA_COMPONENT_TYPE);

  public static final RegistryEntry<ComponentType<PlayerData>> PLAYER_DATA_COMPONENT = COMPONENTS.register(
      NiceBowlMod.id("player_data"),
      () -> ComponentType.<PlayerData>builder().codec(PlayerData.CODEC.codec()).build());

  public static void register() {
    NiceBowlMod.LOGGER.debug("Registering {} components", NiceBowlMod.MOD_ID);
  }
}
