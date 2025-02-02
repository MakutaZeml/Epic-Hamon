package com.zeml.epic_hamon.mixin;

import com.github.standobyte.jojo.item.GlovesItem;
import com.zeml.epic_hamon.capability.LivingData;
import com.zeml.epic_hamon.capability.LivingDataProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

@Mixin(value = PlayerPatch.class, remap = false)
public abstract class MixinPlayerPatch<T extends PlayerEntity> extends LivingEntityPatch<T> {

    @Shadow public abstract float getAttackSpeed(CapabilityItem itemCapability, float baseSpeed);


    /*
    @Inject(method = "getAttackSpeed(Lnet/minecraft/util/Hand;)F",at=@At("RETURN"), cancellable = true)
    public void getAttackSpeed(Hand hand, CallbackInfoReturnable<Float> cir){
        LivingEntity living = this.getOriginal();
        if(living.getMainHandItem().isEmpty() || living.getMainHandItem().getItem() instanceof GlovesItem){
            String action = living.getCapability(LivingDataProvider.CAPABILITY).map(LivingData::getAction).orElse("");
            switch (action){
                case "jojo:hamon_sunlight_yellow_overdrive": case "jojo:jonathan_scarlet_overdrive" :
                    System.out.println(cir.getReturnValue()*.5F );
                    cir.setReturnValue(cir.getReturnValue()*.5F);
                    break;
                case "jojo:jonathan_overdrive_barrage": case "jojo:jonathan_syo_barrage":
                    System.out.println(cir.getReturnValue()*100 );
                    cir.setReturnValue(cir.getReturnValue()*100F);
                    break;
            }
        }

    }




    /**
     * @author Makuta Zeml
     * @reason Implement Stuff

    @Overwrite
    public float getAttackSpeed(Hand hand) {
        float baseSpeed;
        if (hand == Hand.MAIN_HAND) {
            baseSpeed = (float)((PlayerEntity)this.original).getAttributeValue(Attributes.ATTACK_SPEED);
        } else {
            baseSpeed = (float)(this.isOffhandItemValid() ? ((PlayerEntity)this.original).getAttributeValue(Attributes.ATTACK_SPEED) : ((PlayerEntity)this.original).getAttributeBaseValue(Attributes.ATTACK_SPEED));
        }
        LivingEntity living = this.getOriginal();
        if(living.getMainHandItem().isEmpty() || living.getMainHandItem().getItem() instanceof GlovesItem){
            String action = living.getCapability(LivingDataProvider.CAPABILITY).map(LivingData::getAction).orElse("");
            switch (action){
                case "jojo:hamon_sunlight_yellow_overdrive": case "jojo:jonathan_scarlet_overdrive" :
                    baseSpeed = baseSpeed*.33F;
                    break;
                case "jojo:jonathan_overdrive_barrage": case "jojo:jonathan_syo_barrage":
                    baseSpeed = baseSpeed*10F;
                    break;
            }
        }

        System.out.println(this.getAttackSpeed(this.getAdvancedHoldingItemCapability(hand), baseSpeed));
        return this.getAttackSpeed(this.getAdvancedHoldingItemCapability(hand), baseSpeed);
    }



     */

}
