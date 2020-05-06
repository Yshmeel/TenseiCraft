package com.yshmeel.tenseicraft.common.entities;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.client.dialogs.DialogList;
import com.yshmeel.tenseicraft.client.utils.DialogUtils;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;


public class NPCMob extends EntityCreature {
    private float MAX_HEALTH = 0.0F;
    private float MOVEMENT_SPEED = 0.0F;
    public static ResourceLocation MOB_TEXTURE = new ResourceLocation("tenseicraft", "textures/entity/npc.png");
    public static ResourceLocation MOB_HEAD = new ResourceLocation("tenseicraft", "textures/entity/npc_head.png");

    public NPCMob(World worldIn) {
        super(worldIn);
    }

    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(2, new EntityAILookIdle(this));
    }

    protected void entityInit()
    {
        super.entityInit();
    }

    @Override
    protected void applyEntityAttributes() {
        MAX_HEALTH = 30.0F;
        MOVEMENT_SPEED = 0.3F;

        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(MAX_HEALTH);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(MOVEMENT_SPEED);
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        if (this.world.isRemote) {
            DialogUtils.showDialog(DialogList.IRUKA_TUTORIAL);
            return true;
        }

        return false;
    }
}
