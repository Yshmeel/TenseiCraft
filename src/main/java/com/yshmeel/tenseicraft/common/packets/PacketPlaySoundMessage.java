package com.yshmeel.tenseicraft.common.packets;

import com.yshmeel.tenseicraft.client.Sounds;
import com.yshmeel.tenseicraft.common.fighting.jutsu.IJutsu;
import com.yshmeel.tenseicraft.data.ModInfo;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.client.audio.Sound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

public class PacketPlaySoundMessage extends AbstractMessage<PacketPlaySoundMessage> {
    private double x;
    private double y;
    private double z;
    private String soundName;

    public PacketPlaySoundMessage() {
    }

    public PacketPlaySoundMessage(String soundName, double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.soundName = soundName;
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
        this.x = buffer.readDouble();
        this.y = buffer.readDouble();
        this.z = buffer.readDouble();
        this.soundName = buffer.readString(256);
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
        buffer.writeDouble(x);
        buffer.writeDouble(y);
        buffer.writeDouble(z);
        buffer.writeString(soundName);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        if(side.isClient()) {
            player.getEntityWorld().playSound(player, x, y, z, Sounds.valueOf(soundName), SoundCategory.MASTER, 1.0F, 1.0F);
        }
    }
}