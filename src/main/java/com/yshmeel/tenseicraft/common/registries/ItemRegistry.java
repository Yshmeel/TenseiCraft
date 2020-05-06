package com.yshmeel.tenseicraft.common.registries;

import com.yshmeel.tenseicraft.common.blocks.jutsu.StaticWater;
import com.yshmeel.tenseicraft.common.items.weapon.Kunai;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

public class ItemRegistry {
    public static Item KUNAI = new Kunai();

    public static void register()
    {
        setRegister(KUNAI);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRender()
    {
        setRender(KUNAI);
    }

    private static void setRegister(Item item)
    {
        ForgeRegistries.ITEMS.register(item);
    }

    @SideOnly(Side.CLIENT)
    private static void setRender(Item item)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0,
                new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), "inventory"));
    }
}
