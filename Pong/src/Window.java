import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Window extends JFrame implements Runnable {

    //graphics class
    public Graphics2D graphics2D;

    public KL keyListener = new KL();
    public Rect playerOne, ai, ballRect;
    public PlayerController playerController;
    public AIController aiController;
    public Ball ball;

    public Text leftScoreText, rightScoreText;
    public boolean isRunning = true;

    public Window() {
        this.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.setTitle(Constants.TITLE);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(keyListener);
        Constants.TOOLBAR_HEIGHT = this.getInsets().top;
        Constants.INSETS_BOTTOM = this.getInsets().bottom;

        leftScoreText = new Text(0, new Font("Times New Roman", Font.PLAIN, Constants.TEXT_SIZE), Constants.TEXT_X_POSITION, Constants.TEXT_Y_POSITION);
        rightScoreText = new Text(0, new Font("Times New Roman", Font.PLAIN, Constants.TEXT_SIZE), Constants.SCREEN_WIDTH - Constants.TEXT_X_POSITION - Constants.TEXT_SIZE, Constants.TEXT_Y_POSITION);

        graphics2D = (Graphics2D) this.getGraphics();

        playerOne = new Rect(Constants.HZ_PADDING, 40, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT, Constants.PADDLE_COLOR);
        playerController = new PlayerController(playerOne, keyListener);

        ai = new Rect(Constants.SCREEN_WIDTH - Constants.PADDLE_WIDTH - Constants.HZ_PADDING, 40, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT, Constants.PADDLE_COLOR);
        ballRect = new Rect(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2, Constants.BALL_WIDTH, Constants.BALL_WIDTH, Constants.PADDLE_COLOR);
        ball = new Ball(ballRect, playerOne, ai, leftScoreText, rightScoreText);

        aiController = new AIController(new PlayerController(ai), ballRect);

    }

    //update window
    public void update(double dt) {
        //Create double screen behind the scene to draw everything simultaneously
        Image doubleBufferImage = createImage(getWidth(), getHeight());
        Graphics doubleBufferGraphics = doubleBufferImage.getGraphics();
        this.draw(doubleBufferGraphics);
        graphics2D.drawImage(doubleBufferImage, 0, 0, this);

        playerController.update(dt);
        aiController.update(dt);
        ball.update(dt);

        // System.out.println("" + dt + " seconds passed since the last frame"); //time(delta time) it took to complete the last frame
        // System.out.println(1 / dt + " fps");
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        leftScoreText.draw(g2d);
        rightScoreText.draw(g2d);

        playerOne.draw(g2d);
        ai.draw(g2d);
        ballRect.draw(g2d);
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
