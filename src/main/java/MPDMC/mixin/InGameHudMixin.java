package MPDMC.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import MPDMC.GUI.GuiMPD_Client;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(InGameHud.class)
public class InGameHudMixin {
	
	@Inject(at = @At("TAIL"), method = "render")
    public void render(MatrixStack matrixStack, float tickDelta, CallbackInfo info)
	{
		GuiMPD_Client.INSTANCE.render(matrixStack, tickDelta);
	}

	@Inject(at = @At("TAIL"), method = "tick")
	public void tick(CallbackInfo info)
	{
        GuiMPD_Client.INSTANCE.tick();
	}

}
