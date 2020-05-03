package com.yshmeel.tenseicraft.common.entities.renders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class DefaultRender extends RenderLiving
{
    protected ResourceLocation texture;

    public DefaultRender(ModelBase par1ModelBase, float parShadowSize, ResourceLocation texture)
    {
        super(Minecraft.getMinecraft().getRenderManager(), par1ModelBase, parShadowSize);
        this.texture = texture;
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
        return this.texture;
    }
}