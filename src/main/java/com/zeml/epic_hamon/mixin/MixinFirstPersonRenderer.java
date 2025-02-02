package com.zeml.epic_hamon.mixin;

import com.github.standobyte.jojo.client.render.entity.layerrenderer.*;
import com.zeml.epic_hamon.client.render.FrozenEpicFightLayer;
import com.zeml.epic_hamon.client.render.GlovesEpicFightLayer;
import com.zeml.epic_hamon.client.render.HamonBurnEpicFightLayer;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.renderer.FirstPersonRenderer;
import yesman.epicfight.client.renderer.patched.entity.PPlayerRenderer;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;
import yesman.epicfight.client.renderer.patched.layer.EmptyLayer;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;

@Mixin(value = FirstPersonRenderer.class)
public class MixinFirstPersonRenderer extends PatchedLivingEntityRenderer<ClientPlayerEntity, LocalPlayerPatch, PlayerModel<ClientPlayerEntity>> {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        this.addPatchedLayer(GlovesLayer.class, new EmptyLayer<>());
        this.addPatchedLayer(HamonBurnLayer.class, new EmptyLayer<>());
        this.addPatchedLayer(FrozenLayer.class, new EmptyLayer<>());
        this.addPatchedLayer(PillarmanLayer.class, new EmptyLayer<>());
        this.addPatchedLayer(WindCloakLayer.class, new EmptyLayer<>());

    }

}
