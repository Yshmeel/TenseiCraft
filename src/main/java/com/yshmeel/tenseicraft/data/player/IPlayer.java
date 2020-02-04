package com.yshmeel.tenseicraft.data.player;

import net.minecraft.nbt.NBTTagCompound;

public interface IPlayer {
    int rank = 0;
    int clan = 1;
    int chakraAmount = 25;

    /* Chakra System */
    public void consumeChakra(int chakraAmount);
    public void addChakra(int chakraAmount);
    public void setChakra(int chakraAmount);

    public int getChakra();
    /* Chakra System Ends */

    /* Basic system */
    public void setRank(int rank);
    public int getRank();
    public String getRankName();

    public void setClan(int clan);
    public int getClan();
    public String getClanName();
    /* Basic system ends */

    /* Main Methods */
    public void set(NBTTagCompound tag);
    public void copy();
    public NBTTagCompound createCompoundFromData();
    /* Main Methods End */
}
