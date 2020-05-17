package com.yshmeel.tenseicraft.common.items.weapon;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.common.entities.EntityKunai;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class Kunai extends Item {
    private final float attackDamage;
    public Kunai() {
        super();

        this.setUnlocalizedName("tenseicraft.items.kunai");
        this.setRegistryName("kunai");
        this.setCreativeTab(Tensei.WEAPON_TAB);
        this.setMaxDamage(100);
        this.attackDamage = 2.0F;
    }
    public float getAttackDamage()
    {
        return this.attackDamage;
    }
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        stack.damageItem(1, attacker);
        return true;
    }
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        EntityKunai entity = new EntityKunai(worldIn, playerIn);
        worldIn.spawnEntity(entity);
        entity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 0.5F, 2.0F);
        itemstack.shrink(1);

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }
}
