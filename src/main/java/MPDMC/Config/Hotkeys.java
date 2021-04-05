package MPDMC.Config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigHotkey;

import java.util.List;

public class Hotkeys {
    public static final ConfigHotkey RECONNECT_MPD = new ConfigHotkey("reconnectMpd","",        "Use this to reconnect to MPD. Useful if address changed or you just started MPD.");
    public static final ConfigHotkey UPDATE_MPD = new ConfigHotkey("updateMpd",      "",        "This updates MPD's song library. (not yet implemented!)"); // TODO: Implement

    public static final List<ConfigHotkey> HOTKEY_LIST = ImmutableList.of(
            RECONNECT_MPD,
            UPDATE_MPD
    );
}
