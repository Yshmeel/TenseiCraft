package com.yshmeel.tenseicraft.common.packets;

import com.yshmeel.tenseicraft.client.Sounds;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

public class PacketSendMessage  extends AbstractMessage<PacketSendMessage> {
    private double x;
    private double y;
    private double z;
    private ITextComponent message;

    public PacketSendMessage() {
    }

    public PacketSendMessage(ITextComponent message) {
        this.message = message;
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
        this.message = buffer.readTextComponent();
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
        buffer.writeTextComponent(message);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        if(side.isClient()) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                player.sendMessage(message);
            });
        }
    }
}