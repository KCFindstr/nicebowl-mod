package com.rabimimi.nicebowl.items;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.rabimimi.nicebowl.fluids.FluidRegistry;
import com.rabimimi.nicebowl.utils.PlayerData;
import com.rabimimi.nicebowl.utils.PlayerUtils;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
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
    MutableText txt;
    PlayerData player = PlayerUtils.getPlayer(itemStack);
    if (PlayerUtils.isEmpty(player)) {
      txt = Text.translatable("tooltip.juice_bucket.none");
    } else {
      txt = Text.translatable("tooltip.juice_bucket.player", player.name);
    }
    txt = txt.fillStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF99FF)));
    text.add(txt);
  }
}
