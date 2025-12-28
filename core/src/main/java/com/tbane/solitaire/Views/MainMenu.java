package com.tbane.solitaire.Views;

import java.util.ArrayList;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.tbane.solitaire.GUI.Font;
import com.tbane.solitaire.Game.Game;
import com.tbane.solitaire.Game.NoiseShader;
import com.tbane.solitaire.MyInput.MyInput;
import com.tbane.solitaire.Renderer;
import com.tbane.solitaire.SoundManager;
import com.tbane.solitaire.Textures.TexturesManager;
import com.tbane.solitaire.GUI.Button;
import com.tbane.solitaire.GUI.ButtonWithText;

public class MainMenu extends Layout {
    private ArrayList<ButtonWithText> _buttons;


    public MainMenu() {
        super();

        ArrayList<String> btnTexts = new ArrayList<>();

        btnTexts.add("new game");
        btnTexts.add("highscores");
        btnTexts.add("instructions");
        btnTexts.add("about");
        btnTexts.add("exit");

        int btnWdt = 512;
        int btnHgh = 96;
        int padding = 32;

        _buttons = new ArrayList<>();
        int logoSize = 320;
        int logoPadding = 32;
        int hgh = -logoSize + logoPadding + btnTexts.size()*btnHgh - (btnTexts.size()-1)*padding;
        int start_y = Renderer.VIRTUAL_HEIGHT/2 + hgh/2;

        for(int i=0;i<btnTexts.size();i+=1) {
            ButtonWithText btn = new ButtonWithText (
                btnTexts.get(i),
                TexturesManager.getTexture("tex/menuButtonNormal.png"),
                TexturesManager.getTexture("tex/menuButtonHover.png"),
                TexturesManager.getTexture("tex/menuButtonPressed.png"),
                (Renderer.VIRTUAL_WIDTH-btnWdt)/2,
                start_y - i*btnHgh - i*padding,
                btnWdt,
                btnHgh
		    );
            _buttons.add(btn);
        }

        _buttons.get(0).onclick_func = () -> {
            SoundManager.stopAll();
            MyInput.processor.reset();
            LayoutsManager.array.add(new Game());
        };

        _buttons.get(1).onclick_func = () -> {
            LayoutsManager.array.add(new Highscores());
        };

        _buttons.get(2).onclick_func = () -> {
            LayoutsManager.array.add(new Instructions());
        };

        _buttons.get(3).onclick_func = () -> {
            LayoutsManager.array.add(new About());
        };

        _buttons.get(4).onclick_func = () -> {
            Gdx.app.exit();
            System.exit(0);
        };

        SoundManager.playMenuMusic();
    }
    @Override
    public void handleEvents() {
        for(Button btn : _buttons){
            btn.handleEvents();
        }
    }

    @Override
    public void update() {

        for(Button btn : _buttons){
            btn.update();
        }
    }
    @Override
    public void draw() {

        // draw the wooden background
        Sprite background = new Sprite(TexturesManager.getTexture("tex/mainBoard.png").texture);
        background.setPosition(0,0 );
        background.draw(Renderer.spriteBatch);

        // draw a Logo
        Sprite logo = new Sprite(TexturesManager.getTexture("tex/logo.png").texture);
        logo.setPosition(
            Renderer.VIRTUAL_WIDTH/2 - logo.getWidth()/2,
            Renderer.VIRTUAL_HEIGHT/2 - logo.getHeight()/2 + 256+128+32
        );

        Texture mask = TexturesManager.getTexture("tex/cards/noise.png").texture;
        Renderer.spriteBatch.setShader(NoiseShader.shader);
        NoiseShader.shader.bind();
        NoiseShader.shader.setUniformi("u_mask", 1);
        mask.bind(1);
        logo.getTexture().bind(0);
        logo.draw(Renderer.spriteBatch);

        Renderer.spriteBatch.setShader(null);

        // draw "Main Menu"
        GlyphLayout layout = new GlyphLayout();
        layout.setText(Font.titleFont, "Main Menu");

        float textWidth = layout.width;
        float textHeight = Font.titleFont.getCapHeight();

        float x = Renderer.VIRTUAL_WIDTH/2.0f - textWidth / 2f;
        float y = _buttons.get(0)._rect.y + _buttons.get(0)._rect.height + 112;

        Sprite frame = new Sprite(TexturesManager.getTexture("tex/titleFrame.png").texture);
        frame.setOriginCenter();
        frame.setCenter(x + textWidth/2.0f, y-textHeight/2.0f);
        frame.draw(Renderer.spriteBatch);

        Font.titleFont.draw(Renderer.spriteBatch, "Main Menu", x, y);

        Sprite leftSign = new Sprite(TexturesManager.getTexture("tex/mainMenuLeftSign.png").texture);
        leftSign.setOriginCenter();
        leftSign.setCenter(x - 80, y-textHeight/2.0f);
        leftSign.draw(Renderer.spriteBatch);

        Sprite rightSign = new Sprite(TexturesManager.getTexture("tex/mainMenuRightSign.png").texture);
        rightSign.setOriginCenter();
        rightSign.setCenter(x + textWidth + 80, y-textHeight/2.0f);
        rightSign.draw(Renderer.spriteBatch);

        // draw the buttons
        for(Button btn : _buttons){
            btn.draw();
        }

        // ads panel
        Sprite adsPanel = new Sprite(TexturesManager.getTexture("tex/bottomPanel.png").texture);
        adsPanel.setPosition(0, 0);
        adsPanel.draw(Renderer.spriteBatch);
    }
}
