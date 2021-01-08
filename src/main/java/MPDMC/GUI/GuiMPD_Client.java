package MPDMC.GUI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.blaze3d.systems.RenderSystem;

import MPDMC.MpdMC_Main;
import de.dixieflatline.mpcw.client.CommunicationException;
import de.dixieflatline.mpcw.client.Connection;
import de.dixieflatline.mpcw.client.EState;
import de.dixieflatline.mpcw.client.IClient;
import de.dixieflatline.mpcw.client.IConnection;
import de.dixieflatline.mpcw.client.IPlayer;
import de.dixieflatline.mpcw.client.ProtocolException;
import de.dixieflatline.mpcw.client.Status;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

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
    	if (!MpdMC_Main.toggle || minecraft.options.debugEnabled)
    		return;
    	
		RenderSystem.pushMatrix();
		
		if (status != null)
			if (status.getState() == EState.Play)
			{
				// int t = fontRenderer.getWidth(finalText);
				fontRenderer.drawWithShadow(stack, finalText, 2f, 2f, 0xffffff);
				
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
