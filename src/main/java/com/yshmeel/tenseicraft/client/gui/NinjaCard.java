package com.yshmeel.tenseicraft.client.gui;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.client.enums.JutsuSlots;
import com.yshmeel.tenseicraft.client.gui.fonts.DrawFonts;
import com.yshmeel.tenseicraft.common.fighting.jutsu.IJutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.Jutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsutype.IJutsuType;
import com.yshmeel.tenseicraft.common.packets.PacketDispatcher;
import com.yshmeel.tenseicraft.common.packets.PacketLearnJutsuTypeMessage;
import com.yshmeel.tenseicraft.common.packets.PacketSetJutsuSlotsMessage;
import com.yshmeel.tenseicraft.common.packets.PacketUpdateStatsMessage;
import com.yshmeel.tenseicraft.data.ModInfo;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.TextureImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class NinjaCard extends GuiScreen {
    private int guiX;
    private int guiY;
    private int[] BUTTON_SECTION_POS = {0, 0};
    private int[] CONTAINER_SECTION_POS = {0, 0};
    private int mouseX = 0;
    private int mouseY = 0;
    private ResourceLocation skin;
    private String currentTab = "info";
    private boolean isClicked = false;
    public int currentScroll = 0;
    public boolean isScrolling = false;
    public int scrollAllHeight = 0;
    public int scrollMax = 0;

    public boolean selectSlot = false;
    public IJutsu jutsuSelected = null;
    public int updateCooldown = 0;
    public boolean isTooltipEnabled = false;

    public ResourceLocation texture = new ResourceLocation("tenseicraft:textures/gui/ninjacard_body.png");

    public NinjaCard() {
        super();

        IPlayer player = Player.getInstance(Minecraft.getMinecraft().player);
        this.skin = AbstractClientPlayer.getLocationSkin(Minecraft.getMinecraft().player.getName());
        AbstractClientPlayer.getDownloadImageSkin(this.skin, Minecraft.getMinecraft().player.getName());
    }


    public void initGui() {
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        int xSize = 384;
        int ySize = 292;
        float scale = 1.0F;

        guiX = (Math.round((width / 2) / scale) - Math.round(xSize / 2));
        guiY = (Math.round((height / 2) / scale) - Math.round(ySize / 2));
        CONTAINER_SECTION_POS[0] = guiX - 10;
        CONTAINER_SECTION_POS[1] = guiY + 36;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawDefaultBackground();

        this.mouseX = mouseX;
        this.mouseY = mouseY;
        if(this.updateCooldown > 0) {
            this.updateCooldown--;
        }

        IPlayer player = Player.getInstance(Minecraft.getMinecraft().player);
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        int mcHeight = Minecraft.getMinecraft().displayHeight;
        this.scrollAllHeight = ((resolution.getScaledHeight()+mcHeight)/6)+76;
        this.drawDefaultBackground();
        GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
        this.mc.renderEngine.bindTexture(this.texture);
        this.drawModalRectWithCustomSizedTexture(guiX, guiY, 0, 0, 384, 292, 384, 292);

        BUTTON_SECTION_POS[0] = guiX - 30;
        BUTTON_SECTION_POS[1] = guiY + 6;

        CONTAINER_SECTION_POS[0] = guiX - 10;
        CONTAINER_SECTION_POS[1] = BUTTON_SECTION_POS[1] + 30;

        this.renderButtons();
        if(this.currentTab.equals("info")) {
            this.renderInfo(player);
        } else if(this.currentTab.equals("jutsu_learn")) {
            this.renderLearn(player);
        } else if(this.currentTab.equals("jutsu_backpack")) {
            this.renderBackpack(player);
        }
        isClicked = false;
    }

    public void renderButtons() {
        new Button(this.BUTTON_SECTION_POS[0], this.BUTTON_SECTION_POS[1] - 8, I18n.format("common.ninja_card.card_btn"),
                () -> {
                    this.currentTab = "info";
                }, (this.currentTab.equals("info")), this.mouseX, this.mouseY, isClicked, 30).render();

        new Button(this.BUTTON_SECTION_POS[0] + 80, this.BUTTON_SECTION_POS[1] - 8, I18n.format("common.ninja_card.learn_jutsu_btn"),
                () -> {
                    this.currentTab = "jutsu_learn";
                }, (this.currentTab.equals("jutsu_learn")), this.mouseX, this.mouseY, isClicked, 15).render();

        new Button(this.BUTTON_SECTION_POS[0] + 160, this.BUTTON_SECTION_POS[1] - 8, I18n.format("common.ninja_card.backpack_btn"),
                () -> {
                    this.currentTab = "jutsu_backpack";
                }, (this.currentTab.equals("jutsu_backpack")), this.mouseX, this.mouseY, isClicked, 35).render();
    }

    public void renderBackpack(IPlayer player) {
        DrawFonts.Draw.drawString(this.CONTAINER_SECTION_POS[0] + 30, this.CONTAINER_SECTION_POS[1] + 45,
                I18n.format("common.ninja_card.current_slots"), 14, 0xFF000000,
                Tensei.fonts.getFont("naruto"), false, true);

        GL11.glPushMatrix();
        this.renderSlotsHotbar(this.CONTAINER_SECTION_POS[0] + 30, this.CONTAINER_SECTION_POS[1] + 65, player);
        GL11.glPopMatrix();

        DrawFonts.Draw.drawString(this.CONTAINER_SECTION_POS[0] + 30, this.CONTAINER_SECTION_POS[1] + 105,
                I18n.format("common.ninja_card.all_slots"), 14, 0xFF000000,
                Tensei.fonts.getFont("naruto"), false, true);

        GL11.glPushMatrix();
        this.renderAllSlots(this.CONTAINER_SECTION_POS[0] + 30, this.CONTAINER_SECTION_POS[1] + 130, player);
        GL11.glPopMatrix();
    }

    public void renderLearn(IPlayer player) {
        int iter = 0;

        int x = this.CONTAINER_SECTION_POS[0] + 14;
        int y = this.CONTAINER_SECTION_POS[1] + 45;

        if(player.putJutsuTypesToArrayList().size() == 0) {
            DrawFonts.Draw.drawString(x + 15, y + 25,
                    String.format(I18n.format("common.ninja_card.learns_not_found")),
                    14, 0xFF000000, Tensei.fonts.getFont("naruto"), false, true);
        }


        for(HashMap.Entry<String, IJutsuType> entry : ModInfo.jutsuTypes.entrySet()) {
            IJutsuType release = entry.getValue();

            if(!player.hasJutsuType(release.getId())) continue;

            this.renderLearnRelease(x, y, player, release, iter);

            y += 28;
            iter ++;
        }
    }

    public void renderLearnRelease(int x, int y, IPlayer player, IJutsuType release, int key) {
        int bgColor = key%2 == 0 ? 0x99968300 : 0x11FFFFFF;

        Gui.drawRect(x, y, x + 377, y + 25, bgColor);

        DrawFonts.Draw.drawString(x + 15, y + 7,
                I18n.format(release.getName()), 14, 0xFFFFFFFF,
                Tensei.fonts.getFont("naruto"), false, true);

        HashMap<String, Jutsu> jutsuMap = release.getJutsu();

        int additionalJutsuX = x + 60;

        for(HashMap.Entry<String, Jutsu> jutsu : jutsuMap.entrySet()) {
            Jutsu jutsuData = jutsu.getValue();
            int jutsuBgColor = player.isJutsuLearned(jutsuData.getId()) ? 0xF0018000 : 0xF1800000;

            Gui.drawRect(additionalJutsuX, y + 5, additionalJutsuX + 12, y + 5 + 12, jutsuBgColor);

            this.renderJutsuTooltip(additionalJutsuX, y + 5,
                    I18n.format("common.ninja_card.learn", I18n.format(jutsuData.getName())),
                    I18n.format((player.isJutsuLearned(jutsuData.getId()) ? "common.ninja_card.learn_description_learned" :
                                    "common.ninja_card.learn_description"),
                            I18n.format(jutsuData.getDescription()), jutsuData.getPointsLearn()), 12, 12);

            additionalJutsuX += 20;
        }

        GL11.glColor4f(0, 0, 0, 1f);

        IJutsu nextJutsu = release.getNextToLearn(player);

        if(nextJutsu != null) {
            int plusButtonX = x + 350;
            int plusButtonY = y + 5;

            DrawFonts.Draw.drawString(plusButtonX, plusButtonY, "+", 16, 0xFFFFFFFF,
                    Tensei.fonts.getFont("naruto"), true, true);

            this.renderJutsuTooltip(plusButtonX, plusButtonY, I18n.format("common.ninja_card.learn.plus_button_title"),
                    I18n.format((
                            player.getJutsuPoints() >= nextJutsu.getPointsLearn() ?
                                    "common.ninja_card.learn.plus_button_has_jp" :
                                    "common.ninja_card.learn.plus_button_hasnt_jp"
                    )), 16, 16);

            if(this.updateCooldown == 0 && isClicked && (this.mouseX > plusButtonX && this.mouseX < plusButtonX + 16
                    && this.mouseY > plusButtonY && this.mouseY < plusButtonY + 16)) {
                this.updateCooldown = 100;
                PacketDispatcher.sendToServer(
                        new PacketLearnJutsuTypeMessage(release.getId())
                );
            }
        }

    }

    public void renderSlotsHotbar(int x, int y, IPlayer player) {
        int additionalMargin = 0;

        for(int i = 1; i <= 6; i++) {
            int hotbarSlotX = x + additionalMargin;
            int hotbarSlotY = y;
            IJutsu jutsu = ModInfo.getJutsu(player.getJutsuSlots().get(i - 1));

            GL11.glPushMatrix();
            if(this.selectSlot && jutsu == null && isClicked && (this.mouseX > hotbarSlotX && this.mouseX < hotbarSlotX + 30
                    && this.mouseY > hotbarSlotY && this.mouseY < hotbarSlotY + 33)) {
                this.selectSlot = false;

                PacketDispatcher.sendToServer(
                        new PacketSetJutsuSlotsMessage(jutsuSelected.getId() + "&" + i)
                );

                continue;
            } else if(this.updateCooldown == 0 && !this.selectSlot && isClicked && (this.mouseX > hotbarSlotX && this.mouseX < hotbarSlotX + 30
                    && this.mouseY > hotbarSlotY && this.mouseY < hotbarSlotY + 33)) {
                PacketDispatcher.sendToServer(
                        new PacketSetJutsuSlotsMessage(null + "&" + i)
                );

                this.updateCooldown = 60;

                continue;
            }
            GL11.glColor3f(0, 0, 0);
            if((this.mouseX > hotbarSlotX && this.mouseX < hotbarSlotX + 30
                    && this.mouseY > hotbarSlotY && this.mouseY < hotbarSlotY + 33)) {
                Gui.drawRect(hotbarSlotX, hotbarSlotY, hotbarSlotX + 30, hotbarSlotY + 33,
                        0xFF968300);
            } else {
                Gui.drawRect(hotbarSlotX, hotbarSlotY, hotbarSlotX + 30, hotbarSlotY + 33,
                        0xFF000000);
            }

            Gui.drawRect(x + additionalMargin, y, x + additionalMargin + 30, y + 33,
                    (this.selectSlot && jutsu == null ? 0xFF968300: 1711276032));

            if(jutsu != null) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(
                        jutsu.getIcon()
                );
                GL11.glColor3f(255, 255, 255);
                Gui.drawModalRectWithCustomSizedTexture(hotbarSlotX + 3, y + 4, 0, 0, 25, 25, 25, 25);
            }
            GL11.glPopMatrix();

            GL11.glColor4f(-1, -1, -1, 0.4f);
            this.renderTooltip(hotbarSlotX, hotbarSlotY, I18n.format("common.ninja_card.slot") + " " + i,
                    String.format(I18n.format("common.ninja_card.slot_description",
                            i, JutsuSlots.valueOf("SLOT" + i).getKeyboardKeys())), 30, 33);


            additionalMargin += 61;
        }
    }

    public void renderAllSlots(int x, int y, IPlayer player) {
        int additionalMargin = 0;
        int additionalY = 0;
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int mcWidth = Minecraft.getMinecraft().displayWidth;
        int currentXDefault = ((sr.getScaledWidth()+mcWidth)/6)-(128/3)-142;
        int currentX = currentXDefault;
        int boxWidth = 30;
        int boxHeight = 33;

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(currentXDefault, (boxHeight*4),
                currentXDefault+boxWidth*20,
                currentXDefault+boxHeight*20);
        GL11.glColor3f(0, 0, 0);
        for(int i = 0; i < player.getLearnedJutsu().size(); i++) {
            IJutsu jutsu = ModInfo.getJutsu(player.getLearnedJutsu().get(i));

            if(jutsu == null) continue;

            if (player.isJutsuInHotbar(jutsu.getId())) continue;
            if(i%6 == 0 && i != 0) {
                additionalY += 40;
                additionalMargin = 0;
            }

            int buttonX = x + additionalMargin;
            int buttonY = y + additionalY;
            GL11.glColor3f(0,0,0);
            if((this.mouseX > buttonX && this.mouseX < buttonX + 30
                    && this.mouseY > buttonY && this.mouseY < buttonY + 33)) {
                Gui.drawRect(buttonX, buttonY, buttonX + 30, buttonY + 33,
                        0xFF968300);
            } else {
                Gui.drawRect(buttonX, buttonY, buttonX + 30, buttonY + 33,
                        1711276032);
            }


            if( this.updateCooldown == 0 && isClicked && (this.mouseX > buttonX && this.mouseX < buttonX + 30
                    && this.mouseY > buttonY && this.mouseY < buttonY + 33)) {
                // @todo доделать!
                this.selectSlot = true;
                this.jutsuSelected = jutsu;
                this.updateCooldown = 100;
                continue;
            }

            GL11.glPushMatrix();
            GL11.glColor3f(255, 255, 255);
            Minecraft.getMinecraft().getTextureManager().bindTexture(
                    jutsu.getIcon()
            );
            Gui.drawModalRectWithCustomSizedTexture(buttonX + 3, buttonY + 4, 0, 0, 25, 25, 25, 25);
            GL11.glPopMatrix();

            this.renderTooltip(buttonX, buttonY, I18n.format(jutsu.getName()),
                    I18n.format(jutsu.getDescription()), 30, 33);

            additionalMargin += 61;
            additionalMargin += 61;
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
    }

    public void renderInfo(IPlayer player) {

        // render left side
        Minecraft.getMinecraft().getTextureManager().bindTexture(
            this.skin
        );

        GL11.glPushMatrix();

        GL11.glColor3f(255F,255F, 255F);
        GL11.glScalef(2.5F, 2.5F, 2.5F);

        Gui.drawModalRectWithCustomSizedTexture((int) (this.CONTAINER_SECTION_POS[0] / 2.5F) + 15,
                (int) (this.CONTAINER_SECTION_POS[1] / 2.5F) + 17,
                16, 16, 16, 16, 128, 128);

        GL11.glPopMatrix();

        DrawFonts.Draw.drawString(this.CONTAINER_SECTION_POS[0] + 35, this.CONTAINER_SECTION_POS[1] + 100,
                Minecraft.getMinecraft().player.getName() + " " + player.getLastName(), 14, 0xFF000000,
                Tensei.fonts.getFont("naruto"), false, true);

        DrawFonts.Draw.drawString(this.CONTAINER_SECTION_POS[0] + 35, this.CONTAINER_SECTION_POS[1] + 121,
                I18n.format("common.ninja_card.rank") + ": ", 14, 0xFF000000,
                Tensei.fonts.getFont("naruto"), false, true);
        DrawFonts.Draw.drawString(this.CONTAINER_SECTION_POS[0] + 59, this.CONTAINER_SECTION_POS[1] + 121,
                player.getRankName(), 14, 0xFF968300,
                Tensei.fonts.getFont("naruto"), false, true);

        DrawFonts.Draw.drawString(this.CONTAINER_SECTION_POS[0] + 35, this.CONTAINER_SECTION_POS[1] + 133,
                I18n.format("common.ninja_card.village") + ": ", 14, 0xFF000000,
                Tensei.fonts.getFont("naruto"), false, true);
        DrawFonts.Draw.drawString(this.CONTAINER_SECTION_POS[0] + 70, this.CONTAINER_SECTION_POS[1] + 133,
                "no", 14, 0xFF968300,
                Tensei.fonts.getFont("naruto"), false, true);

        DrawFonts.Draw.drawString(this.CONTAINER_SECTION_POS[0] + 35, this.CONTAINER_SECTION_POS[1] + 175,
                I18n.format("common.ninja_card.learned_releases_title"), 20, 0xFF968300,
                Tensei.fonts.getFont("naruto"), false, true);

        // render all releases

        ArrayList<String> jutsuTypes = player.putJutsuTypesToArrayList();
        int jutsuAdditionalMargin = 0;

        for(String key : jutsuTypes) {
            this.renderRelease(this.CONTAINER_SECTION_POS[0] + 35 + jutsuAdditionalMargin, this.CONTAINER_SECTION_POS[1] + 195, key);
            jutsuAdditionalMargin += 20;
        }

        // render left side ends

        // render right side
        int rightSideX = (this.CONTAINER_SECTION_POS[0]*2) + 118;
        int rightSideY = this.CONTAINER_SECTION_POS[1] + 40;

        DrawFonts.Draw.drawString(rightSideX, rightSideY,
                I18n.format("common.ninja_card.stats_title"), 20, 0xFF968300,
                Tensei.fonts.getFont("naruto"), false, true);

        this.renderInfoStat("common.ninjutsu", player.getNinjutsu(), () -> {
            PacketDispatcher.sendToServer(
                new PacketUpdateStatsMessage("ninjutsu")
            );
        }, rightSideX, rightSideY + 20);

        this.renderInfoStat("common.taijutsu", player.getTaijutsu(), () -> {
            PacketDispatcher.sendToServer(
                    new PacketUpdateStatsMessage("taijutsu")
            );
        }, rightSideX, rightSideY + 40);

        this.renderInfoStat("common.speed", player.getSpeed(), () -> {
            PacketDispatcher.sendToServer(
                    new PacketUpdateStatsMessage("speed")
            );
        }, rightSideX, rightSideY + 60);

        this.renderInfoStat("common.genjutsu", player.getGenjutsu(), () -> {
            PacketDispatcher.sendToServer(
                    new PacketUpdateStatsMessage("genjutsu")
            );
        }, rightSideX, rightSideY + 80);

        GL11.glColor3f(255,255, 255);
        Gui.drawRect(rightSideX, rightSideY + 140,
                rightSideX + 135, rightSideY + 141, 1711276032);

        DrawFonts.Draw.drawString(rightSideX, rightSideY + 155,
                I18n.format("common.jutsu_points"), 14, 1711276032,
                Tensei.fonts.getFont("naruto"), false, true);

        DrawFonts.Draw.drawString(rightSideX + 130, rightSideY + 155,
                String.valueOf(player.getJutsuPoints()), 14, 0xFF968300,
                Tensei.fonts.getFont("naruto"), false, true);

        DrawFonts.Draw.drawString(rightSideX, rightSideY + 168,
                I18n.format("common.skill_points"), 14, 1711276032,
                Tensei.fonts.getFont("naruto"), false, true);

        DrawFonts.Draw.drawString(rightSideX + 130, rightSideY + 168,
                String.valueOf(player.getSkillPoints()), 14, 0xFF968300,
                Tensei.fonts.getFont("naruto"), false, true);
    }

    public void renderRelease(int x, int y, String release) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(
                new ResourceLocation(Tensei.MODID, "textures/jutsutype/" + release + ".png")
        );

        GL11.glPushMatrix();
        GL11.glColor3f(255, 255, 255);

        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, 19, 19, 19, 19);
        GL11.glPopMatrix();
    }

    public void renderInfoStat(String i18n, int value, Runnable onAddClick, int x, int y) {
        int plusButtonX = x + 125;
        int plusButtonY = y - 2;

        GL11.glPushMatrix();
        DrawFonts.Draw.drawString(x, y,
                I18n.format(i18n), 14, 0xFF000000,
                Tensei.fonts.getFont("naruto"), false, false);

        DrawFonts.Draw.drawString(x + 91, y,
                String.valueOf(value), 14, 0xFF968300,
                Tensei.fonts.getFont("naruto"), false, false);

        if((this.mouseX > plusButtonX - 16 && this.mouseX < plusButtonX + 16
                && this.mouseY > plusButtonY && this.mouseY < plusButtonY + 16)) {
            Gui.drawRect((plusButtonX + 5) - 7, (plusButtonY + 5) - 5, (plusButtonX + 5) + 3, (plusButtonY + 5) + 5, 1711276032);
        } else {
            Gui.drawRect((plusButtonX + 5) - 7, (plusButtonY + 5) - 5, (plusButtonX + 5) + 3, (plusButtonY + 5) + 5, 0xFF968300);
        }
        DrawFonts.Draw.drawString(plusButtonX, plusButtonY,
                "+", 14, 0xFFFFFFFF,
                Tensei.fonts.getFont("naruto"), false, false);
        GL11.glPopMatrix();

        if(isClicked && (this.mouseX > plusButtonX - 16 && this.mouseX < plusButtonX + 16
                && this.mouseY > plusButtonY && this.mouseY < plusButtonY + 16)) {
            onAddClick.run();
        }
    }
    @Override
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
        try {
            isClicked = true;
            super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int wheelState = Mouse.getEventDWheel();
        int mcHeight = Minecraft.getMinecraft().displayHeight;
        IPlayer player = Player.getInstance(Minecraft.getMinecraft().player);
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        if (wheelState != 0 && player.getLearnedJutsu().size() >= 24) {
            currentScroll += wheelState > 0 ? -50 : 50;
            if(currentScroll < ((sr.getScaledHeight()+mcHeight)/6))
                currentScroll = ((sr.getScaledHeight()+mcHeight)/6);
            else if(currentScroll > (3*player.getLearnedJutsu().size()))
                currentScroll = (3*player.getLearnedJutsu().size());
        }
    }

    public int getCurrentScroll() {
        IPlayer player = Player.getInstance(Minecraft.getMinecraft().player);
        if(player.getLearnedJutsu().size() >= 21) {
            int k = player.getLearnedJutsu().size()/13;
            float i = (k*50*2F)/this.scrollAllHeight;
            return (int)((float)currentScroll*i);
        }
        return 0;
    }

    public void renderTooltip(int x, int y, String title, String description, int width, int height) {
        if((this.mouseX > x && this.mouseX < x + width
                && this.mouseY > y && this.mouseY < y + height) && !isTooltipEnabled) {
            GL11.glPushMatrix();
            Gui.drawRect(x, y + 100, x+ 90, y + 37, 1711276032);
            DrawFonts.Draw.drawString(
                    x+5,y + 45,
                    title, 16,
                    0x00000000, Tensei.fonts.getFont("ptsans"), false, true);
            DrawFonts.Draw.drawString(
                    x+5,y+55,
                    String.format(description), 13,
                    0x00000000, Tensei.fonts.getFont("ptsans"), false, true);
            GL11.glPopMatrix();
            isTooltipEnabled = true;
        } else {
            isTooltipEnabled = false;
        }
    }

    public void renderJutsuTooltip(int x, int y, String title, String description, int width, int height) {
        boolean isHover = this.mouseX > x && this.mouseX < x + width
                && this.mouseY > y && this.mouseY < y + height;

        if(isHover) {
            Gui.drawRect(x, y + 110, x+ 90, y + 27, 1711276032);
            DrawFonts.Draw.drawString(
                    x+5,y + 35,
                    title, 16,
                    0x00000000, Tensei.fonts.getFont("ptsans"), false, true);
            DrawFonts.Draw.drawString(
                    x+5,y+60,
                    String.format(description), 13,
                    0x00000000, Tensei.fonts.getFont("ptsans"), false, true);
        }
    }
    static class Button {
        public int x;
        public int y;
        public String text;
        public Runnable onClick;
        public boolean active;
        public int mouseX;
        public int mouseY;
        public boolean isClicked;
        public int textPosition;

        public Button(int x, int y, String text, Runnable onClick, boolean active, int mouseX,
                      int mouseY, boolean isClicked, int textPosition) {
            this.x = x;
            this.y = y;
            this.text = text;
            this.onClick = onClick;
            this.active = active;
            this.mouseX = mouseX;
            this.mouseY = mouseY;
            this.isClicked = isClicked;
            this.textPosition = textPosition;
        }

        public void render() {
            int textPosX = (this.x * 3) / 2;
            int textPosY = (this.y * 3) / 2;

            ResourceLocation button = new ResourceLocation(Tensei.MODID + ":textures/gui/buttons/ninjacard_button.png");

            Minecraft.getMinecraft().getTextureManager().bindTexture(button);

            Gui.drawModalRectWithCustomSizedTexture(textPosX, textPosY, 0, 0, 100, 23, 100, 23);

            int textX = (textPosX + textPosX/8) - 27;

            if(this.textPosition != 0) {
                textX = textPosX + this.textPosition;
            }

            if((this.mouseX > textX - 20 && this.mouseX < textX + 80
                    && this.mouseY > textPosY && this.mouseY < textPosY + 23)) {
                DrawFonts.Draw.drawString(textX,
                        textPosY + 7, this.text, 16, 0xFF968300,
                        Tensei.fonts.getFont("naruto"), false, true);
            } else {
                DrawFonts.Draw.drawString(textX,
                        textPosY + 7, this.text, 16, (this.active ? 0xFF968300 : 0xFF000000),
                        Tensei.fonts.getFont("naruto"), false, true);
            }


            if(isClicked && (this.mouseX > textX - 40 && this.mouseX < textX + 60
                    && this.mouseY > textPosY && this.mouseY < textPosY + 23)) {
                this.onClick.run();
            }
        }
    }
}
