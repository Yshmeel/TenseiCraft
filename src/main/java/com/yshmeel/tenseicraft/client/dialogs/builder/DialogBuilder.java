package com.yshmeel.tenseicraft.client.dialogs.builder;

import com.yshmeel.tenseicraft.client.dialogs.Dialog;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class DialogBuilder {
    public String dialogId = null;
    public ArrayList<DialogText> dialogs = new ArrayList<>();
    public ResourceLocation head = null;
    public String name = null;
    public SoundEvent speechVoice = null;
    public Callable afterNext = null;
    public Callable afterEnd = null;
    public boolean canSkip = true;

    public DialogBuilder addDialog(String name, String dialog, ResourceLocation head, SoundEvent speechVoice) {
        this.dialogs.add(new DialogText((name == null ? this.name : name), dialog, (head == null ? this.head : head),
               (speechVoice != null ? speechVoice : this.speechVoice)));
        return this;
    }

    public DialogBuilder setCanSkip(boolean canSkip) {
        this.canSkip = canSkip;
        return this;
    }

    public DialogBuilder setDefaultHead(ResourceLocation head) {
        this.head = head;
        return this;
    }

    public DialogBuilder setDefaultName(String name) {
        this.name = name;
        return this;
    }
    public DialogBuilder setDefaultSpeechVoice(SoundEvent speechVoice) {
        this.speechVoice = speechVoice;
        return this;
    }
    public DialogBuilder setAfterEndCallback(Callable callback) {
        this.afterEnd = callback;
        return this;
    }
    public DialogBuilder setAfterNextCallback(Callable callback) {
        this.afterNext = callback;
        return this;
    }
    public DialogBuilder setId(String id) {
        this.dialogId = id;
        return this;
    }

    public Dialog generate() {
        Dialog gui = new Dialog(this);

        return gui;
    }
}
