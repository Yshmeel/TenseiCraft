package com.yshmeel.tenseicraft.common.fighting.jutsu.earth;

import com.yshmeel.tenseicraft.common.entities.GeninMob;
import com.yshmeel.tenseicraft.common.fighting.jutsu.Jutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.entities.clones.EntityClone;
import com.yshmeel.tenseicraft.data.ModInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import org.lwjgl.opengl.GL11;

public class EarthCloneJutsu extends Jutsu {
    @Override
    public void init() {
        this.setId("earth_clone");
        this.setName("common.jutsu.earth_clone");
        this.setDescription("common.jutsu.earth_clone_description");
        this.setType(0);
        this.setIcon(new ResourceLocation("tenseicraft", "textures/jutsutype/earth.png"));
        this.setChakraTake(7.0D);
        this.setPointsLearn(1);
    }

    @Override
    public void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().register(
            EntityEntryBuilder.<Entity>create()
                    .entity(EarthCloneEntity.class)
                    .id(new ResourceLocation("tenseicraft:earth_clone"), ModInfo.entityIds++)
                    .name("EarthClone")
                    .tracker(64, 3, true)
                    .build()
        );
    }

    @Override
    public  boolean throwJutsu(EntityPlayer fromPlayer, EntityPlayer toPlayer, World world) {
        if(fromPlayer != null) {
            if(world == null) {
                world = fromPlayer.getEntityWorld();
            }

            BlockPos position = fromPlayer.getPosition();

            EarthCloneEntity entity = new EarthCloneEntity(fromPlayer, world);
            entity.setPosition(position.getX(), position.getY(), position.getZ());
            world.spawnEntity(entity);

            this.afterUseJutsu(fromPlayer, world);
        }

        return true;
    }

    public static class EarthCloneEntity extends EntityClone {
        EarthCloneEntity(World worldIn) {
            super(worldIn);
        }

        EarthCloneEntity(EntityPlayer player, World worldIn) {
            super(worldIn);

            this.setOwnerId(player.getUniqueID());
            this.setCustomNameTag(player.getName());
        }

        public void onDeath(DamageSource cause)
        {
            if (!this.world.isRemote && this.getOwner() instanceof EntityPlayerMP)
            {
                this.getOwner().sendMessage(new TextComponentTranslation("common.jutsu.clone.kill", this.getCombatTracker().getDeathMessage()));
            }

            float f3 = 5.25f;
            for (int i = 0; i < 15; ++i) {
                this.world.spawnParticle(EnumParticleTypes.SPELL, this.posX - this.motionX * (double)f3 + this.rand.nextDouble() * 0.6 - 0.3, this.posY - this.motionY * (double)f3 - 0.5, this.posZ - this.motionZ * (double)f3 + this.rand.nextDouble() * 0.6 - 0.3, this.motionX, this.motionY, this.motionZ);
            }
            super.onDeath(cause);
        }
    }
}
