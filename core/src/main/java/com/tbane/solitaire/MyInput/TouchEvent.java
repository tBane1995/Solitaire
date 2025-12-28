package com.tbane.solitaire.MyInput;

public class TouchEvent {
    public enum Type { DOWN, UP, DRAG }

    public Type type;
    public float x, y;

    public TouchEvent(Type type, float x, float y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }
}
