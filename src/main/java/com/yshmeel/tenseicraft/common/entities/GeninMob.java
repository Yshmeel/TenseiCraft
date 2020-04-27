package com.yshmeel.tenseicraft.common.entities;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;


public class GeninMob extends EntityMob {
    private float DAMAGE_AMOUNT = 0.0F;
    private float MAX_HEALTH = 0.0F;
    private float MOVEMENT_SPEED = 0.0F;
    private float FOLLOW_RANGE = 0.0F;
    public static ResourceLocation MOB_TEXTURE = new ResourceLocation("tenseicraft", "textures/entity/genin.png");

    public GeninMob(World worldIn) {
        super(worldIn);
    }

    protected void initEntityAI() {
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true, true));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 16f));
        this.tasks.addTask(6, new EntityAIAttackMelee(this, 2, true));
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(4, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(2, new EntityAILookIdle(this));
    }

    protected void entityInit()
    {
        super.entityInit();
    }

    @Override
    protected void applyEntityAttributes() {
        DAMAGE_AMOUNT = 2.0F;
        MAX_HEALTH = 30.0F;
        MOVEMENT_SPEED = 0.3F;
        FOLLOW_RANGE = 32.0F;

        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(MAX_HEALTH);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(DAMAGE_AMOUNT);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(MOVEMENT_SPEED);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(FOLLOW_RANGE);

    }
}
