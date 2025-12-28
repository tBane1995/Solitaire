package com.tbane.solitaire;

import com.badlogic.gdx.Gdx;

public class Time {

    public static float previousTime;
    public static float currentTime;

    Time(){
        previousTime = 0.0f;
        currentTime = 0.0f;
    }

    public static void update() {
        previousTime = currentTime;
        currentTime += Gdx.graphics.getDeltaTime();
    }

    public static float easeOut(float t) {
        return 1.0f - (float)Math.pow(1 - t, 3);
    }
}
