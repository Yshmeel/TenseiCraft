package com.yshmeel.tenseicraft.common.entities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityWood extends Entity {
    public int ticksInGround = 0;
    public EntityWood(World worldIn) {
        super(worldIn);
        this.setSize(0.7f, 1.3f);
        this.ticksExisted = 0;
    }

    public void onUpdate() {
        this.ticksInGround++;
        if(this.ticksInGround >= 60) {
            for(int i = 0; i < 24; i ++) {
                this.world.spawnParticle(EnumParticleTypes.SPELL_INSTANT, this.posX, this.posY + 1, this.posZ , 0.3, 0.7, 0.3);
            }
            this.setDead();
        }
    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }
}
