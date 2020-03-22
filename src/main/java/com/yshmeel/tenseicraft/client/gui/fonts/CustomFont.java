package com.yshmeel.tenseicraft.client.gui.fonts;

import java.util.HashMap;

import net.minecraft.client.gui.Gui;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.TextureImpl;

public class CustomFont {
    public static String glyph = " +=0123456789абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!-_()?,./\"'[]{}*&^:%$;№@#~`><\n";
    public HashMap<String, org.newdawn.slick.Color> colors = new HashMap();
    boolean init = false;
    public HashMap<Integer, UnicodeFont> fonts = new HashMap();
    public String fontUrl = "assets/fonts/arial.ttf";
    public String fontName = "arial";
    public CustomFont(String name, String url) {
        this.fontName = name;
        this.fontUrl = url;
    }

    public UnicodeFont getFont(int razm) {
        if (!fonts.containsKey(Integer.valueOf(razm)))
            createFont(razm);
        return (UnicodeFont) fonts.get(Integer.valueOf(razm));
    }

    public void createFont(int razm) {
        java.awt.Font s = null;
        try {
            s = java.awt.Font.createFont(0, CustomFont.class.getClassLoader().getResourceAsStream(this.fontUrl));
        } catch (Exception e1) { e1.printStackTrace(); }
        UnicodeFont font = new UnicodeFont(s, razm, false, false);
        font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        font.addGlyphs(glyph);
        try {
            font.loadGlyphs();
        } catch (SlickException e) { }
        fonts.put(Integer.valueOf(razm), font);
    }

    public int getWidth(int size, String str) {
        if (!str.startsWith("§"))
            str = "§f" + str;
        UnicodeFont font = getFont(size);
        String result = "";
        for (String s : str.split("§")) {
            if ((s != null) && (s.length() > 1))
                result = result + new String(s.substring(1));
        }
        int width = font.getWidth(result);
        return width;
    }

    public void draw(int size, String str, int x, int y, int color, boolean shadow) {
        draw(size, str, x, y, -1, shadow, color);
    }

    public void draw(int size, String str, int x, int y, int color) {
        draw(size, str, x, y, -1, false, color);
    }

    public void draw(int size, String str, int x, int y) {
        draw(size, str, x, y, -1, false, 0);
    }

    public int getHeight(int size, String str, int maxwidth) {
        if (!str.startsWith("§")) str = "§f" + str;
        String[] data = str.split("§");
        int y = 0;
        int width = 0;
        UnicodeFont font = getFont(size);
        for (String s1 : str.split("\n")) {
            if ((s1 != null) && (s1.length() != 0)) {
                String source = "";
                for (String s : s1.split("§")) {
                    if ((s != null) && (s.length() > 1)) {
                        char col = s.charAt(0);
                        for (String s2 : new String(s.substring(1)).split(" ")) {
                            String t = s2 + " ";
                            source = source + t;
                            if ((maxwidth != -1) && (width + font.getWidth(t) > maxwidth)) {
                                y += font.getHeight(source) + 2;
                                width = 0;
                            }
                            width += font.getWidth(t);
                        }
                    }
                }
                y += font.getHeight(source) + 2;
                width = 0;
            }
        }
        return y;
    }

    public void draw(int size, String str, int x, int y, int maxwidth, boolean shadow, int color) {
        initfonts();
        if (shadow) {
            Gui.drawRect(x - 5, y - 1, maxwidth != -1 ? maxwidth + 5 : x + getWidth(size, str) + 7, y + getHeight(size, str, maxwidth) + 5, 0xFF000000);
        }
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        int sx = x;
        int sy = y;
        int width = 0;
        UnicodeFont font = getFont(size);
        for (String s1 : str.split("\n")) {
            if ((s1 != null) && (s1.length() != 0)) {
                if (!s1.startsWith("§"))
                    s1 = "§f" + s1;
                String source = "";
                for (String s : s1.split("§")) {
                    if ((s != null) && (s.length() > 1)) {
                        char col = s.charAt(0);
                        for (String s2 : new String(s.substring(1)).split(" ")) {
                            String t = s2 + " ";
                            source = source + t;
                            if ((maxwidth != -1) && (width + font.getWidth(t) > maxwidth)) {
                                x = sx;
                                y += font.getHeight(source) + 2;
                                width = 0;
                            }
                            if (color != 0) {
                                font.drawString(x, y, t, new org.newdawn.slick.Color(color));
                            } else {
                                font.drawString(x, y, t, getColor(col));
                            }
                            TextureImpl.bindNone();
                            width += font.getWidth(t);
                            x += font.getWidth(t);
                        }
                    }
                }
                x = sx;
                y += font.getHeight(source) + 2;
                width = 0;
            }
        }

        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public org.newdawn.slick.Color getColor(char s) {
        org.newdawn.slick.Color c = (org.newdawn.slick.Color) colors.get(String.valueOf(s));
        if (c != null) {
            return c;
        }
        return org.newdawn.slick.Color.white;
    }

    public void initfonts() {
        if (init) return;
        init = true;
        colors.put("0", new org.newdawn.slick.Color(0));
        colors.put("1", new org.newdawn.slick.Color(170));
        colors.put("2", new org.newdawn.slick.Color(43520));
        colors.put("3", new org.newdawn.slick.Color(43690));
        colors.put("4", new org.newdawn.slick.Color(11141120));
        colors.put("5", new org.newdawn.slick.Color(11141290));
        colors.put("6", new org.newdawn.slick.Color(16755200));
        colors.put("7", new org.newdawn.slick.Color(11184810));
        colors.put("8", new org.newdawn.slick.Color(5592405));
        colors.put("9", new org.newdawn.slick.Color(5592575));
        colors.put("a", new org.newdawn.slick.Color(5635925));
        colors.put("b", new org.newdawn.slick.Color(5636095));
        colors.put("c", new org.newdawn.slick.Color(16733525));
        colors.put("d", new org.newdawn.slick.Color(16733695));
        colors.put("e", new org.newdawn.slick.Color(16777045));
        colors.put("f", new org.newdawn.slick.Color(16777215));
        colors.put("g", new org.newdawn.slick.Color(12632256));
        colors.put("h", new org.newdawn.slick.Color(13467442));
    }
}