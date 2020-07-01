package com.yshmeel.tenseicraft.common.entities;

import com.yshmeel.tenseicraft.client.dialogs.DialogList;
import com.yshmeel.tenseicraft.client.utils.DialogUtils;
import com.yshmeel.tenseicraft.common.fighting.jutsu.utils.JutsuUtils;
import com.yshmeel.tenseicraft.common.registries.ItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityKunai extends EntityThrowable implements IProjectile {
    public boolean binded = false;
    public int ticksInGround = 0;
    public EntityKunai(World worldIn)
    {
        super(worldIn);

        this.motionX *= 1.0;
        this.motionY *= 1.0;
        this.motionZ *= 1.0;
        this.setSize(0.5F, 0.5F);
        this.noClip = false;
    }

    public EntityKunai(World worldIn, double x, double y, double z)
    {
        this(worldIn);
        this.setPosition(x, y, z);
        this.noClip = false;
    }

    public EntityKunai(World worldIn, EntityLivingBase player)
    {
        super(worldIn, player);

        this.motionX *= 1.0;
        this.motionY *= 1.0;
        this.motionZ *= 1.0;
        this.setSize(0.5F, 0.5F);
        this.noClip = false;
    }
    @Override
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand)
    {
        return EnumActionResult.PASS;
    }
    @Override
    public void onCollideWithPlayer(EntityPlayer player)
    {
        if(this.binded) {
            player.inventory.add(1, new ItemStack(ItemRegistry.KUNAI));
            for(int i = 0; i < 24; i ++) {
                this.world.spawnParticle(EnumParticleTypes.SPELL_INSTANT, this.posX, this.posY + 1, this.posZ , 0.3, 0.7, 0.3);
            }
            this.setDead();
        }
    }

    protected float getGravityVelocity() {
        return 0.00f;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if(result.typeOfHit.equals(RayTraceResult.Type.BLOCK) &&
                !this.world.getBlockState(result.getBlockPos()).equals(Blocks.AIR.getDefaultState())) {
            this.motionX = 0;
            this.motionY = 0;
            this.motionZ = 0;
            this.binded = true;
        } else if(result.typeOfHit.equals(RayTraceResult.Type.ENTITY)) {
            Entity entity = result.entityHit;

            entity.attackEntityFrom(new DamageSource(DamageSource.LIGHTNING_BOLT.toString()), 3.0f);
            this.setDead();
        }
    }

    @Override
    protected void entityInit() {

    }

    public void onUpdate() {
        super.onUpdate();
    }

}
