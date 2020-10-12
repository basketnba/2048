package ru.geekbrains.game_2048;

import java.awt.*;

class CellProperty {

    //паблики убрать возможно
    public Color colorNumber;
    public Color colorCell;
    public Font font;
    public int deltaX;
    public int deltaY;

    CellProperty(Color colorNumber, Color colorCell, Font font, int deltaX, int deltaY) {
        this.colorNumber = colorNumber;
        this.colorCell = colorCell;
        this.font = font;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }
}