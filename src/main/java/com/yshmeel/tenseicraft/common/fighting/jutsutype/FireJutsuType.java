package com.yshmeel.tenseicraft.common.fighting.jutsutype;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.common.fighting.jutsu.Jutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.fire.FireBallJutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.water.WaterWallJutsu;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class FireJutsuType extends JutsuTypeBase {
    @Override
    public void init() {
        HashMap<String, Jutsu> jutsu = new HashMap<>();
        jutsu.put("fire_ball", new FireBallJutsu());

        this.setId("fire");
        this.setName("common.jutsutypes.fire");
        this.setIcon(new ResourceLocation(Tensei.MODID, "textures/jutsutype/fire.png"));
        this.setJutsu(jutsu);
    }
}
