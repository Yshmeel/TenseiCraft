package com.yshmeel.tenseicraft.data.player;

import com.yshmeel.tenseicraft.Tensei;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerCapabilityHandler
{
    public static final ResourceLocation PLAYER_CAP = new ResourceLocation(Tensei.MODID, "player_cap");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event)
    {
        if (!(event.getObject() instanceof EntityPlayer)) return;

        event.addCapability(PLAYER_CAP, new PlayerProvider());
    }
}