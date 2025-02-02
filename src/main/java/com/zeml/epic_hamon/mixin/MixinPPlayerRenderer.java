package com.zeml.epic_hamon.mixin;

import com.github.standobyte.jojo.client.render.entity.layerrenderer.GlovesLayer;
import com.zeml.epic_hamon.client.render.GlovesEpicFightLayer;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.renderer.patched.entity.PHumanoidRenderer;
import yesman.epicfight.client.renderer.patched.entity.PPlayerRenderer;
import yesman.epicfight.client.world.capabilites.entitypatch.player.AbstractClientPlayerPatch;

@Mixin(value = PPlayerRenderer.class)
public class MixinPPlayerRenderer extends PHumanoidRenderer<AbstractClientPlayerEntity, AbstractClientPlayerPatch<AbstractClientPlayerEntity>, PlayerModel<AbstractClientPlayerEntity>> {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        this.addPatchedLayer(GlovesLayer.class, new GlovesEpicFightLayer<>());
    }
}
