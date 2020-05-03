package com.yshmeel.tenseicraft.data.quests;

import com.yshmeel.tenseicraft.common.quests.base.Quest;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class HelloWorldQuest extends Quest {
    @Override
    public void init() {
        this.setId("hello_world");
        this.setName("tenseicraft.quests.hello_world");
        this.setDescription("tenseicraft.quests.hello_world_description");

        this.addCriteria("level", 1);
    }

    @Override
    public NBTTagCompound getQuestObject(IPlayer data) {
        NBTTagCompound object = new NBTTagCompound();

        if(data.getQuestId().equals(this.getId())) {
            boolean hasQuestObject = data.hasQuest();
            if(!hasQuestObject) {
                object.setInteger("step", 1);
            }
        }

        return object;













    }

    @Override
    public void process(EntityPlayer player) {

    }

    @Override
    public void validateCriteria(EntityPlayer player) {

    }
}
