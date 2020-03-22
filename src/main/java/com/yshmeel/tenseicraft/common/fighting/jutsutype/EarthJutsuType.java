package com.yshmeel.tenseicraft.common.fighting.jutsutype;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.common.fighting.jutsu.Jutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.earth.EarthCloneJutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.earth.EarthWallJutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.water.WaterWallJutsu;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class EarthJutsuType extends JutsuTypeBase {
    @Override
    public void init() {
        HashMap<String, Jutsu> jutsu = new HashMap<>();
        jutsu.put("earth_wall", new EarthWallJutsu());
        jutsu.put("earth_clone", new EarthCloneJutsu());

        this.setId("earth");
        this.setName("common.jutsutypes.earth");
        this.setIcon(new ResourceLocation(Tensei.MODID, "textures/jutsutype/earth.png"));
        this.setJutsu(jutsu);
    }
}
