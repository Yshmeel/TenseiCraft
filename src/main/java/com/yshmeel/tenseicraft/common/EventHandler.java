package com.yshmeel.tenseicraft.common;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.common.entities.EntityKunai;
import com.yshmeel.tenseicraft.common.entities.GeninMob;
import com.yshmeel.tenseicraft.common.fighting.jutsu.entities.clones.EntityClone;
import com.yshmeel.tenseicraft.common.packets.PacketDispatcher;
import com.yshmeel.tenseicraft.common.packets.PacketLevelUpMessage;
import com.yshmeel.tenseicraft.common.packets.PacketSyncPlayerDataMessage;
import com.yshmeel.tenseicraft.data.ModInfo;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import com.yshmeel.tenseicraft.data.player.PlayerProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventHandler {
    @SubscribeEvent
    public void onPlayerLogsIn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event)
    {
        if(!event.player.getEntityWorld().isRemote) {
            EntityPlayer player = event.player;
            IPlayer playerData = Player.getInstance(player);
            PacketDispatcher.sendTo(new PacketSyncPlayerDataMessage(playerData.createCompoundFromData(), player), (EntityPlayerMP) player);
        }
    }

    @SubscribeEvent
    public void onMobSpawn(LivingSpawnEvent event) {
        if(!event.getEntity().getEntityWorld().isRemote) {
            Entity entity = event.getEntity();
            if (entity instanceof EntityZombie || entity instanceof EntityCreeper ||
                    entity instanceof EntitySkeleton || entity instanceof EntityEnderman ||
                    entity instanceof EntityGhast || entity instanceof EntityPigZombie
                    || entity instanceof EntityBlaze || entity instanceof EntityWitch || entity instanceof EntitySlime) {
                event.setResult(Event.Result.DENY);
                entity.setDead();
            } else {
                event.setResult(Event.Result.ALLOW);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event)
    {
        if(!event.getEntity().getEntityWorld().isRemote) {
            EntityPlayer player = event.getEntityPlayer();
            IPlayer playerCap = Player.getInstance(player);
            IPlayer oldPlayerCap = Player.getInstance(event.getOriginal());

            playerCap.set(oldPlayerCap.createCompoundFromData());
        }
    }

    @SubscribeEvent
    public void onPlayerRespawn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent event)
    {
        if(!event.player.getEntityWorld().isRemote) {
            EntityPlayer player = event.player;
            IPlayer playerCap = player.getCapability(PlayerProvider.PLAYER_CAP, null);

            playerCap.syncServerToClient();
        }
    }

    @SubscribeEvent
    public void onPlayerJump(LivingEvent.LivingJumpEvent event) {
        if(event.getEntity() instanceof EntityPlayer)  {
            IPlayer player = Player.getInstance((EntityPlayer) event.getEntity());

            if(player.hasChakra(2) && player.isChakraControlEnabled() && !event.getEntity().isSneaking()) {
                event.getEntity().motionY += 0.2F;
                player.consumeChakra(0.4F);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerFall(LivingFallEvent event) {
        if(event.getEntity() instanceof EntityPlayer) {
            IPlayer player = Player.getInstance((EntityPlayer) event.getEntity());

            if(player.isChakraControlEnabled()) {
                event.setCanceled(true);
            }
        }
        if(event.getEntity() instanceof EntityClone) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerServerUpdate(LivingEvent.LivingUpdateEvent event) {
        boolean isRemote = event.getEntity().getEntityWorld().isRemote;
        if(FMLCommonHandler.instance().getMinecraftServerInstance() instanceof IntegratedServer) {
            isRemote = false;
        }
        if(!isRemote) {
            if(event.getEntity() instanceof EntityPlayer) {
                IPlayer player = Player.getInstance((EntityPlayer) event.getEntity());
                EntityPlayer entity = (EntityPlayer) event.getEntity();

                player.onUpdate();

            }
        }
    }

    // @fixme переделать выдачу опыта
    @SubscribeEvent
    public void onPlayerKillMob(LivingDeathEvent event) {
        if(!event.getEntity().world.isRemote) {
            DamageSource source = event.getSource();
            Entity killer = source.getTrueSource();
            if(killer instanceof EntityPlayer) {
                IPlayer killerData = Player.getInstance((EntityPlayer) killer);
                float givenExp = 0.0F;
                if(event.getEntity() instanceof EntityPlayer) {
                    givenExp += 1.0F;
                } else {
                    if(event.getEntity() instanceof GeninMob) {
                        givenExp += 0.8F;
                    } else if(event.getEntity() instanceof EntitySpider) {
                        givenExp += 0.5;
                    } else if(event.getEntity() instanceof EntityClone) {
                        givenExp += 0.0F;
                    } else {
                        givenExp += 0.1F;
                    }
                }

                killerData.addEXP(givenExp);
            }
        }
    }
}
