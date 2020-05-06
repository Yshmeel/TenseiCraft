package com.yshmeel.tenseicraft.client.utils;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.client.Keys;
import com.yshmeel.tenseicraft.client.dialogs.Dialog;
import com.yshmeel.tenseicraft.client.dialogs.TutorialDialog;
import com.yshmeel.tenseicraft.client.dialogs.builder.DialogBuilder;
import com.yshmeel.tenseicraft.client.gui.fonts.DrawFonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.Objects;
import java.util.concurrent.Callable;

public class DialogUtils {
    public static boolean hasActiveDialog = false;
    public static String activeDialogName = "";
    public static int activeDialogId = -1;
    public static String activeDialogMode = "";

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
            DrawFonts.Draw.drawString(cutScenePosX + 10, cutScenePosY + 70, I18n.format("dialogs.common.next"),
                    14, 0xFF968300, Tensei.fonts.getFont("ptsans"), false,
                    true);
        } else {
            DrawFonts.Draw.drawString(cutScenePosX + 10, cutScenePosY + 70, I18n.format("dialogs.common.next"),
                    14, 0xFFFFFFFF, Tensei.fonts.getFont("ptsans"), false,
                    true);
        }

        activeDialogId = dialogId;
        activeDialogMode = mode;

        if(isClicked && hoverBtn) {
            onNext.run();
        }
        
        return true;
    }

    public static String parseText(String text) {
        return text.replace("{username}", Minecraft.getMinecraft().player.getName())
                    .replace("{ninja_card_btn}", Keyboard.getKeyName(Keys.NINJA_CARD_GUI_OPEN.getKeyCode()));
    }

    public static void showDialog(DialogBuilder dialog) {
        Dialog gui = dialog.generate();

        hasActiveDialog = true;
        activeDialogName = dialog.dialogId;

        if(DialogUtils.activeDialogId != -1) {
            gui.currentDialog = DialogUtils.activeDialogId;
        } else {
            gui.currentDialog = 0;
        }
        Minecraft.getMinecraft().displayGuiScreen(gui);

    }

    public static void showDialog(String cutSceneId) {
        showDialog(cutSceneId, null);
    }

    public static void showDialog(String cutSceneId, Callable onEnd) {
        hasActiveDialog = true;
        switch(cutSceneId) {
            case "tutorial":
                if(!(Minecraft.getMinecraft().currentScreen instanceof TutorialDialog)) {
                    if(activeDialogId != -1) {
                        Minecraft.getMinecraft().displayGuiScreen(new TutorialDialog(activeDialogId, activeDialogMode));
                    } else {
                        Minecraft.getMinecraft().displayGuiScreen(new TutorialDialog());
                    }
                }
                break;
            default:
                hasActiveDialog = false;
                break;
        }
    }

    public static void closeDialog() {
        if(hasActiveDialog) {
            Minecraft.getMinecraft().displayGuiScreen(null);
            hasActiveDialog = false;
            activeDialogId = -1;
            activeDialogMode = "";
            activeDialogName = "";
        }
    }
}
