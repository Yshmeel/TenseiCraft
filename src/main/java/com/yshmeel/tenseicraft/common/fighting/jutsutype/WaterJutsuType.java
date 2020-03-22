package com.yshmeel.tenseicraft.common.fighting.jutsutype;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.common.fighting.jutsu.Jutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.water.WaterWallJutsu;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class WaterJutsuType extends JutsuTypeBase {
    @Override
    public void init() {
        HashMap<String, Jutsu> jutsu = new HashMap<>();
        jutsu.put("water_wall", new WaterWallJutsu());

        this.setId("water");
        this.setName("common.jutsutypes.water");
        this.setIcon(new ResourceLocation(Tensei.MODID, "textures/jutsutype/water.png"));
        this.setJutsu(jutsu);
    }
}
