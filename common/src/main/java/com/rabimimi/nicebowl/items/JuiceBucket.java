package com.rabimimi.nicebowl.items;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.rabimimi.nicebowl.blocks.NiceBowlBlock;
import com.rabimimi.nicebowl.fluids.FluidRegistry;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class JuiceBucket extends BucketItem {
  public JuiceBucket() {
    super(
        FluidRegistry.JUICE.get(),
        ItemRegistry.defaultSetting().recipeRemainder(Items.BUCKET).maxCount(1));
  }

  @Override
  public void appendTooltip(ItemStack itemStack, @Nullable World world, List<Text> text,
      TooltipContext tooltip) {
    super.appendTooltip(itemStack, world, text, tooltip);
    NiceBowlBlock.appendTooltip(itemStack, text,
        "tooltip.juice_bucket.none",
        "tooltip.juice_bucket.player");
  }
}
