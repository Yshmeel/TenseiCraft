package com.yshmeel.tenseicraft.client.events;

import com.yshmeel.tenseicraft.client.Keys;
import com.yshmeel.tenseicraft.client.Sounds;
import com.yshmeel.tenseicraft.client.gui.InGameInterface;
import com.yshmeel.tenseicraft.client.gui.NinjaCard;
import com.yshmeel.tenseicraft.client.render.EyesLayer;
import com.yshmeel.tenseicraft.client.utils.CutSceneUtils;
import com.yshmeel.tenseicraft.client.utils.JutsuUtils;
import com.yshmeel.tenseicraft.client.utils.KeyboardUtils;
import com.yshmeel.tenseicraft.common.packets.PacketActivateJutsuMessage;
import com.yshmeel.tenseicraft.common.packets.PacketDispatcher;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;

public class ClientHandler {
    public static String hiddenInput = "";
    public int activateJutsuCooldown = 0;

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if(Minecraft.getMinecraft().player != null) {
            IPlayer player = Player.getInstance(Minecraft.getMinecraft().player);

            if(!hiddenInput.equals("") && activateJutsuCooldown == 0) {
                int slot = JutsuUtils.matchSlot(hiddenInput);

                if(slot != -1) {
                    ArrayList<String> slots = player.getJutsuSlots();
                    if(slots.get(slot - 1) != null) {
                        PacketDispatcher.sendToServer(new PacketActivateJutsuMessage(slots.get(slot - 1)));
                    }
                    hiddenInput = "";
                } else {
                    activateJutsuCooldown = JutsuUtils.COOLDOWN;
                }

            } else if(!hiddenInput.equals("") && activateJutsuCooldown != 0) {
                activateJutsuCooldown--;
            }

            if (player.isDataFilled()) {
                if(!player.isRegistered()) {
                    if(!CutSceneUtils.activeCutSceneMode.equals("showcase")) {
                        if(CutSceneUtils.activeCutSceneDialogId != 8) {
                            CutSceneUtils.showCutScene("tutorial");
                        }
                    } else {
                        CutSceneUtils.activeCutSceneMode = "showcase";
                        CutSceneUtils.activeCutSceneDialogId = 0;
                        CutSceneUtils.showCutScene("tutorial");
                    }

                }
            }
        }

    }
    @SubscribeEvent
    public void guiKeyPressed(GuiScreenEvent.KeyboardInputEvent event) {
        IPlayer player = Player.getInstance(Minecraft.getMinecraft().player);
        if(player != null) {
            if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
                if(!player.isRegistered()) {
                    if(CutSceneUtils.activeCutSceneMode.equals("showcase")) {
                        CutSceneUtils.closeCutScene();
                        CutSceneUtils.activeCutSceneMode = "dialog";
                        CutSceneUtils.activeCutSceneDialogId = 6;
                        CutSceneUtils.showCutScene("tutorial");
                    }
                }
            }
        }

    }

    @SubscribeEvent
    public void keyPressed(InputEvent.KeyInputEvent event) {
        IPlayer player = Player.getInstance(Minecraft.getMinecraft().player);

        if(Keys.CHAKRA_CONTROL_BUTTON.isPressed()) {
            player.setChakraControlEnabled(!player.isChakraControlEnabled());
            Minecraft.getMinecraft().player.sendMessage(
                new TextComponentTranslation("common.tenseicraft.chakra_control." +
                        (player.isChakraControlEnabled() ? "enabled" : "disabled"))
            );
        }

        if(Keys.CHAKRA_FILL_BUTTON.isPressed()) {
            player.setChakraFillModeEnabled(!player.isChakraFillModeEnabled());
            Minecraft.getMinecraft().player.sendMessage(
                    new TextComponentTranslation("common.tenseicraft.chakra_fill." +
                            (player.isChakraFillModeEnabled() ? "enabled" : "disabled"))
            );

            // @todo сделать анимацию медитации.
        }

        if(Keys.NINJA_CARD_GUI_OPEN.isPressed()) {
            Minecraft.getMinecraft().player.playSound(new SoundEvent(Sounds.NINJA_CARD_OPEN_SOUND), 5.0F, 1.0F);
            Minecraft.getMinecraft().displayGuiScreen(new NinjaCard());
        }

        if(Keys.JUTSU_COMBO_1.isPressed()) {
            if(JutsuUtils.canWriteToHidden(this.hiddenInput, Keys.JUTSU_COMBO_1)) {
                this.hiddenInput += KeyboardUtils.getKeyName(Keys.JUTSU_COMBO_1) + " ";
            } else {
                this.hiddenInput = "";
            }
        }

        if(Keys.JUTSU_COMBO_2.isPressed()) {
            if(JutsuUtils.canWriteToHidden(this.hiddenInput, Keys.JUTSU_COMBO_2)) {
                this.hiddenInput += KeyboardUtils.getKeyName(Keys.JUTSU_COMBO_2) + " ";
            } else {
                this.hiddenInput = "";
            }
        }
    }

    @SubscribeEvent
    public void guiOpen(RenderGameOverlayEvent.Pre event)
    {
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        if(event.getType().equals(RenderGameOverlayEvent.ElementType.ALL)) {
            Minecraft.getMinecraft().mcProfiler.startSection("air");
            new InGameInterface().render(event);
            Minecraft.getMinecraft().mcProfiler.endSection();
        }
        if (event.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR)) {
            event.setCanceled(true);
            new InGameInterface().renderHotBar(event);
        }
        if (event.getType().equals(RenderGameOverlayEvent.ElementType.FOOD) || event.getType().equals(RenderGameOverlayEvent.ElementType.EXPERIENCE)) {
            event.setCanceled(true);
            new InGameInterface().renderPlayerStats(event);
        }
        if (event.getType().equals(RenderGameOverlayEvent.ElementType.HEALTH)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void guiPost(RenderGameOverlayEvent.Post event)
    {
    }

    @SubscribeEvent
    public void renderPlayerPost(RenderLivingEvent.Pre event) {
        if(event.getEntity() instanceof EntityPlayer) {
            event.getRenderer().addLayer(new EyesLayer((RenderPlayer) event.getRenderer()));
        }
    }
}
