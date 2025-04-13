package com.rabimimi.nicebowl.blocks;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.rabimimi.nicebowl.items.ItemRegistry;
import com.rabimimi.nicebowl.utils.PlayerData;
import com.rabimimi.nicebowl.utils.PlayerUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.text.Text;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.util.ItemScatterer;

public class NiceBowlBlock extends BlockWithEntity {
  private static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
  public static final IntProperty LEVEL = IntProperty.of("level", 0, 1);

  @Nullable
  public static Text getHoverText(NbtCompound tag) {
    PlayerData player = PlayerUtils.getPlayer(tag);
    if (PlayerUtils.isValid(player)) {
      Text txt = Text.translatable("tooltip.nicebowl.player", player.name)
          .fillStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF99FF)));
      return txt;
    }
    return null;
  }

  public static void appendTooltip(ItemStack itemStack, List<Text> text) {
    if (!itemStack.hasNbt()) {
      return;
    }
    NbtCompound tag = itemStack.getNbt();
    Text txt = getHoverText(tag);
    if (txt != null) {
      text.add(txt);
    }
  }

  public NiceBowlBlock() {
    super(Settings.copy(Blocks.CYAN_WOOL).requiresTool().strength(1).nonOpaque());
    this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 0));
  }

  public BlockState getBlockState(int waterLevel) {
    BlockState blockState = this.getDefaultState();
    return blockState.with(LEVEL, waterLevel <= 0 ? 0 : 1);
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(LEVEL);
    super.appendProperties(builder);
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView worldIn,
      BlockPos pos, ShapeContext context) {
    return SHAPE;
  }

  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new NiceBowlBlockEntity(pos, state);
  }

  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.MODEL;
  }

  @Override
  public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
    super.onBreak(world, pos, state, player);
    if (world.isClient)
      return;
    ItemStack drops = new ItemStack(ItemRegistry.NICE_BOWL.get(), 1);
    BlockEntity entity = world.getBlockEntity(pos);
    if (entity instanceof NiceBowlBlockEntity) {
      NiceBowlBlockEntity bowl = (NiceBowlBlockEntity) entity;
      PlayerUtils.copyPlayerData(bowl, drops);
    }
    ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), drops);
  }

  @Override
  public void appendTooltip(ItemStack itemStack, @Nullable BlockView reader, List<Text> text,
      TooltipContext tooltip) {
    super.appendTooltip(itemStack, reader, text, tooltip);
    appendTooltip(itemStack, text);
  }

  /*
   * @Override
   * public ActionResult onUse(BlockState state, World world, BlockPos pos,
   * PlayerEntity player, Hand hand, BlockHitResult ray) {
   * ItemStack itemStack = player.getStackInHand(hand);
   * if (itemStack.getItem() != Items.BUCKET) {
   * return ActionResult.PASS;
   * }
   * NiceBowlTileEntity tileEntity = (NiceBowlTileEntity)
   * world.getBlockEntity(pos);
   * if (!tileEntity.hasPlayer()) {
   * return ActionResult.PASS;
   * }
   * 
   * return ActionResult.SUCCESS;
   * }
   */

  /*
   * @Override
   * public Fluid tryDrainFluid(WorldAccess world, BlockPos blockPos, BlockState
   * blockState) {
   * NiceBowlTileEntity tileEntity = (NiceBowlTileEntity)
   * world.getBlockEntity(blockPos);
   * if (!tileEntity.hasPlayer()) {
   * return Fluids.EMPTY;
   * }
   * return FluidRegistry.juice.get();
   * }
   */
}
