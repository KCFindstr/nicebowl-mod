package com.kcfindstr.nicebowl.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.kcfindstr.nicebowl.items.ItemRegistry;
import com.kcfindstr.nicebowl.utils.PlayerData;
import com.kcfindstr.nicebowl.utils.PlayerUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
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
  public static IntegerProperty LEVEL = IntegerProperty.create("level", 0, 1);

  public static ITextComponent getHoverText(CompoundNBT tag) {
    PlayerData player = PlayerUtils.getPlayer(tag);
    if (PlayerUtils.isValid(player)) {
      TranslationTextComponent txt = new TranslationTextComponent("tooltip.nicebowl.player", player.name);
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
    this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 0));
  }

  public BlockState getBlockState(int waterLevel) {
    BlockState blockState = this.defaultBlockState();
    return blockState.setValue(LEVEL, waterLevel <= 0 ? 0 : 1);
  }

  @Override
  public BlockState getStateForPlacement(BlockItemUseContext context) {
    TileEntity tileEntity = context.getLevel().getBlockEntity(context.getClickedPos());
    if (tileEntity instanceof NiceBowlTileEntity) {
      NiceBowlTileEntity bowl = (NiceBowlTileEntity) tileEntity;
      return getBlockState(bowl.hasPlayer() ? 1 : 0);
    }
    return super.getStateForPlacement(context);
  }

  @Override
  protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
    builder.add(LEVEL);
    super.createBlockStateDefinition(builder);
  }

  @Override
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    return shape;
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new NiceBowlTileEntity();
  }

  @Override
  public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) {
    super.playerWillDestroy(world, pos, state, player);
    if (!world.isClientSide) {
      ItemStack drops = new ItemStack(ItemRegistry.niceBowl.get(), 1);
      TileEntity entity = world.getBlockEntity(pos);
      if (entity instanceof NiceBowlTileEntity) {
        NiceBowlTileEntity bowl = (NiceBowlTileEntity) entity;
        PlayerUtils.copyPlayerData(bowl, drops);
      }
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
