package ru.geekbrains.game_2048;

import javax.swing.*;
import java.awt.*;

class GameWindow extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameWindow();
            }
        });
    }

    private static final int POS_X = 600;
    private static final int POS_Y = 100;
    private static final int WINDOW_WIDTH = 576;
    private static final int WINDOW_HEIGHT = 799;


    private GameWindow() {
        //добавить обработку исключений
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(POS_X, POS_Y);
        setTitle("2048");

        GameMap gameMap = new GameMap();
        LeaderboardMap leaderboardMap=new LeaderboardMap();
        leaderboardMap.setPreferredSize(new Dimension(0,200));

        add(leaderboardMap, BorderLayout.NORTH);
        add(gameMap, BorderLayout.CENTER);

        //подсчет очков доп поля и формы панели


        setVisible(true);
    }

}