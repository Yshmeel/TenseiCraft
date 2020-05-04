package com.yshmeel.tenseicraft.client.dialogs;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.client.Sounds;
import com.yshmeel.tenseicraft.client.utils.DialogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.io.IOException;
import java.util.concurrent.Callable;

public class IrukaTutorialDialog extends GuiScreen {
    public String[] dialogues = {
            I18n.format("dialogs.tutorial_progress.dialog_1"),
            I18n.format("dialogs.tutorial_progress.dialog_2"),
            I18n.format("dialogs.tutorial_progress.dialog_3")
    };

    public boolean isClicked = false;

    public int currentDialog = 0;
    public ResourceLocation DIALOG_HEAD = new ResourceLocation(Tensei.MODID, "textures/cutscene/npc_head.png");
    public String DIALOG_TITLE = I18n.format("dialogs.tutorial_progress.title");
    public Callable onEnd;

    public IrukaTutorialDialog(Callable onEnd) {
        this.onEnd = onEnd;
    }

    public IrukaTutorialDialog(int currentDialog) {
        this.currentDialog = currentDialog;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        DialogUtils.renderDialog(DIALOG_HEAD, DIALOG_TITLE, dialogues[currentDialog], () -> {
            if(currentDialog != dialogues.length - 1) {
                Minecraft.getMinecraft().player.playSound(new SoundEvent(Sounds.HMM_CUTSCENE), 5.0f, 0.9F);
                currentDialog ++;
            } else {
                try {
                    this.onEnd.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DialogUtils.closeDialog();
            }
        }, mouseX, mouseY, this.isClicked, currentDialog, "dialog");

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
