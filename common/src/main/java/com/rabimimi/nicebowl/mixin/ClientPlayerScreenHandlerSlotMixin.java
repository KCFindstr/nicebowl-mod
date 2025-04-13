package com.rabimimi.nicebowl.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.rabimimi.nicebowl.items.ItemRegistry;
import com.rabimimi.nicebowl.items.NiceBowl;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

@Mixin(targets = "net.minecraft.screen.PlayerScreenHandler$1")
public abstract class ClientPlayerScreenHandlerSlotMixin extends Slot {
  private final EquipmentSlot field_7834;

  private ClientPlayerScreenHandlerSlotMixin(Inventory inventory, int index, int x, int y) {
    super(inventory, index, x, y);
    this.field_7834 = EquipmentSlot.LEGS;
  }

  @Inject(method = "canInsert(Lnet/minecraft/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
  private void injectCanInsert(ItemStack stack, CallbackInfoReturnable<Boolean> ci) {
    if (!(stack.getItem() instanceof NiceBowl)) {
      return;
    }
    EquipmentSlot slot = this.field_7834;
    if (slot == EquipmentSlot.HEAD || slot == EquipmentSlot.LEGS) {
      ci.setReturnValue(true);
    }
  }

  @ModifyVariable(method = "setStack(Lnet/minecraft/item/ItemStack;)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
  public ItemStack modifySetStack(ItemStack stack) {
    if (!(stack.getItem() instanceof NiceBowl niceBowl)) {
      return stack;
    }
    EquipmentSlot slot = this.field_7834;
    if (slot == niceBowl.getSlotType()) {
      return stack;
    }
    Item expectedItem = switch (slot) {
      case HEAD -> ItemRegistry.NICE_BOWL_HEAD.get();
      case LEGS -> ItemRegistry.NICE_BOWL.get();
      default -> null;
    };
    if (expectedItem == null) {
      return stack;
    }
    ItemStack newStack = new ItemStack(expectedItem, stack.getCount());
    if (stack.hasNbt()) {
      newStack.setNbt(stack.getNbt());
    }
    return newStack;
  }
}
