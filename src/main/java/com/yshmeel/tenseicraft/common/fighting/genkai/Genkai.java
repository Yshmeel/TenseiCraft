package com.yshmeel.tenseicraft.common.fighting.genkai;

import com.yshmeel.tenseicraft.common.fighting.jutsu.IJutsu;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class Genkai implements IGenkai {
    protected String id = null;
    protected String name = null;
    protected HashMap<String, IJutsu> jutsu = new HashMap<>();
    protected ResourceLocation icon = null;
    protected ResourceLocation[] eyes = null;
    protected HashMap<String, Object> defaultValues = new HashMap<>();


    @Override
    public void init() {
        this.setDefaultValues();
    }

    @Override
    public void setDefaultValues() {

    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public HashMap<String, IJutsu> getJutsu() {
        return this.jutsu;
    }

    @Override
    public ResourceLocation getIcon() {
        return this.icon;
    }

    @Override
    public ResourceLocation[] getEyes() {
        return this.eyes;
    }

    @Override
    public HashMap<String, Object> getDefaultValues() {
        return this.defaultValues;
    }

    @Override
    public void setId(String value) {
        this.id = value;
    }

    @Override
    public void setName(String value) {
        this.name = value;
    }

    @Override
    public void setJutsu(HashMap<String, IJutsu> value) {
        this.jutsu = value;
    }

    @Override
    public void setIcon(ResourceLocation value) {
        this.icon = value;
    }

    @Override
    public void setEyes(ResourceLocation[] value) {
        this.eyes = value;
    }

    @Override
    public void setDefaultValues(HashMap<String, Object> value) {
        this.defaultValues = value;
    }
}
