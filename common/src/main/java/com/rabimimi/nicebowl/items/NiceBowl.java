package com.rabimimi.nicebowl.items;

import java.util.List;

import com.rabimimi.nicebowl.NiceBowlMod;
import com.rabimimi.nicebowl.blocks.BlockRegistry;
import com.rabimimi.nicebowl.blocks.NiceBowlBlock;
import com.rabimimi.nicebowl.blocks.NiceBowlBlockEntity;
import com.rabimimi.nicebowl.utils.Constants;
import com.rabimimi.nicebowl.utils.PlayerData;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class NiceBowl extends ArmorItem {

  public NiceBowl(RegistryEntry<ArmorMaterial> material, Type type, Settings builder) {
    super(material, type, builder);
  }

  public NiceBowl(Type type) {
    this(ItemRegistry.NICE_BOWL_ARMOR_MATERIAL, type,
        ItemRegistry.defaultSetting().fireproof());
  }

  public NiceBowl() {
    this(Type.LEGGINGS);
  }

  @Override
  public boolean isEnchantable(ItemStack stack) {
    return stack.getCount() == 1;
  }

  @Override
  public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> text,
      TooltipType options) {
    super.appendTooltip(itemStack, context, text, options);
    NiceBowlBlock.appendTooltip(itemStack, text);
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
      if (world.getBlockEntity(blockpos) instanceof NiceBowlBlockEntity blockEntity) {
        stackPlayerData.copyPlayerDataTo(blockEntity);
        blockEntity.setBowlStack(stack);
      } else {
        NiceBowlMod.LOGGER.warn("Created nicebowl block {} does not have a valid entity!", blockpos);
      }
      world.playSound(null, blockpos, SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.BLOCKS);
      stack.decrement(1);
      context.getPlayer().getInventory().markDirty();
    }
    return ActionResult.SUCCESS;
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
    ItemStack newStack = stack.copyComponentsToNewStack(expectedItem, stack.getCount());
    return newStack;
  }

  // #region NeoForge IItemExtension
  public boolean canEquip(ItemStack stack, EquipmentSlot armorType, LivingEntity entity) {
    return armorType == EquipmentSlot.LEGS || armorType == EquipmentSlot.HEAD;
  }
  // #endregion NeoForge IItemExtension
}
