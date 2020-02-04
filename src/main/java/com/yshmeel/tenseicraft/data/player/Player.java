package com.yshmeel.tenseicraft.data.player;

import com.yshmeel.tenseicraft.data.ModInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class Player implements IPlayer {
    private String[] ranksArray = {
        "Villager"
    };

    private int chakraAmount = 25;
    private int rank = 0;
    private int clan = 0;

    @Override
    public void consumeChakra(int chakraAmount) {
        this.chakraAmount -= chakraAmount;

        if(this.chakraAmount < 0) this.chakraAmount = 0;
    }

    @Override
    public void addChakra(int chakraAmount) {
        this.chakraAmount += chakraAmount;
    }

    @Override
    public void setChakra(int chakraAmount) {
        this.chakraAmount = chakraAmount;
    }

    @Override
    public int getChakra() {
        return this.chakraAmount;
    }

    @Override
    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public int getRank() {
        return this.rank;
    }

    @Override
    public String getRankName() {
        return this.ranksArray[this.rank];
    }

    @Override
    public void setClan(int clan) {
        this.clan = clan;
    }

    @Override
    public int getClan() {
        return this.clan;
    }

    @Override
    public String getClanName() {
        return ModInfo.getClan(this.clan).getClanName();
    }

    @Override
    public void set(NBTTagCompound tag) {
        this.chakraAmount = tag.getInteger("chakraAmount");
        this.rank = tag.getInteger("rank");
        this.clan = tag.getInteger("clan");
    }

    @Override
    public void copy() {

    }

    @Override
    public NBTTagCompound createCompoundFromData() {
        NBTTagCompound compound = new NBTTagCompound();

        compound.setInteger("chakraAmount", this.chakraAmount);
        compound.setInteger("clan", this.clan);
        compound.setInteger("rank", this.rank);

        return compound;
    }

    public static IPlayer getInstance(EntityPlayer player) {
        return player.getCapability(PlayerProvider.PLAYER_CAP, null);
    }
}
