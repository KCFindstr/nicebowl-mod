package com.kcfindstr.nicebowl.fluids;

import com.kcfindstr.nicebowl.blocks.BlockRegistry;
import com.kcfindstr.nicebowl.items.ItemRegistry;
import com.kcfindstr.nicebowl.utils.Constants;

import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidRegistry {
  public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Constants.MOD_ID);
  
  private static final ResourceLocation FLUID_STILL = new ResourceLocation(Constants.MOD_ID, "fluid/juice_still");
  private static final ResourceLocation FLUID_FLOW = new ResourceLocation(Constants.MOD_ID, "fluid/juice_flow");

  public static ForgeFlowingFluid.Properties makeProperties()
  {
    return new ForgeFlowingFluid.Properties(
        () -> juice.get(), () -> juiceFlowing.get(),
        FluidAttributes
          .builder(FLUID_STILL, FLUID_FLOW)
          .color(Constants.JUICE_COLOR_TINT)
          .viscosity(2000))
      .bucket(ItemRegistry.juiceBucket)
      .block(BlockRegistry.juice);
  }
  
  public static RegistryObject<FlowingFluid> juice = FLUIDS.register("juice", () ->
    new ForgeFlowingFluid.Source(makeProperties())
  );
  public static RegistryObject<FlowingFluid> juiceFlowing = FLUIDS.register("juice_flow", () ->
    new ForgeFlowingFluid.Flowing(makeProperties())
  );
}