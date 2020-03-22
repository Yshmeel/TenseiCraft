package com.yshmeel.tenseicraft.client.utils;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class KeyboardUtils {
    public static String getKeyName(KeyBinding key) {
        if(key.getKeyCode() < 0) {
            return Mouse.getButtonName(key.getKeyCode());
        } else {
            return Keyboard.getKeyName(key.getKeyCode());
        }
    }
}
