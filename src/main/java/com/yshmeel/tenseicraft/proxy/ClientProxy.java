package com.yshmeel.tenseicraft.proxy;

import com.yshmeel.tenseicraft.client.Keys;
import com.yshmeel.tenseicraft.client.events.ClientHandler;
import com.yshmeel.tenseicraft.common.entities.EntityWood;
import com.yshmeel.tenseicraft.common.entities.GeninMob;
import com.yshmeel.tenseicraft.common.entities.NPCMob;
import com.yshmeel.tenseicraft.common.entities.models.DefaultModel;
import com.yshmeel.tenseicraft.common.entities.models.RunningModel;
import com.yshmeel.tenseicraft.common.entities.renders.DefaultRender;
import com.yshmeel.tenseicraft.common.entities.renders.WoodRender;
import com.yshmeel.tenseicraft.common.fighting.jutsu.earth.EarthCloneJutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.entities.EntityBall;
import com.yshmeel.tenseicraft.common.fighting.jutsu.entities.clones.RenderClone;
import com.yshmeel.tenseicraft.common.fighting.jutsu.entities.renders.RenderBallEntity;
import com.yshmeel.tenseicraft.common.registries.BlockRegistry;
import com.yshmeel.tenseicraft.common.registries.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import scala.collection.parallel.ParIterableLike;

public class ClientProxy extends CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }
    public void init(FMLInitializationEvent event) {
        Keys.init();
        MinecraftForge.EVENT_BUS.register(new ClientHandler());
        RenderingRegistry.registerEntityRenderingHandler(EntityWood.class, new WoodRender());
        RenderingRegistry.registerEntityRenderingHandler(EntityBall.class, new RenderBallEntity(
                Minecraft.getMinecraft().getRenderManager(), 1.0F
        ));
        RenderingRegistry.registerEntityRenderingHandler(GeninMob.class, new DefaultRender(
                new RunningModel(), 1.0F, GeninMob.MOB_TEXTURE
        ));
        RenderingRegistry.registerEntityRenderingHandler(NPCMob.class, new DefaultRender(
                new DefaultModel(0.0F, false), 1.0F, NPCMob.MOB_TEXTURE
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
