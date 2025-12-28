package com.tbane.solitaire.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class Font {

    public static BitmapFont titleFont, buttonFont;
    public static BitmapFont gameTopTextsFont;
    public static BitmapFont descriptionFont;
    public static BitmapFont rankingTitleFont, rankingFont;
    static {

        // MENU
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ScienceGothic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "ąćęłńóśżźĄĆĘŁŃÓŚŻŹ";
        buttonFont = generator.generateFont(parameter);
        generator.dispose();

        FreeTypeFontGenerator generator4 = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ScienceGothic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter4 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter4.size = 56;
        parameter4.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "ąćęłńóśżźĄĆĘŁŃÓŚŻŹ";
        titleFont = generator4.generateFont(parameter4);
        generator4.dispose();

        /// /////////////////////////////////
        // GAME
        FreeTypeFontGenerator generator5 = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ScienceGothic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter5 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter5.size = 36;
        parameter5.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "ąćęłńóśżźĄĆĘŁŃÓŚŻŹ";
        gameTopTextsFont = generator5.generateFont(parameter5);
        generator5.dispose();

        /// ////////////////////////////////
        // INSTRUCTIONS & ABOUT

        FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ScienceGothic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 32;
        parameter2.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "ąćęłńóśżźĄĆĘŁŃÓŚŻŹ";
        descriptionFont = generator2.generateFont(parameter2);
        generator2.dispose();

        /// ////////////////////////////////
        // RANKING
        FreeTypeFontGenerator generator9 = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ScienceGothic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter9 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter9.size = 40;
        parameter9.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "ąćęłńóśżźĄĆĘŁŃÓŚŻŹ";
        rankingTitleFont = generator9.generateFont(parameter9);
        generator9.dispose();

        FreeTypeFontGenerator generator8 = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ScienceGothic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter8 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter8.size = 32;
        parameter8.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "ąćęłńóśżźĄĆĘŁŃÓŚŻŹ";
        rankingFont = generator8.generateFont(parameter8);
        generator8.dispose();

    }
}
