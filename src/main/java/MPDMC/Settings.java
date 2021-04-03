package MPDMC;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.options.CyclingOption;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static MPDMC.TextPositions.*;

public class Settings {

    public static TextPositions CONFIG_POSITION = TOP_LEFT;
    public static double CONFIG_Y_OFFSET = 0.0;
    public static double CONFIG_X_OFFSET = 0.0;
    public static double CONFIG_PADDING = 2.0;

    public static boolean CONFIG_SHOWN = false;

    public static DoubleOption CONFIG_X_OFFSET_OPTION = new DoubleOption("options.mpd_x_offset",
            -250.0, 250.0, 5F, gameOptions -> Settings.CONFIG_X_OFFSET,
            (gameOptions, xoff) -> {
                CONFIG_X_OFFSET = xoff;
            }, (gameOptions, option) ->  Text.of((new TranslatableText("options.mpd_x_offset").getString()) + ": " + Settings.CONFIG_X_OFFSET));
    public static DoubleOption CONFIG_Y_OFFSET_OPTION = new DoubleOption("options.mpd_y_offset",
            -250.0, 250.0, 5F, gameOptions -> Settings.CONFIG_Y_OFFSET,
            (gameOptions, yoff) -> {
                CONFIG_Y_OFFSET = yoff;
            }, (gameOptions, option) ->  Text.of((new TranslatableText("options.mpd_y_offset").getString()) + ": " + Settings.CONFIG_Y_OFFSET));
    public static DoubleOption CONFIG_PADDING_OPTION = new DoubleOption("options.mpd_padding",
            0.0, 10.0, 1F, gameOptions -> Settings.CONFIG_PADDING,
            (gameOptions, padding) -> {
                CONFIG_PADDING = padding;
            }, (gameOptions, option) ->  Text.of((new TranslatableText("options.mpd_padding").getString()) + ": " + Settings.CONFIG_PADDING));
    public static CyclingOption CONFIG_POSITION_OPTION = new CyclingOption("options.mpd_position",
            (gameOptions, integer) -> {
                int index = CONFIG_POSITION.ordinal();
                int nextIndex = index + integer;
                TextPositions[] positions = TextPositions.values();
                nextIndex %= positions.length;
                CONFIG_POSITION = positions[nextIndex];
            }, (gameOptions, cyclingOption) -> {
                switch(CONFIG_POSITION) {
                    case TOP_LEFT:
                        return new TranslatableText("options.mpd_top_left");
                    case TOP:
                        return new TranslatableText("options.mpd_top");
                    case TOP_RIGHT:
                        return new TranslatableText("options.mpd_top_right");
                    case CENTER_LEFT:
                        return new TranslatableText("options.mpd_center_left");
                    case CENTER:
                        return new TranslatableText("options.mpd_center");
                    case CENTER_RIGHT:
                        return new TranslatableText("options.mpd_center_right");
                    case BOTTOM_LEFT:
                        return new TranslatableText("options.mpd_bottom_left");
                    case BOTTOM:
                        return new TranslatableText("options.mpd_bottom");
                    case BOTTOM_RIGHT:
                        return new TranslatableText("options.mpd_bottom_right");
                };
                return new TranslatableText("options.mpd_invalid");
            });

    private final Path settingsFilePath;

    private Map<String, Object> loadedData;

    public Settings() {
        MpdMC_Main.LOGGER.log(Level.DEBUG, "Loading config file");
        settingsFilePath = FabricLoader.getInstance().getConfigDir().resolve("mpd_settings.yml");
        try {
            Yaml yaml = new Yaml();
            loadedData = yaml.load(new FileInputStream(settingsFilePath.toFile()));
            MpdMC_Main.LOGGER.log(Level.DEBUG, "Loading config file succeeded!");

            if (loadedData == null)
                loadedData = new HashMap<String, Object>();

            if (loadedData.get("shown") == null)
                loadedData.put("shown", false);
            else
                CONFIG_SHOWN = (boolean) loadedData.get("shown");
            if (loadedData.get("position") == null)
                loadedData.put("position", TOP_LEFT.toString());
            else
                CONFIG_POSITION = valueOf((String) loadedData.get("position"));
            if (loadedData.get("xoff") == null)
                loadedData.put("xoff", 0.0);
            else
                CONFIG_X_OFFSET = (double) loadedData.get("xoff");
            if (loadedData.get("yoff") == null)
                loadedData.put("yoff", 0.0);
            else
                CONFIG_Y_OFFSET = (double) loadedData.get("yoff");
            if (loadedData.get("padding") == null)
                loadedData.put("yoff", 1.0);
            else
                CONFIG_Y_OFFSET = (double) loadedData.get("padding");
        } catch (FileNotFoundException e) {
            MpdMC_Main.LOGGER.log(Level.WARN, "Loading config file failed! Using default settings...");
            e.printStackTrace();
        }
    }

    public boolean save() {
        Yaml yaml = new Yaml();
        try {
            FileWriter writer = new FileWriter(settingsFilePath.toFile());
            if (loadedData == null)
                loadedData = new HashMap<String, Object>();
            loadedData.replace("position", CONFIG_POSITION.toString());
            loadedData.replace("xoff", CONFIG_X_OFFSET);
            loadedData.replace("yoff", CONFIG_Y_OFFSET);
            loadedData.replace("padding", CONFIG_PADDING);
            loadedData.replace("shown", CONFIG_SHOWN);
            yaml.dump(loadedData, writer);
            MpdMC_Main.LOGGER.log(Level.DEBUG, "Saving config file succeeded!");
            return true;
        } catch (IOException e) {
            MpdMC_Main.LOGGER.log(Level.ERROR, "Saving config file failed!");
            e.printStackTrace();
            return false;
        }
    }
}
