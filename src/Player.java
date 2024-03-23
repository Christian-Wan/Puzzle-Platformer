import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player implements KeyListener{

    private BufferedImage walkR, jumpR, standR;
    private int x, y, frameNumber, framesPassed, speed;
    private PlayPanel playPanel;
    private String direction, previousDirection;
    public Player(PlayPanel p) {
        x = 100;
        y = 100;
        frameNumber = 0;
        framesPassed = 0;
        speed = 5;
        playPanel = p;
        direction = "STAND";
        previousDirection = "STAND";
        try {
            walkR = ImageIO.read(new File("image/Mage_Walk.png"));
            jumpR = ImageIO.read(new File("image/Mage_Jump.png"));
            standR = ImageIO.read(new File("image/Mage_Stand.png"));
        } catch (IOException ignored) {}
    }

    public void update() {
        framesPassed++;
        if (framesPassed == 4) {
            if (frameNumber != 19) {
                frameNumber++;
            }
            else {
                frameNumber = 0;
            }
            framesPassed = 0;
        }
    }
    public void draw(Graphics2D g) {
        BufferedImage image = null;
        if (!direction.equals(previousDirection)) {
            
        }
        g.drawImage(image, x, y, 22 * playPanel.getSCALE(), 32 * 5, null);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int input = e.getKeyChar();
        switch(input) {
            case 'w':
                direction = "UP";
                break;
            case 'a':
                direction = "LEFT";
                break;
            case 's':
                direction = "DOWN";
                break;
            case 'd':
                direction = "RIGHT";
                break;
            default:
                direction = "STAND";
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
