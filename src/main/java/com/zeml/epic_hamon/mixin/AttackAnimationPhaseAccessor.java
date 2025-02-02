package com.zeml.epic_hamon.mixin;

import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import yesman.epicfight.api.animation.types.AttackAnimation;

@Mixin(value = AttackAnimation.Phase.class)
public interface AttackAnimationPhaseAccessor {

    @Accessor("hand")
    Hand getHand();
}
