package com.yusuf.pingpong;

import java.applet.Applet;
import java.awt.*;

public class Tennis extends Applet {
    final int WIDTH = 700, HEIGHT = 500;
    public void init() {
        this.resize(WIDTH, HEIGHT);
    }

    public void paint(Graphics g) {

    }

    public void update(Graphics g) {
        paint(g);
    }
}
