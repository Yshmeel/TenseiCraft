package com.yshmeel.tenseicraft.common.packets;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

public class PacketSyncPlayerDataMessage
        extends AbstractMessage<PacketSyncPlayerDataMessage> {
    private NBTTagCompound data;

    public PacketSyncPlayerDataMessage() {
    }

    public PacketSyncPlayerDataMessage(NBTTagCompound data, EntityPlayer player) {
        this.data = data;

    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
        this.data = buffer.readCompoundTag();
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
        buffer.writeCompoundTag(this.data);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        IPlayer data = Player.getInstance(player);
        data.set(this.data);
    }
}
