package com.yshmeel.tenseicraft.common.fighting.jutsu.entities.clones;

import com.yshmeel.tenseicraft.Tensei;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderClone extends RenderLiving
{
    private ResourceLocation DEFAULT_SKIN = new ResourceLocation(Tensei.MODID, "textures/entity/default.png");

    public RenderClone(ModelBase par1ModelBase, float parShadowSize)
    {
        super(Minecraft.getMinecraft().getRenderManager(), par1ModelBase, parShadowSize);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float f)
    {
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called
     * unless you call Render.bindEntityTexture.
     */
    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        EntityClone entityMob = (EntityClone) entity;
        ResourceLocation texture = DEFAULT_SKIN;

        if(entityMob != null) {
            if(entityMob.hasCustomName()) {
                texture = AbstractClientPlayer.getLocationSkin(entityMob.getCustomNameTag());
                AbstractClientPlayer.getDownloadImageSkin(texture, entityMob.getCustomNameTag());
            }

            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
            return texture;
        }

        return DEFAULT_SKIN;
    }
}