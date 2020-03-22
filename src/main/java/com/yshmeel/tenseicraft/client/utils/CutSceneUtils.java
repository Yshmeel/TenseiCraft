package com.yshmeel.tenseicraft.client.utils;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.client.Keys;
import com.yshmeel.tenseicraft.client.cutscenes.FirstReleaseCutScene;
import com.yshmeel.tenseicraft.client.cutscenes.TutorialCutScene;
import com.yshmeel.tenseicraft.client.gui.fonts.DrawFonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class CutSceneUtils {
    public static boolean hasActiveCutscene = false;
    public static int activeCutSceneDialogId = -1;
    public static String activeCutSceneMode = "";

    public static boolean renderDialog(ResourceLocation dialogHead, String name, String text, Runnable onNext, int mouseX, int mouseY, boolean isClicked, int dialogId, String mode) {
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        int cutScenePosX = 5;
        int cutScenePosY = resolution.getScaledHeight() - 97;

        Gui.drawRect(cutScenePosX, cutScenePosY, cutScenePosX + resolution.getScaledWidth() - 10, cutScenePosY + 90, 0x99000000);

        Minecraft.getMinecraft().getTextureManager().bindTexture(dialogHead);

        GL11.glColor3f(255, 255, 255);
        Gui.drawModalRectWithCustomSizedTexture(cutScenePosX + 10, cutScenePosY - 100, 0, 0, 64, 64, 64, 64);

        DrawFonts.Draw.drawString(cutScenePosX + 10, cutScenePosY - 17, name, 16, 0xFFFFFFFF, Tensei.fonts.getFont("ptsans"), false,
                true);

        DrawFonts.Draw.drawString(cutScenePosX + 10, cutScenePosY + 10, parseText(text), 16, 0xFFFFFFFF, Tensei.fonts.getFont("ptsans"), false,
                true);

        boolean hoverBtn = (mouseX > cutScenePosX + 10 && mouseX < cutScenePosX + 30
                && mouseY > cutScenePosY + 60 && mouseY < cutScenePosY + 80);

        if(hoverBtn) {
            DrawFonts.Draw.drawString(cutScenePosX + 10, cutScenePosY + 70, I18n.format("cutscenes.common.next"),
                    14, 0xFF968300, Tensei.fonts.getFont("ptsans"), false,
                    true);
        } else {
            DrawFonts.Draw.drawString(cutScenePosX + 10, cutScenePosY + 70, I18n.format("cutscenes.common.next"),
                    14, 0xFFFFFFFF, Tensei.fonts.getFont("ptsans"), false,
                    true);
        }

        activeCutSceneDialogId = dialogId;
        activeCutSceneMode = mode;

        if(isClicked && hoverBtn) {
            onNext.run();
        }
        
        return true;
    }

    public static String parseText(String text) {
        return text.replace("{username}", Minecraft.getMinecraft().player.getName())
                    .replace("{ninja_card_btn}", Keyboard.getKeyName(Keys.NINJA_CARD_GUI_OPEN.getKeyCode()));
    }

    public static void showCutScene(String cutSceneId) {
        hasActiveCutscene = true;
        switch(cutSceneId) {
            case "tutorial":
                if(!(Minecraft.getMinecraft().currentScreen instanceof TutorialCutScene)) {
                    if(activeCutSceneDialogId != -1) {
                        Minecraft.getMinecraft().displayGuiScreen(new TutorialCutScene(activeCutSceneDialogId, activeCutSceneMode));
                    } else {
                        Minecraft.getMinecraft().displayGuiScreen(new TutorialCutScene());
                    }
                }
                break;
            case "first_release":
                if(!(Minecraft.getMinecraft().currentScreen instanceof FirstReleaseCutScene)) {
                    if(activeCutSceneDialogId != -1) {
                        Minecraft.getMinecraft().displayGuiScreen(new FirstReleaseCutScene(activeCutSceneDialogId));
                    } else {
                        Minecraft.getMinecraft().displayGuiScreen(new FirstReleaseCutScene());
                    }
                }
                break;
            default:
                hasActiveCutscene = false;
                break;
        }
    }

    public static void closeCutScene() {
        if(hasActiveCutscene) {
            Minecraft.getMinecraft().displayGuiScreen(null);
            activeCutSceneDialogId = -1;
            activeCutSceneMode = "";
        }
    }
}
