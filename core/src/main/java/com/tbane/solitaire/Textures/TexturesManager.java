package com.tbane.solitaire.Textures;

import java.util.ArrayList;
import java.util.List;

public class TexturesManager {
    private static List<Tex> array;

    static {
        array = new ArrayList<>();

        loadTexture("tex/mainBoard.png");
        loadTexture("tex/logo.png");
        loadTexture("tex/panel.png");
        loadTexture("tex/titleFrame.png");
        loadTexture("tex/topPanel.png");
        loadTexture("tex/bottomPanel.png");

        loadTexture("tex/mainMenuLeftSign.png");
        loadTexture("tex/mainMenuRightSign.png");

        loadTexture("tex/menuButtonNormal.png");
        loadTexture("tex/menuButtonHover.png");
        loadTexture("tex/menuButtonPressed.png");

        loadTexture("tex/backButtonNormal.png");
        loadTexture("tex/backButtonHover.png");
        loadTexture("tex/backButtonPressed.png");

        loadTexture("tex/slot.png");
        loadTexture("tex/cards/back.png");
        loadTexture("tex/cards/noise.png");

        for(int i=1;i<=13;i++){
            loadTexture("tex/cards/hearts_" + Integer.toString(i) + ".png");
            loadTexture("tex/cards/pikes_" + Integer.toString(i) + ".png");
            loadTexture("tex/cards/clovers_" + Integer.toString(i) + ".png");
            loadTexture("tex/cards/tiles_" + Integer.toString(i) + ".png");
        }

    }

    private static void loadTexture(String pathfile){
        Tex tex = new Tex(pathfile);
        array.add(tex);
    }

    public static Tex getTexture(String pathfile){
        for(Tex tex : array){
            if(tex.path.equals(pathfile))
                return tex;
        }

        return null;
    }

}
