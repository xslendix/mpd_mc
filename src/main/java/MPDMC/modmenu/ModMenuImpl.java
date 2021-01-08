package MPDMC.modmenu;

import MPDMC.Reference;
import io.github.prospector.modmenu.api.ModMenuApi;

public class ModMenuImpl implements ModMenuApi {
	@Override
	public String getModId()
	{
		return Reference.MOD_ID;
	}
}
