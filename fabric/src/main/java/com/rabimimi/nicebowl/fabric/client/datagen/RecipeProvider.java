package com.rabimimi.nicebowl.fabric.client.datagen;

import java.util.concurrent.CompletableFuture;

import com.rabimimi.nicebowl.items.ItemRegistry;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

public class RecipeProvider extends FabricRecipeProvider {

  public RecipeProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  public void generate(RecipeExporter exporter) {
    ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ItemRegistry.NICE_BOWL.get())
        .pattern("B")
        .pattern("W")
        .pattern("#")
        .input('B', Items.LIGHT_BLUE_WOOL)
        .input('W', Items.WHITE_WOOL)
        .input('#', Items.BOWL)
        .criterion(FabricRecipeProvider.hasItem(Items.BOWL),
            FabricRecipeProvider.conditionsFromItem(Items.BOWL))
        .offerTo(exporter);
  }

}
