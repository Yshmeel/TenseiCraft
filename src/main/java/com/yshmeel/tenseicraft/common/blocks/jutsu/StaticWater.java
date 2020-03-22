package com.yshmeel.tenseicraft.common.blocks.jutsu;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class StaticWater extends Block {

    public StaticWater() {
        super(Material.ROCK);

        this.setRegistryName("static_water")
            .setUnlocalizedName("static_water")
            .setHardness(10000.0f);
    }
}
