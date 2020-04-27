package com.yshmeel.tenseicraft.common.packets;

import com.yshmeel.tenseicraft.common.fighting.jutsu.IJutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsutype.IJutsuType;
import com.yshmeel.tenseicraft.data.ModInfo;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

public class PacketLearnJutsuTypeMessage extends AbstractMessage<PacketLearnJutsuTypeMessage> {
    private String jutsuType;

    public PacketLearnJutsuTypeMessage() {
    }

    public PacketLearnJutsuTypeMessage(String jutsuType) {
        this.jutsuType = jutsuType;
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
        this.jutsuType = buffer.readString(256);
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
        buffer.writeString(this.jutsuType);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        if(side.isServer()) {
            IJutsuType jutsuType = ModInfo.getJutsuType(this.jutsuType);
            IPlayer data = Player.getInstance(player);
            if(jutsuType != null) {
                if(data.hasJutsuType(jutsuType.getId())) {
                    IJutsu nextJutsu = jutsuType.getNextToLearn(data);
                    if(nextJutsu != null) {
                        if(data.getJutsuPoints() >= nextJutsu.getPointsLearn()) {
                            data.addJutsu(nextJutsu.getId());
                            data.setJutsuPoints(data.getJutsuPoints() - nextJutsu.getPointsLearn());
                        }
                    } else {
                        player.sendMessage(new TextComponentTranslation("common.tenseicraft.unexpected_error"));
                    }
                } else {
                    player.sendMessage(new TextComponentTranslation("common.tenseicraft.unexpected_error"));
                }
            }
        }
    }
}