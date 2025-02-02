package com.zeml.epic_hamon.client.render;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.client.render.entity.layerrenderer.PillarmanLayer;
import com.github.standobyte.jojo.client.render.entity.layerrenderer.WindCloakLayer;
import com.github.standobyte.jojo.init.power.non_stand.pillarman.ModPillarmanActions;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import yesman.epicfight.api.client.model.ClientModel;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.EpicFightRenderTypes;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class WindCloakEpicFightLayer  <E extends LivingEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>> extends PatchedLayer<E, T, M, WindCloakLayer<E, M>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(JojoMod.MOD_ID, "textures/entity/biped/wind_cloak.png");

    @Override
    public void renderLayer(T t, E e, WindCloakLayer<E, M> emWindCloakLayer, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, OpenMatrix4f[] openMatrix4fs, float v, float v1, float v2) {
        if (INonStandPower.getNonStandPowerOptional(e)
                .map(power -> power.getHeldAction(true) == ModPillarmanActions.PILLARMAN_WIND_CLOAK.get())
                .orElse(false)){
            float f = (float)e.tickCount + v2;
            ClientModel model1 = t.getEntityModel(ClientModels.LOGICAL_CLIENT);

            IVertexBuilder builder1 = iRenderTypeBuffer.getBuffer(EpicFightRenderTypes.energySwirl(TEXTURE,this.xOffset(f), f * 0.01F));
            model1.drawAnimatedModel(matrixStack,builder1, i,0.2F, 0.2F, 0.2F, 0.25F, LivingRenderer.getOverlayCoords(e, 0.0F), openMatrix4fs);

        }
    }


    protected float xOffset(float p_225634_1_) {
        return p_225634_1_ * 0.01F;
    }
}

