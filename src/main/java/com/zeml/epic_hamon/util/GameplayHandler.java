package com.zeml.epic_hamon.util;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.client.ui.actionshud.ActionsOverlayGui;
import com.github.standobyte.jojo.init.power.non_stand.hamon.ModHamonActions;
import com.github.standobyte.jojo.init.power.non_stand.hamon.ModHamonSkills;
import com.zeml.epic_hamon.network.ModNetwork;
import com.zeml.epic_hamon.network.packets.client.ActionOnPacket;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber
public class GameplayHandler {



    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event){
        if(event.player.level.isClientSide){
            ActionsOverlayGui hud = ActionsOverlayGui.getInstance();
            if (hud.isActionSelectedAndEnabled(ModHamonActions.HAMON_OVERDRIVE.get())) {
                ModNetwork.sendToServer(new ActionOnPacket(ModHamonActions.HAMON_OVERDRIVE.get().getRegistryName().toString()));
            } else if (hud.isActionSelectedAndEnabled(ModHamonActions.HAMON_BEAT.get())) {
                ModNetwork.sendToServer(new ActionOnPacket(ModHamonActions.HAMON_BEAT.get().getRegistryName().toString()));
            } else if (hud.isActionSelectedAndEnabled(ModHamonActions.JONATHAN_SCARLET_OVERDRIVE.get())) {
                ModNetwork.sendToServer(new ActionOnPacket(ModHamonActions.JONATHAN_SCARLET_OVERDRIVE.get().getRegistryName().toString()));
            }else if (hud.isActionSelectedAndEnabled(ModHamonActions.HAMON_TURQUOISE_BLUE_OVERDRIVE.get())) {
                ModNetwork.sendToServer(new ActionOnPacket(ModHamonActions.HAMON_TURQUOISE_BLUE_OVERDRIVE.get().getRegistryName().toString()));
            }else if (hud.isActionSelectedAndEnabled(ModHamonActions.HAMON_SUNLIGHT_YELLOW_OVERDRIVE.get())) {
                ModNetwork.sendToServer(new ActionOnPacket(ModHamonActions.HAMON_SUNLIGHT_YELLOW_OVERDRIVE.get().getRegistryName().toString()));
            }else if (hud.isActionSelectedAndEnabled(ModHamonActions.JONATHAN_OVERDRIVE_BARRAGE.get())) {
                ModNetwork.sendToServer(new ActionOnPacket(ModHamonActions.JONATHAN_OVERDRIVE_BARRAGE.get().getRegistryName().toString()));
            }else if (hud.isActionSelectedAndEnabled(ModHamonActions.JONATHAN_METAL_SILVER_OVERDRIVE.get())) {
                ModNetwork.sendToServer(new ActionOnPacket(ModHamonActions.JONATHAN_METAL_SILVER_OVERDRIVE.get().getRegistryName().toString()));
            }else if (hud.isActionSelectedAndEnabled(ModHamonActions.JONATHAN_METAL_SILVER_OVERDRIVE_WEAPON.get())) {
                ModNetwork.sendToServer(new ActionOnPacket(ModHamonActions.JONATHAN_METAL_SILVER_OVERDRIVE_WEAPON.get().getRegistryName().toString()));
            }else {
                ModNetwork.sendToServer(new ActionOnPacket(""));
            }
        }
    }



}
