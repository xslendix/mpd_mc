package MPDMC;

import MPDMC.Config.Config;
import MPDMC.Event.InputHandler;
import MPDMC.Event.KeybindCallbacks;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;

public class InitHandler implements IInitializationHandler {

    @Override
    public void registerModHandlers() {
        ConfigManager.getInstance().registerConfigHandler(Reference.MOD_ID, new Config());

        KeybindCallbacks.init();
        InputEventHandler.getKeybindManager().registerKeybindProvider(InputHandler.getInstance());
    }
}
