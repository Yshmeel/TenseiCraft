package com.yshmeel.tenseicraft.data.player;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class PlayerStorage implements Capability.IStorage<IPlayer>
{
    @Override
    public NBTBase writeNBT(Capability<IPlayer> capability, IPlayer instance, EnumFacing side)
    {
        NBTTagCompound compound = instance.createCompoundFromData();
        return compound;
    }

    @Override
    public void readNBT(Capability<IPlayer> capability, IPlayer instance, EnumFacing side, NBTBase nbt)
    {
        instance.set(((NBTTagCompound) nbt));
    }
}