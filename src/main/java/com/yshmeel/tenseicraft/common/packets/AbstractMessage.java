package com.yshmeel.tenseicraft.common.packets;

import com.google.common.base.Throwables;
import com.yshmeel.tenseicraft.Tensei;
import io.netty.buffer.ByteBuf;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public abstract class AbstractMessage<T extends AbstractMessage<T>>
        implements IMessage,
        IMessageHandler<T, IMessage> {
    protected abstract void read(PacketBuffer var1) throws IOException;

    protected abstract void write(PacketBuffer var1) throws IOException;

    public abstract void process(EntityPlayer var1, Side var2);

    protected boolean isValidOnSide(Side side) {
        return true;
    }

    protected boolean requiresMainThread() {
        return true;
    }

    public void fromBytes(ByteBuf buffer) {
        try {
            this.read(new PacketBuffer(buffer));
        }
        catch (IOException e) {
            throw Throwables.propagate((Throwable)e);
        }
    }

    public void toBytes(ByteBuf buffer) {
        try {
            this.write(new PacketBuffer(buffer));
        }
        catch (IOException e) {
            throw Throwables.propagate((Throwable)e);
        }
    }

    public final IMessage onMessage(T msg, MessageContext ctx) {
        if (!((AbstractMessage)msg).isValidOnSide(ctx.side)) {
            throw new RuntimeException("Invalid side " + ctx.side.name() + " for " + msg.getClass().getSimpleName());
        }

        ((AbstractMessage)msg).process(Tensei.proxy.getPlayerEntity(ctx), ctx.side);

        return null;
    }

    public static abstract class AbstractServerMessage<T extends AbstractMessage<T>>
            extends AbstractMessage<T> {
        @Override
        protected final boolean isValidOnSide(Side side) {
            return side.isServer();
        }
    }

    public static abstract class AbstractClientMessage<T extends AbstractMessage<T>>
            extends AbstractMessage<T> {
        @Override
        protected final boolean isValidOnSide(Side side) {
            return side.isClient();
        }
    }

}
