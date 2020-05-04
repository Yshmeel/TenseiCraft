package com.yshmeel.tenseicraft.common.fighting.jutsu.fire;

import com.yshmeel.tenseicraft.common.fighting.jutsu.Jutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.entities.EntityWall;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

public class FireBallJutsu extends Jutsu {
    @Override
    public void init() {
        this.setId("earth_wall");
        this.setName("common.jutsu.earth_wall");
        this.setDescription("common.jutsu.earth_wall_description");
        this.setType(0);
        this.setIcon(new ResourceLocation("tenseicraft", "textures/jutsu/earth_wall.png"));
        this.setChakraTake(5.0D);
        this.setPointsLearn(1);
    }

    @Override
    public void registerEntities(RegistryEvent.Register<EntityEntry> event) {
    }

    @Override
    public boolean throwJutsu(EntityPlayer fromPlayer, EntityPlayer toPlayer, World world) {
        if(fromPlayer != null) {
            if(world == null) {
                world = fromPlayer.getEntityWorld();
            }

            EntityWall jutsuEntity = new EntityWall(fromPlayer.getEntityWorld(), fromPlayer);
            jutsuEntity.setBlockToSet(Blocks.DIRT);
            world.spawnEntity(jutsuEntity);

            jutsuEntity.shoot(fromPlayer, fromPlayer.rotationPitch, fromPlayer.rotationYaw, 0.0F, 3.0F, 0.0F);
            this.afterUseJutsu(fromPlayer, world);
        }

        return true;
    }
}
