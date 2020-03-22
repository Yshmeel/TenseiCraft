package com.yshmeel.tenseicraft.client.gui.fonts;

import java.util.ArrayList;

import com.yshmeel.tenseicraft.Tensei;
import net.minecraft.client.renderer.OpenGlHelper;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;

public class DrawFonts {
    public static ArrayList<CustomFont> fonts = Lists.newArrayList();
    public DrawFonts() {
    }
    @SideOnly(Side.CLIENT)
    public void registerFonts() {
        addFont(new CustomFont("ptsans", "assets/tenseicraft/fonts/ptsans.ttf"));
        addFont(new CustomFont("naruto", "assets/tenseicraft/fonts/naruto.ttf"));
    }

    @SideOnly(Side.CLIENT)
    public static class Draw {
        public static void drawString(float x, float y, String text, int size, int color, CustomFont fonts, boolean shadow,
                                  boolean withPopAttrib) {
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);

            if(withPopAttrib) {
                GL11.glPushAttrib (GL11.GL_ALL_ATTRIB_BITS);
            }

            GL11.glTranslatef(x, y, 10);
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            //System.out.println(fonts);
            fonts.draw(size, text, 0,0, color, shadow);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            if(withPopAttrib) {
                GL11.glPopAttrib();
            }
            GL11.glEnable(2929);
            GL11.glPopMatrix();
        }

        public static void drawCenteredString(float x, float y, String text, int size, int color,
                                              CustomFont fonts, boolean shadow) {
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            GL11.glDisable(2929);
            int razm = (int)(size*0.5F);
            int width = fonts.getWidth(razm, text);
            GL11.glTranslatef(x - width / 2, y, 10);
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            fonts.draw(size, text, 0,0, color, shadow);
            GL11.glEnable(2929);
            GL11.glPopMatrix();
        }
    }

    @SideOnly(Side.CLIENT)
    public CustomFont getFont(String name) {
        for(CustomFont font : fonts)
            if(font.fontName.equalsIgnoreCase(name)) return font;

        return null;
    }

    @SideOnly(Side.CLIENT)
    public void addFont(CustomFont font) {
        if(fonts.contains(font)) {
            System.out.println("Font " + font.fontName + " is registered!");
            return ;
        }
        System.out.println("Font " + font.fontName + " is registered!");
        fonts.add(font);
    }
}
