package com.yshmeel.tenseicraft.common.packets;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.client.utils.CutSceneUtils;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TextComponentTranslation;
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
            CutSceneUtils.showCutScene(this.cutsceneId);
        }
    }
}