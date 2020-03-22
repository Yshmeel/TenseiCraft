package com.yshmeel.tenseicraft.common.fighting.genkai;

import com.yshmeel.tenseicraft.common.fighting.jutsu.IJutsu;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public interface IGenkai {
    String id = null;
    String name = null;
    HashMap<String, IJutsu> jutsu = new HashMap<>();
    ResourceLocation icon = null;
    ResourceLocation[] eyes = null;
    HashMap<String, Object> defaultValues = new HashMap<>();

    public void init();
    public void setDefaultValues();

    public String getId();
    public String getName();
    public HashMap<String, IJutsu> getJutsu();
    public ResourceLocation getIcon();
    public ResourceLocation[] getEyes();
    public HashMap<String, Object> getDefaultValues();

    public void setId(String value);
    public void setName(String value);
    public void setJutsu(HashMap<String, IJutsu> value);
    public void setIcon(ResourceLocation value);
    public void setEyes(ResourceLocation[] value);
    public void setDefaultValues(HashMap<String, Object> value);
}
