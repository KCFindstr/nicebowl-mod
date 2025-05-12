package com.rabimimi.nicebowl.blocks;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.mojang.serialization.MapCodec;
import com.rabimimi.nicebowl.NiceBowlMod;
import com.rabimimi.nicebowl.items.ItemRegistry;
import com.rabimimi.nicebowl.utils.AdvancementUtils;
import com.rabimimi.nicebowl.utils.Constants;
import com.rabimimi.nicebowl.utils.PlayerData;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.text.Text;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;

public class NiceBowlBlock extends BlockWithEntity {

  // #region Static fields
  private static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
  public static final IntProperty LEVEL = IntProperty.of("level", 0, 1);

  public static Optional<MutableText> getTooltipText(Optional<PlayerData> playerDataOptional, String keyNone,
      String keyPlayer) {
    if (playerDataOptional.isEmpty())
      return Optional.empty();

    var playerData = playerDataOptional.get();
    MutableText txt = playerData.isEmpty()
        ? Text.translatable(keyNone)
        : Text.translatable(keyPlayer, playerData.name());
    txt = txt.fillStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF99FF)));
    return Optional.of(txt);
  }

  public static Optional<MutableText> getTooltipText(Optional<PlayerData> playerDataOptional) {
    return getTooltipText(playerDataOptional,
        Constants.TOOLTIP_NICEBOWL_NONE,
        Constants.TOOLTIP_NICEBOWL_PLAYER);
  }

  public static void appendTooltip(Optional<PlayerData> playerDataOptional, List<Text> text, String keyNone,
      String keyPlayer) {
    getTooltipText(playerDataOptional, keyNone, keyPlayer).ifPresent(text::add);
  }

  public static void appendTooltip(Optional<PlayerData> playerDataOptional, List<Text> text) {
    appendTooltip(playerDataOptional, text,
        Constants.TOOLTIP_NICEBOWL_NONE,
        Constants.TOOLTIP_NICEBOWL_PLAYER);
  }

  public static void appendTooltip(ItemStack itemStack, List<Text> text, String keyNone, String keyPlayer) {
    appendTooltip(PlayerData.container(itemStack).getPlayerData(), text, keyNone, keyPlayer);
  }

  public static void appendTooltip(ItemStack itemStack, List<Text> text) {
    appendTooltip(itemStack, text,
        Constants.TOOLTIP_NICEBOWL_NONE,
        Constants.TOOLTIP_NICEBOWL_PLAYER);
  }

  // #endregion Static fields

  public NiceBowlBlock() {
    super(Settings.copy(Blocks.CYAN_WOOL).nonOpaque());
    this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 0));
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
  public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
    BlockState ret = super.onBreak(world, pos, state, player);
    if (world.isClient)
      return ret;
    ItemStack drops = world.getBlockEntity(pos) instanceof NiceBowlBlockEntity bowl
        ? bowl.toItemStack()
        : new ItemStack(ItemRegistry.NICE_BOWL.get(), 1);
    dropStack(world, pos, drops);
    return ret;
  }

  @Override
  public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> text,
      TooltipType options) {
    super.appendTooltip(itemStack, context, text, options);
    appendTooltip(itemStack, text);
  }

  @Override
  protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
    Hand hand = player.getActiveHand();
    ItemStack stack = player.getStackInHand(hand);
    if (!(world.getBlockEntity(pos) instanceof NiceBowlBlockEntity blockEntity)) {
      return ActionResult.PASS;
    }
    if (stack.getItem() == ItemRegistry.JUICE_BUCKET.get() && state.get(LEVEL) == 0) {
      if (!world.isClient) {
        PlayerData.container(stack).copyPlayerDataTo(blockEntity);
        ItemStack emptiedStack = BucketItem.getEmptiedStack(stack, player);
        player.setStackInHand(hand, emptiedStack);
        world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, player.getSoundCategory());
      }
      return ActionResult.SUCCESS;
    }
    if (stack.getItem() == Items.BUCKET && state.get(LEVEL) == 1) {
      if (!world.isClient) {
        ItemStack newStack = new ItemStack(ItemRegistry.JUICE_BUCKET.get());
        blockEntity.copyPlayerDataTo(PlayerData.container(newStack));
        blockEntity.clearPlayerData();
        player.setStackInHand(hand, newStack);
        world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, player.getSoundCategory());
      }
      return ActionResult.SUCCESS;
    }
    return ActionResult.PASS;
  }

  private void maybeTeleport(ServerWorld world, ServerPlayerEntity player, PlayerData data) {
    if (data == null || data.isEmpty() || data.uuid().isEmpty())
      return;
    UUID uuid = data.uuid().get();
    if (uuid.equals(player.getUuid()))
      return;
    ServerPlayerEntity target = world.getServer().getPlayerManager().getPlayer(uuid);
    if (target == null || !target.isAlive())
      return;
    AdvancementUtils.grantAdvancement(player, AdvancementUtils.NICEBOWL_TELEPORT);
    player.teleport(target.getServerWorld(),
        target.getX(), target.getY(), target.getZ(),
        Collections.emptySet(), target.getYaw(), target.getPitch());
  }

  @Override
  public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
    super.onSteppedOn(world, pos, state, entity);

    if (world.isClient
        || !(entity instanceof ServerPlayerEntity player)
        || !player.isSneaking()
        || !player.getEquippedStack(EquipmentSlot.LEGS).isEmpty()) {
      return;
    }
    if (!(world.getBlockEntity(pos) instanceof NiceBowlBlockEntity blockEntity)) {
      NiceBowlMod.LOGGER.warn("NiceBowlBlockEntity not found at {}!", pos);
      return;
    }
    // Equip the player with the item
    ItemStack bowlStack = blockEntity.toItemStack();
    player.equipStack(EquipmentSlot.LEGS, bowlStack);
    if (blockEntity.hasAssociatedPlayer()) {
      maybeTeleport((ServerWorld) world, player, blockEntity.getPlayerData().get());
    }
    world.setBlockState(pos, Blocks.AIR.getDefaultState());
  }

  @Override
  protected MapCodec<? extends BlockWithEntity> getCodec() {
    return null;
  }
}
