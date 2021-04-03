package MPDMC.GUI.Screens;

import MPDMC.Settings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

import static MPDMC.MpdMC_Main.settings;

@Environment(EnvType.CLIENT)
public class MPDOptionsScreen extends GameOptionsScreen {
    private ButtonListWidget list;

    public MPDOptionsScreen(Screen parent, GameOptions options) {
        super(parent, options, new TranslatableText("options.mpd"));
    }

    @Override
    protected void init() {
        this.list = new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
        this.list.addSingleOptionEntry(Settings.CONFIG_POSITION_OPTION);
        this.list.addSingleOptionEntry(Settings.CONFIG_PADDING_OPTION);
        this.list.addSingleOptionEntry(Settings.CONFIG_X_OFFSET_OPTION);
        this.list.addSingleOptionEntry(Settings.CONFIG_Y_OFFSET_OPTION);
        this.children.add(this.list);
    }

    @Override
    public void onClose() {
        settings.save();
        super.onClose();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.list.render(matrices, mouseX, mouseY, delta);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 5, 16777215);
        super.render(matrices, mouseX, mouseY, delta);

    }

}
