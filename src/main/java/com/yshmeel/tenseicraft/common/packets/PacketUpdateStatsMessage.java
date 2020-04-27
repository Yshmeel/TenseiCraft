package com.yshmeel.tenseicraft.common.packets;

import com.yshmeel.tenseicraft.client.Sounds;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

public class PacketUpdateStatsMessage extends AbstractMessage<PacketUpdateStatsMessage> {
    private String stat;

    public PacketUpdateStatsMessage() {
    }

    public PacketUpdateStatsMessage(String stat) {
        this.stat = stat;
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
        this.stat = buffer.readString(256);
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
        buffer.writeString(this.stat);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        if(side.isServer()) {
            IPlayer data = Player.getInstance(player);

            if(data.getSkillPoints() >= 1) {
                switch(this.stat) {
                    case "ninjutsu":
                        data.addNinjutsu(1);
                        break;
                    case "taijutsu":
                        data.addTaijutsu(1);
                        break;
                    case "speed":
                        data.addSpeed(1);
                        break;
                    case "genjutsu":
                        data.addGenjutsu(1);
                        break;
                    default:
                        player.sendMessage(new TextComponentTranslation("common.tenseicraft.unexpected_error"));
                        break;
                }
            }
        }
    }
}