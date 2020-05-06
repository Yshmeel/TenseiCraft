package com.yshmeel.tenseicraft;

import com.yshmeel.tenseicraft.client.gui.fonts.DrawFonts;
import com.yshmeel.tenseicraft.common.commands.*;
import com.yshmeel.tenseicraft.common.fighting.jutsu.IJutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.Jutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsutype.IJutsuType;
import com.yshmeel.tenseicraft.data.ModInfo;
import com.yshmeel.tenseicraft.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.GameRules;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

@Mod(modid = Tensei.MODID, name = Tensei.NAME, version = Tensei.VERSION)
public class Tensei
{
    public static final String MODID = "tenseicraft";
    public static final String NAME = "TenseiCraft";
    public static final String VERSION = "1.0";

    public static final CreativeTabs WEAPON_TAB = new CreativeTabs("weapon") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(Items.DIAMOND_AXE);
        }
    };
    @SidedProxy(clientSide = "com.yshmeel.tenseicraft.proxy.ClientProxy", serverSide = "com.yshmeel.tenseicraft.proxy.CommonProxy")
    public static CommonProxy proxy;
    @SideOnly(Side.CLIENT)
    public static DrawFonts fonts;

    public static Logger logger;
    public static ModInfo modInfo = new ModInfo();

    @Mod.Instance
    public static Tensei instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);

        logger = event.getModLog();

        if(FMLCommonHandler.instance().getSide().isClient()) {
            fonts = new DrawFonts();
        }

        if(modInfo.IS_DEV) {
            logger.warn("[Tensei] This is a development build for debugging, additional output will be written here");
        }

        logger.info("[Tensei] Written for https://vk.com/tenseicraft by Yshmeel");
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
        if(FMLCommonHandler.instance().getSide().equals(Side.CLIENT)) {
            fonts.registerFonts();
        }
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        if(ModInfo.IS_SERVER) {
            event.registerServerCommand(new CommandSetLevel());
            event.registerServerCommand(new CommandSetExp());
            event.registerServerCommand(new CommandTest());
            event.registerServerCommand(new CommandAddJutsuType());
            event.registerServerCommand(new CommandAddLearnedJutsu());
            event.registerServerCommand(new CommandAddQuest());

            event.getServer().getEntityWorld().getGameRules().setOrCreateGameRule("keepInventory", "true");
            event.getServer().getEntityWorld().getGameRules().setOrCreateGameRule("showDeathMessages", "false");
        }
    }


}
