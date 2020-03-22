package com.yshmeel.tenseicraft.common.fighting.jutsu.water;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.common.fighting.jutsu.Jutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.earth.EarthWallJutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.utils.JutsuUtils;
import com.yshmeel.tenseicraft.common.registries.BlockRegistry;
import com.yshmeel.tenseicraft.data.ModInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.Random;

public class WaterWallJutsu extends Jutsu {
    @Override
    public void init() {
        this.setId("water_wall");
        this.setName("common.jutsu.water_wall");
        this.setDescription("common.jutsu.water_wall_description");
        this.setType(0);
        this.setIcon(new ResourceLocation("tenseicraft", "textures/jutsutype/water.png"));
        this.setChakraTake(5.0D);
        this.setPointsLearn(1);
    }

    @Override
    public void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().register(
                EntityEntryBuilder.<Entity>create()
                        .entity(WaterWallEntity.class)
                        .id(new ResourceLocation("tenseicraft:water_wall_entity"), ModInfo.entityIds++)
                        .name("WaterWall")
                        .tracker(64, 3, true)
                        .build()
        );
    }

    @Override
    public boolean throwJutsu(EntityPlayer fromPlayer, EntityPlayer toPlayer, World world) {
        if(fromPlayer != null) {
            if(world == null) {
                world = fromPlayer.getEntityWorld();
            }

            WaterWallEntity jutsuEntity = new WaterWallEntity(fromPlayer.getEntityWorld(), fromPlayer);
            world.spawnEntity(jutsuEntity);

            jutsuEntity.shoot(fromPlayer, fromPlayer.rotationPitch, fromPlayer.rotationYaw, 0.0F, 3.0F, 0.0F);
            this.afterUseJutsu(fromPlayer, world);
        }

        return true;
    }

    static class WaterWallEntity extends EntityThrowable implements IProjectile {
        private int ticksInGround = 0;
        private float[][][] savedWallPos = null;
        private boolean start = false;

        public WaterWallEntity(World worldIn)
        {
            super(worldIn);

            this.motionX *= 0.4;
            this.motionY *= 0.4;
            this.motionZ *= 0.4;
            this.setSize(0.0f, 0.0f);
            this.noClip = false;
        }

        public WaterWallEntity(World worldIn, double x, double y, double z)
        {
            this(worldIn);
            this.setPosition(x, y, z);
            this.noClip = false;
        }

        public WaterWallEntity(World worldIn, EntityLivingBase player)
        {
            super(worldIn, player);

            this.motionX *= 0.4;
            this.motionY *= 0.4;
            this.motionZ *= 0.4;
            this.setSize(0.0f, 0.0f);
            this.noClip = false;
        }


        protected float getGravityVelocity() {
            return 0.00f;
        }

        @Override
        protected void onImpact(RayTraceResult result) {
            if(result.typeOfHit.equals(RayTraceResult.Type.BLOCK)) {
                this.motionX = 0;
                this.motionY = 0;
                this.motionZ = 0;
                this.start = true;
                float[][][] wallPos = JutsuUtils.getBestPositionToWall(this.world, result.getBlockPos());

                this.savedWallPos = wallPos;
                for(float[][] rowWallCoords : wallPos) {
                    for(float[] pieceRowWall : rowWallCoords) {
                        BlockPos pos = new BlockPos(pieceRowWall[0], pieceRowWall[1], pieceRowWall[2]);
                        if(this.getEntityWorld().getBlockState(pos).equals(Blocks.AIR.getDefaultState())) {
                            this.getEntityWorld().setBlockState(pos, BlockRegistry.STATIC_WATER_JUTSU.getDefaultState());
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

                if(this.ticksInGround >= 240) {
                    float[][][] wallPos = this.savedWallPos;

                    for(float[][] rowWallCoords : wallPos) {
                        for(float[] pieceRowWall : rowWallCoords) {
                            BlockPos pos = new BlockPos(pieceRowWall[0], pieceRowWall[1], pieceRowWall[2]);
                            if(this.getEntityWorld().getBlockState(pos).equals(BlockRegistry.STATIC_WATER_JUTSU.getDefaultState())) {
                                this.getEntityWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
                            }
                        }
                    }
                    this.setDead();
                    this.start = false;
                }
            } else {
                float f3 = 5.25f;
                for (int i = 0; i < 4; ++i) {
                    this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double)f3 + this.rand.nextDouble() * 0.6 - 0.3, this.posY - this.motionY * (double)f3 - 0.5, this.posZ - this.motionZ * (double)f3 + this.rand.nextDouble() * 0.6 - 0.3, this.motionX, this.motionY, this.motionZ);
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
}
