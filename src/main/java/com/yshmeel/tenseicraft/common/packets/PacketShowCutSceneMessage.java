package com.yshmeel.tenseicraft.common.packets;

import com.yshmeel.tenseicraft.client.utils.DialogUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

public class PacketShowCutSceneMessage  extends AbstractMessage<PacketShowCutSceneMessage> {
    private String cutsceneId;

    public PacketShowCutSceneMessage() {
    }

    public PacketShowCutSceneMessage(String cutsceneId) {
        this.cutsceneId = cutsceneId;
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
        this.cutsceneId = buffer.readString(256);
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
        buffer.writeString(this.cutsceneId);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        if(side.isClient()) {
            DialogUtils.showDialog(this.cutsceneId);
        }
    }
}