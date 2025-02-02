package com.zeml.epic_hamon.client.render;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.client.render.entity.layerrenderer.FrozenLayer;
import com.github.standobyte.jojo.client.render.entity.layerrenderer.PillarmanLayer;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.power.impl.nonstand.type.pillarman.PillarmanData;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.client.model.ClientModel;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.EpicFightRenderTypes;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import javax.annotation.Nullable;

@OnlyIn(value = Dist.CLIENT)
public class PillarmanEpicFightLayer <E extends LivingEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>> extends PatchedLayer<E, T, M, PillarmanLayer<E, M>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(JojoMod.MOD_ID, "textures/entity/biped/pillarman_layer.png");

    @Override
    public void renderLayer(T t, E e, PillarmanLayer<E, M> emPillarmanLayer, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, OpenMatrix4f[] openMatrix4fs, float v, float v1, float v2) {
        if (!e.isInvisible()){
            M model = emPillarmanLayer.getParentModel();
            ClientModel model1 = t.getEntityModel(ClientModels.LOGICAL_CLIENT);

            ResourceLocation texture = getTexture(model,e);
            if (texture == null) return;

            IVertexBuilder builder1 = iRenderTypeBuffer.getBuffer(EpicFightRenderTypes.animatedModel(texture));
            model1.drawAnimatedModel(matrixStack,builder1, i,1.0F, 1.0F, 1.0F, 1.0F, LivingRenderer.getOverlayCoords(e, 0.0F), openMatrix4fs);
        }



    }

    @Nullable
    private ResourceLocation getTexture(EntityModel<?> model, LivingEntity entity) {
        if (INonStandPower.getNonStandPowerOptional(entity).resolve().flatMap(
                        power -> power.getTypeSpecificData(ModPowers.PILLAR_MAN.get()))
                .map(PillarmanData::isStoneFormEnabled).orElse(false)) {
            return TEXTURE;
        }
        return null;
    }
}
