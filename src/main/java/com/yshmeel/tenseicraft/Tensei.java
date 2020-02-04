package com.yshmeel.tenseicraft;

import com.yshmeel.tenseicraft.common.commands.CommandSetClan;
import com.yshmeel.tenseicraft.data.ModInfo;
import com.yshmeel.tenseicraft.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tensei.MODID, name = Tensei.NAME, version = Tensei.VERSION)
public class Tensei
{
    public static final String MODID = "tenseicraft";
    public static final String NAME = "TenseiCraft";
    public static final String VERSION = "1.0";


    @SidedProxy(clientSide = "com.yshmeel.tenseicraft.proxy.ClientProxy", serverSide = "com.yshmeel.tenseicraft.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static Logger logger;
    public static ModInfo modInfo = new ModInfo();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
        logger = event.getModLog();

        if(modInfo.IS_DEV) {
            logger.warn("[Tensei] This is a development build for debugging, additional output will be written here");
        }

        logger.info("[Tensei] Written for https://vk.com/tenseicraft by Yshmeel");

        this.modInfo.createClans();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        if(ModInfo.IS_SERVER) {
            event.registerServerCommand(new CommandSetClan());
        }
    }
}
