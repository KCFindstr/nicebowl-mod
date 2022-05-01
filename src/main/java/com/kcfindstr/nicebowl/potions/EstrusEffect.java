package com.kcfindstr.nicebowl.potions;

import com.kcfindstr.nicebowl.utils.Constants;
import com.kcfindstr.nicebowl.utils.Rand;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class EstrusEffect extends Effect {
  public static final int EFFECT_INTERVAL = 64;

  public EstrusEffect() {
    super(EffectType.BENEFICIAL, Constants.ESTRUS_COLOR_INT);
  }

  private int getEffectInterval(int amplifier) {
    return EFFECT_INTERVAL >> amplifier;
  }

  public boolean tryHeal(int amplifier) {
    return Rand.withProb(Math.max(1, amplifier), 4);
  }

  @Override
  public boolean isDurationEffectTick(int duration, int amplifier) {
    int k = getEffectInterval(amplifier);
    if (k > 0) {
      return duration % k == 0;
    } else {
      return true;
    }
  }

  @Override
  public void applyEffectTick(LivingEntity entity, int amplifier) {
    float remainHealth = entity.getMaxHealth() - entity.getHealth();
    if (remainHealth <= 0) {
      return;
    }
    entity.heal(1.0f);
  }
}
