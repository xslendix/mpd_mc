package MPDMC.modmenu;

import MPDMC.GUI.Screens.MPDOptionsScreen;
import MPDMC.Reference;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;

public class ModMenuImpl implements ModMenuApi {
	@Override
	public String getModId()
	{
		return Reference.MOD_ID;
	}

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return screen -> new MPDOptionsScreen(screen, null);
	}
}
