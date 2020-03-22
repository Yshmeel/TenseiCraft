package com.yshmeel.tenseicraft.data;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.common.entities.GeninMob;
import com.yshmeel.tenseicraft.common.entities.models.DefaultModel;
import com.yshmeel.tenseicraft.common.entities.renders.DefaultRender;
import com.yshmeel.tenseicraft.common.fighting.genkai.IGenkai;
import com.yshmeel.tenseicraft.common.fighting.genkai.SharinganGenkai;
import com.yshmeel.tenseicraft.common.fighting.jutsu.IJutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.Jutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.earth.EarthCloneJutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.entities.clones.RenderClone;
import com.yshmeel.tenseicraft.common.fighting.jutsutype.EarthJutsuType;
import com.yshmeel.tenseicraft.common.fighting.jutsutype.IJutsuType;
import com.yshmeel.tenseicraft.common.fighting.jutsutype.WaterJutsuType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashMap;

@Mod.EventBusSubscriber(modid = Tensei.MODID)
public class ModInfo {
    public static boolean IS_DEV = true;
    public static boolean IS_SERVER = true;
    public static HashMap<String, IJutsuType> jutsuTypes = new HashMap<>();
    public static HashMap<String, IJutsu> jutsu = new HashMap<>();
    public static HashMap<String, IGenkai> genkai = new HashMap<>();
    public static int entityIds = 0;

    public static void initJutsuTypes() {
        jutsuTypes.put("water", new WaterJutsuType());
        jutsuTypes.put("earth", new EarthJutsuType());

        for (HashMap.Entry<String, IJutsuType> keyValue : jutsuTypes.entrySet()) {
            keyValue.getValue().init();

            Tensei.logger.info(String.format("[Tensei] JutsuType %s was registered", keyValue.getValue().getName()));
        }
    }

    public static ArrayList<String> getAllJutsuTypesKey() {
        ArrayList<String> jutsuTypesKey = new ArrayList<>();

        for (HashMap.Entry<String, IJutsuType> keyValue : jutsuTypes.entrySet()) {
            jutsuTypesKey.add(keyValue.getValue().getId());
        }

        return jutsuTypesKey;
    }

    public static IJutsuType getJutsuType(String release) {
        if(jutsuTypes.get(release) == null) {
            return null;
        } else {
            return jutsuTypes.get(release);
        }
    }

    public static void initJutsu() {
        for (HashMap.Entry<String, IJutsuType> keyValue : jutsuTypes.entrySet()) {
            for(HashMap.Entry<String, Jutsu> jutsuObject : keyValue.getValue().getJutsu().entrySet()) {
                jutsuObject.getValue().init();
                jutsu.put(jutsuObject.getValue().getId(), jutsuObject.getValue());

                Tensei.logger.info(String.format("[Tensei] Jutsu %s was registered", keyValue.getValue().getId()));
            }
        }
    }

    public static IJutsu getJutsu(String jutsuId) {
        if(jutsu.get(jutsuId) == null) {
            return null;
        } else {
            return jutsu.get(jutsuId);
        }
    }

    public static void initGenkai() {
        genkai.put("sharingan", new SharinganGenkai());

        for (HashMap.Entry<String, IGenkai> keyValue : genkai.entrySet()) {
            keyValue.getValue().init();

            Tensei.logger.info(String.format("[Tensei] Genkai %s was registered", keyValue.getValue().getId()));
        }
    }

    public static IGenkai getGenkai(String genkaiId) {
        if(genkai.get(genkaiId) == null) {
            return null;
        } else {
            return genkai.get(genkaiId);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event) {

    }

    @SubscribeEvent
    public static void register(RegistryEvent.Register<EntityEntry> event) {
        ModInfo.initJutsuTypes();
        ModInfo.initJutsu();
        ModInfo.initGenkai();

        event.getRegistry().register(
            EntityEntryBuilder.<Entity>create()
                    .entity(GeninMob.class)
                    .id(new ResourceLocation("tenseicraft:null"), entityIds++)
                    .name("GeninMob")
                    .tracker(64, 3, true)
                    .build()
        );

        EntitySpawnPlacementRegistry.setPlacementType(GeninMob.class, EntityLiving.SpawnPlacementType.ON_GROUND);

        HashMap<String, IJutsu> jutsus = ModInfo.jutsu;

        for(HashMap.Entry<String, IJutsu> jutsu : jutsus.entrySet()) {
            jutsu.getValue().registerEntities(event);
        }
    }
}
