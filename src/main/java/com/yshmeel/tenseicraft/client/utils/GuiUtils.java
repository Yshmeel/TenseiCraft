package com.yshmeel.tenseicraft.client.utils;

import com.yshmeel.tenseicraft.Tensei;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiUtils {
    public static void renderArrow(int x, int y, int width, int height, float rotate) {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(
                new ResourceLocation(Tensei.MODID, "textures/gui/arrow.png")
        );

        GL11.glRotatef(rotate, 0.0f, 0.0f, 1.0f);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
        GL11.glPopMatrix();
    }
}
