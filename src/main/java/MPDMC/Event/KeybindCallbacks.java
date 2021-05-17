package MPDMC.Event;

import MPDMC.Config.Config;
import MPDMC.Config.Hotkeys;
import MPDMC.GUI.GuiConfigs;
import MPDMC.GUI.GuiMPD_Client;
import MPDMC.MpdMC_Main;
import de.dixieflatline.mpcw.client.CommunicationException;
import de.dixieflatline.mpcw.client.ProtocolException;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.interfaces.IClientTickHandler;
import fi.dy.masa.malilib.util.GuiUtils;
import fi.dy.masa.malilib.util.InventoryUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;

public class KeybindCallbacks {

    public static void init() {
        Hotkeys.CONFIG_SCREEN_MPD.getKeybind().setCallback((action, key) -> {
            GuiConfigs gui = new GuiConfigs();
            GuiBase.openGui(gui);

            return true;
        });

        Hotkeys.PREV_MPD.getKeybind().setCallback((action, key) -> {
            try {
                GuiMPD_Client.player.previous();
            } catch (CommunicationException | ProtocolException e) {
                e.printStackTrace();
            }
            return true;
        });
        Hotkeys.NEXT_MPD.getKeybind().setCallback((action, key) -> {
            try {
                GuiMPD_Client.player.next();
            } catch (CommunicationException | ProtocolException e) {
                e.printStackTrace();
            }
            return true;
        });
        Hotkeys.PAUSE_MPD.getKeybind().setCallback((action, key) -> {
            try {
                GuiMPD_Client.player.pause();
            } catch (CommunicationException | ProtocolException e) {
                e.printStackTrace();
            }
            return true;
        });
        Hotkeys.STOP_MPD.getKeybind().setCallback((action, key) -> {
            try {
                GuiMPD_Client.player.stop();
            } catch (CommunicationException | ProtocolException e) {
                e.printStackTrace();
            }

            return true;
        });
        Hotkeys.RECONNECT_MPD.getKeybind().setCallback(((action, key) -> {
            GuiMPD_Client.connect(Config.Internal.CONFIG_ADDRESS_OPTION.getStringValue());

            return true;
        }));
    }

}
