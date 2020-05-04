package com.yshmeel.tenseicraft.client.dialogs;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.client.Sounds;
import com.yshmeel.tenseicraft.client.gui.RegisterScreen;
import com.yshmeel.tenseicraft.client.gui.fonts.DrawFonts;
import com.yshmeel.tenseicraft.client.utils.DialogUtils;
import com.yshmeel.tenseicraft.client.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class TutorialDialog extends GuiScreen {
    public String[] firstDialogues = {
        I18n.format("dialogs.tutorial.step_first.dialog_1"),
        I18n.format("dialogs.tutorial.step_first.dialog_2"),
        I18n.format("dialogs.tutorial.step_first.dialog_3"),
        I18n.format("dialogs.tutorial.step_first.dialog_4"),
        I18n.format("dialogs.tutorial.step_first.dialog_5"),
        I18n.format("dialogs.tutorial.step_first.dialog_6"),
        I18n.format("dialogs.tutorial.step_second.dialog_7"),
        I18n.format("dialogs.tutorial.step_second.dialog_8"),
        I18n.format("dialogs.tutorial.step_second.dialog_9"),
        I18n.format("dialogs.tutorial.step_second.dialog_10")
    };

    public boolean isClicked = false;

    public int currentDialog = 0;
    public String currentMode = "dialog";
    public ResourceLocation DIALOG_HEAD = new ResourceLocation(Tensei.MODID, "textures/cutscene/six_path.png");
    public String DIALOG_TITLE = I18n.format("dialogs.tutorial.title");

    public TutorialDialog() {

    }

    public TutorialDialog(int currentDialog, String currentMode) {
        this.currentDialog = currentDialog;
        this.currentMode = currentMode;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if(currentMode.equals("dialog")) {
            this.drawDefaultBackground();
            DialogUtils.renderDialog(DIALOG_HEAD, DIALOG_TITLE, firstDialogues[currentDialog], () -> {
                if(currentDialog == 5) {
                    currentMode = "showcase";
                    currentDialog = 0;
                } else if(currentDialog == 8) {
                    Minecraft.getMinecraft().player.playSound(new SoundEvent(Sounds.NINJA_CARD_OPEN_SOUND), 5.0f, 0.9F);
                    Minecraft.getMinecraft().displayGuiScreen(new RegisterScreen());
                    currentDialog ++;
                } else if(currentDialog == 9) {
                    DialogUtils.closeDialog();
                } else {
                    Minecraft.getMinecraft().player.playSound(new SoundEvent(Sounds.HMM_CUTSCENE), 5.0f, 0.9F);
                    currentDialog ++;
                }
            }, mouseX, mouseY, this.isClicked, currentDialog, currentMode);
        } else if(currentMode.equals("showcase")) {
            DialogUtils.activeDialogMode = "showcase";
            switch(currentDialog) {
                case 0:
                    int hpBarX = 165;
                    int hpBarY = 50;

                    ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());

                    GuiUtils.renderArrow(hpBarX, hpBarY, 32, 14, 0.0f);
                    GL11.glColor3f(0,0,0);
                    Gui.drawRect(hpBarX + 35, hpBarY - 5, hpBarX + 180, hpBarY + 80, 0xFF000000);
                    DrawFonts.Draw.drawString(hpBarX + 40, hpBarY, String.format(I18n.format("cutscene.tutorial.showcase.bars")), 16,
                            0xFFFFFFFF, Tensei.fonts.getFont("ptsans"), false, true);

                    GL11.glColor3f(255, 255, 255);
                    GuiUtils.renderArrow(hpBarX - 130, hpBarY + 30, 32, 14, 0.0f);
                    GL11.glColor3f(0,0,0);
                    Gui.drawRect(hpBarX - 80, hpBarY + 35, hpBarX + 20, hpBarY + 40 + 90, 0xFF000000);
                    DrawFonts.Draw.drawString(hpBarX - 74, hpBarY + 41, String.format(I18n.format("cutscene.tutorial.showcase.icons")), 16,
                            0xFFFFFFFF, Tensei.fonts.getFont("ptsans"), false, true);

                    DrawFonts.Draw.drawString((resolution.getScaledWidth()/3) - 10, (resolution.getScaledHeight()/2) - 15,
                            String.format(I18n.format("cutscene.tutorial.showcase.enter_to_continue")), 36,
                            0xFFFFFFFF, Tensei.fonts.getFont("ptsans"), true, true);
                    break;
            }
        }

        this.isClicked = false;
    }

    @Override
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
        try {
            isClicked = true;
            super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
