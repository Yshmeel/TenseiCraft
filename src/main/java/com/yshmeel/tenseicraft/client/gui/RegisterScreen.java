package com.yshmeel.tenseicraft.client.gui;

import com.yshmeel.tenseicraft.Tensei;
import com.yshmeel.tenseicraft.client.gui.fonts.DrawFonts;
import com.yshmeel.tenseicraft.client.utils.DialogUtils;
import com.yshmeel.tenseicraft.common.packets.PacketDispatcher;
import com.yshmeel.tenseicraft.common.packets.PacketRegisterPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class RegisterScreen extends GuiScreen {
    private int[] BUTTON_SECTION_POS = {0, 0};
    private int[] CONTAINER_SECTION_POS = {0, 0};
    private GuiTextField lastName;
    private int guiX;
    private int guiY;
    private boolean isClicked;
    private String[] families = {
        "Uchiha",
        "Senju",
        "Uzumaki",
        "Hyuga",
        "Nara",
        "Aburame",
        "Otsusuki",
        "Lee",
        "Hatake"
    };
    public ResourceLocation texture = new ResourceLocation("tenseicraft:textures/gui/ninjacard_body.png");

    public void initGui() {
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        int xSize = 384;
        int ySize = 292;
        float scale = 1.0F;

        guiX = (Math.round((width / 2) / scale) - Math.round(xSize / 2));
        guiY = (Math.round((height / 2) / scale) - Math.round(ySize / 2));
        CONTAINER_SECTION_POS[0] = guiX - 10;
        CONTAINER_SECTION_POS[1] = guiY + 36;

        this.lastName = new GuiTextField(1, this.fontRenderer, CONTAINER_SECTION_POS[0] + 30,
                CONTAINER_SECTION_POS[1] + 60,
                232, 20);
        lastName.setMaxStringLength(8);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());

        GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
        this.mc.renderEngine.bindTexture(this.texture);
        this.drawModalRectWithCustomSizedTexture(guiX, guiY, 0, 0, 384, 292, 384, 292);

        DrawFonts.Draw.drawString(CONTAINER_SECTION_POS[0] + 30, CONTAINER_SECTION_POS[1] + 15,
                I18n.format("cutscene.register.hello_world", Minecraft.getMinecraft().player.getName()),
                29, 0xFF968300, Tensei.fonts.getFont("ptsans"), false, true);

        DrawFonts.Draw.drawString(CONTAINER_SECTION_POS[0] + 30, CONTAINER_SECTION_POS[1] + 40,
                I18n.format("cutscene.register.last_name_select", Minecraft.getMinecraft().player.getName()),
                16, 0xFF000000, Tensei.fonts.getFont("ptsans"), false, true);
        this.lastName.drawTextBox();

        DrawFonts.Draw.drawString(CONTAINER_SECTION_POS[0] + 30, CONTAINER_SECTION_POS[1] + 90,
                I18n.format("cutscene.register.list_of_lastnames", Minecraft.getMinecraft().player.getName()),
                23, 0xFF968300, Tensei.fonts.getFont("ptsans"), false, true);

        int familiesX = (CONTAINER_SECTION_POS[0] + families.length*3) + 4;
        int familiesY = (CONTAINER_SECTION_POS[1] + 120);
        int familyIter = 0;

        for(String family : families){
            if(familyIter % 3 == 0 && familyIter != 0) {
                familiesX += 110;
                familiesY = (CONTAINER_SECTION_POS[1] + 120);
            }
            new Button(familiesX, familiesY, family, () -> {
                this.lastName.setText(family);
            }, false, mouseX, mouseY, isClicked, 35).render();
            familiesY += 30;
            familyIter ++;
        }

        new Button((CONTAINER_SECTION_POS[0] + families.length*3) + 4, familiesY + 10, I18n.format("dialogs.common.create"), () -> {
            if(this.lastName.getText().length() > 2) {
                PacketDispatcher.sendToServer(
                    new PacketRegisterPlayer(Minecraft.getMinecraft().player, this.lastName.getText())
                );

                DialogUtils.closeDialog();
                DialogUtils.activeDialogMode = "dialog";
                DialogUtils.activeDialogId = 9;
                DialogUtils.showDialog("tutorial");
            }
        }, false, mouseX, mouseY, isClicked, 35).render();

        this.isClicked = false;
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void keyTyped(char par1, int par2) throws IOException {
        if(String.valueOf(par1).matches("^[a-zA-Z]*$") || par2 == 14) {
            super.keyTyped(par1, par2);
            this.lastName.textboxKeyTyped(par1, par2);
        }
    }

    public void updateScreen()
    {
        super.updateScreen();
        this.lastName.updateCursorCounter();
    }

    protected void mouseClicked(int x, int y, int btn) throws IOException {
        super.mouseClicked(x, y, btn);
        isClicked = true;
        this.lastName.mouseClicked(x, y, btn);
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
            int textPosX = this.x;
            int textPosY = this.y;

            ResourceLocation button = new ResourceLocation(Tensei.MODID + ":textures/gui/buttons/ninjacard_button.png");

            Minecraft.getMinecraft().getTextureManager().bindTexture(button);
            GL11.glColor3f(255, 255, 255);

            Gui.drawModalRectWithCustomSizedTexture(textPosX, textPosY, 0, 0, 100, 23, 100, 23);

            int textX = (textPosX + textPosX/8) - 27;

            if(this.textPosition != 0) {
                textX = textPosX + this.textPosition;
            }

            if((this.mouseX > textX - 40 && this.mouseX < textX + 60
                    && this.mouseY > textPosY && this.mouseY < textPosY + 23)) {
                DrawFonts.Draw.drawString(textX,
                        textPosY + 6, this.text, 16, 0xFF968300,
                        Tensei.fonts.getFont("ptsans"), false, true);
            } else {
                DrawFonts.Draw.drawString(textX,
                        textPosY + 6, this.text, 16, (this.active ? 0xFF968300 : 0xFF000000),
                        Tensei.fonts.getFont("ptsans"), false, true);
            }


            if(isClicked && (this.mouseX > textX - 40 && this.mouseX < textX + 60
                    && this.mouseY > textPosY && this.mouseY < textPosY + 23)) {
                this.onClick.run();
            }
        }
    }
}
