package com.kcfindstr.nicebowl.items;

import java.util.List;

import javax.annotation.Nullable;

import com.kcfindstr.nicebowl.blocks.BlockRegistry;
import com.kcfindstr.nicebowl.blocks.NiceBowlBlock;
import com.kcfindstr.nicebowl.blocks.NiceBowlTileEntity;
import com.kcfindstr.nicebowl.utils.PlayerData;
import com.kcfindstr.nicebowl.utils.PlayerUtils;

import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class NiceBowl extends ArmorItem {
  public NiceBowl(IArmorMaterial material, EquipmentSlotType slot, Properties builder) {
    super(material, slot, builder);
  }

  public NiceBowl() {
    super(NicebowlArmorMaterial.NICEBOWL, EquipmentSlotType.LEGS, new Properties()
      .tab(ItemRegistry.itemGroup)
      .setNoRepair());
  }

  @Override
  public boolean isDamageable(ItemStack stack) {
    return false;
  }

  @Override
  public void appendHoverText(ItemStack itemStack, @Nullable World world, List<ITextComponent> text, ITooltipFlag tooltip) {
    NiceBowlBlock.addHoverText(itemStack, text);
    super.appendHoverText(itemStack, world, text, tooltip);
  }

  @Override
  public ActionResultType useOn(ItemUseContext context) {
    World world = context.getLevel();
    BlockPos blockpos = context.getClickedPos();
    BlockState blockstate = world.getBlockState(blockpos);
    BlockItemUseContext blockItemUseContext = new BlockItemUseContext(context);
    ItemStack stack = context.getItemInHand();
    if (!blockstate.canBeReplaced(blockItemUseContext))
    {
      blockpos = blockpos.offset(context.getClickedFace().getNormal());
      blockstate = world.getBlockState(blockpos);
    }
    if (!blockstate.canBeReplaced(blockItemUseContext)) {
      return ActionResultType.FAIL;
    }
    PlayerData player = PlayerUtils.getPlayer(stack);
    NiceBowlBlock created = BlockRegistry.niceBowl.get();
    BlockState newBlockstate = created.getBlockState(PlayerUtils.isValid(player) ? 1 : 0);
    world.setBlockAndUpdate(blockpos, newBlockstate);
    if (!world.isClientSide) {
      if (PlayerUtils.isValid(player)) {
        TileEntity tileEntity = world.getBlockEntity(blockpos);
        if (tileEntity instanceof NiceBowlTileEntity) {
          NiceBowlTileEntity entity = (NiceBowlTileEntity) tileEntity;
          entity.setPlayer(player);
          world.sendBlockUpdated(blockpos, blockstate, newBlockstate, 3);
        }
      }
    }
    context.getPlayer().playSound(SoundEvents.WOOL_PLACE, 1.0F, 1.0F);
    stack.setCount(stack.getCount() - 1);
    return ActionResultType.SUCCESS;
  }

  @Override
  public boolean canEquip(ItemStack stack, EquipmentSlotType armorType, Entity entity) {
    return armorType == EquipmentSlotType.HEAD || armorType == EquipmentSlotType.LEGS;
  }
  
  @Override
  public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
    String layer = slot == EquipmentSlotType.LEGS ? "2" : "1";
    String name = PlayerUtils.isValid(PlayerUtils.getPlayer(stack)) ? "nicebowl_used_layer_" : "nicebowl_layer_";
    String path = "nicebowl:textures/models/armor/" + name + layer + ".png";
    return path;
  }
}
