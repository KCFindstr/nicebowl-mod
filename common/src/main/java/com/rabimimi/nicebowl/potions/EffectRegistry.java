package com.rabimimi.nicebowl.potions;

import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import com.rabimimi.nicebowl.NiceBowlMod;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;

public class EffectRegistry {
  public static final DeferredRegister<StatusEffect> EFFECTS = DeferredRegister.create(NiceBowlMod.MOD_ID,
      RegistryKeys.STATUS_EFFECT);

  private static final RegistrySupplier<StatusEffect> ESTRUS_SUPPLIER = EFFECTS.register("estrus", EstrusEffect::new);
  public static final Supplier<RegistryEntry<StatusEffect>> ESTRUS = Suppliers
      .memoize(() -> getReference(ESTRUS_SUPPLIER));

  public static RegistryEntry<StatusEffect> getReference(RegistrySupplier<StatusEffect> input) {
    return EFFECTS.getRegistrar().getHolder(input.getId());
  }
}
