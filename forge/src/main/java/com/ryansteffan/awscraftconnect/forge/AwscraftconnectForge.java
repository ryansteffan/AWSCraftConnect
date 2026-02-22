package com.ryansteffan.awscraftconnect.forge;

import com.ryansteffan.awscraftconnect.Awscraftconnect;
import dev.architectury.platform.forge.EventBuses;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.checkerframework.checker.units.qual.C;

@Mod(Awscraftconnect.MOD_ID)
public final class AwscraftconnectForge {
    public static IEventBus MOD_EVENT_BUS;

    public AwscraftconnectForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(Awscraftconnect.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.addListener(this::onMultiplayerScreenRender);
        MinecraftForge.EVENT_BUS.addListener(this::onMainMenuScreenRender);

        // Run our common setup.
        Awscraftconnect.init();
    }

    private void onMultiplayerScreenRender(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen) {
            JoinMultiplayerScreen multiplayerScreen = (JoinMultiplayerScreen) event.getScreen();
            System.out.println("API BASE URL: " + Awscraftconnect.CONFIG.getBaseURL());
            System.out.println("API KEY: " + Awscraftconnect.CONFIG.getApiKey());
        }
    }

    private void onMainMenuScreenRender(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof net.minecraft.client.gui.screens.TitleScreen) {
            String buttonText = "AWS Craft Connect Settings";
            Component multiplayerLabel = Component.translatable("menu.multiplayer");
            Button multiplayerButton = null;
            int step = 0;

            for (Renderable renderable : event.getScreen().renderables) {
                if (renderable instanceof Button) {
                    Button button = (Button) renderable;
                    if (button.getMessage().getString().equals(multiplayerLabel.getString())) {
                        multiplayerButton = button;
                        step = button.getHeight() + 2;
                        break;
                    }
                }
            }

            if (multiplayerButton != null && step > 0) {
                int anchorY = multiplayerButton.getY();
                for (Renderable renderable : event.getScreen().renderables) {
                    if (renderable instanceof Button) {
                        Button button = (Button) renderable;
                        if (button.getY() > anchorY) {
                            button.setPosition(button.getX(), button.getY() + step);
                        }
                    }
                }

                int x = multiplayerButton.getX();
                int y = anchorY + step;
                int width = multiplayerButton.getWidth();
                Button configButton = Button.builder(Component.literal(buttonText),
                        this::handleConfigClick
                ).bounds(x, y, width, 20).build();
                event.addListener(configButton);
                event.getScreen().renderables.add(configButton);
            }
        }
    }

    private void handleConfigClick(Button button) {
        System.out.println("Config button clicked!");
        Minecraft.getInstance().setScreen(
                new ConfigScreen(Component.literal("AWS Craft Connect Settings"))
        );
    }
}

class ConfigScreen extends Screen {
    private ConfigScreenLayout layout;
    private String baseURL;
    private String apiKey;
    private EditBox baseURLInput;
    private EditBox apiKeyInput;
    private Button saveButton;
    private Button cancelButton;
    private boolean isCancled = false;

    protected ConfigScreen(Component title) {
        super(title);
        this.layout = new ConfigScreenLayout(new Vec2(super.width, super.height));
    }

    @Override
    protected void init() {
        super.init();
        this.baseURLInput = new EditBox(
                this.font,
                this.layout.baseURLInputPos.X,
                this.layout.baseURLInputPos.Y,
                this.layout.itemHeightWidth.X,
                this.layout.itemHeightWidth.Y,
                Component.literal("Base URL"));
        this.addRenderableWidget(this.baseURLInput);
        this.apiKeyInput = new EditBox(
                this.font,
                this.layout.apiKeyInputPos.X,
                this.layout.apiKeyInputPos.Y,
                this.layout.itemHeightWidth.X,
                this.layout.itemHeightWidth.Y,
                Component.literal("API Key"));
        this.addRenderableWidget(this.apiKeyInput);
        this.saveButton = Button.builder(Component.literal("Save"),
                button -> {
                    this.onClose();
                }
        ).bounds(
                this.layout.saveButtonPos.X,
                this.layout.saveButtonPos.Y,
                this.layout.itemHeightWidth.X,
                this.layout.itemHeightWidth.Y
        ).build();
        this.addRenderableWidget(this.saveButton);
        this.cancelButton = Button.builder(Component.literal("Cancel"),
                button -> {
                    this.isCancled = true;
                    this.onClose();
                }
        ).bounds(
                this.layout.cancelButtonPos.X,
                this.layout.cancelButtonPos.Y,
                this.layout.itemHeightWidth.X,
                this.layout.itemHeightWidth.Y
        ).build();
        this.addRenderableWidget(this.cancelButton);
    }

    @Override
    public void tick() {
        super.tick();
        // Handle any updates to the config options here
        this.baseURLInput.tick();
        this.apiKeyInput.tick();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public void onClose() {
        super.onClose();
        // Save the config options here
        if (isCancled) return;
        Awscraftconnect.CONFIG.setBaseURL(this.baseURL);
        Awscraftconnect.CONFIG.setApiKey(this.apiKey);
    }
}

class Vec2 {
    public final int X;
    public final int Y;

    public Vec2(int x, int y) {
        this.X = x;
        this.Y = y;
    }
}

class ConfigScreenLayout {
    public final Vec2 itemHeightWidth;
    public final Vec2 baseURLLabelPos;
    public final Vec2 baseURLInputPos;
    public final Vec2 apiKeyLabelPos;
    public final Vec2 apiKeyInputPos;
    public final Vec2 saveButtonPos;
    public final Vec2 cancelButtonPos;

    public ConfigScreenLayout(Vec2 screenWidthHeight) {
        int centerX = screenWidthHeight.X / 2;
        int centerY = screenWidthHeight.Y / 2;
        this.itemHeightWidth = new Vec2(200, 20);
        this.baseURLLabelPos = new Vec2(centerX - 100, centerY - 30);
        this.baseURLInputPos = new Vec2(centerX - 100, centerY - 10);
        this.apiKeyLabelPos = new Vec2(centerX - 100, centerY + 10);
        this.apiKeyInputPos = new Vec2(centerX - 100, centerY + 30);
        this.saveButtonPos = new Vec2(centerX - 100, centerY + 50);
        this.cancelButtonPos = new Vec2(centerX, centerY + 50);
    }
}