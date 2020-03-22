package com.yshmeel.tenseicraft.client.enums;

import com.yshmeel.tenseicraft.client.Keys;
import com.yshmeel.tenseicraft.client.utils.KeyboardUtils;
import org.lwjgl.input.Keyboard;

public enum JutsuSlots {
    SLOT1 ("{combo_key1} {combo_key2}"),
    SLOT2 ("{combo_key1} {combo_key2} {combo_key2}"),
    SLOT3 ("{combo_key2} {combo_key2} {combo_key1}"),
    SLOT4 ("{combo_key2} {combo_key1}"),
    SLOT5 ("{combo_key2} {combo_key1} {combo_key2}"),
    SLOT6 ("{combo_key1} {combo_key2} {combo_key1}");

    private String keys;

    JutsuSlots(String keys) {
        this.keys = keys;
    }

    public String getKeys() {
        return keys;
    }

    public String getKeyboardKeys() {
        String keys = this.getKeys();

        String[] splitedKeys = keys.split(" ");
        String newKeys = "";

        for(String key : splitedKeys) {
            newKeys += key.replace("{combo_key1}", KeyboardUtils.getKeyName(Keys.JUTSU_COMBO_1))
                    .replace("{combo_key2}", KeyboardUtils.getKeyName(Keys.JUTSU_COMBO_2)) + " ";
        }

        return newKeys.trim();
    }
}
