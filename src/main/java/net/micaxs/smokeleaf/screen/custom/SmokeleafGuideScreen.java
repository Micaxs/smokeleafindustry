package net.micaxs.smokeleaf.screen.custom;

import net.micaxs.smokeleaf.SmokeleafIndustries;
import net.micaxs.smokeleaf.screen.widget.UvImageButton;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SmokeleafGuideScreen extends Screen {
    // Texture: assets/smokeleafindustries/textures/gui/smokeleaf_guide.png (256x256)
    private static final ResourceLocation TEX = ResourceLocation.fromNamespaceAndPath(
            SmokeleafIndustries.MODID, "textures/gui/smokeleaf_guide.png");

    // Full sheet size
    private static final int TEX_W = 256, TEX_H = 256;

    // Single page sub-rect on the sheet
    private static final int IMAGE_WIDTH = 192;
    private static final int IMAGE_HEIGHT = 194; // page ends at 194px, buttons start below
    private static final int PAGE_U = 0, PAGE_V = 0;

    // Vanilla-like text layout
    public static final int PAGE_TEXT_X_OFFSET = 36;
    public static final int PAGE_TEXT_Y_OFFSET = 30;
    protected static final int TEXT_WIDTH = 114;
    protected static final int MAX_LINES = 14;
    public static final int PAGE_INDICATOR_TEXT_Y_OFFSET = 16;
    private static final float TEXT_SCALE = 0.75F;

    // Button sprites [2x2 grid], each 21x20, starting at ((u,v)=(0,194))
    // Top row: NEXT [normal, hover], Bottom row: PREV [normal, hover]
    private static final int BTN_W = 22, BTN_H = 13;

    // Title rendering
    private static final float TITLE_SCALE = 1.1F;   // larger font
    private static final int TITLE_EXTRA_Y = 10;     // title starts 10px lower
    private static final int TITLE_BODY_SPACING = 6; // small gap between title and body

    // State like BookViewScreen
    private BookAccess bookAccess;
    private int currentPage = 0;
    private List<FormattedCharSequence> cachedPageComponents = Collections.emptyList(); // body lines
    private int cachedPage = -1;

    private UvImageButton forwardButton;
    private UvImageButton backButton;

    public SmokeleafGuideScreen() {
        super(GameNarrator.NO_TITLE);
        List<Page> pages = new ArrayList<>();
        pages.add(new Page(
                Component.translatable("book.smokeleafindustries.page1.title"),
                Component.translatable("book.smokeleafindustries.page1.body")
        ));
        pages.add(new Page(
                Component.translatable("book.smokeleafindustries.page2.title"),
                Component.translatable("book.smokeleafindustries.page2.body")
        ));
        pages.add(new Page(
                Component.translatable("book.smokeleafindustries.page3.title"),
                Component.translatable("book.smokeleafindustries.page3.body")
        ));
        pages.add(new Page(
                Component.translatable("book.smokeleafindustries.page4.title"),
                Component.translatable("book.smokeleafindustries.page4.body")
        ));
        pages.add(new Page(
                Component.translatable("book.smokeleafindustries.page5.title"),
                Component.translatable("book.smokeleafindustries.page5.body")
        ));
        pages.add(new Page(
                Component.translatable("book.smokeleafindustries.page6.title"),
                Component.translatable("book.smokeleafindustries.page6.body")
        ));
        pages.add(new Page(
                Component.translatable("book.smokeleafindustries.page7.title"),
                Component.translatable("book.smokeleafindustries.page7.body")
        ));
        pages.add(new Page(
                Component.translatable("book.smokeleafindustries.page8.title"),
                Component.translatable("book.smokeleafindustries.page8.body")
        ));
        pages.add(new Page(
                Component.translatable("book.smokeleafindustries.page9.title"),
                Component.translatable("book.smokeleafindustries.page9.body")
        ));
        pages.add(new Page(
                Component.translatable("book.smokeleafindustries.page10.title"),
                Component.translatable("book.smokeleafindustries.page10.body")
        ));
        pages.add(new Page(
                Component.translatable("book.smokeleafindustries.page11.title"),
                Component.translatable("book.smokeleafindustries.page11.body")
        ));
        this.bookAccess = new BookAccess(pages);
    }

    public static void open() {
        Minecraft.getInstance().setScreen(new SmokeleafGuideScreen());
    }

    @Override
    protected void init() {
        createMenuControls();
        createPageControlButtons();
    }

    protected void createMenuControls() {
        int top = 2;
        int y = top + IMAGE_HEIGHT + 4;
        this.addRenderableWidget(
                Button.builder(CommonComponents.GUI_DONE, b -> this.onClose())
                        .bounds(this.width / 2 - 100, y, 200, 20)
                        .build()
        );
    }

    protected void createPageControlButtons() {
        int left = (this.width - IMAGE_WIDTH) / 2;
        int top = 2;

        int btnY = top + IMAGE_HEIGHT - BTN_H - 28;

        this.forwardButton = this.addRenderableWidget(
                new UvImageButton(left + 116, btnY, BTN_W, BTN_H, 0, 193, 23, 193, TEX, TEX_W, TEX_H,
                        b -> this.pageForward()
                ));
        this.backButton = this.addRenderableWidget(
                new UvImageButton(left + 43, btnY, BTN_W, BTN_H, 0, 206, 23, 206, TEX, TEX_W, TEX_H,
                        b -> this.pageBack()
                ));
        this.updateButtonVisibility();
    }

    public void setBookAccess(BookAccess access) {
        this.bookAccess = access;
        this.currentPage = Mth.clamp(this.currentPage, 0, access.getPageCount());
        this.updateButtonVisibility();
        this.cachedPage = -1;
    }

    public boolean setPage(int pageNum) {
        int i = Mth.clamp(pageNum, 0, this.bookAccess.getPageCount() - 1);
        if (i != this.currentPage) {
            this.currentPage = i;
            this.updateButtonVisibility();
            this.cachedPage = -1;
            return true;
        }
        return false;
    }

    protected boolean forcePage(int pageNum) {
        return this.setPage(pageNum);
    }

    protected void pageBack() {
        if (this.currentPage > 0) --this.currentPage;
        this.updateButtonVisibility();
    }

    protected void pageForward() {
        if (this.currentPage < this.getNumPages() - 1) ++this.currentPage;
        this.updateButtonVisibility();
    }

    private int getNumPages() {
        return this.bookAccess.getPageCount();
    }

    private void updateButtonVisibility() {
        if (this.forwardButton != null) {
            this.forwardButton.visible = this.currentPage < this.getNumPages() - 1;
        }
        if (this.backButton != null) {
            this.backButton.visible = this.currentPage > 0;
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) return true;
        switch (keyCode) {
            case 266 -> { // Page Up
                if (this.backButton != null && this.backButton.visible) this.backButton.onPress();
                return true;
            }
            case 267 -> { // Page Down
                if (this.forwardButton != null && this.forwardButton.visible) this.forwardButton.onPress();
                return true;
            }
            default -> { return false; }
        }
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        super.render(g, mouseX, mouseY, partialTick);

        int left = (this.width - IMAGE_WIDTH) / 2;
        int top = 2;

        // Cache body lines with scale-aware width
        if (this.cachedPage != this.currentPage) {
            FormattedText body = this.bookAccess.getBody(this.currentPage);
            int splitWidth = Mth.floor(TEXT_WIDTH / TEXT_SCALE);
            this.cachedPageComponents = this.font.split(body, splitWidth);
        }
        this.cachedPage = this.currentPage;

        // Render title (centered, scaled, 10px lower)
        Component title = this.bookAccess.getTitle(this.currentPage);
        int titleBaseY = top + PAGE_TEXT_Y_OFFSET + 2 + TITLE_EXTRA_Y;
        int centerX = left + PAGE_TEXT_X_OFFSET + TEXT_WIDTH / 2;
        int titleWidth = this.font.width(title);

        g.pose().pushPose();
        g.pose().translate(centerX, titleBaseY, 0);
        g.pose().scale(TITLE_SCALE, TITLE_SCALE, 1.0F);
        g.drawString(this.font, title, -titleWidth / 2, 0, 0x000000, false);
        g.pose().popPose();

        // Render body with scale and small gap after title
        int bodyStartY = titleBaseY + Mth.floor(9 * TITLE_SCALE) + TITLE_BODY_SPACING;
        int lines = Math.min(MAX_LINES, this.cachedPageComponents.size());

        g.pose().pushPose();
        g.pose().translate(left + PAGE_TEXT_X_OFFSET, bodyStartY, 0);
        g.pose().scale(TEXT_SCALE, TEXT_SCALE, 1.0F);
        for (int l = 0; l < lines; ++l) {
            FormattedCharSequence seq = this.cachedPageComponents.get(l);
            g.drawString(this.font, seq, 0, l * 9, 0x000000, false);
        }
        g.pose().popPose();

        // Hover styles
        Style style = this.getClickedComponentStyleAt((double) mouseX, (double) mouseY);
        if (style != null) {
            g.renderComponentHoverEffect(this.font, style, mouseX, mouseY);
        }
    }

    @Override
    public void renderBackground(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        this.renderTransparentBackground(g);
        int left = (this.width - IMAGE_WIDTH) / 2;
        int top = 2;
        g.blit(TEX, left, top, PAGE_U, PAGE_V, IMAGE_WIDTH, IMAGE_HEIGHT, TEX_W, TEX_H);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            Style style = this.getClickedComponentStyleAt(mouseX, mouseY);
            if (style != null && this.handleComponentClicked(style)) {
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean handleComponentClicked(Style style) {
        ClickEvent click = style.getClickEvent();
        if (click == null) return false;

        if (click.getAction() == ClickEvent.Action.CHANGE_PAGE) {
            try {
                int i = Integer.parseInt(click.getValue()) - 1;
                return this.forcePage(i);
            } catch (Exception ignored) {
                return false;
            }
        } else {
            boolean handled = super.handleComponentClicked(style);
            if (handled && click.getAction() == ClickEvent.Action.RUN_COMMAND) {
                this.onClose();
            }
            return handled;
        }
    }

    @Nullable
    public Style getClickedComponentStyleAt(double mouseX, double mouseY) {
        if (this.cachedPageComponents.isEmpty()) return null;

        int left = (this.width - IMAGE_WIDTH) / 2;
        int top = 2;

        // Match title/body offsets
        int titleBaseY = top + PAGE_TEXT_Y_OFFSET + 2 + TITLE_EXTRA_Y;
        int bodyStartY = titleBaseY + Mth.floor(9 * TITLE_SCALE) + TITLE_BODY_SPACING;

        // Convert mouse to local body-space (on-screen)
        int xOnScreen = Mth.floor(mouseX - (double) left - (double) PAGE_TEXT_X_OFFSET);
        int yOnScreen = Mth.floor(mouseY - (double) bodyStartY);

        if (xOnScreen < 0 || yOnScreen < 0) return null;

        // Convert to unscaled text space for hit-testing
        int x = Mth.floor(xOnScreen / TEXT_SCALE);
        int y = Mth.floor(yOnScreen / TEXT_SCALE);

        int lines = Math.min(MAX_LINES, this.cachedPageComponents.size());
        int splitWidth = Mth.floor(TEXT_WIDTH / TEXT_SCALE);

        if (x > splitWidth || y >= 9 * lines) return null;

        int lineIdx = y / 9;
        if (lineIdx < 0 || lineIdx >= this.cachedPageComponents.size()) return null;

        FormattedCharSequence seq = this.cachedPageComponents.get(lineIdx);
        return this.minecraft.font.getSplitter().componentStyleAtWidth(seq, x);
    }

    public static record Page(Component title, Component body) {}

    public static record BookAccess(List<Page> pages) {
        public int getPageCount() {
            return this.pages.size();
        }
        public Component getTitle(int page) {
            return (page >= 0 && page < getPageCount()) ? this.pages.get(page).title() : Component.empty();
        }
        public FormattedText getBody(int page) {
            return (page >= 0 && page < getPageCount()) ? this.pages.get(page).body() : FormattedText.EMPTY;
        }
    }
}