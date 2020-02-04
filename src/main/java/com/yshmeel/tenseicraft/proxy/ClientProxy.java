package com.yshmeel.tenseicraft.proxy;

import com.yshmeel.tenseicraft.client.events.ClientHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ClientHandler());
        super.init(event);
    }
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
