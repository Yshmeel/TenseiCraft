package com.yshmeel.tenseicraft.client.events;

import com.yshmeel.tenseicraft.client.gui.InGameInterface;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientHandler {
    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {

    }

    @SubscribeEvent
    public void guiOpen(GuiOpenEvent event)
    {
        if(((Gui) event.getGui()) instanceof GuiIngame) {
            event.setGui(new InGameInterface());
        }
    }
}
