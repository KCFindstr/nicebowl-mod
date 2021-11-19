package com.kcfindstr.nicebowl.items;

import java.util.List;

import javax.annotation.Nullable;

import com.kcfindstr.nicebowl.blocks.BlockRegistry;
import com.kcfindstr.nicebowl.blocks.NiceBowlBlock;
import com.kcfindstr.nicebowl.blocks.NiceBowlTileEntity;
import com.kcfindstr.nicebowl.utils.PlayerData;
import com.kcfindstr.nicebowl.utils.PlayerUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
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
  public void appendHoverText(ItemStack itemStack, @Nullable World world, List<ITextComponent> text, ITooltipFlag tooltip) {
    NiceBowlBlock.addHoverText(itemStack, text);
    super.appendHoverText(itemStack, world, text, tooltip);
  }

  @Override
  public ActionResultType useOn(ItemUseContext context) {
    World world = context.getLevel();
    BlockPos blockpos = context.getClickedPos();
    BlockState blockstate = world.getBlockState(blockpos);
    Block block = blockstate.getBlock();
    BlockItemUseContext blockItemUseContext = new BlockItemUseContext(context);
    ItemStack stack = context.getItemInHand();
    if (!block.canBeReplaced(blockstate, blockItemUseContext))
    {
      blockpos = blockpos.offset(context.getClickedFace().getNormal());
      blockstate = world.getBlockState(blockpos);
      block = blockstate.getBlock();
    }
    if (!block.canBeReplaced(blockstate, blockItemUseContext)) {
      return ActionResultType.FAIL;
    }
    Block created = BlockRegistry.niceBowl.get();
    BlockState newBlockstate = created.defaultBlockState();
    world.setBlockAndUpdate(blockpos, newBlockstate);
    if (!world.isClientSide) {
      PlayerData player = PlayerUtils.getPlayer(stack);
      if (PlayerUtils.isValid(player)) {
        NiceBowlTileEntity entity = (NiceBowlTileEntity) world.getBlockEntity(blockpos);
        entity.setPlayer(player);
        world.sendBlockUpdated(blockpos, blockstate, newBlockstate, 3);
      }
    }
    stack.setCount(stack.getCount() - 1);
    return ActionResultType.SUCCESS;
  }

  @Override
  public boolean canEquip(ItemStack stack, EquipmentSlotType armorType, Entity entity) {
    return armorType == EquipmentSlotType.HEAD || armorType == EquipmentSlotType.LEGS;
  }
  
  @Override
  public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
    String path = "nicebowl:textures/models/armor/nicebowl_layer_" + (slot == EquipmentSlotType.LEGS ? "2" : "1") + ".png";
    return path;
  }
}
