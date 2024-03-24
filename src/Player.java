import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player implements KeyListener{

    private BufferedImage walkR, jumpR, standR, walkL, jumpL, standL;
    private int x, y, frameNumber, framesPassed, speed;
    private PlayPanel playPanel;
    private boolean up, down, left, right, facingRight, facingLeft;
    public Player(PlayPanel p) {
        x = 100;
        y = 100;
        frameNumber = 0;
        framesPassed = 0;
        speed = 5;
        playPanel = p;
        up = false;
        down = false;
        left = false;
        right = false;
        facingLeft = false;
        facingRight = true;
        try {
            walkR = ImageIO.read(new File("image/Mage_WalkR.png"));
            jumpR = ImageIO.read(new File("image/Mage_JumpR.png"));
            standR = ImageIO.read(new File("image/Mage_StandR.png"));
            walkL = ImageIO.read(new File("image/Mage_WalkL.png"));
            standL = ImageIO.read(new File("image/Mage_StandL.png"));
        } catch (IOException ignored) {}
    }

    public void update() {
        framesPassed++;

        if (right) {
            x += 3;
        }
        else if (left) {
            x -= 3;
        }



        if (framesPassed == 6) {
            if (right) {
                if (frameNumber != 7) {
                    frameNumber++;
                } else {
                    frameNumber = 0;
                }
            }
            else if (left) {
                if (frameNumber != 7) {
                    frameNumber++;
                } else {
                    frameNumber = 0;
                }
            }
            else if (up) {
                // add forced jump animation
            }
            else {
                if (frameNumber != 5) {
                    frameNumber++;
                }
                else {
                    frameNumber = 0;
                }
            }
            framesPassed = 0;
        }
    }
    public void draw(Graphics2D g) {
        BufferedImage image = null;
        if (right) {
            image = walkR.getSubimage(25 + (64 * frameNumber), 21, 14, 22);
            System.out.println("WALK");
        }
        else if (left) {
            image = walkL.getSubimage(25 + (64 * frameNumber), 21, 14, 22);
            System.out.println("WALK");
        }
        else if (up) {
            image = null;
        }
        else if (facingRight) {
            image = standR.getSubimage(25 + (64 * (frameNumber / 3)), 21, 14, 22);
        }
        else {
            image = standL.getSubimage(25 + (64 * (frameNumber / 3)), 21, 14, 22);
        }
        g.drawImage(image, x, y, 22 * playPanel.getSCALE(), 32 * playPanel.getSCALE(), null);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int input = e.getKeyCode();
        switch(input) {
            case KeyEvent.VK_UP:
                if (!up) {
                    frameNumber = 0;
                }
                up = true;
                break;
            case KeyEvent.VK_LEFT:
                if (!left) {
                    frameNumber = 0;
                }
                facingRight = false;
                facingLeft = true;
                left = true;
                System.out.println("LEFT");
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                frameNumber = 0;
                break;
            case KeyEvent.VK_RIGHT:
                if (!right) {
                    frameNumber = 0;
                }
                facingLeft = false;
                facingRight = true;
                right = true;
                System.out.println("RIGHT");
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int input = e.getKeyCode();
        switch(input) {
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                frameNumber = 0;
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                frameNumber = 0;
                break;
        }
    }
}
