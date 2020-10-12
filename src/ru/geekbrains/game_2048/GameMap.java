package ru.geekbrains.game_2048;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

class GameMap extends JPanel {

    private static final int MAP_SIZE_X = 4;
    private static final int MAP_SIZE_Y = 4;
    private static final int FIELD_MARGIN = 8;
    private static final int CORNERS_ROUND = 10;

    private int cellWidth;//нужно ли выносить в поле
    private int cellHeight;//нужно ли выносить в поле
    private final int[][] field = new int[MAP_SIZE_Y][MAP_SIZE_X];
    private final ListProperties listProperties;
    private final Color colorEmptyCell = new Color(205, 193, 181);

    //    private boolean initialized;
    private boolean isMoved;
    private ArrayList<Integer> listCheck = new ArrayList<>();
    private final Random rnd = new Random();
    private boolean gameOver;

    GameMap() {
        initGame();
        setBackground(new Color(188, 175, 161));
        try {
            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Axiforma-Bold.ttf")));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        Font font1 = new Font("Axiforma-Bold", Font.PLAIN, 75);
        Font font2 = new Font("Axiforma-Bold", Font.PLAIN, 65);
        Font font3 = new Font("Axiforma-Bold", Font.PLAIN, 48);
        Font font4 = new Font("Axiforma-Bold", Font.PLAIN, 41);
        Font font5 = new Font("Axiforma-Bold", Font.PLAIN, 37);

        listProperties = new ListProperties();
        //попробовать сделать автоматическое определение местоположение числа в ячейке
        listProperties.add(2, new Color(120, 110, 101), new Color(239, 229, 219), font1, 50, 100);
        listProperties.add(4, new Color(120, 110, 101), new Color(238, 225, 201), font1, 47, 100);
        listProperties.add(8, new Color(255, 255, 255), new Color(243, 179, 122), font1, 47, 100);
        listProperties.add(16, new Color(255, 255, 255), new Color(237, 142, 83), font1, 32, 100);
        listProperties.add(32, new Color(255, 255, 255), new Color(247, 125, 95), font1, 28, 100);
        listProperties.add(64, new Color(255, 255, 255), new Color(235, 89, 52), font1, 24, 100);
        listProperties.add(128, new Color(255, 255, 255), new Color(244, 217, 107), font2, 18, 96);
        listProperties.add(256, new Color(255, 255, 255), new Color(242, 209, 74), font2, 12, 96);
        listProperties.add(512, new Color(255, 255, 255), new Color(229, 193, 38), font2, 20, 96);
        listProperties.add(1024, new Color(255, 255, 255), new Color(227, 188, 11), font3, 15, 90);
        listProperties.add(2048, new Color(255, 255, 255), new Color(237, 197, 0), font3, 10, 90);
        listProperties.add(4096, new Color(255, 255, 255), new Color(94, 219, 148), font3, 9, 90);
        listProperties.add(8192, new Color(255, 255, 255), new Color(35, 187, 100), font3, 15, 90);
        listProperties.add(16384, new Color(255, 255, 255), new Color(33, 141, 79), font4, 11, 87);
        listProperties.add(32768, new Color(255, 255, 255), new Color(32, 140, 80), font4, 11, 87);
        listProperties.add(65536, new Color(255, 255, 255), new Color(34, 187, 97), font4, 8, 87);
        listProperties.add(131072, new Color(255, 255, 255), new Color(32, 140, 80), font5, 11, 85);

        setFocusable(true);
        //cДЕЛАТЬ ИМПЛЕМЕНТС ВОЗМОЖНО
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                update(e);
            }
        });
//        initialized=true;
    }

    private void initGame() {
        for (int i = 0; i < 2; i++) cellRandom();
    }

    private void cellRandom() {
        int x, y;
        do {
            x = rnd.nextInt(MAP_SIZE_X);
            y = rnd.nextInt(MAP_SIZE_Y);
        } while (!isEmptyCell(x, y));
        boolean needNumber = rnd.nextBoolean();
        if (needNumber && rnd.nextBoolean()) {
            field[y][x] = 4;
        } else field[y][x] = 2;
    }

    private boolean isEmptyCell(int x, int y) {
        return field[y][x] == 0;
    }

    private void update(KeyEvent e) {
        if (gameOver) return;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                for (int i = 0; i < MAP_SIZE_Y; i++) {
                    int countZero = 0;
                    for (int j = 0; j < MAP_SIZE_X; j++) {
                        int fieldValue = field[i][j];//возможно использовать метод эмпти селл вставить везде
                        if (fieldValue == 0) {
                            countZero++;
                            continue;
                        }
                        listCheck.add(fieldValue);
                    }
                    for (int j = listCheck.size() - 1; j > 0; j--) {
                        if (listCheck.get(j).equals(listCheck.get(j - 1))) {
                            listCheck.set(j, listCheck.get(j) * 2);
                            listCheck.remove(j - 1);
                            j--;
                            countZero++;
                        }
                    }
                    for (int j = 0; j < countZero; j++) listCheck.add(0, 0);
                    for (int j = 0; j < listCheck.size(); j++) {
                        if (field[i][j] != listCheck.get(j)) {
                            field[i][j] = listCheck.get(j);
                            isMoved = true;
                        }
                    }
                    //возможно надо добавить метод на обнуление а в ячейку просто вставлять 0
                    listCheck.clear();
                }
                break;
            case KeyEvent.VK_LEFT:
                for (int i = 0; i < MAP_SIZE_Y; i++) {
                    int countZero = 0;
                    for (int j = 0; j < MAP_SIZE_X; j++) {
                        int fieldValue = field[i][j];
                        if (fieldValue == 0) {
                            countZero++;
                            continue;
                        }
                        listCheck.add(fieldValue);
                    }
                    for (int j = 0; j < listCheck.size() - 1; j++) {
                        if (listCheck.get(j).equals(listCheck.get(j + 1))) {
                            listCheck.set(j, listCheck.get(j) * 2);
                            listCheck.remove(j + 1);
                            countZero++;
                        }
                    }
                    for (int j = 0; j < countZero; j++) listCheck.add(0);
                    for (int j = 0; j < listCheck.size(); j++) {
                        if (field[i][j] != listCheck.get(j)) {
                            field[i][j] = listCheck.get(j);
                            isMoved = true;
                        }
                    }
                    listCheck.clear();
                }
                break;
            case KeyEvent.VK_UP:
                for (int i = 0; i < MAP_SIZE_X; i++) {
                    int countZero = 0;
                    for (int j = 0; j < MAP_SIZE_Y; j++) {
                        int fieldValue = field[j][i];
                        if (fieldValue == 0) {
                            countZero++;
                            continue;
                        }
                        listCheck.add(fieldValue);
                    }
                    for (int j = 0; j < listCheck.size() - 1; j++) {
                        if (listCheck.get(j).equals(listCheck.get(j + 1))) {
                            listCheck.set(j, listCheck.get(j) * 2);
                            listCheck.remove(j + 1);
                            countZero++;
                        }
                    }
                    for (int j = 0; j < countZero; j++) listCheck.add(0);
                    for (int j = 0; j < listCheck.size(); j++) {
                        if (field[j][i] != listCheck.get(j)) {
                            field[j][i] = listCheck.get(j);
                            isMoved = true;
                        }
                    }
                    listCheck.clear();
                }
                break;
            case KeyEvent.VK_DOWN:
                for (int i = 0; i < MAP_SIZE_X; i++) {
                    int countZero = 0;
                    for (int j = 0; j < MAP_SIZE_Y; j++) {
                        int fieldValue = field[j][i];
                        if (fieldValue == 0) {
                            countZero++;
                            continue;
                        }
                        listCheck.add(fieldValue);
                    }
                    for (int j = listCheck.size() - 1; j > 0; j--) {
                        if (listCheck.get(j).equals(listCheck.get(j - 1))) {
                            listCheck.set(j, listCheck.get(j) * 2);
                            listCheck.remove(j - 1);
                            j--;
                            countZero++;
                        }
                    }
                    for (int j = 0; j < countZero; j++) listCheck.add(0, 0);
                    for (int j = 0; j < listCheck.size(); j++) {
                        if (field[j][i] != listCheck.get(j)) {
                            field[j][i] = listCheck.get(j);
                            isMoved = true;
                        }
                    }
                    listCheck.clear();
                }
                break;
        }
//        добавить дефолтную секцию свитч возможно чтобы выкидывало вообще из метода и не работало все что ниже на любую клавишу
        // также попробовать объединить кейсы два в один убрав брейки если содержимое кейсов одинаковое, что будет:)))
        if (isMoved) {
            isMoved = false;
            cellRandom();
            repaint();
        }
        if (isMapFull() && !isCheckMove()) {
            System.out.println("End");
            //new panel game over
            gameOver = true;
        }
    }

    private boolean isCheckMove() {
        //метод не получилось укоротить поскольку пропускаем боковые узлы по периметру
        for (int i = 0; i < MAP_SIZE_Y - 1; i++) {
            for (int j = 0; j < MAP_SIZE_X; j++) {
                if (field[i][j] == field[i + 1][j]) return true;
            }
        }
        for (int i = 0; i < MAP_SIZE_Y; i++) {
            for (int j = 0; j < MAP_SIZE_X - 1; j++) {
                if (field[i][j] == field[i][j + 1]) return true;
            }
        }
        return false;
    }

    private boolean isMapFull() {
        for (int i = 0; i < MAP_SIZE_Y; i++) {
            for (int j = 0; j < MAP_SIZE_X; j++) {
                if (field[i][j] == 0) return false;
            }
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
        System.out.println("Отрисовка");
    }

    private void render(Graphics g) {
//        if (!initialized) return;
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        cellWidth = panelWidth / MAP_SIZE_X;
        cellHeight = panelHeight / MAP_SIZE_Y;
        for (int i = 0; i < MAP_SIZE_Y; i++) {
            for (int j = 0; j < MAP_SIZE_X; j++) {
                int fieldValue = field[i][j];
                if (fieldValue == 0) {
                    g.setColor(colorEmptyCell);
                    g.fillRoundRect(j * cellWidth + FIELD_MARGIN, i * cellHeight + FIELD_MARGIN, cellWidth - 2 * FIELD_MARGIN, cellHeight - 2 * FIELD_MARGIN, CORNERS_ROUND, CORNERS_ROUND);
                } else {
                    g.setColor(listProperties.getColorCell(fieldValue));
                    g.fillRoundRect(j * cellWidth + FIELD_MARGIN, i * cellHeight + FIELD_MARGIN, cellWidth - 2 * FIELD_MARGIN, cellHeight - 2 * FIELD_MARGIN, CORNERS_ROUND, CORNERS_ROUND);
                    g.setColor(listProperties.getColorNumber(fieldValue));
                    g.setFont(listProperties.getFont(fieldValue));
                    g.drawString(Integer.toString(fieldValue), j * cellWidth + listProperties.getDeltaX(fieldValue), i * cellHeight + listProperties.getDeltaY(fieldValue));
                }
            }
        }
    }
}