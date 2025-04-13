package com.rabimimi.nicebowl.items;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.rabimimi.nicebowl.blocks.BlockRegistry;
import com.rabimimi.nicebowl.blocks.NiceBowlBlock;
import com.rabimimi.nicebowl.blocks.NiceBowlBlockEntity;
import com.rabimimi.nicebowl.utils.PlayerData;
import com.rabimimi.nicebowl.utils.PlayerUtils;

import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.block.entity.BlockEntity;
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
    super(NicebowlArmorMaterial.NICEBOWL, Type.LEGGINGS,
        ItemRegistry.defaultSetting().fireproof());
  }

  @Override
  public boolean isDamageable() {
    return false;
  }

  @Override
  public void appendTooltip(ItemStack itemStack, @Nullable World world, List<Text> text,
      TooltipContext tooltip) {
    NiceBowlBlock.appendTooltip(itemStack, text);
    super.appendTooltip(itemStack, world, text, tooltip);
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
    PlayerData player = PlayerUtils.getPlayer(stack);
    NiceBowlBlock nicebowl = BlockRegistry.NICE_BOWL.get();
    BlockState newBlockstate = nicebowl.getBlockState(PlayerUtils.isValid(player) ? 1 : 0);
    world.setBlockState(blockpos, newBlockstate);
    if (!world.isClient) {
      if (PlayerUtils.isValid(player)) {
        BlockEntity blockEntity = world.getBlockEntity(blockpos);
        if (blockEntity instanceof NiceBowlBlockEntity entity) {
          entity.setPlayer(player);
          world.updateListeners(blockpos, blockstate, newBlockstate, 3);
        }
      }
      context.getPlayer().playSound(SoundEvents.BLOCK_WOOL_PLACE, 1.0F, 1.0F);
      stack.decrement(1);
    }
    return ActionResult.SUCCESS;
  }

  // TODO: Implement
  // @Override
  // public boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity
  // entity) {
  // return armorType == EquipmentSlot.HEAD || armorType == EquipmentSlot.LEGS;
  // }

  // @Override
  // public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot
  // slot, String type) {
  // String layer = slot == EquipmentSlot.LEGS ? "2" : "1";
  // String name = PlayerUtils.isValid(PlayerUtils.getPlayer(stack)) ?
  // "nicebowl_used_layer_" : "nicebowl_layer_";
  // String path = "nicebowl:textures/models/armor/" + name + layer + ".png";
  // return path;
  // }
}
