package com.tbane.solitaire.GUI;

import com.tbane.solitaire.Renderer;
import com.tbane.solitaire.Textures.Tex;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class ButtonWithText extends Button {

    String _text;
    public ButtonWithText(String text, Tex normalTexture, Tex hoverTexture, Tex pressedTexture, int x, int y, int width, int height) {
        super(normalTexture, hoverTexture, pressedTexture, x, y, width, height);
        _text = text;

    }

    @Override
    public void handleEvents() {
        super.handleEvents();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw() {
        super.draw();

        GlyphLayout layout = new GlyphLayout();
        layout.setText(Font.buttonFont, _text);

        float textWidth = layout.width;
        float textHeight = Font.buttonFont.getCapHeight();

        float x = _rect.x + (_rect.width - textWidth) / 2f;
        float y = _rect.y + (_rect.height + textHeight) / 2f;

        Font.buttonFont.draw(Renderer.spriteBatch, _text, x, y);
    }
}
