package MPDMC.Config;

import MPDMC.Reference;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;

import java.io.File;

import static MPDMC.TextPositions.*;

public class Config implements IConfigHandler {

    static String CONFIG_FILE_NAME = Reference.MOD_ID + ".json";

    public static class Visual {

        public static ConfigDouble CONFIG_X_OFFSET_OPTION = new ConfigDouble("x_offset", 0.0, -100, 100, "This is the X offset for the text relative to the position.");
        public static ConfigDouble CONFIG_Y_OFFSET_OPTION = new ConfigDouble("y_offset", 0.0, -100, 100, "This is the Y offset for the text relative to the position.");
        public static ConfigDouble CONFIG_PADDING_OPTION = new ConfigDouble("padding", 2.0, 0, 150, "The padding of the text relative to the screen.");

        public static ConfigOptionList CONFIG_POSITION_OPTION = new ConfigOptionList("position", TOP_LEFT, "The position on screen for the text");

        public static final ImmutableList<IConfigBase> SETTINGS = ImmutableList.of(
                CONFIG_POSITION_OPTION,
                CONFIG_X_OFFSET_OPTION,
                CONFIG_Y_OFFSET_OPTION,
                CONFIG_PADDING_OPTION
        );

    }

    public static class Internal {
        public static ConfigString CONFIG_ADDRESS_OPTION = new ConfigString("mpdAddress", "mpd://localhost:6600", "The address of the MPD server.");
        public static ConfigInteger CONFIG_UPDATE_SPEED = new ConfigInteger("updateSpeed", 500, 1, 2000, "This is how often the text updates in milliseconds. The lower the faster, but may affect performance.");

        public static final ImmutableList<IConfigBase> SETTINGS = ImmutableList.of(
                CONFIG_ADDRESS_OPTION,
                CONFIG_UPDATE_SPEED
        );
    }

    public static class Enabled {
        public static ConfigBooleanHotkeyed CONFIG_SHOWN_OPTION = new ConfigBooleanHotkeyed("shown", false, "UP", "This toggles the MPD text.");

        public static final ImmutableList<IHotkeyTogglable> SETTINGS = ImmutableList.of(
                CONFIG_SHOWN_OPTION
        );
    }

    public static class Format {
        public static ConfigString CONFIG_FORMAT_ARTIST = new ConfigString("formatWithArtist", "Now playing: \"%title%\" by %artist%", "This format is what will appear if the author was found.");
        public static ConfigString CONFIG_FORMAT_NO_ARTIST = new ConfigString("formatWithoutArtist", "Now playing: \"%title%\"", "This format is what will appear if no author was found. %title% in this case may be the filename or song title.");

        public static final ImmutableList<IConfigBase> SETTINGS = ImmutableList.of(
                CONFIG_FORMAT_ARTIST,
                CONFIG_FORMAT_NO_ARTIST
        );
    }

    public static void loadFromFile()
    {
        File configFile = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

        if (configFile.exists() && configFile.isFile() && configFile.canRead())
        {
            JsonElement element = JsonUtils.parseJsonFile(configFile);

            if (element != null && element.isJsonObject())
            {
                JsonObject root = element.getAsJsonObject();

                ConfigUtils.readConfigBase(root, "Internal", Internal.SETTINGS);
                ConfigUtils.readConfigBase(root, "Visual", Visual.SETTINGS);
                ConfigUtils.readConfigBase(root, "Format", Format.SETTINGS);
                ConfigUtils.readHotkeyToggleOptions(root, "EnabledHotkeys", "Enabled", Enabled.SETTINGS);
                ConfigUtils.readConfigBase(root, "Hotkey", Hotkeys.HOTKEY_LIST);
            }
        }

    }

    public static void saveToFile()
    {
        File dir = FileUtils.getConfigDirectory();

        if ((dir.exists() && dir.isDirectory()) || dir.mkdirs())
        {
            JsonObject root = new JsonObject();

            ConfigUtils.writeConfigBase(root, "Internal", Internal.SETTINGS);
            ConfigUtils.writeConfigBase(root, "Visual", Visual.SETTINGS);
            ConfigUtils.writeConfigBase(root, "Format", Format.SETTINGS);
            ConfigUtils.writeHotkeyToggleOptions(root, "EnabledHotkeys", "Enabled", Enabled.SETTINGS);
            ConfigUtils.writeConfigBase(root, "Hotkey", Hotkeys.HOTKEY_LIST);

            JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
        }
    }

    @Override
    public void load()
    {
        loadFromFile();
    }

    @Override
    public void save()
    {
        saveToFile();
    }

}
