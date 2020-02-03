package com.yshmeel.tenseicraft.proxy;

import com.yshmeel.tenseicraft.common.EventHandler;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import com.yshmeel.tenseicraft.data.player.PlayerCapabilityHandler;
import com.yshmeel.tenseicraft.data.player.PlayerStorage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {

    }
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerCapabilityHandler());
        CapabilityManager.INSTANCE.register(IPlayer.class, new PlayerStorage(), Player.class);
    }
    public void postInit(FMLPostInitializationEvent event) {

    }
}
