package com.zeml.epic_hamon.capability;

import com.github.standobyte.jojo.action.Action;
import com.zeml.epic_hamon.network.ModNetwork;
import com.zeml.epic_hamon.network.packets.server.HamonActionOnPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;

public class LivingData implements INBTSerializable<CompoundNBT> {
    private final LivingEntity entity;
    private String action = "";
    private String action2 = "";

    public LivingData(LivingEntity entity) {
        this.entity = entity;
    }

    public void setAction(String action) {
        this.action = action;
        if(this.entity instanceof ServerPlayerEntity){
            ModNetwork.sendToClient(new HamonActionOnPacket(entity.getId(),action),(ServerPlayerEntity) entity);
        }
    }

    public String getAction() {
        return this.action;

    }

    public void setAction2(String action2) {
        this.action2 = action2;
    }

    public String getAction2() {
        return this.action2;
    }

    public void syncWithAnyPlayer(ServerPlayerEntity player) {
        //AddonPackets.sendToClient(new TrPickaxesThrownPacket(entity.getId(), pickaxesThrown), player);
    }

    // If there is data that should only be known to the player, and not to other ones, sync it here instead.
    public void syncWithEntityOnly(ServerPlayerEntity player) {
        ModNetwork.sendToClient(new HamonActionOnPacket(player.getId(),this.action), player);
    }



    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("action",this.action);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.action = nbt.getString("action");
    }
}
