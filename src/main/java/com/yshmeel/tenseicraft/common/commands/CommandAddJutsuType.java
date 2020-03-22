package com.yshmeel.tenseicraft.common.commands;

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

public class CommandAddJutsuType extends CommandBase {
    @Override
    public String getName() {
        return "add_jutsutype";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/add_jutsutype <name> <type of jutsu>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 2) {
            String jutsuType = String.valueOf(args[1]);

            EntityPlayer target = server.getPlayerList().getPlayerByUsername(args[0]);
            if(target == null) {
                sender.sendMessage(new TextComponentTranslation("commands.tensei.simple.player_not_found"));
            } else {
                IJutsuType release = ModInfo.getJutsuType(jutsuType);

                if(release == null) {
                    sender.sendMessage(new TextComponentTranslation("common.tensei.jutsutype.not_found"));
                } else {
                    IPlayer targetPlayer = Player.getInstance(target);

                    targetPlayer.addJutsuType(release.getId());

                    sender.sendMessage(new TextComponentTranslation("commands.tensei.add_jutsutype.success",
                            target.getName(), release.getName()));

                    target.sendMessage(new TextComponentTranslation("commands.tensei.add_jutsutype.success_target",
                            sender.getName(), release.getName()));
                }
            }
        } else {
            sender.sendMessage(new TextComponentTranslation("commands.tensei.simple.wrong_arguments"));
        }
    }
}