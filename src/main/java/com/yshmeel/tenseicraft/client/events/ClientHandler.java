package com.yshmeel.tenseicraft.client.events;

import com.yshmeel.tenseicraft.client.Keys;
import com.yshmeel.tenseicraft.client.Sounds;
import com.yshmeel.tenseicraft.client.dialogs.DialogList;
import com.yshmeel.tenseicraft.client.dialogs.builder.DialogBuilder;
import com.yshmeel.tenseicraft.client.gui.InGameInterface;
import com.yshmeel.tenseicraft.client.gui.NinjaCard;
import com.yshmeel.tenseicraft.client.render.EyesLayer;
import com.yshmeel.tenseicraft.client.utils.DialogUtils;
import com.yshmeel.tenseicraft.client.utils.JutsuUtils;
import com.yshmeel.tenseicraft.client.utils.KeyboardUtils;
import com.yshmeel.tenseicraft.common.fighting.jutsu.earth.EarthCloneJutsu;
import com.yshmeel.tenseicraft.common.fighting.jutsu.entities.clones.EntityClone;
import com.yshmeel.tenseicraft.common.packets.PacketActivateJutsuMessage;
import com.yshmeel.tenseicraft.common.packets.PacketDispatcher;
import com.yshmeel.tenseicraft.common.packets.PacketReplaceTechniqueMessage;
import com.yshmeel.tenseicraft.data.player.IPlayer;
import com.yshmeel.tenseicraft.data.player.Player;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
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
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class ClientHandler {
    public static String hiddenInput = "";
    public int activateJutsuCooldown = 0;
    public int guiIdle = 0;

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
                    if(!DialogUtils.activeDialogMode.equals("showcase")) {
                        if(DialogUtils.activeDialogId != 8) {
                            DialogUtils.showDialog("tutorial");
                        }
                    } else {
                        DialogUtils.activeDialogMode = "showcase";
                        DialogUtils.activeDialogId = 0;
                        DialogUtils.showDialog("tutorial");
                    }

                }
            }

            if(guiIdle > 0) {
                guiIdle--;
            }

            if(guiIdle == 0) {
                if(DialogUtils.hasActiveDialog) {
                    DialogBuilder dialog = DialogList.registeredDialogs.get(DialogUtils.activeDialogName);
                    if(dialog != null) {
                        if(!dialog.canSkip) {
                            DialogUtils.showDialog(dialog);
                        } else {
                            DialogUtils.closeDialog();
                        }
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
                    if(DialogUtils.activeDialogMode.equals("showcase")) {
                        DialogUtils.closeDialog();
                        DialogUtils.activeDialogMode = "dialog";
                        DialogUtils.activeDialogId = 6;
                        DialogUtils.showDialog("tutorial");
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

        if(player.getChakra() != player.getMaxChakra()) {
            if(Keys.CHAKRA_FILL_BUTTON.isKeyDown() && !player.isChakraFillModeEnabled()) {
                player.setChakraFillModeEnabled(true);
                Minecraft.getMinecraft().player.sendMessage(
                        new TextComponentTranslation("common.tenseicraft.chakra_fill.enabled")
                );

                // @todo сделать анимацию медитации.
            } else if(!Keys.CHAKRA_FILL_BUTTON.isKeyDown() && player.isChakraFillModeEnabled()) {
                player.setChakraFillModeEnabled(false);
                Minecraft.getMinecraft().player.sendMessage(
                        new TextComponentTranslation("common.tenseicraft.chakra_fill.disabled")
                );
            }
        }

        if(Keys.REPLACE_TECHNIQUE.isPressed() && player.getMoveCooldown() == 0) {
            PacketDispatcher.sendToServer(new PacketReplaceTechniqueMessage());
        }

        if(Keys.NINJA_CARD_GUI_OPEN.isPressed()) {
            Minecraft.getMinecraft().player.playSound(new SoundEvent(Sounds.NINJA_CARD_OPEN_SOUND), 5.0F, 1.0F);
            Minecraft.getMinecraft().displayGuiScreen(new NinjaCard());
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            guiIdle = 5;
        }

        if(Keys.JUTSU_COMBO_1.isPressed()) {
            if(JutsuUtils.canWriteToHidden(hiddenInput, Keys.JUTSU_COMBO_1)) {
                hiddenInput += KeyboardUtils.getKeyName(Keys.JUTSU_COMBO_1) + " ";
            } else {
                hiddenInput = "";
            }
        }

        if(Keys.JUTSU_COMBO_2.isPressed()) {
            if(JutsuUtils.canWriteToHidden(hiddenInput, Keys.JUTSU_COMBO_2)) {
                hiddenInput += KeyboardUtils.getKeyName(Keys.JUTSU_COMBO_2) + " ";
            } else {
                hiddenInput = "";
            }
        }
    }

    @SubscribeEvent
    public void guiOpen(RenderGameOverlayEvent.Pre event)
    {
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        InGameInterface hud = new InGameInterface();
        if(event.getType().equals(RenderGameOverlayEvent.ElementType.ALL)) {
            Minecraft.getMinecraft().mcProfiler.startSection("air");
            hud.render(event);
            Minecraft.getMinecraft().mcProfiler.endSection();
        }
        if (event.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR)) {
            event.setCanceled(true);
            hud.renderHotBar(event);
        }
        if (event.getType().equals(RenderGameOverlayEvent.ElementType.FOOD) || event.getType().equals(RenderGameOverlayEvent.ElementType.EXPERIENCE)) {
            event.setCanceled(true);
            hud.renderPlayerStats(event);
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
