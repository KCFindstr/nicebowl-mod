package com.kcfindstr.nicebowl.potions;

import com.kcfindstr.nicebowl.utils.Constants;

import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectRegistry {
  public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Constants.MOD_ID);
  
  public static RegistryObject<EstrusEffect> estrus = EFFECTS.register("estrus", EstrusEffect::new);
}