package MPDMC.GUI;

import com.mojang.blaze3d.systems.RenderSystem;
import de.dixieflatline.mpcw.client.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static MPDMC.Settings.*;

public class GuiMPD_Client {
	
	public static final GuiMPD_Client INSTANCE = new GuiMPD_Client();
	
	private static Logger LOGGER;
    private final MinecraftClient minecraft;
    private final TextRenderer fontRenderer;
    
    private IConnection connection;
    private IClient client;
    private IPlayer player;
    private Status status;
    
    private String finalText;

    public GuiMPD_Client()
    {
    	minecraft = MinecraftClient.getInstance();
        fontRenderer = minecraft.textRenderer;
    	LOGGER = LogManager.getLogger();
    	
    	try {
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

        if (LOGGER != null) LOGGER.debug("Created GUI");
        
        Thread t1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
            	while (true)
            	{
            		getStatus();
            		try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
            	}
            }
        });  
        t1.start();
    }
    
    public void render(MatrixStack stack, float partialTicks)
    {
    	if (!CONFIG_SHOWN || minecraft.options.debugEnabled)
    		return;
    	
		RenderSystem.pushMatrix();

		float width = MinecraftClient.getInstance().getWindow().getScaledWidth();
		float height = MinecraftClient.getInstance().getWindow().getScaledHeight();
		float widthText = fontRenderer.getWidth(finalText);
		float heightText = fontRenderer.fontHeight;

		if (status != null)
			if (status.getState() == EState.Play)
			{
				// int t = fontRenderer.getWidth(finalText);

				switch(CONFIG_POSITION) {
					case TOP_LEFT:
						fontRenderer.drawWithShadow(stack, finalText, (float)CONFIG_X_OFFSET+(float)CONFIG_PADDING, (float)CONFIG_Y_OFFSET+ (float)CONFIG_PADDING, 0xffffff);
						break;
					case TOP:
						fontRenderer.drawWithShadow(stack, finalText, width/2-widthText/2 + (float)CONFIG_X_OFFSET, (float)CONFIG_Y_OFFSET+ (float)CONFIG_PADDING, 0xffffff);
						break;
					case TOP_RIGHT:
						fontRenderer.drawWithShadow(stack, finalText, width-widthText+(float)CONFIG_X_OFFSET- (float)CONFIG_PADDING, (float)CONFIG_Y_OFFSET+ (float)CONFIG_PADDING, 0xffffff);
						break;
					case CENTER_LEFT:
						fontRenderer.drawWithShadow(stack, finalText, (float)CONFIG_X_OFFSET+ (float)CONFIG_PADDING, height/2-heightText/2+(float)CONFIG_Y_OFFSET, 0xffffff);
						break;
					case CENTER:
						fontRenderer.drawWithShadow(stack, finalText, width/2-widthText/2+(float)CONFIG_X_OFFSET, height/2-heightText/2+(float)CONFIG_Y_OFFSET, 0xffffff);
						break;
					case CENTER_RIGHT:
						fontRenderer.drawWithShadow(stack, finalText, width-widthText+(float)CONFIG_X_OFFSET- (float)CONFIG_PADDING, height/2-heightText/2+(float)CONFIG_Y_OFFSET, 0xffffff);
						break;
					case BOTTOM_LEFT:
						fontRenderer.drawWithShadow(stack, finalText, (float)CONFIG_X_OFFSET+ (float)CONFIG_PADDING, height-heightText-(float)CONFIG_Y_OFFSET- (float)CONFIG_PADDING, 0xffffff);
						break;
					case BOTTOM:
						fontRenderer.drawWithShadow(stack, finalText, width/2-widthText/2+(float)CONFIG_X_OFFSET, height-heightText-(float)CONFIG_Y_OFFSET- (float)CONFIG_PADDING, 0xffffff);
						break;
					case BOTTOM_RIGHT:
						fontRenderer.drawWithShadow(stack, finalText, width-widthText+(float)CONFIG_X_OFFSET- (float)CONFIG_PADDING, height-heightText-(float)CONFIG_Y_OFFSET- (float)CONFIG_PADDING, 0xffffff);
						break;
				};

				// TODO: Add progress bar

			}

		RenderSystem.popMatrix();
	}

	public void tick()
	{
	}

	private void getStatus()
	{
		try {
			status = player.getStatus();

			if (status.getArtist() != null)
			{
				finalText = "Now playing: \"" + status.getTitle() + "\" by " + status.getArtist();
			}
			else if (status.getTitle() != null)
			{
				finalText = "Now playing: \"" + status.getTitle() + "\"";
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
