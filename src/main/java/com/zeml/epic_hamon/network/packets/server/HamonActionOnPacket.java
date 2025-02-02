package com.zeml.epic_hamon.network.packets.server;

import com.zeml.epic_hamon.capability.LivingData;
import com.zeml.epic_hamon.capability.LivingDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class HamonActionOnPacket {
    private final int patchID;
    private final String action;

    public HamonActionOnPacket(int entityID, String action){
        this.patchID = entityID;
        this.action = action;
    }

    public static void encode(HamonActionOnPacket msg, PacketBuffer buf){
        buf.writeInt(msg.patchID);
        buf.writeUtf(msg.action);
    }

    public static HamonActionOnPacket decode(PacketBuffer buffer){
        return new HamonActionOnPacket(buffer.readInt(), buffer.readUtf());
    }


    public static void handle(HamonActionOnPacket msg, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            Entity entity = com.github.standobyte.jojo.client.ClientUtil.getEntityById(msg.patchID);
            if(entity instanceof LivingEntity){
                LivingEntity living = (LivingEntity) entity;
                LazyOptional<LivingData> playerDataOptional = living.getCapability(LivingDataProvider.CAPABILITY);
                playerDataOptional.ifPresent(playerData->{
                    playerData.setAction(msg.action);
                });
            }
        });
        ctx.get().setPacketHandled(true);

    }

}
