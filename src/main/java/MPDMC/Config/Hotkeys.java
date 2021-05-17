package MPDMC.Config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigHotkey;

import java.util.List;

public class Hotkeys {
    public static final ConfigHotkey CONFIG_SCREEN_MPD = new ConfigHotkey("configScreen","M",        "Opens up the configuration screen.");

    public static final ConfigHotkey RECONNECT_MPD = new ConfigHotkey("reconnectMpd","",        "Use this to reconnect to MPD. Useful if address changed or you just started MPD.");
    public static final ConfigHotkey PAUSE_MPD = new ConfigHotkey("pauseMpd",      "",        "This pauses/resumes the current song.");
    public static final ConfigHotkey STOP_MPD = new ConfigHotkey("stopMpd",      "",        "This stops the current song.");
    public static final ConfigHotkey NEXT_MPD = new ConfigHotkey("nextMpd",      "",        "This plays the next song.");
    public static final ConfigHotkey PREV_MPD = new ConfigHotkey("prevMpd",      "",        "This plays the previous song.");

    public static final List<ConfigHotkey> HOTKEY_LIST = ImmutableList.of(
            CONFIG_SCREEN_MPD,
            RECONNECT_MPD,
            PAUSE_MPD,
            STOP_MPD,
            NEXT_MPD,
            PREV_MPD
    );
}
