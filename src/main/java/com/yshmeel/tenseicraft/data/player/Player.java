package com.yshmeel.tenseicraft.data.player;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.common.fighting.jutsu.IJutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsutype.IJutsuType;
import com.yshmeel.tenseicraft.common.packets.PacketDispatcher;
import com.yshmeel.tenseicraft.common.packets.PacketLevelUpMessage;
import com.yshmeel.tenseicraft.common.packets.PacketShowCutSceneMessage;
import com.yshmeel.tenseicraft.common.packets.PacketSyncPlayerDataMessage;
import com.yshmeel.tenseicraft.common.quests.base.IQuest;
import com.yshmeel.tenseicraft.data.GensEnum;
import com.yshmeel.tenseicraft.data.ModInfo;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class Player implements IPlayer {
    private String[] ranksArray = {
        "Villager"
    };

    private EntityPlayer playerInstance = null;
    private boolean dataFilled = false;
    private boolean syncEnabled = true;
    private String lastName = "";
    private double chakraAmount = 25;
    private int rank = 0;
    private int level = 1;
    private int ninjutsu = 1;
    private int taijutsu = 1;
    private int speed = 1;
    private int genjutsu = 1;
    private double exp = 0;
    private boolean chakraControlEnabled = true;
    private boolean chakraFillEnabled = false;
    private boolean registered = false;

    private String clanGenName = "";
    private NBTTagCompound clanGen = new NBTTagCompound();
    private NBTTagCompound jutsuTypes = new NBTTagCompound();
    private NBTTagCompound jutsuLearned = new NBTTagCompound();
    private NBTTagCompound jutsuSlots = new NBTTagCompound();
    private int jutsuPoints = 0;
    private int skillPoints = 0;
    private NBTTagCompound quest = new NBTTagCompound();
    private String questId = "";

    /* Timers */
    private int chakraFillTimer = 0; // fills to 30, then reset

    @Override
    public void consumeChakra(double chakraAmount) {
        this.chakraAmount -= chakraAmount;
    }

    @Override
    public void addChakra(double chakraAmount) {
        this.chakraAmount += chakraAmount;

        if(this.chakraAmount > this.getMaxChakra()) {
            this.chakraAmount = this.getMaxChakra();
        } else if(this.chakraAmount < 0) {
            this.chakraAmount = 0;
        }

        this.syncServerToClient();
    }

    @Override
    public void setChakra(double chakraAmount) {
        this.chakraAmount = chakraAmount;

        if(this.chakraAmount > this.getMaxChakra()) {
            this.chakraAmount = this.getMaxChakra();
        } else if(this.chakraAmount < 0) {
            this.chakraAmount = 0;
        }

        this.syncServerToClient();
    }

    @Override
    public boolean hasChakra(double chakraAmount) {
        return this.chakraAmount >= chakraAmount;
    }

    @Override
    public double getChakra() {
        return this.chakraAmount;
    }

    @Override
    public double getMaxChakra() {
        double chakra = this.getNinjutsu()*25;

        return chakra;
    }

    @Override
    public boolean isChakraControlEnabled() {
        return this.chakraControlEnabled;
    }

    @Override
    public void setChakraControlEnabled(boolean state) {
        this.chakraControlEnabled = state;
        this.syncServerToClient();
    }

    @Override
    public boolean isChakraFillModeEnabled() {
        return this.chakraFillEnabled;
    }

    @Override
    public void setChakraFillModeEnabled(boolean state) {
        this.chakraFillEnabled = state;
        this.syncServerToClient();
    }

    @Override
    public void setRank(int rank) {
        this.rank = rank;
        this.syncServerToClient();
    }

    @Override
    public int getRank() {
        return this.rank;
    }

    @Override
    public String getRankName() {
        if(FMLCommonHandler.instance().getSide().isClient()) {
            return I18n.format("common.player.ranks." + this.ranksArray[this.rank].toLowerCase());
        }

        return this.ranksArray[this.rank];
    }

    @Override
    public void setRegistered(boolean value) {
        this.registered = value;
        this.syncServerToClient();
    }

    @Override
    public boolean isRegistered() {
        return this.registered;
    }

    @Override
    public void setLastName(String value) {
        this.lastName = value;

        this.syncServerToClient();
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
        this.syncServerToClient();
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void setEXP(double exp) {
        this.exp = exp;
        this.syncServerToClient();
    }

    @Override
    public void addEXP(double exp) {
        this.exp += exp;
        this.syncServerToClient();
    }

    @Override
    public double getEXP() {
        return this.exp;
    }

    @Override
    public double getMaxEXP() {
        return 1.2 + Double.valueOf(this.level / 1.4);
    }

    @Override
    public void addNinjutsu(int value) {
        this.ninjutsu += value;
        this.syncServerToClient();
    }

    @Override
    public void setNinjutsu(int value) {
        this.ninjutsu = value;
        this.syncServerToClient();
    }

    @Override
    public int getNinjutsu() {
        return this.ninjutsu;
    }

    @Override
    public void addTaijutsu(int value) {
        this.taijutsu += value;
        this.syncServerToClient();
    }

    @Override
    public void setTaijutsu(int value) {
        this.taijutsu = value;
        this.syncServerToClient();
    }

    @Override
    public int getTaijutsu() {
        return this.taijutsu;
    }

    @Override
    public void addSpeed(int value) {
        this.speed += value;
        this.syncServerToClient();
    }

    @Override
    public void setSpeed(int value) {
        this.speed = value;
        this.syncServerToClient();
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public void addGenjutsu(int value) {
        this.genjutsu += value;
        this.syncServerToClient();
    }

    @Override
    public void setGenjutsu(int value) {
        this.genjutsu = value;
        this.syncServerToClient();
    }

    @Override
    public int getGenjutsu() {
        return this.genjutsu;
    }

    @Override
    public boolean isDataFilled() {
        return this.dataFilled;
    }

    @Override
    public void setPlayerInstance(EntityPlayer player) {
        this.playerInstance = player;
    }

    @Override
    public void onUpdate() {
        if(this.chakraFillTimer >= 30) {
            if(!this.isChakraFillModeEnabled()) {
                this.addChakra(0.3D);
                this.chakraFillTimer = 0;
            }
        } else {
            this.chakraFillTimer ++;
        }

        if(this.isChakraFillModeEnabled()) {
            if(this.getChakra() == this.getMaxChakra()) {
                this.setChakraFillModeEnabled(false);
                this.playerInstance.sendMessage(
                        new TextComponentTranslation("common.tenseicraft.chakra_fill.disabled_force")
                );
            } else {
                this.addChakra(0.4D);
            }
        }

        if(this.getEXP() >= this.getMaxEXP()) {
            this.setEXP(0.0D);
            this.setLevel(this.getLevel()+1);
            this.onUpdateEXP();
            this.addJutsuPoints(new Random().nextInt(2) + 1);
            this.addSkillPoints(new Random().nextInt(2) + 1);

            this.playerInstance.sendMessage(
                    new TextComponentTranslation("common.tenseicraft.level_up",
                            this.getLevel()
                    )
            );

            PacketDispatcher.sendToClient(new PacketLevelUpMessage(this.playerInstance, this.getLevel()), this.playerInstance);
        }

        if(this.isChakraControlEnabled()) {
            if(!this.hasChakra(0.2)) {
                this.setChakraControlEnabled(false);
                this.playerInstance.sendMessage(
                        new TextComponentTranslation("common.tenseicraft.chakra_control.disabled_force")
                );
            } else {
                if (this.playerInstance.collided && this.playerInstance.isSneaking()) {
                    this.playerInstance.motionY += 0.1F;
                    // @todo отправлять на клиент анимацию лазанья
                }
            }
        }


        if(this.isChakraFillModeEnabled()) {
            if (this.playerInstance.posX != this.playerInstance.prevPosX ||
                    this.playerInstance.posY != this.playerInstance.prevPosY ||
                    this.playerInstance.posZ != this.playerInstance.prevPosZ) {
                this.playerInstance.setVelocity(0, 0, 0);
                this.playerInstance.setPositionAndUpdate(this.playerInstance.prevPosX, this.playerInstance.prevPosY, this.playerInstance.prevPosZ);
            }
        }
    }

    @Override
    public void onUpdateEXP() {
        Tensei.logger.info(this.putJutsuTypesToArrayList().size());
        if(this.getLevel() == 4 && this.putJutsuTypesToArrayList().size() == 0) {
            ArrayList<String> jutsuTypes = ModInfo.getAllJutsuTypesKey();

            int random = new Random().nextInt(jutsuTypes.size());

            this.addJutsuType(jutsuTypes.get(random));
            PacketDispatcher.sendToClient(new PacketShowCutSceneMessage("first_release"), this.playerInstance);
        }
    }

    @Override
    public NBTTagCompound getClanGen() {
        return this.clanGen;
    }

    @Override
    public void setClanGen(String clanGen) {
        NBTTagCompound compound = new NBTTagCompound();
        NBTTagCompound genInfo = new NBTTagCompound();
        GensEnum gen = GensEnum.valueOf(clanGen);

        genInfo.setFloat("timeUnlock", 0.0F);
        genInfo.setString("name", gen.getName());
        genInfo.setBoolean("isUnlocked", gen.getTimeUnlock() == 0.0F);

        compound.setTag(clanGen, genInfo);

        this.clanGen = compound;
        this.syncServerToClient();
    }

    @Override
    public boolean updateClanGenInfo(NBTTagCompound clanGenInfo) {
        if(this.clanGenName.equals(null)) {
            return false;
        }

        this.clanGen.setTag(this.clanGenName, clanGenInfo);
        this.syncServerToClient();
        return true;
    }

    @Override
    public void addJutsuType(String jutsuType) {
        this.jutsuTypes.setBoolean(jutsuType, true);
        this.syncServerToClient();
    }

    @Override
    public void removeJutsuType(String jutsuType) {
        this.jutsuTypes.setBoolean(jutsuType, false);
        this.syncServerToClient();
    }

    @Override
    public boolean hasJutsuType(String jutsuType) {
        return this.jutsuTypes.getBoolean(jutsuType);
    }

    @Override
    public ArrayList<String> putJutsuTypesToArrayList() {
        ArrayList<String> jutsuTypes = new ArrayList<>();

        for(String key : ModInfo.getAllJutsuTypesKey()) {
            if (this.hasJutsuType(key)) {
                jutsuTypes.add(key);
            }
        }

        return jutsuTypes;
    }

    @Override
    public void addJutsu(String jutsuId) {
        this.jutsuLearned.setBoolean(jutsuId, true);
        this.syncServerToClient();
    }

    @Override
    public void removeJutsu(String jutsuId) {
        this.jutsuLearned.setBoolean(jutsuId, false);
        this.syncServerToClient();
    }

    @Override
    public boolean isJutsuLearned(String jutsuId) {
        return this.jutsuLearned.getBoolean(jutsuId);
    }

    @Override
    public ArrayList<String> getLearnedJutsu() {
        ArrayList<String> learned = new ArrayList<>();

        for(String key : this.jutsuLearned.getKeySet()) {
            learned.add(key);
        }

        return learned;
    }

    @Override
    public void setJutsuSlots(ArrayList<String> slots) {
        NBTTagCompound compound = new NBTTagCompound();

        if(slots.size() != 0) {
            int currentSlot = 1;

            for(String slot: slots) {
                if(this.isJutsuLearned(slot)) {
                    compound.setString("slot " + currentSlot, slot);
                } else {
                    compound.setString("slot " + currentSlot, null);
                }

                currentSlot ++;
            }

            this.jutsuSlots = compound;
        }
    }

    @Override
    public ArrayList<String> getJutsuSlots() {
        ArrayList<String> slots = new ArrayList<>();

        for(int i = 1; i <= 6; i++) {
            slots.add(this.jutsuSlots.getString("slot " + i));
        }

        return slots;
    }

    @Override
    public void appendJutsuToSlot(String jutsuId, int slotId) {
        this.jutsuSlots.setString("slot " + slotId, (jutsuId == null ? "" : jutsuId));

        this.syncServerToClient();
    }

    @Override
    public boolean isJutsuInHotbar(String jutsuId) {
        ArrayList<String> jutsuSlots = this.getJutsuSlots();

        for(String id : jutsuSlots) {
            if(jutsuId.equals(id)) return true;
        }

        return false;
    }

    @Override
    public void setJutsuPoints(int jutsuPoints) {
        this.jutsuPoints = jutsuPoints;
        this.syncServerToClient();
    }

    @Override
    public void addJutsuPoints(int jutsuPoints) {
        this.jutsuPoints += jutsuPoints;
        this.syncServerToClient();
    }

    @Override
    public int getJutsuPoints() {
        return this.jutsuPoints;
    }

    @Override
    public void setSkillPoints(int value) {
        this.skillPoints = value;
    }

    @Override
    public void addSkillPoints(int value) {
        this.skillPoints += value;
    }

    @Override
    public int getSkillPoints() {
        return skillPoints;
    }

    @Override
    public NBTTagCompound getQuest() {
        return this.quest;
    }

    @Override
    public boolean assignQuest(IQuest quest) {
        this.questId = quest.getId();
        this.quest = quest.getQuestObject(this);
        this.syncServerToClient();
        return true;
    }

    @Override
    public boolean hasQuest() {
        return quest == null;
    }

    @Override
    public boolean resetQuest() {
        this.quest = null;
        this.questId = "";
        this.syncServerToClient();
        return true;
    }

    @Override
    public String getQuestId() {
        return this.questId;
    }

    @Override
    public boolean setQuestId(String value) {
        this.questId = value;
        return true;
    }

    @Override
    public void set(NBTTagCompound tag) {
        this.chakraAmount = tag.getDouble("chakraAmount");
        this.rank = tag.getInteger("rank");
        this.level = tag.getInteger("level");
        this.exp = tag.getDouble("exp");
        this.clanGen = tag.getCompoundTag("clanGen");
        this.clanGenName = tag.getString("clanGenName");
        this.jutsuPoints = tag.getInteger("jutsuPoints");
        this.jutsuLearned = tag.getCompoundTag("jutsuLearned");
        this.jutsuTypes = tag.getCompoundTag("jutsuTypes");
        this.chakraControlEnabled = tag.getBoolean("chakraControlEnabled");
        this.chakraFillEnabled = tag.getBoolean("chakraFillEnabled");
        this.ninjutsu = tag.getInteger("ninjutsu");
        this.taijutsu = tag.getInteger("taijutsu");
        this.speed = tag.getInteger("speed");
        this.genjutsu = tag.getInteger("genjutsu");
        this.skillPoints = tag.getInteger("skillPoints");
        this.jutsuSlots = tag.getCompoundTag("jutsuSlots");
        this.lastName = tag.getString("lastName");
        this.registered = tag.getBoolean("registered");
        this.quest = tag.getCompoundTag("quest");
        this.questId = tag.getString("questId");
        this.dataFilled = true;
    }

    @Override
    public NBTTagCompound createCompoundFromData() {
        NBTTagCompound compound = new NBTTagCompound();

        compound.setDouble("chakraAmount", this.chakraAmount);
        compound.setInteger("rank", this.rank);
        compound.setInteger("level", this.level);
        compound.setDouble("exp", this.exp);
        compound.setTag("clanGen", this.clanGen);
        compound.setString("clanGenName", this.clanGenName);
        compound.setInteger("jutsuPoints", this.jutsuPoints);
        compound.setTag("jutsuLearned", this.jutsuLearned);
        compound.setTag("jutsuTypes", this.jutsuTypes);
        compound.setTag("jutsuSlots", this.jutsuSlots);
        compound.setBoolean("chakraControlEnabled", this.chakraControlEnabled);
        compound.setBoolean("chakraFillEnabled", this.chakraFillEnabled);
        compound.setInteger("ninjutsu", this.ninjutsu);
        compound.setInteger("taijutsu", this.taijutsu);
        compound.setInteger("speed", this.speed);
        compound.setInteger("genjutsu", this.genjutsu);
        compound.setInteger("skillPoints", this.skillPoints);
        compound.setString("lastName", this.lastName);
        compound.setBoolean("registered", this.registered);
        compound.setTag("quest", this.quest);
        compound.setString("questId", this.questId);

        return compound;
    }

    @Override
    public void setSyncEnabled(boolean value) {
        this.syncEnabled = value;
    }

    @Override
    public void syncServerToClient() {
        if(this.syncEnabled) {
            if(this.playerInstance.getEntityWorld().isRemote) {
                this.syncClientToServer();
            } else {
                PacketDispatcher.sendTo(new PacketSyncPlayerDataMessage(this.createCompoundFromData(), playerInstance), (EntityPlayerMP) playerInstance);
            }
        }
    }

    @Override
    public void syncClientToServer() {
        PacketDispatcher.sendToServer(new PacketSyncPlayerDataMessage(this.createCompoundFromData(), playerInstance));
    }


    public static IPlayer getInstance(EntityPlayer player) {
        if(player == null) return null;

        IPlayer data = player.getCapability(PlayerProvider.PLAYER_CAP, null);

        data.setPlayerInstance(player);

        return data;
    }
}
