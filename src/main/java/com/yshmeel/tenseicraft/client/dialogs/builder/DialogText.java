package com.yshmeel.tenseicraft.client.dialogs.builder;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class DialogText {
    public String name;
    public String text;
    public ResourceLocation head;
    public SoundEvent speechVoice;

    /**
     * Creates dialog text with own head, own text, own speech voice
     * @param name - dialog title, only i18n!
     * @param text - dialog text, only i18n!!
     * @param head - dialog head, accepts only ResourceLocation
     * @param speechVoice - dialog speech voice, only SoundEvent
     */
    public DialogText(String name, String text, ResourceLocation head, SoundEvent speechVoice) {
        this.name = I18n.format(name);
        this.text = I18n.format(text);
        this.head = head;
        this.speechVoice = speechVoice;
    }
}
