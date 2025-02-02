package com.zeml.epic_hamon.client.render;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.client.render.entity.layerrenderer.HamonBurnLayer;
import com.github.standobyte.jojo.client.render.entity.layerrenderer.ZombieLayer;
import com.github.standobyte.jojo.init.power.non_stand.ModPowers;
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

import javax.annotation.Nullable;

public class ZombieEpicFightLayer <E extends LivingEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>> extends PatchedLayer<E, T, M, ZombieLayer<E, M>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(JojoMod.MOD_ID, "textures/entity/biped/sated_zombie.png");

    @Override
    public void renderLayer(T t, E e, ZombieLayer<E, M> emZombieLayer, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, OpenMatrix4f[] openMatrix4fs, float v, float v1, float v2) {
        if (!e.isInvisible()){
            M model = emZombieLayer.getParentModel();

            ClientModel model1 = t.getEntityModel(ClientModels.LOGICAL_CLIENT);
            ResourceLocation texture = getTexture(model, e);
            if (texture == null) return;

            IVertexBuilder builder1 = iRenderTypeBuffer.getBuffer(EpicFightRenderTypes.animatedModel(texture));
            model1.drawAnimatedModel(matrixStack,builder1, i,1.0F, 1.0F, 1.0F, 1.0F, LivingRenderer.getOverlayCoords(e, 0.0F), openMatrix4fs);
        }
    }


    @Nullable
    private ResourceLocation getTexture(EntityModel<?> model, LivingEntity entity) {
        if (INonStandPower.getNonStandPowerOptional(entity).resolve().flatMap(
                power -> power.getTypeSpecificData(ModPowers.ZOMBIE.get())
                        .map(zombie -> !zombie.isDisguiseEnabled())).orElse(false)) {
            return TEXTURE;
        }
        return null;
    }
}
