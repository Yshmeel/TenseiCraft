package com.yshmeel.tenseicraft.common.packets;

import com.yshmeel.tenseicraft.client.Sounds;
import com.yshmeel.tenseicraft.common.fighting.jutsu.IJutsu;
import com.yshmeel.tenseicraft.data.ModInfo;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

public class PacketActivateJutsuMessage extends AbstractMessage<PacketActivateJutsuMessage> {
    private String jutsuId;

    public PacketActivateJutsuMessage() {
    }

    public PacketActivateJutsuMessage(String jutsuId) {
        this.jutsuId = jutsuId;
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
        this.jutsuId = buffer.readString(256);
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
        buffer.writeString(this.jutsuId);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        if(side.isServer()) {
            IJutsu jutsu = ModInfo.getJutsu(this.jutsuId);
            IPlayer data = Player.getInstance(player);
            if(jutsu != null ) {
                if(data.isJutsuLearned(jutsu.getId())) {
                    if(data.hasChakra(jutsu.getChakraTake())) {
                        jutsu.throwJutsu(player, null, player.getEntityWorld());
                    } else {
                        player.sendMessage(new TextComponentTranslation("common.jutsu.not_enough_chakra",
                                jutsu.getChakraTake()));
                    }
                } else {
                    player.sendMessage(new TextComponentTranslation("common.tenseicraft.unexpected_error"));
                }
            }
        }
    }
}