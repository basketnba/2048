package ru.geekbrains.game_2048;

import javax.swing.*;
import java.awt.*;

class LeaderboardMap extends JPanel {

    LeaderboardMap() {
        setBackground(new Color(251, 249, 240));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
        System.out.println("render leaderboa");
    }

    private void render(Graphics g) {

    }
}
