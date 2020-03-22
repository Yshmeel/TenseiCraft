package com.yshmeel.tenseicraft.common.commands;

import com.yshmeel.tenseicraft.data.ModInfo;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandSetLevel extends CommandBase {
    @Override
    public String getName() {
        return "set_level";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/set_level <name> <level>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 2) {
            int level = Integer.valueOf(args[1]);

            EntityPlayer target = server.getPlayerList().getPlayerByUsername(args[0]);
            if(target == null) {
                sender.sendMessage(new TextComponentTranslation("commands.tensei.simple.player_not_found"));
            } else {
                IPlayer targetPlayer = Player.getInstance(target);

                targetPlayer.setLevel(level);

                sender.sendMessage(new TextComponentTranslation("commands.tensei.set_level.success",
                        target.getName(), level));

                target.sendMessage(new TextComponentTranslation("commands.tensei.set_level.success_target",
                        sender.getName(), level));
            }
        } else {
            sender.sendMessage(new TextComponentTranslation("commands.tensei.simple.wrong_arguments"));
        }
    }
}
