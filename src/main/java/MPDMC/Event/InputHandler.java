package MPDMC.Event;

import MPDMC.Config.Config;
import MPDMC.Config.Hotkeys;
import MPDMC.Reference;
import fi.dy.masa.malilib.hotkeys.*;

public class InputHandler implements IKeybindProvider {

    private static final InputHandler INSTANCE = new InputHandler();

    private InputHandler() { }

    public static InputHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void addKeysToMap(IKeybindManager manager) {
        for (IHotkey hotkey : Hotkeys.HOTKEY_LIST) {
            manager.addKeybindToMap(hotkey.getKeybind());
        }
        for (IHotkey hotkey : Config.Enabled.SETTINGS) {
            manager.addKeybindToMap(hotkey.getKeybind());
        }
    }

    @Override
    public void addHotkeys(IKeybindManager manager) {
        manager.addHotkeysForCategory(Reference.MOD_NAME, "mpd.category.hotkeys", Hotkeys.HOTKEY_LIST);
    }

}
