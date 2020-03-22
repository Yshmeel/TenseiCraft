package com.yshmeel.tenseicraft.client.utils;

import com.yshmeel.tenseicraft.client.Keys;
import com.yshmeel.tenseicraft.client.enums.JutsuSlots;
import net.minecraft.client.settings.KeyBinding;

public class JutsuUtils {
    public static int COOLDOWN = 60; // 60 in ticks = 3 seconds
    public static int SLOTS = 6;

    public static boolean canWriteToHidden(String hiddenInput, KeyBinding key) {
        return (key.equals(Keys.JUTSU_COMBO_1) || key.equals(Keys.JUTSU_COMBO_2)) && hiddenInput.length() < 6;
    }

    public static int matchSlot(String hiddenInput) {
        for(int i = 1; i <= SLOTS; i++) {
            JutsuSlots slots = JutsuSlots.valueOf("SLOT" + i);

            if(slots.getKeyboardKeys().equals(hiddenInput.trim())) return i;
        }

        return -1;
    }
}
