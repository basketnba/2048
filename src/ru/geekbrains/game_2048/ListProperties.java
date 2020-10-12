package ru.geekbrains.game_2048;

import java.awt.*;
import java.util.HashMap;

class ListProperties {

    private final HashMap<Integer, CellProperty> records = new HashMap<>();

    void add(int number, Color colorNumber, Color colorCell, Font font, int deltaX, int deltaY) {
        if (!records.containsKey(number)) {
            records.put(number, new CellProperty(colorNumber, colorCell, font, deltaX, deltaY));
        } else throw new RuntimeException("Такое число уже создано");
    }

    Color getColorNumber(int number) {
        if (records.containsKey(number)) return records.get(number).colorNumber;
        throw new RuntimeException("Этого числа нет в списке. Недостижимый результат");
    }

    Color getColorCell(int number) {
        if (records.containsKey(number)) return records.get(number).colorCell;
        throw new RuntimeException("Этого числа нет в списке. Недостижимый результат");
    }

    Font getFont(int number) {
        if (records.containsKey(number)) return records.get(number).font;
        throw new RuntimeException("Этого числа нет в списке. Недостижимый результат");
    }

    int getDeltaX(int number) {
        if (records.containsKey(number)) return records.get(number).deltaX;
        throw new RuntimeException("Этого числа нет в списке. Недостижимый результат");
    }

    int getDeltaY(int number) {
        if (records.containsKey(number)) return records.get(number).deltaY;
        throw new RuntimeException("Этого числа нет в списке. Недостижимый результат");
    }
}