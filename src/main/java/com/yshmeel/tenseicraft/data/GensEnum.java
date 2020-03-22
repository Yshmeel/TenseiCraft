package com.yshmeel.tenseicraft.data;

public enum GensEnum {
    SHARINGAN ("common.gens.sharingan", 120.0F, new String[] {
        "sharingan_bats",
        "sharingan_demo"
    });

    private String name;
    private float timeUnlock;
    private String[] jutsuIds;

    GensEnum(String name, float timeUnlock, String[] jutsuIds) {
        this.name = name;
        this.timeUnlock = timeUnlock;
        this.jutsuIds = jutsuIds;
    }

    public float getTimeUnlock() {
        return timeUnlock;
    }

    public String getName() {
        return name;
    }

    public String[] getJutsuIds() {
        return jutsuIds;
    }
}
