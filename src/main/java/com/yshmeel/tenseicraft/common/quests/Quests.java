package com.yshmeel.tenseicraft.common.quests;

import com.yshmeel.tenseicraft.common.quests.base.IQuest;
import com.yshmeel.tenseicraft.data.ModInfo;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.entity.player.EntityPlayer;

public class Quests {
    public static void assignQuestToPlayer(String questId, EntityPlayer player) {
        IQuest quest = ModInfo.getQuest(questId);
        IPlayer playerData = Player.getInstance(player);

        playerData.assignQuest(quest);
    }
}