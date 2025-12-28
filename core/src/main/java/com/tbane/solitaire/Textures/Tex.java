package com.tbane.solitaire.Textures;

import com.badlogic.gdx.graphics.Texture;

public class Tex {
    public String path;
    public Texture texture;

    public Tex(String pathfile){
        path = pathfile;
        texture = new Texture(pathfile);
    }
}
