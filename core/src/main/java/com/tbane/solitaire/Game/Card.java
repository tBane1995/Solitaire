package com.tbane.solitaire.Game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tbane.solitaire.MyInput.MyInput;
import com.tbane.solitaire.MyInput.MyInputProcessor;
import com.tbane.solitaire.Renderer;
import com.tbane.solitaire.Textures.Tex;
import com.tbane.solitaire.Textures.TexturesManager;

public class Card {
    public Rectangle _rect;
    public static float _dy;
    public static float _padding;
    public static Vector2 _size;

    enum cardColor {Clover, Pike, Heart, Tile}

    ;
    public cardColor _color;
    public int _value;
    public boolean _isOpen;

    public Card(cardColor color, int value) {
        _rect = new Rectangle(
            0,
            0,
            _size.x,
            _size.y
        );

        _color = color;
        _value = value;
        _isOpen = false;
    }

    public Vector2 getPosition() {
        return new Vector2(_rect.x, _rect.y);
    }

    void setPosition(Vector2 position) {
        _rect.x = position.x;
        _rect.y = position.y;
    }

    public boolean topCursorHover() {
        Rectangle topRect = new Rectangle(
            _rect.x,
            _rect.y + _rect.height - _dy,
            _rect.width,
            _dy
        );

        if (topRect.contains(MyInput.processor.getTouchPosition())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean cursorHover() {

        if (_rect.contains(MyInput.processor.getTouchPosition())) {
            return true;
        } else {
            return false;
        }
    }

    void draw() {

        Tex tex = null;

        if (_isOpen == false) {
            tex = TexturesManager.getTexture("tex/cards/back.png");
        } else {
            switch (_color) {
                case Heart:
                    tex = TexturesManager.getTexture("tex/cards/hearts_" + Integer.toString(_value) + ".png");
                    break;
                case Pike:
                    tex = TexturesManager.getTexture("tex/cards/pikes_" + Integer.toString(_value) + ".png");
                    break;
                case Tile:
                    tex = TexturesManager.getTexture("tex/cards/tiles_" + Integer.toString(_value) + ".png");
                    break;
                case Clover:
                    tex = TexturesManager.getTexture("tex/cards/clovers_" + Integer.toString(_value) + ".png");
            }
            ;
        }

        Sprite sprite = new Sprite(tex.texture);
        sprite.setPosition(_rect.x, _rect.y);
        sprite.setSize(_rect.width, _rect.height);
        Texture mask = TexturesManager.getTexture("tex/cards/noise.png").texture;

        Renderer.spriteBatch.setShader(NoiseShader.shader);
        NoiseShader.shader.bind();
        NoiseShader.shader.setUniformi("u_mask", 1);
        mask.bind(1);
        sprite.getTexture().bind(0);

        sprite.draw(Renderer.spriteBatch);
        Renderer.spriteBatch.setShader(null);

    }

}
