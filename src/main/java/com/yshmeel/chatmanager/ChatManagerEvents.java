package com.yshmeel.chatmanager;

import com.yshmeel.chatmanager.api.Channel;
import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.common.packets.PacketDispatcher;
import com.yshmeel.tenseicraft.common.packets.PacketSendMessage;
import com.yshmeel.tenseicraft.data.ModInfo;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ChatManagerEvents {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerChat(ServerChatEvent event) {
        if(ModInfo.IS_SERVER) {
            event.setCanceled(true);
            EntityPlayer player = event.getPlayer();
            ITextComponent text = event.getComponent();
            World world = player.getEntityWorld();
            Channel channel = Tensei.chatManager.getChannelFromMessage(text);
            ITextComponent message = Tensei.chatManager.parseText(player, text, channel);

            if(channel.getChatRange() == -1) {
                for(EntityPlayerMP entity : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers()) {
                    if(entity == null) continue;
                    entity.sendMessage(message);
                }
            } else {
                NetworkRegistry.TargetPoint target = new NetworkRegistry.TargetPoint(world.provider.getDimension(),
                        player.posX, player.posY, player.posZ, channel.getChatRange());

                PacketDispatcher.sendToAllAround(new PacketSendMessage(message), target);
            }

        }
    }
}
