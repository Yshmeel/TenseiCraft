package com.yshmeel.tenseicraft.client.render;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.common.fighting.genkai.IGenkai;
import com.yshmeel.tenseicraft.data.ModInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerEndermanEyes;
import net.minecraft.client.renderer.entity.layers.LayerEntityOnShoulder;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class EyesLayer implements LayerRenderer<EntityPlayer>
{
    private final RenderPlayer playerRenderer;

    public EyesLayer(RenderPlayer playerRendererIn)
    {
        this.playerRenderer = playerRendererIn;
    }

    @Override
    public void doRenderLayer(EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        IGenkai sharingan = ModInfo.getGenkai("sharingan");
        this.playerRenderer.getMainModel().bipedHead.renderWithRotation(0.001F);;
        for(int i = 0; i <= 1; i++) {
            this.playerRenderer.bindTexture(sharingan.getEyes()[0]);
            this.playerRenderer.getMainModel().bipedHead.renderWithRotation(0.001F);;
        }
    }

    @Override
    public boolean shouldCombineTextures()
    {
        return false;
    }
}