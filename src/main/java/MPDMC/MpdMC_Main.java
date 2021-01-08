package MPDMC;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.options.StickyKeyBinding;
import net.minecraft.client.util.InputUtil;

public class MpdMC_Main implements ClientModInitializer {
	
	// TODO: Implement settings (MPD host, port, position, style etc.)

    public static Logger LOGGER = LogManager.getLogger();
    private static KeyBinding keyBinding, keyBinding2;
    
    public static boolean toggle = false;
    
    @Override
    public void onInitializeClient() {
        log(Level.INFO, "Initializing");
        keyBinding = new StickyKeyBinding("key.mpdmc.toggle", InputUtil.UNKNOWN_KEY.getCode(), "category.mpdmc.toggle", () -> true);
        keyBinding2 = new KeyBinding("key.mpdmc.press", InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEY.getCode(), "category.mpdmc.toggle");
        
        KeyBindingHelper.registerKeyBinding(keyBinding);
        KeyBindingHelper.registerKeyBinding(keyBinding2);
        
        ClientTickEvents.END_CLIENT_TICK.register(client->{
        	onTick(client);
        });
    }
    
    public void onTick(MinecraftClient mc) {
    	toggle = keyBinding.isPressed() || keyBinding2.isPressed();

    }

    public static void log(Level level, String message)
    {
        LOGGER.log(level, "["+Reference.MOD_NAME+"] " + message);
    }

}