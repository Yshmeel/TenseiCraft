package com.yshmeel.tenseicraft.common.packets;

import com.yshmeel.tenseicraft.Tensei;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketDispatcher {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Tensei.MODID);

    public static void init() {
        int id = 0;
        INSTANCE.registerMessage(PacketSyncPlayerDataMessage.class, PacketSyncPlayerDataMessage.class, id++, Side.SERVER);
        INSTANCE.registerMessage(PacketSyncPlayerDataMessage.class, PacketSyncPlayerDataMessage.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(PacketLevelUpMessage.class, PacketLevelUpMessage.class, id++, Side.SERVER);
        INSTANCE.registerMessage(PacketLevelUpMessage.class, PacketLevelUpMessage.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(PacketUpdateStatsMessage.class, PacketUpdateStatsMessage.class, id++, Side.SERVER);
        INSTANCE.registerMessage(PacketSetJutsuSlotsMessage.class, PacketSetJutsuSlotsMessage.class, id++, Side.SERVER);
        INSTANCE.registerMessage(PacketActivateJutsuMessage.class, PacketActivateJutsuMessage.class, id++, Side.SERVER);
        INSTANCE.registerMessage(PacketLearnJutsuTypeMessage.class, PacketLearnJutsuTypeMessage.class, id++, Side.SERVER);
        INSTANCE.registerMessage(PacketRegisterPlayer.class, PacketRegisterPlayer.class, id++, Side.SERVER);
        INSTANCE.registerMessage(PacketUpdateStatsMessage.class, PacketUpdateStatsMessage.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(PacketSetJutsuSlotsMessage.class, PacketSetJutsuSlotsMessage.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(PacketActivateJutsuMessage.class, PacketActivateJutsuMessage.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(PacketLearnJutsuTypeMessage.class, PacketLearnJutsuTypeMessage.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(PacketRegisterPlayer.class, PacketRegisterPlayer.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(PacketShowCutSceneMessage.class, PacketShowCutSceneMessage.class, id++, Side.SERVER);
        INSTANCE.registerMessage(PacketShowCutSceneMessage.class, PacketShowCutSceneMessage.class, id++, Side.CLIENT);
    }

    public static void sendToAll(IMessage message)
    {
        if(message != null)
            INSTANCE.sendToAll(message);
    }

    public static void sendTo(IMessage message, EntityPlayer player)
    {
        INSTANCE.sendTo(message, (EntityPlayerMP) player);
    }

    public static void sendToDimension(IMessage message, int dimensionId)
    {
        INSTANCE.sendToDimension(message, dimensionId);
    }

    public static void sendToServer(IMessage message)
    {
        INSTANCE.sendToServer(message);
    }

    public static void sendToClient(IMessage message, EntityPlayer player) {
        if(!player.getEntityWorld().isRemote) {
            sendTo(message, player);
        } else {
            sendToServer(message);
        }
    }
}
