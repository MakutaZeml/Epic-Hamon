package com.zeml.epic_hamon.mixin;

import com.github.standobyte.jojo.client.render.entity.layerrenderer.*;
import com.zeml.epic_hamon.client.render.*;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.renderer.patched.entity.PPlayerRenderer;
import yesman.epicfight.client.renderer.patched.entity.PatchedEntityRenderer;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;


@Mixin(value = PatchedLivingEntityRenderer.class)
public abstract class MixinPatchedLivingEntityRenderer <E extends LivingEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>> extends PatchedEntityRenderer<E, T, LivingRenderer<E, M>>{

    @Shadow public abstract void addPatchedLayer(Class<?> originalLayerClass, PatchedLayer<E, T, M, ? extends LayerRenderer<E, M>> patchedLayer);

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        this.addPatchedLayer(HamonBurnLayer.class, new HamonBurnEpicFightLayer<>());
        this.addPatchedLayer(FrozenLayer.class, new FrozenEpicFightLayer<>());
        this.addPatchedLayer(PillarmanLayer.class, new PillarmanEpicFightLayer<>());
        this.addPatchedLayer(WindCloakLayer.class, new WindCloakEpicFightLayer<>());
        this.addPatchedLayer(ZombieLayer.class, new ZombieEpicFightLayer<>());
    }

}
