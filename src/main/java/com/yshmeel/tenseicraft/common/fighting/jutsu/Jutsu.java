package com.yshmeel.tenseicraft.common.fighting.jutsu;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.client.Sounds;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

public class Jutsu implements IJutsu {
    protected String id = null;
    protected String name = null;
    protected String description = null;
    protected int type = 0;
    protected ResourceLocation icon = null;
    protected double chakraTake = 0;
    protected int points = 1;

    @Override
    public void init() {

    }

    @Override
    public boolean throwJutsu(EntityPlayer fromPlayer, EntityPlayer toPlayer, World world) {
        return false;
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
    public String getDescription() {
        return this.description;
    }

    @Override
    public int getType() {
        return this.type;
    }

    @Override
    public ResourceLocation getIcon() {
        return this.icon;
    }

    @Override
    public double getChakraTake() {
        return this.chakraTake;
    }

    @Override
    public int getPointsLearn() {
        return this.points;
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
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void setIcon(ResourceLocation icon) {
        this.icon = icon;
    }

    @Override
    public void setChakraTake(double value) {
        this.chakraTake = value;
    }

    @Override
    public void setPointsLearn(int value) {
        this.points = value;
    }

    @Override
    public void registerEntities(RegistryEvent.Register<EntityEntry> event) {

    }

    @Override
    public void afterUseJutsu(EntityPlayer player, World world) {
        IPlayer data = Player.getInstance(player);
        BlockPos position = player.getPosition();

        data.consumeChakra(this.getChakraTake());

        SoundEvent sound = new SoundEvent(Sounds.JUTSU_SOUND).setRegistryName("jutsu_sound");

        world.playSound(null, position, sound, SoundCategory.MASTER, 1.0F, 1.0F);
    }
}
