package com.kcfindstr.nicebowl.items;

import java.util.List;

import javax.annotation.Nullable;

import com.kcfindstr.nicebowl.fluids.FluidRegistry;
import com.kcfindstr.nicebowl.utils.PlayerData;
import com.kcfindstr.nicebowl.utils.PlayerUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class JuiceBucket extends BucketItem {
  public JuiceBucket() {
    super(
      FluidRegistry.juice,
      new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ItemRegistry.itemGroup));
  }

  @Override
  public void appendHoverText(ItemStack itemStack, @Nullable World world, List<ITextComponent> text, ITooltipFlag tooltip) {
    super.appendHoverText(itemStack, world, text, tooltip);
    TranslationTextComponent txt;
    PlayerData player = PlayerUtils.getPlayer(itemStack);
    if (PlayerUtils.isValid(player)) {
      txt = new TranslationTextComponent("tooltip.juice_bucket.player", player.name);
    } else {
      txt = new TranslationTextComponent("tooltip.juice_bucket.none");
    }
    txt.setStyle(Style.EMPTY.withColor(TextFormatting.LIGHT_PURPLE));
    text.add(txt);
  }
}
