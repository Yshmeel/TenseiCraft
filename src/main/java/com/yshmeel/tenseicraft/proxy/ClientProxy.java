package com.yshmeel.tenseicraft.proxy;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.client.Keys;
import com.yshmeel.tenseicraft.client.events.ClientHandler;
import com.yshmeel.tenseicraft.common.entities.GeninMob;
import com.yshmeel.tenseicraft.common.entities.models.DefaultModel;
import com.yshmeel.tenseicraft.common.entities.renders.DefaultRender;
import com.yshmeel.tenseicraft.common.fighting.jutsu.earth.EarthCloneJutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.entities.clones.RenderClone;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.Random;

public class ClientProxy extends CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }
    public void init(FMLInitializationEvent event) {
        Keys.init();
        MinecraftForge.EVENT_BUS.register(new ClientHandler());

        RenderingRegistry.registerEntityRenderingHandler(GeninMob.class, new DefaultRender(
                new DefaultModel(), 1.0F, GeninMob.MOB_TEXTURE
        ));

        RenderingRegistry.registerEntityRenderingHandler(EarthCloneJutsu.EarthCloneEntity.class, new RenderClone(
                new ModelPlayer(0.0F, false), 1.0F
        ));

        super.init(event);
    }
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

    }

    @Override
    public EntityPlayer getPlayerEntity(MessageContext ctx) {
        return ctx.side.isClient() ? Minecraft.getMinecraft().player : super.getPlayerEntity(ctx);
    }
}
