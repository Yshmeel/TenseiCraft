package com.yshmeel.tenseicraft.data.player;

import com.yshmeel.tenseicraft.common.fighting.jutsutype.IJutsuType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

public interface IPlayer {
    String lastName = "";
    int rank = 0;
    double chakraAmount = 25;
    int level = 1;
    int ninjutsu = 1;
    int taijutsu = 1;
    int speed = 1;
    int genjutsu = 1;
    double exp = 0;
    boolean chakraControlEnabled = true;
    boolean chakraFillEnabled = false;
    boolean registered = false;

    NBTTagCompound clanGen = new NBTTagCompound();
    NBTTagCompound jutsuTypes = new NBTTagCompound();
    NBTTagCompound jutsuLearned = new NBTTagCompound();
    NBTTagCompound jutsuSlots = new NBTTagCompound();
    int jutsuPoints = 0;
    int skillPoints = 0;
    String clanGenName = "";

    EntityPlayer playerInstance = null;
    boolean dataFilled = false;

    /* Chakra System */
    public void consumeChakra(double chakraAmount);
    public void addChakra(double chakraAmount);
    public void setChakra(double chakraAmount);

    public boolean hasChakra(double chakraAmount);
    public double getChakra();
    public double getMaxChakra();

    public boolean isChakraControlEnabled();
    public void setChakraControlEnabled(boolean state);

    public boolean isChakraFillModeEnabled();
    public void setChakraFillModeEnabled(boolean state);
    /* Chakra System Ends */

    /* Basic system */
    public void setRank(int rank);
    public int getRank();
    public String getRankName();

    public void setRegistered(boolean value);
    public boolean isRegistered();

    public void setLastName(String value);
    public String getLastName();

    public void setLevel(int level);
    public int getLevel();

    public void setEXP(double exp);
    public void addEXP(double exp);
    public double getEXP();
    public double getMaxEXP();

    public void addNinjutsu(int value);
    public void setNinjutsu(int value);
    public int getNinjutsu();
    public void addTaijutsu(int value);
    public void setTaijutsu(int value);
    public int getTaijutsu();
    public void addSpeed(int value);
    public void setSpeed(int value);
    public int getSpeed();
    public void addGenjutsu(int value);
    public void setGenjutsu(int value);
    public int getGenjutsu();

    public boolean isDataFilled();

    public void setPlayerInstance(EntityPlayer player);
    public void onUpdate();
    public void onUpdateEXP();
    /* Basic system ends */

    /* Fighting system */

    public NBTTagCompound getClanGen();
    public void setClanGen(String clanGen);
    public boolean updateClanGenInfo(NBTTagCompound clanGenInfo);

    public void addJutsuType(String jutsuType);
    public void removeJutsuType(String jutsuType);
    public boolean hasJutsuType(String jutsuType);
    public ArrayList<String> putJutsuTypesToArrayList();

    public void addJutsu(String jutsuId);
    public void removeJutsu(String jutsuId);
    public boolean isJutsuLearned(String jutsuId);
    public ArrayList<String> getLearnedJutsu();

    public void setJutsuSlots(ArrayList<String> slots);
    public ArrayList<String> getJutsuSlots();
    public void appendJutsuToSlot(String jutsuId, int slotId);
    public boolean isJutsuInHotbar(String jutsuId);

    /* Fighting system ends */

    /* Learning system */

    public void setJutsuPoints(int jutsuPoints);
    public void addJutsuPoints(int jutsuPoints);
    public int getJutsuPoints();

    public void setSkillPoints(int value);
    public void addSkillPoints(int value);
    public int getSkillPoints();

    /* Learning system ends */

    /* Main Methods */
    public void set(NBTTagCompound tag);
    public NBTTagCompound createCompoundFromData();
    public void setSyncEnabled(boolean value);
    public void syncServerToClient();
    public void syncClientToServer();
    /* Main Methods End */
}
