package com.yshmeel.tenseicraft.common.quests.base;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;

public interface IQuest {
    String id = null;
    String name = null;
    String description = null;
    HashMap<String, Object> criteria = new HashMap<>();

    public void init();
    public NBTTagCompound getQuestObject(IPlayer data);
    public void process(EntityPlayer player);
    public void validateCriteria(EntityPlayer player);

    public void setId(String id);
    public String getId();
    public void setName(String name);
    public String getName();
    public void setDescription(String value);
    public String getDescription();
    public void addCriteria(String key, Object value);
    public Object getCriteriaByKey(String key);
}
