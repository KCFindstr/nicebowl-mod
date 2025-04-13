package com.rabimimi.nicebowl.fluids;

import com.rabimimi.nicebowl.NiceBowlMod;
import com.rabimimi.nicebowl.blocks.BlockRegistry;
import com.rabimimi.nicebowl.utils.Constants;

import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import dev.architectury.core.fluid.ArchitecturyFlowingFluid;
import dev.architectury.core.fluid.ArchitecturyFluidAttributes;
import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;

public class FluidRegistry {
  public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(
      NiceBowlMod.MOD_ID, RegistryKeys.FLUID);

  private static final Identifier FLUID_STILL = new Identifier(NiceBowlMod.MOD_ID, "fluid/juice_still");
  private static final Identifier FLUID_FLOW = new Identifier(NiceBowlMod.MOD_ID, "fluid/juice_flow");

  public static RegistrySupplier<FlowableFluid> JUICE;
  public static RegistrySupplier<FlowableFluid> JUICE_FLOWING;

  public static final ArchitecturyFluidAttributes JUICE_ATTRIBUTES = SimpleArchitecturyFluidAttributes.ofSupplier(
      () -> JUICE_FLOWING,
      () -> JUICE)
      .sourceTexture(FLUID_STILL)
      .flowingTexture(FLUID_FLOW)
      .color(Constants.JUICE_COLOR_TINT)
      .viscosity(2000)
      .block(BlockRegistry.JUICE);

  static {
    JUICE = FLUIDS.register("juice",
        () -> new ArchitecturyFlowingFluid.Source(JUICE_ATTRIBUTES));
    JUICE_FLOWING = FLUIDS.register("juice_flow",
        () -> new ArchitecturyFlowingFluid.Flowing(JUICE_ATTRIBUTES));
  }
}
