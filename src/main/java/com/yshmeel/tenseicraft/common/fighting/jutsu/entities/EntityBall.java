package com.yshmeel.tenseicraft.common.fighting.jutsu.entities;

import com.yshmeel.tenseicraft.common.fighting.jutsu.utils.JutsuUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.concurrent.Callable;

public class EntityBall extends EntityThrowable implements IProjectile {
    /* Entity types */
    public static int RADIUS_CIRCLE_ON_COLLIDE = 0;
    public static int RADIUS_CIRCLE_ON_COLLIDE_WITH_EXPLOSION = 1;

    private int ticksInGround = 0;
    private boolean start = false;
    private Callable onCollide = null;
    private Callable onJutsuEnd = null;
    private EnumParticleTypes particleType = null;
    public Integer type = null;
    public Block block = null;
    public ResourceLocation jutsuTexture = null;
    public int timeLimit = 240;
    public float[] size = {0.0f, 0.0f};
    public float[][] savedBlockPos = null;

    public EntityBall(World worldIn)
    {
        super(worldIn);

        this.motionX *= 0.4;
        this.motionY *= 0.4;
        this.motionZ *= 0.4;
        this.setSize(this.size[0], this.size[1]);
        this.noClip = false;
    }

    public EntityBall(World worldIn, double x, double y, double z)
    {
        this(worldIn);
        this.setPosition(x, y, z);
        this.noClip = false;
    }

    public EntityBall(World worldIn, EntityLivingBase player)
    {
        super(worldIn, player);

        this.motionX *= 0.4;
        this.motionY *= 0.4;
        this.motionZ *= 0.4;
        this.setSize(this.size[0], this.size[1]);
        this.noClip = false;
    }

    public EntityBall(World worldIn, EntityLivingBase player, float width, float height)
    {
        super(worldIn, player);

        this.motionX *= 0.4;
        this.motionY *= 0.4;
        this.motionZ *= 0.4;
        this.setSize(width, height);
        this.noClip = false;
    }

    public void setOnCollide(Callable function) {
        this.onCollide = function;
    }

    public void setOnJutsuEnd(Callable function) {
        this.onJutsuEnd = function;
    }

    public void setParticleType(EnumParticleTypes particle) {
        this.particleType = particle;
    }
    public void setJutsuTexture(ResourceLocation jutsuTexture) {
        this.jutsuTexture = jutsuTexture;
    }
    public void setType(int type) {
        this.type = type;
    }
    public void setBlock(Block block) {
        this.block = block;
    }
    public void setTimeLimit(int limit) {
        this.timeLimit = limit*20;
    }
    public void setEntitySize(float width, float height) {
        this.size[0] = width;
        this.size[1] = height;

        this.setSize(width, height);
    }

    protected float getGravityVelocity() {
        return 0.00f;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if(result.getBlockPos() != null) {
            if((result.typeOfHit.equals(RayTraceResult.Type.BLOCK)
                    || result.typeOfHit.equals(RayTraceResult.Type.ENTITY)) && !this.world.getBlockState(result.getBlockPos()).equals(Blocks.AIR.getDefaultState())) {
                this.setSize(0.0F, 0.0F);
                this.motionX = 0;
                this.motionY = 0;
                this.motionZ = 0;
                this.start = true;
                if(this.type != null) {
                    if(this.type == RADIUS_CIRCLE_ON_COLLIDE || this.type == RADIUS_CIRCLE_ON_COLLIDE_WITH_EXPLOSION) {
                        // @todo сделать проверку на приваты
                        float[][] positions = JutsuUtils.getRadiusCircle(this.world, this.getPosition());
                        for(int i = 0; i < positions.length; i++) {
                            positions[i][1] = (positions[i][1] > this.thrower.getPosition().getY()
                                    ? this.thrower.getPosition().getY() : positions[i][1]);
                            BlockPos pos = new BlockPos(positions[i][0],  positions[i][1],
                                    positions[i][2]);
                            if(this.world.getBlockState(pos).equals(Blocks.AIR.getDefaultState())) {
                                this.world.setBlockState(pos, this.block.getDefaultState());
                            }
                        }
                        this.savedBlockPos = positions;
                    }
                    if(this.type == RADIUS_CIRCLE_ON_COLLIDE_WITH_EXPLOSION) {
                        this.world.createExplosion(this, this.posX, this.posY, this.posZ, 1.0F, true);
                    }
                }

                if(this.onCollide != null) {
                    try {
                        this.onCollide.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    @Override
    protected void entityInit() {

    }

    public void onUpdate() {
        super.onUpdate();

        if(this.start) {
            this.ticksInGround++;

            if(this.ticksInGround >= this.timeLimit) {
                if(this.type != null) {
                    if(this.type == RADIUS_CIRCLE_ON_COLLIDE || this.type == RADIUS_CIRCLE_ON_COLLIDE_WITH_EXPLOSION) {
                        for(int i = 0; i < this.savedBlockPos.length; i++) {
                            BlockPos pos = new BlockPos(this.savedBlockPos[i][0], this.savedBlockPos[i][1],
                                    this.savedBlockPos[i][2]);
                            if(this.world.getBlockState(pos).equals(this.block.getDefaultState())) {
                                this.world.setBlockState(pos, Blocks.AIR.getDefaultState());
                            }
                        }
                    }
                }
                if(this.onJutsuEnd != null) {
                    try {
                        this.onJutsuEnd.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                this.setDead();
                this.start = false;
            }
        } else {
            if(this.particleType != null) {
                float f3 = 5.25f;
                for (int i = 0; i < 4; ++i) {
                    this.world.spawnParticle(this.particleType, this.posX - this.motionX * (double)f3 + this.rand.nextDouble() * 0.6 - 0.3, this.posY - this.motionY * (double)f3 - 0.5, this.posZ - this.motionZ * (double)f3 + this.rand.nextDouble() * 0.6 - 0.3, this.motionX, this.motionY, this.motionZ);
                }
            }

        }

    }

    public void shoot(Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy)
    {
        float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        float f1 = -MathHelper.sin(pitch * 0.017453292F);
        float f2 = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        this.shoot((double)f, (double)f1, (double)f2, velocity, inaccuracy);
        this.motionX += shooter.motionX;
        this.motionZ += shooter.motionZ;

        if (!shooter.onGround)
        {
            this.motionY += shooter.motionY;
        }
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy)
    {
        float f = MathHelper.sqrt(x * x + y * y + z * z);
        x = x / (double)f;
        y = y / (double)f;
        z = z / (double)f;
        x = x + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        y = y + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        z = z + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        x = x * (double)velocity;
        y = y * (double)velocity;
        z = z * (double)velocity;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        float f1 = MathHelper.sqrt(x * x + z * z);
        this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(y, (double)f1) * (180D / Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
        this.ticksInGround = 0;
    }
}