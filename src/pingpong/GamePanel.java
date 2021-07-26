package pingpong;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int PING_PONG_BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    //Human player
    Paddle paddle1;
    //AI player
    Paddle paddle2;
    PingPongBall pingPongBall;
    Score score;
    boolean gameStarted;
    Graphics gfx;


    GamePanel() {
        newPaddles();
        newPingPongBall();
        //gameStarted = false;
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newPingPongBall() {
        //random = new Random();
        pingPongBall = new PingPongBall((GAME_WIDTH/2) - (PING_PONG_BALL_DIAMETER/2), (GAME_HEIGHT/2) - (PING_PONG_BALL_DIAMETER/2),
                PING_PONG_BALL_DIAMETER, PING_PONG_BALL_DIAMETER);
    }
    public void newPaddles() {
        paddle1 = new Paddle(0, (GAME_HEIGHT/2)-(PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT/2)-(PADDLE_HEIGHT/2), PADDLE_WIDTH,
                PADDLE_HEIGHT, 2);

    }
    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }
    public void draw(Graphics g) {
        paddle1.draw(g);
        paddle2.draw(g);
        pingPongBall.draw(g);
        score.draw(g);
        /*
        if(!gameStarted) {
            g.setColor(Color.blue);
            g.drawString("Welcome to Ping Pong made by Yusuf Arfan Ismail", 340, 100);
            g.drawString("Press the enter key to start", 310, 130);
        }

         */
        Toolkit.getDefaultToolkit().sync(); // I forgot to add this line of code in the video, it helps with the animation
    }
    public void move() {
        paddle1.move();
        paddle2.move();
        pingPongBall.move();
    }
    public void checkCollision() {
        //bounce ball off top and bottom window edges
        if(pingPongBall.y <= 0) {
            pingPongBall.setYDirection(-pingPongBall.yVelocity);
        }
        if(pingPongBall.y >= GAME_HEIGHT - PING_PONG_BALL_DIAMETER) {
            pingPongBall.setYDirection(-pingPongBall.yVelocity);
        }
        //bounces ping pong ball of paddles
        if(pingPongBall.intersects(paddle1)) {
            pingPongBall.xVelocity = Math.abs(pingPongBall.xVelocity);
            pingPongBall.xVelocity++;
            if(pingPongBall.yVelocity > 0)
                pingPongBall.yVelocity++;
            else
                pingPongBall.yVelocity--;
            pingPongBall.setXDirection(pingPongBall.xVelocity);
            pingPongBall.setXDirection(pingPongBall.yVelocity);
        }
        if(pingPongBall.intersects(paddle2)) {
            pingPongBall.xVelocity = Math.abs(pingPongBall.xVelocity);
            pingPongBall.xVelocity++; //optional for more difficulty
            if(pingPongBall.yVelocity > 0)
                pingPongBall.yVelocity++; //optional for more difficulty
            else
                pingPongBall.yVelocity--;
            pingPongBall.setXDirection(-pingPongBall.xVelocity);
            pingPongBall.setYDirection(pingPongBall.yVelocity);
        }
        //stops paddles at window edges
        if(paddle1.y <= 0 )
            paddle1.y = 0;
        if(paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;

        if(paddle2.y <= 0 )
            paddle2.y = 0;
        if(paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;
        //give a player one point and creates new paddles and ping pong ball
        if(pingPongBall.x <= 0) {
            score.player2++;
            newPaddles();
            newPingPongBall();
            System.out.println("Player 2: "+score.player2);
        }
        if(pingPongBall.x >= GAME_WIDTH - PING_PONG_BALL_DIAMETER) {
            score.player1++;
            newPaddles();
            newPingPongBall();
            System.out.println("Player 1: "+score.player1);
        }
    }
    public void run() {
        //game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while(true) {
            long now = System.nanoTime();
            delta += (now -lastTime)/ns;
            lastTime = now;
            if(delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }
    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
            //TODO add start game. enter
        }
        public void keyReleased(KeyEvent e) {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }
    }
}
