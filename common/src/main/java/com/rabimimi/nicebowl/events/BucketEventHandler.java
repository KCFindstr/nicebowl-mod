package com.rabimimi.nicebowl.events;

import org.jetbrains.annotations.Nullable;

import com.rabimimi.nicebowl.blocks.NiceBowlBlock;
import com.rabimimi.nicebowl.blocks.NiceBowlBlockEntity;
import com.rabimimi.nicebowl.items.ItemRegistry;
import com.rabimimi.nicebowl.utils.PlayerUtils;

import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BucketEventHandler {

  private static final int DEFAULT_AND_RERENDER = 11;

  public static void init() {
    PlayerEvent.FILL_BUCKET.register(BucketEventHandler::onFillBucket);
  }

  public static CompoundEventResult<ItemStack> onFillBucket(
      PlayerEntity player,
      World world,
      ItemStack stack,
      @Nullable HitResult target) {
    if (!(target instanceof BlockHitResult blockHitResult) || player.getWorld().isClient) {
      return CompoundEventResult.pass();
    }
    BlockPos pos = blockHitResult.getBlockPos();
    BlockState blockState = world.getBlockState(pos);
    if (!(blockState.getBlock() instanceof NiceBowlBlock)
        || !(world.getBlockEntity(pos) instanceof NiceBowlBlockEntity blockEntity)
        || !blockEntity.hasPlayer()) {
      return CompoundEventResult.pass();
    }
    // Collect juice
    BlockState newBlockState = blockState.with(NiceBowlBlock.LEVEL, 0);
    world.setBlockState(pos, newBlockState, DEFAULT_AND_RERENDER);
    ItemStack itemStack = new ItemStack(ItemRegistry.juiceBucket.get());
    PlayerUtils.copyPlayerData(blockEntity, itemStack);
    blockEntity.setPlayer(null);
    player.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
    return CompoundEventResult.interruptTrue(itemStack);
  }
}
