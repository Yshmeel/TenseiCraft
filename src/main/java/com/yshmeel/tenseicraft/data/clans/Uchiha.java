package com.yshmeel.tenseicraft.data.clans;

import net.minecraft.util.ResourceLocation;

public class Uchiha extends Clan {
    public void init() {
        this.clanName = "Uchiha";
        this.type = 2;

        this.addAsset("icon", new ResourceLocation("/clans/icons/uchiha.png"));
    }
}
