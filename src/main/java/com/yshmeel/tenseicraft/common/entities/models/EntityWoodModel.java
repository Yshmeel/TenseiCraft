package com.yshmeel.tenseicraft.common.entities.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWoodModel extends ModelBase{
    public ModelRenderer woodRenderer;

    public EntityWoodModel()
    {
        this(0, 0, 32, 32);
    }

    public EntityWoodModel(int p_i46365_1_, int p_i46365_2_, int p_i46365_3_, int p_i46365_4_)
    {
        this.textureWidth = p_i46365_3_;
        this.textureHeight = p_i46365_4_;
        this.woodRenderer = new ModelRenderer(this, p_i46365_1_, p_i46365_2_);
        this.woodRenderer.addBox(-12.0F, -20.0F, -12.0F, 12, 20, 12, 0.0F);
        this.woodRenderer.setRotationPoint(0.0F, 0.0F, 0.0F);
    }

    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        this.woodRenderer.render(scale);
    }

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.woodRenderer.rotateAngleY = netHeadYaw * 0.017453292F;
        this.woodRenderer.rotateAngleX = headPitch * 0.017453292F;
    }
}
