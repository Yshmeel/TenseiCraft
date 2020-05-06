package com.yshmeel.tenseicraft.client.dialogs;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.client.Sounds;
import com.yshmeel.tenseicraft.client.dialogs.builder.DialogBuilder;
import com.yshmeel.tenseicraft.client.dialogs.builder.DialogText;
import com.yshmeel.tenseicraft.client.utils.DialogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Dialog extends GuiScreen {
    // переделать под двух и более персонажей
    public ArrayList<DialogText> dialogues = new ArrayList<>();
    public boolean isClicked = false;

    public int currentDialog = 0;
    public ResourceLocation DIALOG_HEAD = new ResourceLocation(Tensei.MODID, "textures/cutscene/six_path.png");
    public String DIALOG_TITLE = I18n.format("dialogs.tutorial.title");
    public Callable onNextDialog;
    public Callable onEndDialog;

    public Dialog(DialogBuilder dialog) {
        this.dialogues = dialog.dialogs;
        this.DIALOG_TITLE = dialog.name;
        this.DIALOG_HEAD = dialog.head;
        this.onNextDialog = dialog.afterNext;
        this.onEndDialog = dialog.afterEnd;
    }

    public Dialog(int currentDialog) {
        this.currentDialog = currentDialog;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if(this.mc != null) {
            this.drawDefaultBackground();
            DialogText dialog = dialogues.get(currentDialog);
            DialogUtils.renderDialog(dialog.head, dialog.name, dialog.text, () -> {
                if(currentDialog != dialogues.size() - 1) {
                    // @todo возможность менять звук
                    if(this.onNextDialog != null) {
                        try {
                            this.onNextDialog.call();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Minecraft.getMinecraft().player.playSound(dialog.speechVoice, 5.0f, 0.9F);
                    currentDialog ++;
                } else {
                    if(this.onEndDialog != null) {
                        try {
                            this.onEndDialog.call();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    DialogUtils.closeDialog();
                }
            }, mouseX, mouseY, this.isClicked, currentDialog, "dialog");

            this.isClicked = false;
        }

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