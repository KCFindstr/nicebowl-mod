package com.kcfindstr.nicebowl.items;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class NiceBowlGroup extends ItemGroup {
  public NiceBowlGroup() {
    super("nicebowl_group");
  }

  @Override
  public ItemStack makeIcon() {
    return new ItemStack(ItemRegistry.niceBowl.get());
  }
}