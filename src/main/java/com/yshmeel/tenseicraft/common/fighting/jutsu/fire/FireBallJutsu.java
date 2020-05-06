package com.yshmeel.tenseicraft.common.fighting.jutsu.fire;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.common.fighting.jutsu.Jutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.entities.EntityBall;
import com.yshmeel.tenseicraft.common.fighting.jutsu.entities.EntityWall;
import com.yshmeel.tenseicraft.common.fighting.jutsu.utils.JutsuUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

public class FireBallJutsu extends Jutsu {
    @Override
    public void init() {
        this.setId("earth_wall");
        this.setName("common.jutsu.fire_ball");
        this.setDescription("common.jutsu.fire_ball_description");
        this.setType(0);
        this.setIcon(new ResourceLocation("tenseicraft", "textures/jutsu/fire_ball.png"));
        this.setChakraTake(5.0D);
        this.setPointsLearn(1);
    }

    @Override
    public void registerEntities(RegistryEvent.Register<EntityEntry> event) {
    }

    // @todo сделать текстурку для файрбола
    @Override
    public boolean throwJutsu(EntityPlayer fromPlayer, EntityPlayer toPlayer, World world) {
        if(fromPlayer != null) {
            if(world == null) {
                world = fromPlayer.getEntityWorld();
            }

            EntityBall jutsuEntity = new EntityBall(fromPlayer.getEntityWorld(), fromPlayer, 2f, 2f);
            jutsuEntity.setTimeLimit(8);
            jutsuEntity.setJutsuTexture(new ResourceLocation(Tensei.MODID, "textures/jutsu/balls/fire.png"));
            jutsuEntity.setParticleType(EnumParticleTypes.SPELL);
            jutsuEntity.setType(EntityBall.RADIUS_CIRCLE_ON_COLLIDE_WITH_EXPLOSION);
            // @todo переделать блок который ставится чтобы огонь не распространялся
            jutsuEntity.setBlock(Blocks.FIRE);
            jutsuEntity.setOnCollide(() -> {
                return null;
            });
            jutsuEntity.setOnJutsuEnd(() -> {
                Tensei.logger.info("end");
                return null;
            });
            world.spawnEntity(jutsuEntity);

            jutsuEntity.shoot(fromPlayer, fromPlayer.rotationPitch, fromPlayer.rotationYaw, 0.0F, 1.0F, 0.0F);
            this.afterUseJutsu(fromPlayer, world);
        }

        return true;
    }
}
