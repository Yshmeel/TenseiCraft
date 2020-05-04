package com.yshmeel.tenseicraft.common.packets;

import com.yshmeel.tenseicraft.common.fighting.jutsu.IJutsu;
import com.yshmeel.tenseicraft.data.ModInfo;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

public class PacketSpawnParticleMessage extends AbstractMessage<PacketSpawnParticleMessage> {
    private double x;
    private double y;
    private double z;
    private int amountOfParticles;
    private double coordsPerX;
    private double coordsPerY;
    private double coordsPerZ;
    private String particleId;

    public PacketSpawnParticleMessage() {
    }

    public PacketSpawnParticleMessage(double x, double y, double z, String particleId, int amountOfParticles, double coordsPerX,
            double coordsPerY, double coordsPerZ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.particleId = particleId;
        this.amountOfParticles = amountOfParticles;
        this.coordsPerX = coordsPerX;
        this.coordsPerY = coordsPerY;
        this.coordsPerZ = coordsPerZ;
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
        this.x = buffer.readDouble();
        this.y = buffer.readDouble();
        this.z = buffer.readDouble();
        this.particleId = buffer.readString(256);
        this.amountOfParticles = buffer.readInt();
        this.coordsPerX = buffer.readDouble();
        this.coordsPerY = buffer.readDouble();
        this.coordsPerZ = buffer.readDouble();
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
        buffer.writeDouble(x);
        buffer.writeDouble(y);
        buffer.writeDouble(z);
        buffer.writeString(particleId);
        buffer.writeInt(amountOfParticles);
        buffer.writeDouble(this.coordsPerX);
        buffer.writeDouble(this.coordsPerY);
        buffer.writeDouble(this.coordsPerZ);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        if(side.isClient()) {
            for(int i = 0; i < amountOfParticles; i++) {
                player.getEntityWorld().spawnParticle(EnumParticleTypes.valueOf(particleId),
                        x + (this.coordsPerX * i),
                        y + (this.coordsPerY * i),
                        z + (this.coordsPerZ * i),
                        this.coordsPerX, this.coordsPerY, this.coordsPerZ);
            }
        }
    }
}