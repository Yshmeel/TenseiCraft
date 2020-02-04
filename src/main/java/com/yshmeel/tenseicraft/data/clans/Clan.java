package com.yshmeel.tenseicraft.data.clans;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public abstract class Clan {
    protected String clanName;
    protected int type = 0; // 0 - unrare, 1 - medium rare, 2 - ultra rare(donate clans)

    protected HashMap<String, ResourceLocation> assets = new HashMap<>();

    public void init() { }

    public boolean addAsset(String assetName, ResourceLocation resourceLocation) {
        assets.put(assetName, resourceLocation);

        return true;
    }

    public HashMap<String, ResourceLocation> getAssets() {
        return assets;
    }

    public String getClanName() {
        return clanName;
    }
}
