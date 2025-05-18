package com.rabimimi.nicebowl.fabric.client.datagen;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.rabimimi.nicebowl.NiceBowlMod;
import com.rabimimi.nicebowl.components.ComponentRegistry;
import com.rabimimi.nicebowl.items.ItemRegistry;
import com.rabimimi.nicebowl.potions.EffectRegistry;
import com.rabimimi.nicebowl.utils.AdvancementUtils;
import com.rabimimi.nicebowl.utils.PlayerData;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.EffectsChangedCriterion;
import net.minecraft.advancement.criterion.ImpossibleCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityEffectPredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

class AdvancementsProvider extends FabricAdvancementProvider {

  protected AdvancementsProvider(FabricDataOutput output,
      CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
    super(output, registryLookup);
  }

  @Override
  public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {
    AdvancementEntry rootAdvancement = createRootAdvancement(consumer);
    AdvancementEntry nicebowlRecipeAdv = createNicebowlRecipeAdvancement(consumer, rootAdvancement);
    AdvancementEntry nicebowlHeadRecipeAdv = createNicebowlHeadRecipeAdvancement(consumer, nicebowlRecipeAdv);
    AdvancementEntry nicebowlOvernightActionAdv = createNicebowlOvernightActionAdvancement(consumer,
        nicebowlRecipeAdv);
    AdvancementEntry receiveNicebowlActionAdv = createReceiveNicebowlActionAdvancement(consumer, nicebowlRecipeAdv);
    createBlockProjectileAdvancement(consumer, nicebowlHeadRecipeAdv);
    AdvancementEntry juiceBucketRecipeAdv = createJuiceBucketRecipeAdvancement(consumer,
        nicebowlOvernightActionAdv);
    createSendNicebowlAdvancement(consumer, nicebowlOvernightActionAdv);
    createNicebowlTeleportAdvancement(consumer, receiveNicebowlActionAdv);
    createEnterEstrusAdvancement(consumer, juiceBucketRecipeAdv, registryLookup);
  }

  private AdvancementEntry createRootAdvancement(Consumer<AdvancementEntry> consumer) {
    return Advancement.Builder.create()
        .display(
            ItemRegistry.NICE_BOWL.get(),
            Text.translatable("advancement.nicebowl.root.title"),
            Text.translatable("advancement.nicebowl.root.description"),
            NiceBowlMod.id("textures/gui/advancements/background.png"),
            AdvancementFrame.TASK,
            false,
            false,
            false)
        .criterion("has_bowl", InventoryChangedCriterion.Conditions.items(Items.BOWL))
        .criterion("has_the_recipe", RecipeUnlockedCriterion.create(Identifier.of("nicebowl", "nicebowl")))
        .requirements(AdvancementRequirements.anyOf(List.of("has_bowl", "has_the_recipe")))
        .rewards(AdvancementRewards.Builder.recipe(ItemRegistry.NICE_BOWL.getId()))
        .build(consumer, "nicebowl:root");
  }

  private AdvancementEntry createNicebowlRecipeAdvancement(Consumer<AdvancementEntry> consumer,
      AdvancementEntry parent) {
    return Advancement.Builder.create().parent(parent)
        .display(
            ItemRegistry.NICE_BOWL.get(),
            Text.translatable("advancement.nicebowl.nicebowl.title"),
            Text.translatable("advancement.nicebowl.nicebowl.description"),
            null, // background
            AdvancementFrame.TASK,
            true, true, false)
        .criterion("has_nicebowl", InventoryChangedCriterion.Conditions.items(ItemRegistry.NICE_BOWL.get()))
        .build(consumer, "nicebowl:recipes/nicebowl");
  }

  private AdvancementEntry createNicebowlHeadRecipeAdvancement(Consumer<AdvancementEntry> consumer,
      AdvancementEntry parent) {
    return Advancement.Builder.create().parent(parent)
        .display(
            ItemRegistry.NICE_BOWL_HEAD.get(),
            Text.translatable("advancement.nicebowl.nicebowl_head.title"),
            Text.translatable("advancement.nicebowl.nicebowl_head.description"),
            null,
            AdvancementFrame.TASK,
            true, true, false)
        .criterion("has_nicebowl_head",
            InventoryChangedCriterion.Conditions.items(ItemRegistry.NICE_BOWL_HEAD.get()))
        .build(consumer, "nicebowl:recipes/nicebowl_head");
  }

  private AdvancementEntry createNicebowlOvernightActionAdvancement(Consumer<AdvancementEntry> consumer,
      AdvancementEntry parent) {
    return Advancement.Builder.create().parent(parent)
        .display(
            ItemRegistry.NICE_BOWL.get(),
            Text.translatable("advancement.nicebowl.nicebowl_overnight.title"),
            Text.translatable("advancement.nicebowl.nicebowl_overnight.description"),
            null,
            AdvancementFrame.TASK,
            true, true, false)
        .criterion("nicebowl_automate", Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions())) // Placeholder
        .build(consumer, AdvancementUtils.NICEBOWL_OVERNIGHT.toString());
  }

  private AdvancementEntry createReceiveNicebowlActionAdvancement(Consumer<AdvancementEntry> consumer,
      AdvancementEntry parent) {
    return Advancement.Builder.create().parent(parent)
        .display(
            ItemRegistry.NICE_BOWL.get(),
            Text.translatable("advancement.nicebowl.receive_nicebowl.title"),
            Text.translatable("advancement.nicebowl.receive_nicebowl.description"),
            null,
            AdvancementFrame.CHALLENGE,
            true, true, false)
        .criterion("nicebowl_automate", Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions())) // Placeholder
        .build(consumer, AdvancementUtils.RECEIVE_NICEBOWL.toString());
  }

  private void createBlockProjectileAdvancement(Consumer<AdvancementEntry> consumer, AdvancementEntry parent) {
    Advancement.Builder.create().parent(parent)
        .display(
            Items.ARROW,
            Text.translatable("advancement.nicebowl.block_projectile.title"),
            Text.translatable("advancement.nicebowl.block_projectile.description"),
            null,
            AdvancementFrame.TASK,
            true, true, false)
        .criterion("nicebowl_automate", Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions())) // Placeholder
        .build(consumer, AdvancementUtils.BLOCK_PROJECTILE.toString());
  }

  private AdvancementEntry createJuiceBucketRecipeAdvancement(Consumer<AdvancementEntry> consumer,
      AdvancementEntry parent) {
    return Advancement.Builder.create().parent(parent)
        .display(
            ItemRegistry.JUICE_BUCKET.get(),
            Text.translatable("advancement.nicebowl.juice_bucket.title"),
            Text.translatable("advancement.nicebowl.juice_bucket.description"),
            null,
            AdvancementFrame.TASK,
            true, true, false)
        .criterion("has_juice_bucket",
            InventoryChangedCriterion.Conditions.items(ItemRegistry.JUICE_BUCKET.get()))
        .build(consumer, "nicebowl:recipes/juice_bucket");
  }

  private void createSendNicebowlAdvancement(Consumer<AdvancementEntry> consumer, AdvancementEntry parent) {
    ItemStack sendNicebowlIcon = new ItemStack(ItemRegistry.NICE_BOWL);
    sendNicebowlIcon.set(ComponentRegistry.PLAYER_DATA_COMPONENT.value(), PlayerData.EMPTY);
    Advancement.Builder.create().parent(parent)
        .display(
            sendNicebowlIcon,
            Text.translatable("advancement.nicebowl.send_nicebowl.title"),
            Text.translatable("advancement.nicebowl.send_nicebowl.description"),
            null,
            AdvancementFrame.CHALLENGE,
            true, true, true // hidden = true
        )
        .criterion("nicebowl_automate", Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions())) // Placeholder
        .build(consumer, AdvancementUtils.SEND_NICEBOWL.toString());
  }

  private void createNicebowlTeleportAdvancement(Consumer<AdvancementEntry> consumer, AdvancementEntry parent) {
    ItemStack teleportNicebowlIcon = new ItemStack(ItemRegistry.NICE_BOWL);
    teleportNicebowlIcon.set(ComponentRegistry.PLAYER_DATA_COMPONENT.value(), PlayerData.EMPTY);

    Advancement.Builder.create().parent(parent)
        .display(
            teleportNicebowlIcon,
            Text.translatable("advancement.nicebowl.nicebowl_teleport.title"),
            Text.translatable("advancement.nicebowl.nicebowl_teleport.description"),
            null,
            AdvancementFrame.CHALLENGE,
            true, true, false)
        .criterion("nicebowl_automate", Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions())) // Placeholder
        .build(consumer, AdvancementUtils.NICEBOWL_TELEPORT.toString());
  }

  private void createEnterEstrusAdvancement(Consumer<AdvancementEntry> consumer, AdvancementEntry parent,
      RegistryWrapper.WrapperLookup registryLookup) {
    var estrusEffect = registryLookup.createRegistryLookup().getOrThrow(RegistryKeys.STATUS_EFFECT)
        .getOrThrow(EffectRegistry.ESTRUS.get().getKey().get());
    var entityEffectPredicate = EntityEffectPredicate.Builder.create()
        .addEffect(estrusEffect);

    Advancement.Builder.create().parent(parent)
        .display(
            ItemRegistry.ESTRUS.get(),
            Text.translatable("advancement.nicebowl.enter_estrus.title"),
            Text.translatable("advancement.nicebowl.enter_estrus.description"),
            null,
            AdvancementFrame.TASK,
            true, true, false)
        .criterion("has_estrus", EffectsChangedCriterion.Conditions.create(entityEffectPredicate))
        .build(consumer, "nicebowl:actions/enter_estrus");
  }
}
