package com.yshmeel.tenseicraft.data;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.data.clans.Clan;
import com.yshmeel.tenseicraft.data.clans.Demo;
import com.yshmeel.tenseicraft.data.clans.Null;
import com.yshmeel.tenseicraft.data.clans.Uchiha;

import java.util.ArrayList;
import java.util.HashMap;

public class ModInfo {
    public static boolean IS_DEV = true;
    public static boolean IS_SERVER = true;
    public static HashMap<Integer, Clan> clans = new HashMap<>();

    public static void createClans() {
        clans.put(0, new Null());
        clans.put(1, new Demo());
        clans.put(2, new Uchiha());

        for(HashMap.Entry<Integer, Clan> keyValue : clans.entrySet()) {
            keyValue.getValue().init();

            Tensei.logger.info(String.format("[Tensei] Clan %s was registered", keyValue.getValue().getClanName()));
        }
    }

    public static Clan getClan(int clan) {
        if(clans.get(clan) == null) {
            return new Null();
        } else {
            return clans.get(clan);
        }
    }
}
