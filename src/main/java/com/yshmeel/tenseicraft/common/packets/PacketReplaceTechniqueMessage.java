package com.yshmeel.tenseicraft.common.packets;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.client.utils.DialogUtils;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStrongholdPieces;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;
import java.util.Random;

public class PacketReplaceTechniqueMessage  extends AbstractMessage<PacketReplaceTechniqueMessage> {

    public PacketReplaceTechniqueMessage() {
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
    }

    @Override
    // @todo Сделать количество техник подмены
    public void process(EntityPlayer player, Side side) {
        if(side.isServer()) {
            BlockPos position = player.getPosition();
            World world = player.getEntityWorld();
            Random rand = new Random();

            int distance = 5;
            float f1 = MathHelper.cos(-player.rotationYaw * 0.017453292F - (float)Math.PI);
            float f2 = MathHelper.sin(-player.rotationYaw * 0.017453292F - (float)Math.PI);
            float f3 = -MathHelper.cos(-player.rotationPitch * 0.017453292F);
            float f4 = MathHelper.sin(-player.rotationPitch * 0.017453292F);

            BlockPos nextPosition = new BlockPos(position.getX()+(distance*f2*f3), position.getY(),
                    position.getZ()+(distance*f1*f3));

            if(world.getBlockState(nextPosition).toString().equals("minecraft:air")) {
                player.setPositionAndUpdate(nextPosition.getX(), nextPosition.getY(), nextPosition.getZ());
            } else {
                player.setPositionAndUpdate(nextPosition.getX(), nextPosition.getY() + 1, nextPosition.getZ() + 1);
            }

            NetworkRegistry.TargetPoint target = new NetworkRegistry.TargetPoint(world.provider.getDimension(),
                    nextPosition.getX() + 0.5D, nextPosition.getY() + 1.0D, nextPosition.getZ() + 0.5D, 5.d);

            PacketDispatcher.sendToAllAround(new PacketPlaySoundMessage("teleport_sound", nextPosition.getX(),
                    nextPosition.getY() + 1, nextPosition.getZ()), target);

            PacketDispatcher.sendToAllAround(new PacketSpawnParticleMessage(nextPosition.getX(),
                    nextPosition.getY() + 1, nextPosition.getZ(), EnumParticleTypes.SPELL.toString(), 15, 0.1D, 0.0D, -0.2D), target);
        } else if(side.isClient()) {
        }
    }
}