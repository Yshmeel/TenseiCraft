package com.yshmeel.tenseicraft.common.packets;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.client.Sounds;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

public class PacketRegisterPlayer  extends AbstractMessage<PacketRegisterPlayer> {
    private String lastName;

    public PacketRegisterPlayer() {
    }

    public PacketRegisterPlayer(EntityPlayer player, String lastName) {
        this.lastName = lastName;
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
        this.lastName = buffer.readString(8);
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
        buffer.writeString(this.lastName);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        if(side.isServer()) {
            IPlayer data = Player.getInstance(player);

            if(this.lastName.length() > 2) {
                if(!data.isRegistered()) {
                    data.setChakraControlEnabled(false);
                    data.setLastName(this.lastName);
                    data.setNinjutsu(1);
                    data.setTaijutsu(0);
                    data.setGenjutsu(0);
                    data.setSpeed(0);
                    data.setRegistered(true);
                    Tensei.logger.info("[Tensei} Player " + player.getName() + " was registered on server!");
                } else {
                    player.sendMessage(new TextComponentTranslation("common.tenseicraft.unexpected_error"));
                }
            }
        }
    }
}