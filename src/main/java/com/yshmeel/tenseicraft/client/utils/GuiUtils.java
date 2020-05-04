package com.yshmeel.tenseicraft.client.utils;

import com.yshmeel.tenseicraft.Tensei;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiUtils {
    public static final double TWICE_PI = Math.PI*2;
    private static Tessellator tessellator = Tessellator.getInstance();
    private static BufferBuilder worldRenderer = tessellator.getBuffer();
    public static void renderArrow(int x, int y, int width, int height, float rotate) {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(
                new ResourceLocation(Tensei.MODID, "textures/gui/arrow.png")
        );

        GL11.glRotatef(rotate, 0.0f, 0.0f, 1.0f);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
        GL11.glPopMatrix();
    }

    public static void drawRegularPolygon(double x, double y, int radius, int sides)
    {
        GL11.glPushMatrix();
        GL11.glPushAttrib (GL11.GL_ALL_ATTRIB_BITS);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        worldRenderer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x, y, 0).endVertex();

        for(int i = 0; i <= sides ;i++)
        {
            double angle = (TWICE_PI * i / sides) + Math.toRadians(180);
            worldRenderer.pos(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0).endVertex();
        }
        tessellator.draw();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

}
