package com.yshmeel.tenseicraft.common.fighting.jutsutype;

import com.yshmeel.tenseicraft.common.fighting.jutsu.IJutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.Jutsu;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public interface IJutsuType {
    public void init();

    public void setName(String name);
    public void setIcon(ResourceLocation icon);
    public void setId(String id);
    public void setJutsu(HashMap<String, Jutsu> jutsu);

    public String getId();
    public String getName();
    public ResourceLocation getIcon();
    public HashMap<String, Jutsu> getJutsu();

    public IJutsu getNextToLearn(IPlayer player);
}
