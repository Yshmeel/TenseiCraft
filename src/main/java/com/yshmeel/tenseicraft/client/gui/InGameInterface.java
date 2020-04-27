package com.yshmeel.tenseicraft.client.gui;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.client.events.ClientHandler;
import com.yshmeel.tenseicraft.client.gui.fonts.DrawFonts;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class InGameInterface extends Gui {
    public static int[] NICKNAME_POS = {5, 25};
    protected static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("minecraft:textures/gui/widgets.png");
    private Minecraft mc = Minecraft.getMinecraft();
    protected RenderItem itemRenderer = null;
    public Random rand = new Random();
    private float zLevel = 0F;
    protected int updateCounter = 20;
    private long healthUpdateCounter;
    protected int playerHealth;
    protected int lastPlayerHealth;
    protected long lastSystemTime;

    public InGameInterface() {
        this.itemRenderer = this.mc.getRenderItem();
    }

    public void render(RenderGameOverlayEvent event) {
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        int[] EXPBAR_POS = {resolution.getScaledWidth(), resolution.getScaledHeight()};
//        DrawFonts.Draw.drawString(250, 250, Minecraft.getMinecraft().player.getName(), 16, 0x00000000,
//                Tensei.fonts.getFont("ptsans"));
        IPlayer player = Player.getInstance(Minecraft.getMinecraft().player);
        /* HP Bar render */
        GL11.glPushMatrix();
        DrawFonts.Draw.drawString((float) NICKNAME_POS[0]+3, (float) NICKNAME_POS[1]-5,
                Minecraft.getMinecraft().player.getName(), 20, 0xFFFFFFFF,
                Tensei.fonts.getFont("ptsans"), true, true);

        float playerHealth = Minecraft.getMinecraft().player.getHealth();

        playerHealth = (playerHealth/Minecraft.getMinecraft().player.getMaxHealth()) * 150;
        if(playerHealth > 150) {
            playerHealth = 150;
        }

        double chakra = player.getChakra();
        // @todo доделать максимум чакры, т.е сделать ниндзюцу, так же сделать улучшение макс чакры за счет улучшения уровня
        double maxChakra = player.getMaxChakra();

        chakra = (chakra/maxChakra) * 150;
        if(chakra > 150) {
            chakra = 150;
        }

        /* Draw hp bar */
        Gui.drawRect(NICKNAME_POS[0], NICKNAME_POS[1]+15, NICKNAME_POS[0]+150, NICKNAME_POS[1]+27, 0xFFFFFFFF);
        Gui.drawRect(NICKNAME_POS[0], NICKNAME_POS[1]+15, NICKNAME_POS[0]+(int) playerHealth, NICKNAME_POS[1]+27, 0xFFAA0000);

        Gui.drawRect(NICKNAME_POS[0], NICKNAME_POS[1]+35, NICKNAME_POS[0]+150, NICKNAME_POS[1]+47, 0xFFFFFFFF);
        Gui.drawRect(NICKNAME_POS[0], NICKNAME_POS[1]+35, NICKNAME_POS[0]+(int) chakra, NICKNAME_POS[1]+47, 0xFF0000CC);
        /* HP Bar render ends */

        GL11.glPopMatrix();

        /* Draw some icons */

        GL11.glPushMatrix();

        int iconPosX = NICKNAME_POS[0];
        int iconPosY = NICKNAME_POS[1]+55;

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor3f(255, 255, 255);

        if(player.isChakraFillModeEnabled()) {
            Minecraft.getMinecraft().renderEngine.bindTexture(
                    new ResourceLocation("tenseicraft", "textures/icons/yoga.png")
            );

            Gui.drawModalRectWithCustomSizedTexture(iconPosX, iconPosY, 0, 0, 16, 12, 16, 12);

            iconPosX += 16;
        }

        if(player.isChakraControlEnabled()) {
            Minecraft.getMinecraft().renderEngine.bindTexture(
                    new ResourceLocation("tenseicraft", "textures/icons/control.png")
            );

            Gui.drawModalRectWithCustomSizedTexture(iconPosX, iconPosY, 0, 0, 16, 12, 16, 12);
        }

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        /* Draw some icons ends */

        /* EXP Bar render */
        double maxEXP = player.getMaxEXP();
        DrawFonts.Draw.drawString(7, EXPBAR_POS[1]-26,
                "Lvl. " + player.getLevel(), 17, 0xFFFFFFFF,
                Tensei.fonts.getFont("ptsans"), true, true);

        int xpRightPos = (int) Math.round(((player.getEXP()/maxEXP) * (EXPBAR_POS[0] - 5)));

        if(xpRightPos < 5) {
            xpRightPos = 5;
        }

        Gui.drawRect(5, EXPBAR_POS[1]-14, EXPBAR_POS[0]-5, EXPBAR_POS[1]-7, 0x99000000);
        Gui.drawRect(5, EXPBAR_POS[1]-14, xpRightPos, EXPBAR_POS[1]-7, 0xFFFFA500);

        /* EXP Bar render ends */

        /* Render jutsu buttons */
        int additionalJutsuX = 0;
        String[] jutsuInputSplit = ClientHandler.hiddenInput.trim().split(" ");
        for(String jutsuInputKey : jutsuInputSplit) {
            if(jutsuInputKey.equals("")) continue;

            GL11.glColor3f(0,0, 0);
            DrawFonts.Draw.drawString(7 + additionalJutsuX, EXPBAR_POS[1]-70,
                    jutsuInputKey, 28, 0xFFFFFFFF,
                    Tensei.fonts.getFont("ptsans"), true, true);
            additionalJutsuX += 18;
        }

    }

    public void renderHotBar(RenderGameOverlayEvent.Pre event) {
        ScaledResolution sr = new ScaledResolution(this.mc);
        int height = sr.getScaledHeight() - 13;
        if (this.mc.getRenderViewEntity() instanceof EntityPlayer)
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(WIDGETS_TEX_PATH);
            EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
            ItemStack itemstack = entityplayer.getHeldItemOffhand();
            EnumHandSide enumhandside = entityplayer.getPrimaryHand().opposite();
            int i = sr.getScaledWidth() / 2;
            float f = this.zLevel;
            int j = 182;
            int k = 91;
            this.zLevel = -90.0F;
            this.drawTexturedModalRect(i - 91, height - 22, 0, 0, 182, 22);
            this.drawTexturedModalRect(i - 91 - 1 + entityplayer.inventory.currentItem * 20, height - 22 - 1, 0, 22, 24, 22);

            if (!itemstack.isEmpty())
            {
                if (enumhandside == EnumHandSide.LEFT)
                {
                    this.drawTexturedModalRect(i - 91 - 29, height - 23, 24, 22, 29, 24);
                }
                else
                {
                    this.drawTexturedModalRect(i + 91, height - 23, 53, 22, 29, 24);
                }
            }

            this.zLevel = f;
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            RenderHelper.enableGUIStandardItemLighting();

            for (int l = 0; l < 9; ++l)
            {
                int i1 = i - 90 + l * 20 + 2;
                int j1 = height - 16 - 3;
                this.renderHotbarItem(i1, j1, 0.0F, entityplayer, entityplayer.inventory.mainInventory.get(l));
            }

            if (!itemstack.isEmpty())
            {
                int l1 = height - 16 - 3;

                if (enumhandside == EnumHandSide.LEFT)
                {
                    this.renderHotbarItem(i - 91 - 26, l1, 0.0F, entityplayer, itemstack);
                }
                else
                {
                    this.renderHotbarItem(i + 91 + 10, l1, 0.0F, entityplayer, itemstack);
                }
            }

            if (this.mc.gameSettings.attackIndicator == 2)
            {
                float f1 = this.mc.player.getCooledAttackStrength(0.0F);

                if (f1 < 1.0F)
                {
                    int i2 = height - 20;
                    int j2 = i + 91 + 6;

                    if (enumhandside == EnumHandSide.RIGHT)
                    {
                        j2 = i - 91 - 22;
                    }

                    this.mc.getTextureManager().bindTexture(Gui.ICONS);
                    int k1 = (int)(f1 * 19.0F);
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    this.drawTexturedModalRect(j2, i2, 0, 94, 18, 18);
                    this.drawTexturedModalRect(j2, i2 + 18 - k1, 18, 112 - k1, 18, k1);
                }
            }

            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }
    }

    protected void renderHotbarItem(int p_184044_1_, int p_184044_2_, float p_184044_3_, EntityPlayer player, ItemStack stack)
    {
        if (!stack.isEmpty())
        {
            float f = (float)stack.getAnimationsToGo() - p_184044_3_;

            if (f > 0.0F)
            {
                GlStateManager.pushMatrix();
                float f1 = 1.0F + f / 5.0F;
                GlStateManager.translate((float)(p_184044_1_ + 8), (float)(p_184044_2_ + 12), 0.0F);
                GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                GlStateManager.translate((float)(-(p_184044_1_ + 8)), (float)(-(p_184044_2_ + 12)), 0.0F);
            }

            this.itemRenderer.renderItemAndEffectIntoGUI(player, stack, p_184044_1_, p_184044_2_);

            if (f > 0.0F)
            {
                GlStateManager.popMatrix();
            }

            this.itemRenderer.renderItemOverlays(this.mc.fontRenderer, stack, p_184044_1_, p_184044_2_);
        }
    }

    public void renderPlayerStats(RenderGameOverlayEvent.Pre event) {
       if(!this.mc.player.isCreative()) {
           ScaledResolution scaledRes = new ScaledResolution(this.mc);
           int height = scaledRes.getScaledHeight() - 15;
           if (this.mc.getRenderViewEntity() instanceof EntityPlayer)
           {
               EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
               int i = MathHelper.ceil(entityplayer.getHealth());
               boolean flag = this.healthUpdateCounter > (long)this.updateCounter && (this.healthUpdateCounter - (long)this.updateCounter) / 3L % 2L == 1L;

               if (i < this.playerHealth && entityplayer.hurtResistantTime > 0)
               {
                   this.lastSystemTime = Minecraft.getSystemTime();
                   this.healthUpdateCounter = (long)(this.updateCounter + 20);
               }
               else if (i > this.playerHealth && entityplayer.hurtResistantTime > 0)
               {
                   this.lastSystemTime = Minecraft.getSystemTime();
                   this.healthUpdateCounter = (long)(this.updateCounter + 10);
               }

               if (Minecraft.getSystemTime() - this.lastSystemTime > 1000L)
               {
                   this.playerHealth = i;
                   this.lastPlayerHealth = i;
                   this.lastSystemTime = Minecraft.getSystemTime();
               }

               this.playerHealth = i;
               int j = this.lastPlayerHealth;
               this.rand.setSeed((long)(this.updateCounter * 312871));
               FoodStats foodstats = entityplayer.getFoodStats();
               int k = foodstats.getFoodLevel();
               IAttributeInstance iattributeinstance = entityplayer.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
               int l = scaledRes.getScaledWidth() / 2 - 91;
               int i1 = scaledRes.getScaledWidth() / 2 + 91;
               int j1 = height - 39;
               float f = (float)iattributeinstance.getAttributeValue();
               int k1 = MathHelper.ceil(entityplayer.getAbsorptionAmount());
               int l1 = MathHelper.ceil((f + (float)k1) / 2.0F / 10.0F);
               int i2 = Math.max(10 - (l1 - 2), 3);
               int j2 = j1 - (l1 - 1) * i2 - 10;
               int k2 = j1 - 10;
               int l2 = k1;
               int i3 = entityplayer.getTotalArmorValue();
               int j3 = -1;

               if (entityplayer.isPotionActive(MobEffects.REGENERATION))
               {
                   j3 = this.updateCounter % MathHelper.ceil(f + 5.0F);
               }

               this.mc.mcProfiler.startSection("armor");

               for (int k3 = 0; k3 < 10; ++k3)
               {
                   if (i3 > 0)
                   {
                       int l3 = l + k3 * 8;

                       if (k3 * 2 + 1 < i3)
                       {
                           this.drawTexturedModalRect(l3, j2, 34, 9, 9, 9);
                       }

                       if (k3 * 2 + 1 == i3)
                       {
                           this.drawTexturedModalRect(l3, j2, 25, 9, 9, 9);
                       }

                       if (k3 * 2 + 1 > i3)
                       {
                           this.drawTexturedModalRect(l3, j2, 16, 9, 9, 9);
                       }
                   }
               }

               Entity entity = entityplayer.getRidingEntity();

               if (entity == null || !(entity instanceof EntityLivingBase))
               {
                   this.mc.mcProfiler.endStartSection("food");

                   for (int l5 = 0; l5 < 10; ++l5)
                   {
                       int j6 = j1;
                       int l6 = 16;
                       int j7 = 0;

                       if (entityplayer.isPotionActive(MobEffects.HUNGER))
                       {
                           l6 += 36;
                           j7 = 13;
                       }

                       if (entityplayer.getFoodStats().getSaturationLevel() <= 0.0F && this.updateCounter % (k * 3 + 1) == 0)
                       {
                           j6 = j1 + (this.rand.nextInt(3) - 1);
                       }

                       int l7 = i1 - l5 * 8 - 9;
                       this.drawTexturedModalRect(l7, j6, 16 + j7 * 9, 27, 9, 9);

                       if (l5 * 2 + 1 < k)
                       {
                           this.drawTexturedModalRect(l7, j6, l6 + 36, 27, 9, 9);
                       }

                       if (l5 * 2 + 1 == k)
                       {
                           this.drawTexturedModalRect(l7, j6, l6 + 45, 27, 9, 9);
                       }
                   }
               }

               this.mc.mcProfiler.endStartSection("air");

               if (entityplayer.isInsideOfMaterial(Material.WATER))
               {
                   int i6 = this.mc.player.getAir();
                   int k6 = MathHelper.ceil((double)(i6 - 2) * 10.0D / 300.0D);
                   int i7 = MathHelper.ceil((double)i6 * 10.0D / 300.0D) - k6;

                   for (int k7 = 0; k7 < k6 + i7; ++k7)
                   {
                       if (k7 < k6)
                       {
                           this.drawTexturedModalRect(i1 - k7 * 8 - 9, k2, 16, 18, 9, 9);
                       }
                       else
                       {
                           this.drawTexturedModalRect(i1 - k7 * 8 - 9, k2, 25, 18, 9, 9);
                       }
                   }
               }

               this.mc.mcProfiler.endSection();
           }
       }
    }
}
