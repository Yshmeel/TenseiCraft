package com.yshmeel.tenseicraft.common.commands;

import com.yshmeel.tenseicraft.common.fighting.jutsu.IJutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsutype.IJutsuType;
import com.yshmeel.tenseicraft.data.ModInfo;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandAddLearnedJutsu extends CommandBase {
    @Override
    public String getName() {
        return "add_learned_jutsu";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/add_learned_jutsu <name> <id of jutsu>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 2) {
            String jutsuId = String.valueOf(args[1]);

            EntityPlayer target = server.getPlayerList().getPlayerByUsername(args[0]);
            if(target == null) {
                sender.sendMessage(new TextComponentTranslation("commands.tensei.simple.player_not_found"));
            } else {
                IJutsu jutsu = ModInfo.getJutsu(jutsuId);

                if(jutsu == null) {
                    sender.sendMessage(new TextComponentTranslation("common.tensei.jutsutype.not_found"));
                } else {
                    IPlayer targetPlayer = Player.getInstance(target);

                    targetPlayer.addJutsu(jutsu.getId());

                    sender.sendMessage(new TextComponentTranslation("commands.tensei.add_jutsu.success",
                            target.getName(), jutsu.getName()));

                    target.sendMessage(new TextComponentTranslation("commands.tensei.add_jutsu.success_target",
                            sender.getName(), jutsu.getName()));
                }
            }
        } else {
            sender.sendMessage(new TextComponentTranslation("commands.tensei.simple.wrong_arguments"));
        }
    }
}