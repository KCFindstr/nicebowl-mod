package com.rabimimi.nicebowl.fabric.client.datagen;

import java.util.concurrent.CompletableFuture;

import com.rabimimi.nicebowl.blocks.BlockRegistry;
import com.rabimimi.nicebowl.fluids.FluidRegistry;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class TagsProvider {
  public static class FluidProvider extends FabricTagProvider<Fluid> {
    public static final TagKey<Fluid> WATER = TagKey.of(RegistryKeys.FLUID,
        Identifier.ofVanilla("water"));

    public FluidProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
      super(output, RegistryKeys.FLUID, registriesFuture);
    }

    @Override
    protected void configure(WrapperLookup wrapperLookup) {
      getOrCreateTagBuilder(WATER).add(FluidRegistry.JUICE.get());
      getOrCreateTagBuilder(WATER).add(FluidRegistry.FLOWING_JUICE.get());
    }
  }

  public static class BlockProvider extends FabricTagProvider<Block> {
    public static final TagKey<Block> WOOL = TagKey.of(RegistryKeys.BLOCK,
        Identifier.ofVanilla("wool"));

    public BlockProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
      super(output, RegistryKeys.BLOCK, registriesFuture);
    }

    @Override
    protected void configure(WrapperLookup wrapperLookup) {
      getOrCreateTagBuilder(WOOL).add(BlockRegistry.NICE_BOWL.get());
    }
  }
}
