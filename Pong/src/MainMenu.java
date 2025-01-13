import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MainMenu extends JFrame implements Runnable {

    //graphics class
    public Graphics2D graphics2D;
    public KL keyListener = new KL();
    public ML mouseListener = new ML();
    public Text startGame, exitGame, pong;
    public boolean isRunning = true;

    public MainMenu() {
        this.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.setTitle(Constants.TITLE);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(keyListener);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.startGame = new Text("Start Game", new Font("Times New Roman", Font.PLAIN, 40), Constants.SCREEN_WIDTH / 2.0 - 140.0, Constants.SCREEN_HEIGHT / 2.0, Color.WHITE);
        this.exitGame = new Text("Exit", new Font("Times New Roman", Font.PLAIN,40), Constants.SCREEN_WIDTH / 2.0 - 80.0, Constants.SCREEN_HEIGHT / 2.0 + 60.0, Color.WHITE);
        this.pong = new Text("Pong", new Font("Times New Roman", Font.PLAIN, 100), Constants.SCREEN_WIDTH / 2.0 - 150.0, 200, Color.WHITE);

        graphics2D = (Graphics2D) getGraphics();
    }

    //update window
    public void update(double dt) {
        Image doubleBufferImage = createImage(getWidth(), getHeight());
        Graphics doubleBufferGraphics = doubleBufferImage.getGraphics();
        this.draw(doubleBufferGraphics);
        graphics2D.drawImage(doubleBufferImage, 0, 0, this);

        if (mouseListener.getX() > startGame.x && mouseListener.getX() < startGame.x + startGame.width &&
        mouseListener.getY() > startGame.y - startGame.height / 2.0 && mouseListener.getY() < startGame.y + startGame.height / 2.0) {
            startGame.color = new Color(178, 178, 178);
            if (mouseListener.isPressed()) {
                Main.changeState(1);
            }
        } else {
            startGame.color = Color.WHITE;
        }
        if (mouseListener.getX() > exitGame.x && mouseListener.getX() < exitGame.x + exitGame.width &&
                mouseListener.getY() > exitGame.y - exitGame.height / 2.0 && mouseListener.getY() < exitGame.y + exitGame.height / 2.0) {
            exitGame.color = new Color(178, 178, 178);
            if (mouseListener.isPressed()) {
                Main.changeState(2);
            }
        } else {
            exitGame.color = Color.WHITE;
        }
        System.out.println(mouseListener.getX() + mouseListener.getY());
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        startGame.draw(g2d);
        exitGame.draw(g2d);
        pong.draw(g2d);
    }

    public void stop() {
        isRunning = false;
    }

    @Override
    public void run() {
        double LastFrameTime = 0.0;
        while (isRunning) {
            double time = Time.getTimeElapsed();
            double deltaTime = time - LastFrameTime;
            LastFrameTime = time;

            update(deltaTime);
        }
        this.dispose();
    }
}
