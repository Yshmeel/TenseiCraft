package com.yshmeel.tenseicraft.client.dialogs;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.client.Sounds;
import com.yshmeel.tenseicraft.client.dialogs.builder.DialogBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.util.HashMap;

public class DialogList {
    public static DialogBuilder IRUKA_TUTORIAL = new DialogBuilder()
            .setId("iruka_tutorial")
            .setDefaultHead(new ResourceLocation(Tensei.MODID, "textures/dialogs/iruka_head.png"))
            .setDefaultName("dialogs.tutorial_progress.title")
            .setDefaultSpeechVoice(new SoundEvent(Sounds.HMM_CUTSCENE))
            .setCanSkip(false)
            .setAfterEndCallback(() -> {
                System.out.println("ended dialog with iruka");

                return true;
                // @todo выдавать квест чтоль
            })
            .addDialog(null, "dialogs.tutorial_progress.dialog_1", null, null)
            .addDialog(null, "dialogs.tutorial_progress.dialog_2", null, null)
            .addDialog(null, "dialogs.tutorial_progress.dialog_3", null, null);

    public static DialogBuilder FIRST_RELEASE = new DialogBuilder()
            .setId("first_release")
            .setDefaultHead(new ResourceLocation(Tensei.MODID, "textures/cutscene/six_path.png"))
            .setDefaultName("dialogs.tutorial.title")
            .setDefaultSpeechVoice(new SoundEvent(Sounds.HMM_CUTSCENE))
            .setCanSkip(false)
            .addDialog(null, "dialogs.first_release.dialog_1", null, null)
            .addDialog(null, "dialogs.first_release.dialog_2", null, null)
            .addDialog(null, "dialogs.first_release.dialog_3", null, null);

    public static HashMap<String, DialogBuilder> registeredDialogs = new HashMap<>();

    public static void register() {
        registeredDialogs.put(IRUKA_TUTORIAL.dialogId, IRUKA_TUTORIAL);
        registeredDialogs.put(FIRST_RELEASE.dialogId, FIRST_RELEASE);
    }
}
