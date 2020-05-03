package com.yshmeel.tenseicraft.common.commands;

import com.yshmeel.tenseicraft.common.entities.GeninMob;
import com.yshmeel.tenseicraft.common.entities.NPCMob;
import com.yshmeel.tenseicraft.common.fighting.jutsu.IJutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.Jutsu;
import com.yshmeel.tenseicraft.data.ModInfo;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandTest  extends CommandBase {
    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/test <type>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 1) {
            int type = Integer.valueOf(args[0]);

            switch(type) {
                case 0:
                    BlockPos pos  = sender.getCommandSenderEntity().getPosition();
                    EntityCreature mobToSpawn = new NPCMob(sender.getEntityWorld());
                    mobToSpawn.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
                    sender.getEntityWorld().spawnEntity(
                        mobToSpawn
                    );
                    break;
                case 1:
                    EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
                    IJutsu jutsu = ModInfo.jutsu.get("water_wall");

                    jutsu.throwJutsu(player, null, player.getEntityWorld());
                    break;
            }
        } else {
            sender.sendMessage(new TextComponentTranslation("commands.tensei.simple.wrong_arguments"));
        }
    }
}
