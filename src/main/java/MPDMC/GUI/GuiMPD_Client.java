package MPDMC.GUI;

import MPDMC.Config.Config;
import com.mojang.blaze3d.systems.RenderSystem;
import de.dixieflatline.mpcw.client.*;
import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static MPDMC.TextPositions.*;

public class GuiMPD_Client {
	
	public static final GuiMPD_Client INSTANCE = new GuiMPD_Client();
	
	private static Logger LOGGER;
    private final MinecraftClient minecraft;
    private final TextRenderer fontRenderer;
    
    public static IConnection connection;
    public static IClient client;
    public static IPlayer player;
    private static Status status;
    
    private static String finalText;

    public static boolean toggled = false;

    public static void connect(String address) {
		try {
			if (connection != null)
				connection.disconnect();
			connection = Connection.create("mpd://localhost:6600");
			if (LOGGER != null) LOGGER.debug("Connecting to MPD.");
			connection.connect();

			client = connection.getClient();
			player = client.getPlayer();

			getStatus();
		} catch (Exception e) {
			if (LOGGER != null) LOGGER.error("Failed to connect to MPD!");
			connection = null;
			e.printStackTrace();
		}
	}

	// https://stackoverflow.com/a/3657496
	public static String ellipsize(String text, int max) {
		if (text.length() <= max)
			return text;

		// Start by chopping off at the word before max
		// This is an over-approximation due to thin-characters...
		int end = text.lastIndexOf(' ', max - 3);

		// Just one long word. Chop it off.
		if (end == -1)
			return text.substring(0, max-3) + "...";

		// Step forward as long as textWidth allows.
		int newEnd = end;
		do {
			end = newEnd;
			newEnd = text.indexOf(' ', end + 1);

			// No more spaces.
			if (newEnd == -1)
				newEnd = text.length();

		} while ((text.substring(0, newEnd) + "...").length() < max);

		return text.substring(0, end) + "...";
	}

	public GuiMPD_Client()
    {
    	minecraft = MinecraftClient.getInstance();
        fontRenderer = minecraft.textRenderer;
    	LOGGER = LogManager.getLogger();

    	connect("mpd://localhost:6600");

        if (LOGGER != null) LOGGER.debug("Created GUI");
        
        Thread t1 = new Thread(() -> {
			while (true)
			{
				getStatus();
				try {
					Thread.sleep(Config.Internal.CONFIG_UPDATE_SPEED.getIntegerValue());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
        t1.start();
    }
    
    public void render(MatrixStack stack, float partialTicks)
    {
    	if (!Config.Enabled.CONFIG_SHOWN_OPTION.getBooleanValue() || minecraft.options.debugEnabled)
    		return;
    	
		float width = MinecraftClient.getInstance().getWindow().getScaledWidth();
		float height = MinecraftClient.getInstance().getWindow().getScaledHeight();
		float widthText = fontRenderer.getWidth(finalText);
		float heightText = fontRenderer.fontHeight;

		if (status != null)
			if (status.getState() == EState.Play)
			{
				float x_off = (float) Config.Visual.CONFIG_X_OFFSET_OPTION.getDoubleValue();
				float y_off = (float) Config.Visual.CONFIG_Y_OFFSET_OPTION.getDoubleValue();
				float padding = (float) Config.Visual.CONFIG_PADDING_OPTION.getDoubleValue();

				IConfigOptionListEntry optionListValue = Config.Visual.CONFIG_POSITION_OPTION.getOptionListValue();
				if (TOP_LEFT.equals(optionListValue))
					fontRenderer.drawWithShadow(stack, finalText, x_off + padding, y_off + padding, 0xffffff);
				else if (TOP.equals(optionListValue))
					fontRenderer.drawWithShadow(stack, finalText, width / 2 - widthText / 2 + x_off, y_off + padding, 0xffffff);
				else if (TOP_RIGHT.equals(optionListValue))
					fontRenderer.drawWithShadow(stack, finalText, width - widthText + x_off - padding, y_off + padding, 0xffffff);
				else if (CENTER_LEFT.equals(optionListValue))
					fontRenderer.drawWithShadow(stack, finalText, x_off + padding, height / 2 - heightText / 2 + y_off, 0xffffff);
				else if (CENTER.equals(optionListValue))
					fontRenderer.drawWithShadow(stack, finalText, width / 2 - widthText / 2 + x_off, height / 2 - heightText / 2 + y_off, 0xffffff);
				else if (CENTER_RIGHT.equals(optionListValue))
					fontRenderer.drawWithShadow(stack, finalText, width - widthText + x_off - padding, height / 2 - heightText / 2 + y_off, 0xffffff);
				else if (BOTTOM_LEFT.equals(optionListValue))
					fontRenderer.drawWithShadow(stack, finalText, x_off + padding, height - heightText - y_off - padding, 0xffffff);
				else if (BOTTOM.equals(optionListValue))
					fontRenderer.drawWithShadow(stack, finalText, width / 2 - widthText / 2 + x_off, height - heightText - y_off - padding, 0xffffff);
				else if (BOTTOM_RIGHT.equals(optionListValue))
					fontRenderer.drawWithShadow(stack, finalText, width - widthText + x_off - padding, height - heightText - y_off - padding, 0xffffff);

				// TODO: Add progress bar

			}
	}

	public void tick()
	{
	}

	public static String getFormatted(String input) {
		String artist = ellipsize(status.getArtist(), Config.Format.CONFIG_CUT_STRING.getIntegerValue());
		String title = ellipsize(status.getTitle(), Config.Format.CONFIG_CUT_STRING.getIntegerValue());
    	return input.replace("%artist%", artist).replace("%title%", title);
	}

	private static void getStatus()
	{
		try {
			status = player.getStatus();

			if (status.getArtist() != null)
			{
				finalText = getFormatted(Config.Format.CONFIG_FORMAT_ARTIST.getStringValue());
			}
			else if (status.getTitle() != null)
			{
				finalText = getFormatted(Config.Format.CONFIG_FORMAT_NO_ARTIST.getStringValue());
			}
			else
			{
				finalText = "";
			}
		} catch (CommunicationException e) {
			LOGGER.error("Failed to get player status! (CommunicationException)");
			e.printStackTrace();
		} catch (ProtocolException e) {
			LOGGER.error("Failed to get player status! (ProtocolException)");
			e.printStackTrace();
		}
    }
    
}
