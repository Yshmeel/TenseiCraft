package com.yshmeel.tenseicraft.client;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;

@Mod.EventBusSubscriber
public class Sounds {
    public static ResourceLocation LEVEL_UP_SOUND =
            new ResourceLocation("tenseicraft", "level_up");
    public static ResourceLocation NINJA_CARD_OPEN_SOUND =
            new ResourceLocation("tenseicraft", "ninja_card_open");
    public static ResourceLocation JUTSU_SOUND =
            new ResourceLocation("tenseicraft", "jutsu_sound");
    public static ResourceLocation HMM_CUTSCENE =
            new ResourceLocation("tenseicraft", "hmm_cutscene");
    public static ResourceLocation TELEPORT =
            new ResourceLocation("tenseicraft", "teleport_sound");
    public static HashMap<String, SoundEvent> registeredSounds = new HashMap<>();

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event){
        final SoundEvent[] soundEvents = {
            new SoundEvent(LEVEL_UP_SOUND).setRegistryName("level_up"),
            new SoundEvent(NINJA_CARD_OPEN_SOUND).setRegistryName("ninja_card_open"),
            new SoundEvent(JUTSU_SOUND).setRegistryName("jutsu_sound"),
            new SoundEvent(HMM_CUTSCENE).setRegistryName("hmm_cutscene"),
            new SoundEvent(TELEPORT).setRegistryName("teleport_sound")
        };

        for(SoundEvent sound : soundEvents) {
            registeredSounds.put(sound.getRegistryName().toString().replace("tenseicraft:", ""), sound);
        }

        event.getRegistry().registerAll(soundEvents);
    }

    public static SoundEvent valueOf(String name) {
        return registeredSounds.get(name);
    }
}
