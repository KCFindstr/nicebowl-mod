package com.rabimimi.nicebowl.items;

import org.jetbrains.annotations.Nullable;

import com.rabimimi.nicebowl.NiceBowlMod;
import com.rabimimi.nicebowl.blocks.BlockRegistry;
import com.rabimimi.nicebowl.utils.PlayerUtils;

import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.item.ItemPropertiesRegistry;

public class ItemRegistry {
  public static final DeferredRegister<ItemGroup> TABS = DeferredRegister.create(NiceBowlMod.MOD_ID,
      RegistryKeys.ITEM_GROUP);
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(NiceBowlMod.MOD_ID, RegistryKeys.ITEM);

  public static final RegistrySupplier<ItemGroup> NICE_BOWL_GROUP = TABS.register(NiceBowlMod.MOD_ID,
      () -> CreativeTabRegistry
          .create(Text.translatable("itemGroup.nicebowl.nicebowl_group"),
              () -> new ItemStack(ItemRegistry.niceBowl.get())));

  public static final RegistrySupplier<ArmorItem> niceBowl = ITEMS.register("nicebowl", NiceBowl::new);
  public static final RegistrySupplier<Item> estrus = ITEMS.register("estrus", () -> new Item(defaultSetting()));
  public static final RegistrySupplier<ArmorItem> niceBowlHead = ITEMS.register("nicebowl_head", NiceBowlHead::new);
  public static final RegistrySupplier<BucketItem> juiceBucket = ITEMS.register("juice_bucket", JuiceBucket::new);
  public static final RegistrySupplier<BlockItem> niceBowlBlock = ITEMS.register("nicebowl_block",
      () -> new BlockItem(BlockRegistry.NICE_BOWL.get(), defaultSetting()));

  public static Item.Settings defaultSetting() {
    return new Item.Settings().arch$tab(NICE_BOWL_GROUP);
  }

  public static void postInit() {
    // Register item properties
    Identifier used = NiceBowlMod.id("used");
    ClampedModelPredicateProvider usedPredicate = (ItemStack stack, @Nullable ClientWorld world,
        @Nullable LivingEntity entity, int seed) -> PlayerUtils.getPlayer(stack) == null ? 0 : 1;
    ItemPropertiesRegistry.register(niceBowl.get(), used, usedPredicate);
    ItemPropertiesRegistry.register(niceBowlHead.get(), used, usedPredicate);
  }
}
