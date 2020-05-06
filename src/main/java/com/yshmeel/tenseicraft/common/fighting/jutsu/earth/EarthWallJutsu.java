package com.yshmeel.tenseicraft.common.fighting.jutsu.earth;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.client.Sounds;
import com.yshmeel.tenseicraft.common.fighting.jutsu.Jutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.entities.EntityWall;
import com.yshmeel.tenseicraft.common.fighting.jutsu.utils.JutsuUtils;
import com.yshmeel.tenseicraft.common.registries.BlockRegistry;
import com.yshmeel.tenseicraft.data.ModInfo;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.block.state.IBlockState;
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

public class EarthWallJutsu extends Jutsu {
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

            jutsuEntity.shoot(fromPlayer, fromPlayer.rotationPitch, fromPlayer.rotationYaw, 0.0F, 1.0F, 0.0F);
            this.afterUseJutsu(fromPlayer, world);
        }

        return true;
    }
}
