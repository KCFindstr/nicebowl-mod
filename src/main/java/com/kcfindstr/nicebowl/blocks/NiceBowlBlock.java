package com.kcfindstr.nicebowl.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.kcfindstr.nicebowl.items.ItemRegistry;
import com.kcfindstr.nicebowl.utils.PlayerUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class NiceBowlBlock extends Block {
  private static final VoxelShape shape = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);

  public static ITextComponent getHoverText(CompoundNBT tag) {
    String player = PlayerUtils.getPlayer(tag);
    if (player != null) {
      TranslationTextComponent txt = new TranslationTextComponent("tooltip.nicebowl.player", player);
      txt.setStyle(Style.EMPTY.withColor(TextFormatting.LIGHT_PURPLE));
      return txt;
    }
    return null;
  }
  
  public static void addHoverText(ItemStack itemStack, List<ITextComponent> text) {
    if (!itemStack.hasTag()) {
      return;
    }
    CompoundNBT tag = itemStack.getTag();
    ITextComponent txt = getHoverText(tag);
    if (txt != null) {
      text.add(txt);
    }
  }

  public NiceBowlBlock() {
    super(Properties.of(Material.WOOL).harvestLevel(1).strength(1).noOcclusion().sound(SoundType.WOOL));
  }

  @Override
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    return shape;
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new NiceBowlTileEntity();
  }

  @Override
  public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) {
    super.playerWillDestroy(world, pos, state, player);
    if (!world.isClientSide) {
      NiceBowlTileEntity tileEntity = (NiceBowlTileEntity) world.getBlockEntity(pos);
      ItemStack drops = new ItemStack(ItemRegistry.niceBowl.get(), 1);
      PlayerUtils.copyPlayerData(tileEntity, drops);
      InventoryHelper.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), drops);
    }
  }

  @Override
  public void appendHoverText(ItemStack itemStack, @Nullable IBlockReader reader, List<ITextComponent> text, ITooltipFlag tooltip) {
    super.appendHoverText(itemStack, reader, text, tooltip);
    addHoverText(itemStack, text);
  }

  /*
  @Override
  public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult ray) {
    ItemStack itemStack = player.getItemInHand(hand);
    if (itemStack.getItem() != Items.BUCKET) {
      return ActionResultType.PASS;
    }
    NiceBowlTileEntity tileEntity = (NiceBowlTileEntity) world.getBlockEntity(pos);
    if (!tileEntity.hasPlayer()) {
      return ActionResultType.PASS;
    }

    return ActionResultType.SUCCESS;
  }
  */

  /*
  @Override
  public Fluid takeLiquid(IWorld world, BlockPos blockPos, BlockState blockState) {
    NiceBowlTileEntity tileEntity = (NiceBowlTileEntity) world.getBlockEntity(blockPos);
    if (!tileEntity.hasPlayer()) {
      return Fluids.EMPTY;
    }
    return FluidRegistry.juice.get();
  }
  */
}
