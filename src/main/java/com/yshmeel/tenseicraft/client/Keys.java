package com.yshmeel.tenseicraft.client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class Keys {
    private static final String keyCategory = "Tensei Craft";
    public static final KeyBinding CHAKRA_CONTROL_BUTTON = new KeyBinding("key.chakra_control", Keyboard.KEY_L, keyCategory);
    public static final KeyBinding CHAKRA_FILL_BUTTON = new KeyBinding("key.chakra_fill", Keyboard.KEY_B, keyCategory);
    public static final KeyBinding NINJA_CARD_GUI_OPEN = new KeyBinding("key.ninja_card_gui", Keyboard.KEY_C, keyCategory);
    public static final KeyBinding JUTSU_COMBO_1 = new KeyBinding("key.jutsu_combo_1", Keyboard.KEY_Z, keyCategory);
    public static final KeyBinding JUTSU_COMBO_2 = new KeyBinding("key.jutsu_combo_2", Keyboard.KEY_X, keyCategory);
    public static void init() {
        ClientRegistry.registerKeyBinding(CHAKRA_CONTROL_BUTTON);
        ClientRegistry.registerKeyBinding(CHAKRA_FILL_BUTTON);
        ClientRegistry.registerKeyBinding(NINJA_CARD_GUI_OPEN);
        ClientRegistry.registerKeyBinding(JUTSU_COMBO_1);
        ClientRegistry.registerKeyBinding(JUTSU_COMBO_2);
    }
}
