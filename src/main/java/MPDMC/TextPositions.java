package MPDMC;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.util.StringUtils;

public enum TextPositions implements IConfigOptionListEntry {
    TOP_LEFT        ("top_left",        "mpd.top_left"),
    TOP             ("top",             "mpd.top"),
    TOP_RIGHT       ("top_right",       "mpd.top_right"),
    CENTER_LEFT     ("center_left",     "mpd.center_left"),
    CENTER          ("center",          "mpd.center"),
    CENTER_RIGHT    ("center_right",    "mpd.center_right"),
    BOTTOM_LEFT     ("bottom_left",     "mpd.bottom_left"),
    BOTTOM          ("bottom",          "mpd.bottom"),
    BOTTOM_RIGHT    ("bottom_right",    "mpd.bottom_right");

    private final String configString, translationKey;

    TextPositions(String configString, String translationKey) {
        this.configString = configString;
        this.translationKey = translationKey;
    }

    @Override
    public String getStringValue() {
        return this.configString;
    }

    @Override
    public String getDisplayName() {
        return StringUtils.translate(this.translationKey);
    }

    @Override
    public IConfigOptionListEntry cycle(boolean forward) {
        int id = this.ordinal();

        if (forward) {
            if (++id >= values().length)
                id = 0;
        } else {
            if (--id < 0)
                id = values().length - 1;
        }

        return values()[id % values().length];
    }

    @Override
    public IConfigOptionListEntry fromString(String value) {
        return fromStaticString(value);
    }

    public static TextPositions fromStaticString(String value) {
        for (TextPositions pos : TextPositions.values())
            if (pos.configString.equalsIgnoreCase(value))
                return pos;

        return TextPositions.TOP_LEFT;
    }
}
