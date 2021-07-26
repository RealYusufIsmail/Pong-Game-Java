package pingpong;

import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame{

    GamePanel panel;

    GameFrame() {
        panel = new GamePanel();
        this.add(panel);
        this.setTitle("Not Ping Pong made by Yusuf Arfan Ismail");
        this.setResizable(false);
        this.setBackground(Color.cyan);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    //https://gist.github.com/RealYusufIsmail/02600e8a0e2fe4114b1da7f145691380
}
