package com.yshmeel.tenseicraft.common.fighting.jutsutype;

import com.yshmeel.tenseicraft.common.fighting.jutsu.IJutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.Jutsu;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public abstract class JutsuTypeBase implements IJutsuType {
    String id = null;
    String name = null;
    ResourceLocation icon = null;
    HashMap<String, Jutsu> jutsu = null;

    @Override
    public String getId() {
        return this.id;
    }
    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public ResourceLocation getIcon() {
        return this.icon;
    }
    @Override
    public HashMap<String, Jutsu> getJutsu() {
        return this.jutsu;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public void setIcon(ResourceLocation icon) {
        this.icon = icon;
    }
    @Override
    public void setJutsu(HashMap<String, Jutsu> jutsu) {
        this.jutsu = jutsu;
    }

    @Override
    public IJutsu getNextToLearn(IPlayer player) {
        for(HashMap.Entry<String, Jutsu> entry : this.getJutsu().entrySet()) {
            if(player.isJutsuLearned(entry.getValue().getId())) {
                continue;
            } else {
                return entry.getValue();
            }
        }

        return null;
    }
}
