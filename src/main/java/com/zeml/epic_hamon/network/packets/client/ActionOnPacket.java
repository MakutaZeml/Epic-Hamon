package com.zeml.epic_hamon.network.packets.client;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.client.ui.actionshud.ActionsOverlayGui;
import com.github.standobyte.jojo.init.power.JojoCustomRegistries;
import com.github.standobyte.jojo.network.packets.IModPacketHandler;
import com.zeml.epic_hamon.capability.LivingDataProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ActionOnPacket {

    private final String action;

    public ActionOnPacket(String action){
        this.action = action;
    }


    public static class Handler implements IModPacketHandler<ActionOnPacket>{


        @Override
        public void encode(ActionOnPacket actionOnPacket, PacketBuffer buf) {
            buf.writeUtf(actionOnPacket.action);
        }

        @Override
        public ActionOnPacket decode(PacketBuffer buf) {
            return new ActionOnPacket(buf.readUtf(32767));
        }

        @Override
        public void handle(ActionOnPacket actionOnPacket, Supplier<NetworkEvent.Context> ctx) {
            NetworkEvent.Context context = ctx.get();
            context.enqueueWork(() -> {
                ServerPlayerEntity player = context.getSender();
                if(player != null){
                    player.getCapability(LivingDataProvider.CAPABILITY).ifPresent(playerData ->playerData.setAction(actionOnPacket.action) );
                }
            });
        }

        @Override
        public Class<ActionOnPacket> getPacketClass() {
            return ActionOnPacket.class;
        }
    }

}
