package MPDMC.Event;

import MPDMC.Config.Config;
import MPDMC.Config.Hotkeys;
import MPDMC.GUI.GuiConfigs;
import MPDMC.GUI.GuiMPD_Client;
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

public class KeybindCallbacks implements IHotkeyCallback {

    private static final KeybindCallbacks INSTANCE = new KeybindCallbacks();

    public static KeybindCallbacks getInstance()
    {
        return INSTANCE;
    }

    private KeybindCallbacks()
    {
    }

    public void setCallbacks()
    {
        for (ConfigHotkey hotkey : Hotkeys.HOTKEY_LIST)
        {
            hotkey.getKeybind().setCallback(this);
        }
    }

    @Override
    public boolean onKeyAction(KeyAction action, IKeybind key)
    {
        MinecraftClient mc = MinecraftClient.getInstance();

        if (mc.player == null || mc.world == null) {
            return false;
        }

        // TODO: Add keybind to open menu

        if (!(GuiUtils.getCurrentScreen() instanceof HandledScreen)) {
            return false;
        }

        if (key == Hotkeys.UPDATE_MPD.getKeybind()) {
            // TODO: Implement
        } else if (key == Hotkeys.RECONNECT_MPD.getKeybind())
            GuiMPD_Client.connect(Config.Internal.CONFIG_ADDRESS_OPTION.getStringValue());

        return false;
    }

}
