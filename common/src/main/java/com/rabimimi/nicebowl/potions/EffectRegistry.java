package com.rabimimi.nicebowl.potions;

import com.rabimimi.nicebowl.NiceBowlMod;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.RegistryKeys;

public class EffectRegistry {
  public static final DeferredRegister<StatusEffect> EFFECTS = DeferredRegister.create(NiceBowlMod.MOD_ID,
      RegistryKeys.STATUS_EFFECT);

  public static DeferredSupplier<EstrusEffect> ESTRUS = EFFECTS.register("estrus", EstrusEffect::new);
}
