package com.yshmeel.tenseicraft.common.packets;

import com.yshmeel.tenseicraft.client.Sounds;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

public class PacketLevelUpMessage extends AbstractMessage<PacketLevelUpMessage> {
    private int level;

    public PacketLevelUpMessage() {
    }

    public PacketLevelUpMessage(EntityPlayer player, int level) {
        this.level = level;
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
        this.level = buffer.getInt(0);
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
        buffer.writeInt(this.level);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        if(side.isClient()) {
            SoundEvent event = new SoundEvent(Sounds.LEVEL_UP_SOUND);
            player.playSound(event, 0.7F, 1.0F);
        }
    }
}