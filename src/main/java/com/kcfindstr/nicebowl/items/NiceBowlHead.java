package com.kcfindstr.nicebowl.items;

import net.minecraft.inventory.EquipmentSlotType;

public class NiceBowlHead extends NiceBowl {
  public NiceBowlHead() {
    super(NicebowlArmorMaterial.NICEBOWL, EquipmentSlotType.HEAD, new Properties()
      .setNoRepair());
  }
}
