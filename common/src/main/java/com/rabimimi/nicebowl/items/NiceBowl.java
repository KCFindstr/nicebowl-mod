package com.rabimimi.nicebowl.items;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.rabimimi.nicebowl.NiceBowlMod;
import com.rabimimi.nicebowl.blocks.BlockRegistry;
import com.rabimimi.nicebowl.blocks.NiceBowlBlock;
import com.rabimimi.nicebowl.utils.Constants;
import com.rabimimi.nicebowl.utils.PlayerData;

import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class NiceBowl extends ArmorItem {
  public NiceBowl(ArmorMaterial material, Type type, Settings builder) {
    super(material, type, builder);
  }

  public NiceBowl() {
    this(NicebowlArmorMaterial.NICEBOWL, Type.LEGGINGS, ItemRegistry.defaultSetting().fireproof());
  }

  @Override
  public boolean isDamageable() {
    return false;
  }

  @Override
  public void appendTooltip(ItemStack itemStack, @Nullable World world, List<Text> text,
      TooltipContext tooltip) {
    super.appendTooltip(itemStack, world, text, tooltip);
    NiceBowlBlock.appendTooltip(itemStack, text);
  }

  public boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity entity) {
    return armorType == EquipmentSlot.LEGS || armorType == EquipmentSlot.HEAD;
  }

  public ItemStack getEquipable(EquipmentSlot slot, ItemStack stack) {
    if (slot == null) {
      return stack;
    }
    Item expectedItem = switch (slot) {
      case HEAD -> ItemRegistry.NICE_BOWL_HEAD.get();
      case LEGS -> ItemRegistry.NICE_BOWL.get();
      default -> null;
    };
    if (expectedItem == null || expectedItem.equals(stack.getItem())) {
      return stack;
    }
    ItemStack newStack = new ItemStack(expectedItem, stack.getCount());
    if (stack.hasNbt()) {
      newStack.setNbt(stack.getNbt());
    }
    return newStack;
  }

  @Override
  public ActionResult useOnBlock(ItemUsageContext context) {
    World world = context.getWorld();
    BlockPos blockpos = context.getBlockPos();
    BlockState blockstate = world.getBlockState(blockpos);
    ItemPlacementContext itemPlacementContext = new ItemPlacementContext(context);
    ItemStack stack = context.getStack();
    if (!blockstate.canReplace(itemPlacementContext)) {
      blockpos = blockpos.offset(context.getSide());
      blockstate = world.getBlockState(blockpos);
    }
    if (!blockstate.canReplace(itemPlacementContext)) {
      return ActionResult.FAIL;
    }
    if (!world.isClient) {
      var stackPlayerData = PlayerData.container(stack);
      NiceBowlBlock nicebowl = BlockRegistry.NICE_BOWL.get();
      BlockState newBlockstate = nicebowl.getDefaultState();
      world.setBlockState(blockpos, newBlockstate, Constants.DEFAULT_AND_RERENDER);
      if (world.getBlockEntity(blockpos) instanceof PlayerData.IContainer container) {
        stackPlayerData.copyPlayerDataTo(container);
      } else {
        NiceBowlMod.LOGGER.warn("Created nicebowl block {} does not have a valid entity!", blockpos);
      }
      world.playSound(null, blockpos, SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.BLOCKS);
      stack.decrement(1);
      context.getPlayer().getInventory().markDirty();
    }
    return ActionResult.SUCCESS;
  }
}
