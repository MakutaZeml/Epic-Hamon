package com.zeml.epic_hamon.client.render;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.client.render.entity.layerrenderer.FrozenLayer;
import com.github.standobyte.jojo.client.render.entity.layerrenderer.HamonBurnLayer;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.client.model.ClientModel;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.EpicFightRenderTypes;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

@OnlyIn(value = Dist.CLIENT)
public class FrozenEpicFightLayer<E extends LivingEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>> extends PatchedLayer<E, T, M, FrozenLayer<E, M>> {


    @Override
    public void renderLayer(T t, E e, FrozenLayer<E, M> frozenLayer, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, OpenMatrix4f[] openMatrix4fs, float v, float v1, float v2) {
        if(!e.isInvisible()){
            M model = frozenLayer.getParentModel();
            ClientModel model1 = t.getEntityModel(ClientModels.LOGICAL_CLIENT);

            ResourceLocation texture = getTexture(model, e);
            if (texture == null) return;

            IVertexBuilder builder1 = iRenderTypeBuffer.getBuffer(EpicFightRenderTypes.animatedModel(texture));
            model1.drawAnimatedModel(matrixStack,builder1, ClientUtil.MAX_MODEL_LIGHT,1.0F, 1.0F, 1.0F, 1.0F, LivingRenderer.getOverlayCoords(e, 0.0F), openMatrix4fs);

        }
    }

    @Nullable
    private ResourceLocation getTexture(EntityModel<?> model, LivingEntity entity) {
        EffectInstance freeze = entity.getEffect(ModStatusEffects.FREEZE.get());
        if (freeze != null) {
            int freezelvl = Math.min(freeze.getAmplifier(), 3);
            HamonBurnLayer.TextureSize freezesize = HamonBurnLayer.TextureSize.getClosestTexSize(model);
            return LAYER_TEXTURES_FREEZE.get(freezesize)[freezelvl];
        }
        return null;
    }

    private Map<HamonBurnLayer.TextureSize, ResourceLocation[]> LAYER_TEXTURES_FREEZE =Util.make(new EnumMap<>(HamonBurnLayer.TextureSize.class), map -> {
        map.put(HamonBurnLayer.TextureSize._64x32, new ResourceLocation[] {
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t64x32/1.png"),
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t64x32/2.png"),
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t64x32/3.png"),
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t64x32/4.png")
        });
        map.put(HamonBurnLayer.TextureSize._64x64, new ResourceLocation[] {
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t64x64/1.png"),
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t64x64/2.png"),
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t64x64/3.png"),
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t64x64/4.png")
        });
        map.put(HamonBurnLayer.TextureSize._128x64, new ResourceLocation[] {
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t128x64/1.png"),
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t128x64/2.png"),
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t128x64/3.png"),
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t128x64/4.png")
        });
        map.put(HamonBurnLayer.TextureSize._128x128, new ResourceLocation[] {
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t128x128/1.png"),
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t128x128/2.png"),
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t128x128/3.png"),
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t128x128/4.png")
        });
        map.put(HamonBurnLayer.TextureSize._256x128, new ResourceLocation[] {
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t256x128/1.png"),
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t256x128/2.png"),
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t256x128/3.png"),
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t256x128/4.png")
        });
        map.put(HamonBurnLayer.TextureSize._256x256, new ResourceLocation[] {
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t256x256/1.png"),
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t256x256/2.png"),
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t256x256/3.png"),
                new ResourceLocation(JojoMod.MOD_ID, "textures/entity/layer/vampire_freeze/t256x256/4.png")
        });
    });;

}
