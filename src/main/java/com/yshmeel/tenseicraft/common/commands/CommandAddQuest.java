package com.yshmeel.tenseicraft.common.commands;

import com.yshmeel.tenseicraft.common.entities.GeninMob;
import com.yshmeel.tenseicraft.common.fighting.jutsu.IJutsu;
import com.yshmeel.tenseicraft.common.quests.Quests;
import com.yshmeel.tenseicraft.data.ModInfo;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandAddQuest extends CommandBase {
    @Override
    public String getName() {
        return "add_quest";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/add_quest <player> <type>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 2) {
            String playerName = String.valueOf(args[0]);
            String questId = String.valueOf(args[1]);

            EntityPlayer target = server.getPlayerList().getPlayerByUsername(playerName);
            if(target == null) {
                sender.sendMessage(new TextComponentTranslation("commands.tensei.simple.player_not_found"));
            } else {
                Quests.assignQuestToPlayer(questId, target);
            }
        } else {
            sender.sendMessage(new TextComponentTranslation("commands.tensei.simple.wrong_arguments"));
        }
    }
}
