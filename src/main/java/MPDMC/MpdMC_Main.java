package MPDMC;

import MPDMC.Config.Config;
import fi.dy.masa.malilib.event.InitializationHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;

public class MpdMC_Main implements ClientModInitializer {
	
	// TODO: Implement settings (MPD host, port, position, style etc.)

    public static Logger LOGGER = LogManager.getLogger();

    public static Config settings = new Config();

    @Override
    public void onInitializeClient() {
        log(Level.INFO, "Initializing");
        InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());

        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }

    public void onTick(MinecraftClient mc) {
    	// TODO: Make this more efficient
        // settings.save();
    }

    public static void log(Level level, String message)
    {
        LOGGER.log(level, "["+Reference.MOD_NAME+"] " + message);
    }

}