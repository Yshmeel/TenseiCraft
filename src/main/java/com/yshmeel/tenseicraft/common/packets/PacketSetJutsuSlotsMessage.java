package com.yshmeel.tenseicraft.common.packets;

import com.yshmeel.tenseicraft.common.fighting.jutsu.IJutsu;
import com.yshmeel.tenseicraft.data.ModInfo;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

public class PacketSetJutsuSlotsMessage  extends AbstractMessage<PacketSetJutsuSlotsMessage> {
    private String compiledData;

    public PacketSetJutsuSlotsMessage() {
    }

    public PacketSetJutsuSlotsMessage(String stat) {
        this.compiledData = stat;
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
        this.compiledData = buffer.readString(256);
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
        buffer.writeString(this.compiledData);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        if(side.isServer()) {
            IPlayer data = Player.getInstance(player);

            String[] explodedData = this.compiledData.split("&");
            if(explodedData.length == 2) {
                String jutsuId = explodedData[0];
                int slotId = Integer.valueOf(explodedData[1]);

                IJutsu jutsu = ModInfo.getJutsu(jutsuId);
                if(jutsuId.equals("null")) {
                    data.appendJutsuToSlot(null, slotId);
                } else if(jutsu != null && slotId > 0 && slotId <= 6) {
                    data.appendJutsuToSlot(jutsu.getId(), slotId);
                }

            }
        }
    }
}