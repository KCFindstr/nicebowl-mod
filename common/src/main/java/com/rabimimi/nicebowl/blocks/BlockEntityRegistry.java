package com.rabimimi.nicebowl.blocks;

import com.rabimimi.nicebowl.NiceBowlMod;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.RegistryKeys;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;

public class BlockEntityRegistry {
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(
      NiceBowlMod.MOD_ID, RegistryKeys.BLOCK_ENTITY_TYPE);

  public static final RegistrySupplier<BlockEntityType<NiceBowlBlockEntity>> NICE_BOWL_BLOCK_ENTITY = BLOCK_ENTITIES
      .register(
          "nice_bowl_block_entity",
          () -> BlockEntityType.Builder.create(NiceBowlBlockEntity::newBowl,
              BlockRegistry.NICE_BOWL.get()).build(null));

  public static final RegistrySupplier<BlockEntityType<NiceBowlBlockEntity>> JUICE_BLOCK_ENTITY = BLOCK_ENTITIES
      .register(
          "juice_block_entity",
          () -> BlockEntityType.Builder.create(NiceBowlBlockEntity::newJuice,
              BlockRegistry.JUICE.get()).build(null));
}
