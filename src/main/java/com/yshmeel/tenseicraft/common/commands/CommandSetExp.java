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

public class CommandSetExp extends CommandBase {
    @Override
    public String getName() {
        return "set_exp";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/set_exp <name> <exp>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 2) {
            int exp = Integer.valueOf(args[1]);

            EntityPlayer target = server.getPlayerList().getPlayerByUsername(args[0]);
            if(target == null) {
                sender.sendMessage(new TextComponentTranslation("commands.tensei.simple.player_not_found"));
            } else {
                IPlayer targetPlayer = Player.getInstance(target);

                targetPlayer.setEXP(exp);

                sender.sendMessage(new TextComponentTranslation("commands.tensei.set_exp.success",
                        target.getName(), exp));

                target.sendMessage(new TextComponentTranslation("commands.tensei.set_exp.success_target",
                        sender.getName(), exp));
            }
        } else {
            sender.sendMessage(new TextComponentTranslation("commands.tensei.simple.wrong_arguments"));
        }
    }
}
