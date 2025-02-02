package com.zeml.epic_hamon.client.render;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.client.render.entity.layerrenderer.GlovesLayer;
import com.github.standobyte.jojo.item.GlovesItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.client.model.ClientModel;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.EpicFightRenderTypes;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.HashMap;
import java.util.Map;

import static com.github.standobyte.jojo.client.render.entity.layerrenderer.GlovesLayer.getRenderedGlovesItem;

@OnlyIn(value = Dist.CLIENT)
public class GlovesEpicFightLayer <E extends LivingEntity, T extends LivingEntityPatch<E>, M extends PlayerModel<E>> extends PatchedLayer<E, T, M, GlovesLayer<E, M>> {
    private final boolean slim = false;
    private static final Map<ResourceLocation, ClientModel> ARMOR_MODELS = new HashMap<ResourceLocation, ClientModel>();




    @Override
    public void renderLayer(T t, E e, GlovesLayer<E, M> emGlovesLayer, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, OpenMatrix4f[] openMatrix4fs, float v, float v1, float v2) {
        ItemStack glovesItemStack = getRenderedGlovesItem(e);
        if (!glovesItemStack.isEmpty()){

            GlovesItem gloves = (GlovesItem) glovesItemStack.getItem();
            ClientModel model1 = t.getEntityModel(ClientModels.LOGICAL_CLIENT);

            ResourceLocation texture = getTexture(gloves);


            IVertexBuilder builder1 = iRenderTypeBuffer.getBuffer(EpicFightRenderTypes.animatedModel(texture));
            model1.drawAnimatedModel(matrixStack,builder1, i,1.0F, 1.0F, 1.0F, 1.0F, LivingRenderer.getOverlayCoords(e, 0.0F), openMatrix4fs);
        }
    }



    private ResourceLocation getTexture(GlovesItem gloves) {
        return new ResourceLocation(
                gloves.getRegistryName().getNamespace(),
                "textures/entity/layer/" + gloves.getRegistryName().getPath() + (slim ? "_slim" : "") + ".png");
    }


    private ClientModel glovesModel(GlovesItem glovesItem){
        ResourceLocation glovesLocation = glovesItem.getRegistryName();
        return ARMOR_MODELS.get(glovesLocation);
    }






}
