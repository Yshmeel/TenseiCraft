package com.yshmeel.tenseicraft.common.commands;

import com.yshmeel.tenseicraft.data.ModInfo;
import com.yshmeel.tenseicraft.data.clans.Clan;
import com.yshmeel.tenseicraft.data.clans.Null;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandSetClan extends CommandBase {
    @Override
    public String getName() {
        return "set_clan";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/set_clan <name> <id>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 2) {
            int clanId = Integer.valueOf(args[1]);
            Clan clan = ModInfo.getClan(clanId);

            if(clan instanceof Null) {
                sender.sendMessage(new TextComponentTranslation("commands.tensei.set_clan.clan_not_found"));
            } else {
                EntityPlayer target = server.getPlayerList().getPlayerByUsername(args[0]);
                if(target == null) {
                    sender.sendMessage(new TextComponentTranslation("commands.tensei.simple.player_not_found"));
                } else {
                    IPlayer targetPlayer = Player.getInstance(target);

                    targetPlayer.setClan(clanId);

                    sender.sendMessage(new TextComponentTranslation("commands.tensei.set_clan.success",
                            target.getName(), clan.getClanName()));

                    target.sendMessage(new TextComponentTranslation("commands.tensei.set_clan.success_target",
                            sender.getName(), clan.getClanName()));
                }
            }
        } else {
            sender.sendMessage(new TextComponentTranslation("commands.tensei.simple.wrong_arguments"));
        }
    }
}
