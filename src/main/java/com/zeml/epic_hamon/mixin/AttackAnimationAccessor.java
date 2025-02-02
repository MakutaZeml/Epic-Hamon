package com.zeml.epic_hamon.mixin;

import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import yesman.epicfight.api.animation.types.AttackAnimation;

@Mixin(value = AttackAnimation.class, remap = false)
public interface AttackAnimationAccessor {



}
