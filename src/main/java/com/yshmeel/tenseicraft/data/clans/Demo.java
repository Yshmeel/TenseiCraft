package com.yshmeel.tenseicraft.data.clans;

import net.minecraft.util.ResourceLocation;

public class Demo extends Clan {
    public void init() {
        this.clanName = "Demo";
        this.type = 0;

        this.addAsset("icon", new ResourceLocation("/clans/icons/demo.png"));
    }
}
