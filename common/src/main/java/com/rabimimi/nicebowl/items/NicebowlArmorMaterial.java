package com.rabimimi.nicebowl.items;

import java.util.function.Supplier;

import net.minecraft.item.ArmorItem.Type;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public enum NicebowlArmorMaterial implements ArmorMaterial {
  NICEBOWL("nicebowl",
      20,
      new int[] { 2, 5, 6, 2 },
      20,
      SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
      0.0F,
      0.1F,
      () -> Ingredient.ofItems(Items.WHITE_WOOL));

  private static final int[] BASE_DURABILITY = new int[] { 13, 15, 16, 11 };
  private final String name;
  private final int durabilityMultiplier;
  private final int[] protectionAmounts;
  private final int enchantability;
  private final SoundEvent equipSound;
  private final float toughness;
  private final float knockbackResistance;
  private final Supplier<Ingredient> repairIngredient;

  private NicebowlArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability,
      SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
    this.name = name;
    this.durabilityMultiplier = durabilityMultiplier;
    this.protectionAmounts = protectionAmounts;
    this.enchantability = enchantability;
    this.equipSound = equipSound;
    this.toughness = toughness;
    this.knockbackResistance = knockbackResistance;
    this.repairIngredient = repairIngredient;
  }

  @Override
  public int getEnchantability() {
    return this.enchantability;
  }

  @Override
  public SoundEvent getEquipSound() {
    return this.equipSound;
  }

  @Override
  public Ingredient getRepairIngredient() {
    return this.repairIngredient.get();
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public float getToughness() {
    return this.toughness;
  }

  @Override
  public float getKnockbackResistance() {
    return this.knockbackResistance;
  }

  @Override
  public int getDurability(Type type) {
    return BASE_DURABILITY[type.getEquipmentSlot().getEntitySlotId()] * this.durabilityMultiplier;
  }

  @Override
  public int getProtection(Type type) {
    return this.protectionAmounts[type.getEquipmentSlot().getEntitySlotId()];
  }
}
