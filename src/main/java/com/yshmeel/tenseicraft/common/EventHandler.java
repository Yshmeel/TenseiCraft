package com.yshmeel.tenseicraft.common;

import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.PlayerCapabilityHandler;
import com.yshmeel.tenseicraft.data.player.PlayerProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler {
    @SubscribeEvent
    public void onPlayerLogsIn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event)
    {
        EntityPlayer player = event.player;
        IPlayer playerData = player.getCapability(PlayerProvider.PLAYER_CAP, null);

        player.sendMessage(new TextComponentString(String.format("Hello, %s. You have clan `%s` on this server",
                player.getName(), playerData.getClanName())));
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event)
    {
        EntityPlayer player = event.getEntityPlayer();
        IPlayer playerCap = player.getCapability(PlayerProvider.PLAYER_CAP, null);
        IPlayer oldPlayerCap = event.getOriginal().getCapability(PlayerProvider.PLAYER_CAP, null);

        playerCap.set(oldPlayerCap.createCompoundFromData());
    }
}
