package com.rabimimi.nicebowl.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.rabimimi.nicebowl.items.NiceBowl;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

@Mixin(net.minecraft.screen.slot.ArmorSlot.class)
public abstract class ClientScreenArmorSlotMixin extends Slot {
  private final EquipmentSlot equipmentSlot;

  private ClientScreenArmorSlotMixin(Inventory inventory, int index, int x, int y) {
    super(inventory, index, x, y);
    this.equipmentSlot = EquipmentSlot.LEGS;
  }

  @Inject(method = "canInsert(Lnet/minecraft/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
  private void injectCanInsert(ItemStack stack, CallbackInfoReturnable<Boolean> ci) {
    if (!(stack.getItem() instanceof NiceBowl)) {
      return;
    }
    EquipmentSlot slot = this.equipmentSlot;
    if (slot == EquipmentSlot.HEAD || slot == EquipmentSlot.LEGS) {
      ci.setReturnValue(true);
    }
  }

  @ModifyVariable(method = "setStack(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
  public ItemStack modifySetStack(ItemStack stack, ItemStack previousStack) {
    if (!(stack.getItem() instanceof NiceBowl niceBowl)) {
      return stack;
    }
    return niceBowl.getEquipable(this.equipmentSlot, stack);
  }
}
