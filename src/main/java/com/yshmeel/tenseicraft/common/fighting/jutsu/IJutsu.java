package com.yshmeel.tenseicraft.common.fighting.jutsu;

import com.yshmeel.tenseicraft.data.player.IPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

import javax.annotation.Nullable;

public interface IJutsu {

    public void init();
    public boolean throwJutsu(EntityPlayer fromPlayer,
                              @Nullable EntityPlayer toPlayer, @Nullable World world);

    public String getId();
    public String getName();
    public String getDescription();
    public int getType();
    public ResourceLocation getIcon();
    public double getChakraTake();
    public int getPointsLearn();

    public void setId(String id);
    public void setName(String name);
    public void setDescription(String description);
    public void setType(int type);
    public void setIcon(ResourceLocation icon);
    public void setChakraTake(double value);
    public void setPointsLearn(int value);

    // events
    public void registerEntities(RegistryEvent.Register<EntityEntry> event);
    public void afterUseJutsu(EntityPlayer player, World world);
}
