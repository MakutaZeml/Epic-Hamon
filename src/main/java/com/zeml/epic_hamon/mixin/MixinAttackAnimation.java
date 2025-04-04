package com.zeml.epic_hamon.mixin;

import com.github.standobyte.jojo.init.ModParticles;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.item.GlovesItem;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.nonstand.type.hamon.HamonData;
import com.github.standobyte.jojo.power.impl.nonstand.type.hamon.skill.BaseHamonSkill;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.github.standobyte.jojo.util.mc.damage.DamageUtil;
import com.zeml.epic_hamon.capability.LivingData;
import com.zeml.epic_hamon.capability.LivingDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.Optional;

import static com.github.standobyte.jojo.action.non_stand.HamonAction.addPointsForAction;

@Mixin(value = AttackAnimation.class, remap = false)
public abstract class MixinAttackAnimation extends ActionAnimation {


    @Shadow public abstract LivingEntity getTrueEntity(Entity entity);

    @Mutable
    @Shadow @Final protected static AnimationProperty.ActionAnimationCoordSetter COMMON_COORD_SETTER;

    @Shadow public abstract void end(LivingEntityPatch<?> entitypatch, boolean isEnd);
    @Shadow public abstract AttackAnimation.Phase  getPhaseByTime(float elapsedTime);

    public MixinAttackAnimation(float convertTime, String path, Model model) {
        super(convertTime, path, model);
    }




    @Inject(method = "getDamageTo",at=@At("RETURN"))
    protected void getDamageTo(LivingEntityPatch<?> entitypatch, LivingEntity target, AttackAnimation.Phase phase, ExtendedDamageSource source, CallbackInfoReturnable<Float> cir) {
        Entity sourceOwner = source.getOwner();
        if(sourceOwner instanceof LivingEntity){
            LivingEntity living = (LivingEntity) sourceOwner;
            INonStandPower.getNonStandPowerOptional(living).ifPresent(power->{
                living.getCapability(LivingDataProvider.CAPABILITY).ifPresent(livingData -> {
                    switch (living.getCapability(LivingDataProvider.CAPABILITY).map(LivingData::getAction).orElse("")){
                        case "jojo:hamon_overdrive":
                            if(living.getMainHandItem().isEmpty() || living.getMainHandItem().getItem() instanceof GlovesItem){
                                hamon$normalHamonDamage(power,target,living,false,2.0F,600);
                                power.consumeEnergy(600);
                            }
                            break;
                        case "jojo:hamon_overdrive_beat":
                            if(living.getMainHandItem().isEmpty() || living.getMainHandItem().getItem() instanceof GlovesItem){
                                hamon$normalHamonDamage(power,target,living,true,2.0F,600);
                                power.consumeEnergy(600);
                            }
                            break;
                        case "jojo:jonathan_overdrive_barrage": case "jojo:jonathan_syo_barrage":
                            if((living.getMainHandItem().isEmpty() || living.getMainHandItem().getItem() instanceof GlovesItem)
                                    &&(living.getOffhandItem().isEmpty() || living.getOffhandItem().getItem() instanceof GlovesItem)){
                                hamon$normalHamonDamage(power,target,living,true,0.1F,70);
                                power.consumeEnergy(70);
                            }
                            break;
                        case "jojo:hamon_sunlight_yellow_overdrive":
                            if(((living.getMainHandItem().isEmpty() || living.getMainHandItem().getItem() instanceof GlovesItem)
                                    &&(living.getOffhandItem().isEmpty() || living.getOffhandItem().getItem() instanceof GlovesItem)) &&
                                    power.getEnergy() >0){
                                hamon$normalHamonDamage(power,target,living,true,10.0F,power.getEnergy());
                                power.consumeEnergy(power.getEnergy());
                            }
                            break;
                        case "jojo:jonathan_scarlet_overdrive":
                            if(((living.getMainHandItem().isEmpty() || living.getMainHandItem().getItem() instanceof GlovesItem)
                                    &&(living.getOffhandItem().isEmpty() || living.getOffhandItem().getItem() instanceof GlovesItem))
                            && power.getEnergy() >0){
                                hamon$normalHamonDamageFire(power,target,living,true,10.0F,power.getEnergy());
                                power.consumeEnergy(power.getEnergy());
                            }
                            break;
                        case "jojo:jonathan_metal_silver_overdrive_weapon":
                            if(MCUtil.isItemWeapon(living.getMainHandItem())){
                                hamon$normalHamonDamageSilver(power,target,living,false,2,750);
                            }
                            break;
                        case "jojo:jonathan_metal_silver_overdrive":
                            if(MCUtil.isItemWeapon(living.getMainHandItem())){
                                hamon$normalHamonDamageBetterSilver(power,target,living,false,2,1000);
                            }
                            break;

                    }
                });
            });


        }
    }







    @Inject(method = "getPlaySpeed", at=@At("RETURN"), cancellable = true)
    protected void clcGetPlaySpeed(LivingEntityPatch<?> entityPatch, CallbackInfoReturnable<Float> cir){
        LivingEntity living = entityPatch.getOriginal();
        if(living.getMainHandItem().isEmpty() || living.getMainHandItem().getItem() instanceof GlovesItem){
            String action = living.getCapability(LivingDataProvider.CAPABILITY).map(LivingData::getAction).orElse("");

            if((living.getMainHandItem().isEmpty() || living.getMainHandItem().getItem() instanceof GlovesItem)
                    &&(living.getOffhandItem().isEmpty() || living.getOffhandItem().getItem() instanceof GlovesItem)){
                switch (action){
                    case "jojo:hamon_sunlight_yellow_overdrive": case "jojo:jonathan_scarlet_overdrive" :
                                                cir.setReturnValue(cir.getReturnValue()*.5F);

                        break;
                    case "jojo:jonathan_overdrive_barrage": case "jojo:jonathan_syo_barrage":
                        cir.setReturnValue(cir.getReturnValue()*100F) ;

                        break;
                }
            }
        }
    }


/*
    @Overwrite
    public float getPlaySpeed(LivingEntityPatch<?> entitypatch) {
        if (this.getProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED).isPresent()) {
            return super.getPlaySpeed(entitypatch);
        } else if (entitypatch instanceof PlayerPatch) {
            AttackAnimation.Phase phase = this.getPhaseByTime(entitypatch.getAnimator().getPlayerFor(this).getElapsedTime());
            float speedFactor = (Float)this.getProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR).orElse(1.0F);
            Optional<Float> property = this.getProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED);
            float correctedSpeed = (Float)property.map((value) -> {
                return ((PlayerPatch)entitypatch).getAttackSpeed(((AttackAnimationPhaseAccessor)(Object)phase).getHand()) / value;
            }).orElse(this.totalTime * ((PlayerPatch)entitypatch).getAttackSpeed(phase.hand));
            correctedSpeed = (float)Math.round(correctedSpeed * 1000.0F) / 1000.0F;
            return 1.0F + (correctedSpeed - 1.0F) * speedFactor;
        } else {
            return 1.0F;
        }
    }

 */




    @Unique
  protected boolean dealDamage(LivingEntity targetEntity, float dmgAmount, LivingEntity user, INonStandPower power, HamonData hamon, boolean beat) {
      if (beat) {
          dmgAmount *= 1.5F;
      }
      return DamageUtil.dealHamonDamage(targetEntity, dmgAmount, user, null);
  }


  @Unique
  private void hamon$normalHamonDamage(INonStandPower power, LivingEntity target, LivingEntity user, boolean strongerVersion, float damage, float cost){
      if (!user.level.isClientSide()) {
          Entity entity = target.getEntity();
          if (entity instanceof LivingEntity) {
              LivingEntity targetEntity = (LivingEntity) entity;
              HamonData hamon = power.getTypeSpecificData(ModPowers.HAMON.get()).get();;
              float efficiency = hamon.getActionEfficiency(cost, true);
              if (dealDamage( targetEntity, damage * efficiency, user, power, hamon,strongerVersion)) {
                  if (strongerVersion) {
                      user.level.playSound(null, targetEntity.getX(), targetEntity.getEyeY(), targetEntity.getZ(), ModSounds.HAMON_SYO_PUNCH.get(), targetEntity.getSoundSource(), 1F, 1.5F);
                      targetEntity.knockback(1.25F, user.getX() - targetEntity.getX(), user.getZ() - targetEntity.getZ());
                  }
                  addPointsForAction(power, hamon, BaseHamonSkill.HamonStat.STRENGTH, cost, efficiency);
              }
          }
      }
  }

    @Unique
    private void hamon$normalHamonDamageFire(INonStandPower power, LivingEntity target, LivingEntity user, boolean strongerVersion, float damage, float cost){
        if (!user.level.isClientSide()) {
            Entity entity = target.getEntity();
            if (entity instanceof LivingEntity) {
                LivingEntity targetEntity = (LivingEntity) entity;
                HamonData hamon = power.getTypeSpecificData(ModPowers.HAMON.get()).get();;
                float efficiency = hamon.getActionEfficiency(cost, true);
                if (DamageUtil.dealHamonDamage(targetEntity, damage, user, null, attack -> attack.hamonParticle(ModParticles.HAMON_SPARK_RED.get()))) {
                    DamageUtil.setOnFire(targetEntity, MathHelper.floor(2 + 8F * (float) hamon.getHamonStrengthLevel() /
                            (float) HamonData.MAX_STAT_LEVEL * hamon.getActionEfficiency(cost, true)), false);
                    user.level.playSound(null, targetEntity.getX(), targetEntity.getEyeY(), targetEntity.getZ(), ModSounds.HAMON_SYO_PUNCH.get(), targetEntity.getSoundSource(), power.getEnergy()/power.getMaxEnergy(), 1.0F);
                    addPointsForAction(power, hamon, BaseHamonSkill.HamonStat.STRENGTH, cost, efficiency);
                }
            }
        }
    }


    @Unique
    private void hamon$normalHamonDamageSilver(INonStandPower power, LivingEntity target, LivingEntity user, boolean strongerVersion, float damage, float cost){
        if (!user.level.isClientSide()) {
            Entity entity = target.getEntity();
            if (entity instanceof LivingEntity) {
                LivingEntity targetEntity = (LivingEntity) entity;
                HamonData hamon = power.getTypeSpecificData(ModPowers.HAMON.get()).get();;
                float efficiency = hamon.getActionEfficiency(cost, true);
                if (dealDamageSilver( targetEntity, damage * efficiency, user, power, hamon)) {
                    if (strongerVersion) {
                        user.level.playSound(null, targetEntity.getX(), targetEntity.getEyeY(), targetEntity.getZ(), ModSounds.HAMON_SYO_PUNCH.get(), targetEntity.getSoundSource(), 1F, 1.5F);
                        targetEntity.knockback(1.25F, user.getX() - targetEntity.getX(), user.getZ() - targetEntity.getZ());
                    }
                    addPointsForAction(power, hamon, BaseHamonSkill.HamonStat.STRENGTH, cost, efficiency);
                }
            }
        }
    }


    @Unique
    protected boolean dealDamageSilver(LivingEntity targetEntity, float dmgAmount, LivingEntity user, INonStandPower power, HamonData hamon) {
        return DamageUtil.dealHamonDamage(targetEntity, dmgAmount, user, null, attack -> attack.hamonParticle(ModParticles.HAMON_SPARK_SILVER.get()));
    }


    @Unique
    private void hamon$normalHamonDamageBetterSilver(INonStandPower power, LivingEntity target, LivingEntity user, boolean strongerVersion, float damage, float cost){
        if (!user.level.isClientSide()) {
            Entity entity = target.getEntity();
            if (entity instanceof LivingEntity) {
                LivingEntity targetEntity = (LivingEntity) entity;
                HamonData hamon = power.getTypeSpecificData(ModPowers.HAMON.get()).get();;
                float efficiency = hamon.getActionEfficiency(cost, true);
                if (dealDamageBetterSilver( targetEntity, damage * efficiency, user, power, hamon)) {
                    if (strongerVersion) {
                        user.level.playSound(null, targetEntity.getX(), targetEntity.getEyeY(), targetEntity.getZ(), ModSounds.HAMON_SYO_PUNCH.get(), targetEntity.getSoundSource(), 1F, 1.5F);
                        targetEntity.knockback(1.25F, user.getX() - targetEntity.getX(), user.getZ() - targetEntity.getZ());
                    }
                    addPointsForAction(power, hamon, BaseHamonSkill.HamonStat.STRENGTH, cost, efficiency);
                }
            }
        }
    }

    @Unique
    protected boolean dealDamageBetterSilver(LivingEntity targetEntity, float dmgAmount, LivingEntity user, INonStandPower power, HamonData hamon) {
        float ratio = getSilverDamageMultiplier(targetEntity);
        return DamageUtil.dealHamonDamage(targetEntity, ratio * dmgAmount, user, null, attack -> attack.hamonParticle(ModParticles.HAMON_SPARK_SILVER.get()));
    }

    @Unique
    private static float getSilverDamageMultiplier(LivingEntity targetEntity) {
        float mult = 1;

        for (int i = 0; i < 4; i++) {
            if (!targetEntity.getItemBySlot(EquipmentSlotType.byTypeAndIndex(EquipmentSlotType.Group.ARMOR, i)).isEmpty()) {
                mult += 0.2F;
            }
        }
        for (Hand hand : Hand.values()) {
            ItemStack heldStack = targetEntity.getItemInHand(hand);
            if (MCUtil.isItemWeapon(heldStack)) {
                mult += 0.2F;
                break;
            }
        }

        return mult;
    }


}
