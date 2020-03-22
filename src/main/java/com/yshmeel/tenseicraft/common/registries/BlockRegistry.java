package com.yshmeel.tenseicraft.common.registries;

import com.yshmeel.tenseicraft.common.blocks.jutsu.StaticWater;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRegistry {
    public static Block STATIC_WATER_JUTSU = new StaticWater();

    public static void register()
    {
        setRegister(STATIC_WATER_JUTSU);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRender()
    {
        setRender(STATIC_WATER_JUTSU);
    }

    private static void setRegister(Block block)
    {
        ForgeRegistries.BLOCKS.register(block);
        ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }

    @SideOnly(Side.CLIENT)
    private static void setRender(Block block)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0,
                new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }
}
