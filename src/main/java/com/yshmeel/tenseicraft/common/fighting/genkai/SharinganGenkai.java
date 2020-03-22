package com.yshmeel.tenseicraft.common.fighting.genkai;

import com.yshmeel.tenseicraft.Tensei;
import net.minecraft.util.ResourceLocation;

public class SharinganGenkai extends Genkai {

    @Override
    public void init() {
        this.setId("sharingan");
        this.setName("common.genkai.sharingan");
        this.setEyes(new ResourceLocation[] {
            new ResourceLocation(Tensei.MODID, "textures/player/test_eye.png")
        });

        super.init();
    }

    @Override
    public void setDefaultValues() {
        super.setDefaultValues();

        this.defaultValues.put("types", 3);
        this.defaultValues.put("time_to_unlock1type", 60F);
        this.defaultValues.put("time_to_unlock2type", 120F);
        this.defaultValues.put("time_to_unlock3type", 180F);
    }
}
