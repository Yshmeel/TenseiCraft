package com.yshmeel.tenseicraft.proxy;

import com.yshmeel.tenseicraft.common.EventHandler;
import com.yshmeel.tenseicraft.common.packets.PacketDispatcher;
import com.yshmeel.tenseicraft.common.registries.BlockRegistry;
import com.yshmeel.tenseicraft.common.registries.ItemRegistry;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import com.yshmeel.tenseicraft.data.player.PlayerCapabilityHandler;
import com.yshmeel.tenseicraft.data.player.PlayerStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        BlockRegistry.register();
        ItemRegistry.register();

    }
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerCapabilityHandler());
        PacketDispatcher.init();
        CapabilityManager.INSTANCE.register(IPlayer.class, new PlayerStorage(), Player.class);
    }
    public void postInit(FMLPostInitializationEvent event) {

    }
    public EntityPlayer getPlayerEntity(MessageContext ctx) {
        return ctx.getServerHandler().player;
    }
}
