package com.rabimimi.nicebowl.items;

import java.util.List;

import com.rabimimi.nicebowl.blocks.NiceBowlBlock;
import com.rabimimi.nicebowl.components.ComponentRegistry;
import com.rabimimi.nicebowl.fluids.FluidRegistry;
import com.rabimimi.nicebowl.utils.PlayerData;

import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

public class JuiceBucket extends BucketItem {
  public JuiceBucket() {
    super(
        FluidRegistry.JUICE.get(),
        ItemRegistry.defaultSetting().recipeRemainder(Items.BUCKET)
            .component(ComponentRegistry.PLAYER_DATA_COMPONENT.value(), PlayerData.EMPTY)
            .maxCount(1));
  }

  @Override
  public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> text,
      TooltipType options) {
    super.appendTooltip(itemStack, context, text, options);
    NiceBowlBlock.appendTooltip(itemStack, text,
        "tooltip.juice_bucket.none",
        "tooltip.juice_bucket.player");
  }
}
