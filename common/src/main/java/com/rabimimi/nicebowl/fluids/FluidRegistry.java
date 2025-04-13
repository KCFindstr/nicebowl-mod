package com.rabimimi.nicebowl.fluids;

import com.rabimimi.nicebowl.NiceBowlMod;
import com.rabimimi.nicebowl.blocks.BlockRegistry;
import com.rabimimi.nicebowl.items.ItemRegistry;
import com.rabimimi.nicebowl.utils.Constants;

import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import dev.architectury.core.fluid.ArchitecturyFlowingFluid;
import dev.architectury.core.fluid.ArchitecturyFluidAttributes;
import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;

public class FluidRegistry {
  public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(
      NiceBowlMod.MOD_ID, RegistryKeys.FLUID);

  private static final Identifier FLUID_STILL = new Identifier(NiceBowlMod.MOD_ID, "block/juice_still");
  private static final Identifier FLUID_FLOW = new Identifier(NiceBowlMod.MOD_ID, "block/juice_flow");

  public static final RegistrySupplier<FlowableFluid> JUICE = FLUIDS.register("juice",
      () -> new ArchitecturyFlowingFluid.Source(FluidRegistry.JUICE_ATTRIBUTES));
  public static final RegistrySupplier<FlowableFluid> FLOWING_JUICE = FLUIDS.register("flowing_juice",
      () -> new ArchitecturyFlowingFluid.Flowing(FluidRegistry.JUICE_ATTRIBUTES));

  public static final ArchitecturyFluidAttributes JUICE_ATTRIBUTES = SimpleArchitecturyFluidAttributes.ofSupplier(
      () -> FLOWING_JUICE,
      () -> JUICE)
      .sourceTexture(FLUID_STILL)
      .flowingTexture(FLUID_FLOW)
      .bucketItem(ItemRegistry.JUICE_BUCKET)
      .block(BlockRegistry.JUICE)
      .color(Constants.JUICE_COLOR_TINT)
      .rarity(Rarity.UNCOMMON)
      .viscosity(2000)
      .temperature(310)
      .convertToSource(false)
      .block(BlockRegistry.JUICE);
}
