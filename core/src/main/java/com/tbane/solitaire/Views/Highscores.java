package com.tbane.solitaire.Views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tbane.solitaire.GUI.Button;
import com.tbane.solitaire.GUI.Font;
import com.tbane.solitaire.MyInput.MyInput;
import com.tbane.solitaire.Renderer;
import com.tbane.solitaire.Textures.TexturesManager;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;


import java.util.ArrayList;

public class Highscores extends Layout {

    private Button _backBtn;
    private int panelWdt, panelHgh;
    private int padding;
    private int innerWdt, innerHgh;

    public Highscores() {
        super();

        _backBtn = new Button(
            TexturesManager.getTexture("tex/backButtonNormal.png"),
            TexturesManager.getTexture("tex/backButtonHover.png"),
            TexturesManager.getTexture("tex/backButtonPressed.png"),
            32, Renderer.VIRTUAL_HEIGHT - 96 - 32, 96, 96
        );

        _backBtn.onclick_func = () -> { LayoutsManager.pop_back(); };

        panelWdt = 720;
        panelHgh = 1100;
        padding = 32;
        innerWdt = panelWdt - 2 * padding;
        innerHgh = panelHgh - 2 * padding;


    }

    @Override
    public void handleEvents() {
        _backBtn.handleEvents();

        if(MyInput.processor.isBackPressed()){
            LayoutsManager.pop_back();
            MyInput.processor.reset();
        }


    }

    @Override
    public void update() {
        _backBtn.update();
    }

    @Override
    public void draw() {
        Sprite background = new Sprite(TexturesManager.getTexture("tex/mainBoard.png").texture);
        background.setPosition(0,0 );
        background.draw(Renderer.spriteBatch);

        _backBtn.draw();

        // draw text "Instructions"
        GlyphLayout layout = new GlyphLayout();
        layout.setText(Font.titleFont, "Highscores");

        float textWidth = layout.width;
        float textHeight = Font.titleFont.getCapHeight();

        float x = Renderer.VIRTUAL_WIDTH/2.0f - textWidth / 2f + 32;
        float y = _backBtn._rect.y + _backBtn._rect.height/2.0f;

        Sprite frame = new Sprite(TexturesManager.getTexture("tex/titleFrame.png").texture);
        frame.setOriginCenter();
        frame.setCenter(x + textWidth/2.0f, y);
        frame.draw(Renderer.spriteBatch);

        Font.titleFont.draw(Renderer.spriteBatch, "Highscores", x, y+textHeight/2.0f);


        // draw a panel
        Sprite panel = new Sprite(TexturesManager.getTexture("tex/panel.png").texture);
        float panelX = padding;
        float panelY = Renderer.VIRTUAL_HEIGHT - panel.getTexture().getHeight() - (96 + 2 * padding);
        panel.setPosition(panelX, panelY);
        panel.draw(Renderer.spriteBatch);

        // ads panel
        Sprite adsPanel = new Sprite(TexturesManager.getTexture("tex/bottomPanel.png").texture);
        adsPanel.setPosition(0, 0);
        adsPanel.draw(Renderer.spriteBatch);
    }
}
