package com.yshmeel.tenseicraft.common.fighting.jutsu.entities.clones;

import com.google.common.base.Optional;
import com.yshmeel.tenseicraft.common.entities.ai.EntityAIFollowOwnerNew;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.IEntitySelectorFactory;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityClone extends EntityTameable {
    public EntityClone(World worldIn) {
        super(worldIn);

        this.setHealth(1.0F);
        this.tasks.addTask(5, (EntityAIBase)new EntityAIFollowOwner((EntityTameable)this, 1.0, 10.0f, 12.0f));
        this.tasks.addTask(7, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        this.tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(9, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIOwnerHurtByTarget((EntityTameable)this));
        this.targetTasks.addTask(2, (EntityAIBase)new EntityAIOwnerHurtTarget((EntityTameable)this));
        this.targetTasks.addTask(3, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, true));
        this.tasks.addTask(10, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.setSize(1.0F, 2.0F);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0);
    }

    @Nullable
    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
    }

    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEFINED;
    }

    protected boolean canDespawn() {
        return false;
    }

    public boolean canAttackClass(Class par1Class) {
        return true;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    protected int getDropItemId() {
        return -1;
    }

}
