import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class ColorChangingPong extends JPanel implements ActionListener, KeyListener {

    private int ballX = 150, ballY = 150, ballXSpeed = 3, ballYSpeed = 3;
    private int paddleX = 200, paddleY = 450, paddleWidth = 100, paddleHeight = 10;
    private final Timer timer;
    private Color paddleColor = Color.BLUE;
    private boolean moveLeft = false, moveRight = false;

    public ColorChangingPong() {
        timer = new Timer(10, this);
        timer.start();
        this.setFocusable(true);
        this.addKeyListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Ball
        g.setColor(Color.WHITE);
        g.fillOval(ballX, ballY, 20, 20);

        // Paddle
        g.setColor(paddleColor);
        g.fillRect(paddleX, paddleY, paddleWidth, paddleHeight);
    }

    public void actionPerformed(ActionEvent e) {
        ballX += ballXSpeed;
        ballY += ballYSpeed;

        // Wall collision
        if (ballX <= 0 || ballX >= getWidth() - 20) ballXSpeed = -ballXSpeed;
        if (ballY <= 0) ballYSpeed = -ballYSpeed;

        // Paddle collision
        if (ballY + 20 >= paddleY &&
            ballX + 20 >= paddleX &&
            ballX <= paddleX + paddleWidth) {
            ballYSpeed = -ballYSpeed;
            changePaddleColor();
        }

        // Game Over
        if (ballY > getHeight()) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "Game Over!");
            System.exit(0);
        }

        // Paddle movement
        if (moveLeft && paddleX > 0) paddleX -= 5;
        if (moveRight && paddleX < getWidth() - paddleWidth) paddleX += 5;

        repaint();
    }

    private void changePaddleColor() {
        Random rand = new Random();
        paddleColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) moveLeft = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) moveRight = true;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) moveLeft = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) moveRight = false;
    }

    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Color Changing Pong");
        ColorChangingPong game = new ColorChangingPong();
        frame.add(game);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
 