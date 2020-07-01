package com.yshmeel.chatmanager;

import com.yshmeel.chatmanager.api.Channel;
import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.common.packets.PacketDispatcher;
import com.yshmeel.tenseicraft.common.packets.PacketPlaySoundMessage;
import com.yshmeel.tenseicraft.common.packets.PacketSendMessage;
import com.yshmeel.tenseicraft.data.ModInfo;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatManager {
    public static HashMap<String, Channel> channels = new HashMap<>();
    public static String MESSAGE_PATTERN = "%pre_text% §3%rank% §f%display_name%§3%village%: §f%message%";

    public ChatManager() {
        this.registerChannel("local", "chatmanager.channels.local.name", "",
                "L",
                "", "", 30.0d,false);
        this.registerChannel("global", "chatmanager.channels.global.name", "!",
                "G",
                "chatmanager.channels.global.description",
                "chatmanager.channels.global.rules", -1,true);
    }

    public Channel registerChannel(String channelName, String channelI18n, String channelTag, String channelPreText,
                                   String channelDescription, String channelRules,
                                   double messageRange,
                                   boolean showChannelInfo) {
        Channel channel = new Channel();

        channel.setName(channelName).setI18n(channelI18n).setTag(channelTag)
                .setPreText(channelPreText)
                .setDescription(channelDescription)
                .setRules(channelRules)
                .setChannelInfoState(showChannelInfo);

        channels.put(channelName, channel);
        Tensei.logger.info("[Tensei] Successfully registered channel with name " + channelName);
        return channel;
    }

    public Channel getChannel(String key) {
        Channel channel = channels.get(key);
        return (channel == null ? channels.get("local") : channel);
    }

    public Channel getChannelFromMessage(ITextComponent message) {
        String text = message.getUnformattedComponentText().replaceFirst("<", "").replaceFirst(">", "").trim();
        Channel object = channels.get("local");

        for(HashMap.Entry<String, Channel> entry : channels.entrySet()) {
            Channel entryChannel = entry.getValue();

            if(text.startsWith(entryChannel.getTag())) {
                object = entryChannel;
                break;
            }
        }

        return object;
    }

    // @todo Сделать название деревни вместо фамилии

    public ITextComponent parseText(EntityPlayer player, ITextComponent text, Channel channel) {
        String message = text.getUnformattedComponentText().replaceFirst("<", "").replaceFirst(">", "").trim();
        IPlayer playerObject = Player.getInstance(player);

        message = message.replace(channel.getTag(), "");
        message = MESSAGE_PATTERN.replaceFirst("%rank%", "[" + playerObject.getRankName() + "]")
                .replaceFirst("%display_name%", player.getName() + " " + playerObject.getLastName())
                .replaceFirst("%village%", "")
                .replaceFirst("%pre_text%", channel.getFormattedPreText())
                .replaceFirst("%message%", message);

        return new TextComponentString(message);
    }
}
