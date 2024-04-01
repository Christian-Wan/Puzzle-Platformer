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
    private boolean up, down, left, right, facingRight, facingLeft, jumpAnimation, inAir;
    private Rectangle collisionBox;
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
        inAir = false;
        collisionBox = new Rectangle(x + playPanel.getSCALE(), y, 13 * playPanel.getSCALE(), 22 * playPanel.getSCALE());
        try {
            walkR = ImageIO.read(new File("image/Mage_WalkR.png"));
            walkL = ImageIO.read(new File("image/Mage_WalkL.png"));
            jumpR = ImageIO.read(new File("image/Mage_JumpR.png"));
            jumpL = ImageIO.read(new File("image/Mage_JumpL.png"));
            standR = ImageIO.read(new File("image/Mage_StandR.png"));
            standL = ImageIO.read(new File("image/Mage_StandL.png"));
        } catch (IOException e) {}
    }

    public void update() {
        framesPassed++;

        if (right && (!wallOnRight(playPanel.getTempPlatform2()) && !wallOnRight(playPanel.getTempPlatform3()))) {
            x += 3;
        }
        else if (left && (!wallOnLeft(playPanel.getTempPlatform2()) && !wallOnLeft(playPanel.getTempPlatform3()))) {
            x -= 3;
        }

        //if touching platform send player to top of the platform
        if (touchingPlatform(playPanel.getTempPlatform()) || touchingPlatform(playPanel.getTempPlatform2()) || touchingPlatform(playPanel.getTempPlatform3())) {
            inAir = false;
        }
        else {
            inAir = true;
        }
        if (!up && inAir) {
            y += 5;
        }
        else if (up) {
            y -= 3;
        }

        collisionBox.setLocation(x + playPanel.getSCALE(), y);

        if (framesPassed == 6) {
            if (jumpAnimation) {
                if (frameNumber != 10) {
                    frameNumber++;
                    if (frameNumber == 6) {
                        up = false;
                    }
                }
                else {
                    frameNumber = 0;
                    jumpAnimation = false;
                }
            }
            else if (right) {
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
    //the standing left animation is wrong and the jumping animation is a little messed up
    public void draw(Graphics2D g) {
        BufferedImage image = null;
        if (jumpAnimation && facingRight) {
            image = jumpR.getSubimage(1 + (64 * frameNumber), 25, 15, 23);
        }
        else if (jumpAnimation && facingLeft) {
            image = jumpL.getSubimage(1 + (64 * frameNumber), 25, 15, 23);
        }
        else if (right) {
            image = walkR.getSubimage(24 + (64 * frameNumber), 21, 15, 23);
//            System.out.println("WALK");
        }
        else if (left) {
            image = walkL.getSubimage(24 + (64 * frameNumber), 21, 15, 23);
//            System.out.println("WALK");
        }
        else if (facingRight) {
            image = standR.getSubimage(24 + (64 * (frameNumber / 3)), 21, 15, 23);
        }
        else {
            image = standL.getSubimage(24 + (64 * (frameNumber / 3)), 21, 15, 23);
        }
        g.drawImage(image, x, y, 14 * playPanel.getSCALE(), 23 * playPanel.getSCALE(), null);
        g.drawRect(collisionBox.x, collisionBox.y, collisionBox.width, collisionBox.height);
    }


    //Don't know if true but the player can most likely walk through walls if the wall is smaller than the player and the wall is at player's chest level
    public boolean touchingPlatform(Rectangle rect) {
        if ((rect.getY() + rect.getHeight() >= collisionBox.getY() + collisionBox.getHeight()) && (rect.getY() <= collisionBox.getY() + collisionBox.getHeight()) && ((rect.getX() < collisionBox.getX() && rect.getX() + rect.getWidth() > collisionBox.getX()) || (rect.getX() < collisionBox.getX() + collisionBox.getWidth() && rect.getX() + rect.getWidth() > collisionBox.getX() + collisionBox.getWidth()))) {
            y = (int) (rect.getY() - collisionBox.getHeight());
            return true;
        }
        return false;
    }
//Problem is after the player touches a corer, ends on top of the box, and holds left as they land they
//solution: make everything pixel perfect
    public boolean wallOnLeft(Rectangle rect) {
        //first two statements determine if the player has clipped into the wall. The next one determines if the player is on the same y level as the wall
        return (rect.getX() + rect.getWidth() >= collisionBox.getX()) && (rect.getX() <= collisionBox.getX()) && ((rect.getY() + 1 < collisionBox.getY() && rect.getY() + rect.getHeight() - 1 > collisionBox.getY()) || (rect.getY() < (collisionBox.getY() + collisionBox.getHeight()) && rect.getY() + rect.getHeight() > collisionBox.getY()  + collisionBox.getHeight()));
    }

    public boolean wallOnRight(Rectangle rect) {
        return (rect.getX() <= collisionBox.getX() + collisionBox.getWidth()) && (rect.getX() + rect.getWidth() >= collisionBox.getX() + collisionBox.getWidth()) && ((rect.getY() + 1 < collisionBox.getY() && rect.getY() + rect.getHeight() - 1 > collisionBox.getY()) || (rect.getY() + 1 < collisionBox.getY() + collisionBox.getHeight() && rect.getY() + rect.getHeight() - 1 > collisionBox.getY()  + collisionBox.getHeight()));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int input = e.getKeyCode();
        switch(input) {
            case KeyEvent.VK_UP:
                if (!up && !inAir) {
                    frameNumber = 0;
                    up = true;
                    jumpAnimation = true;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (!left && !jumpAnimation) {
                    frameNumber = 0;
                }
                facingRight = false;
                facingLeft = true;
                left = true;
//                System.out.println("LEFT");
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                frameNumber = 0;
                break;
            case KeyEvent.VK_RIGHT:
                if (!right && !jumpAnimation) {
                    frameNumber = 0;
                }
                facingLeft = false;
                facingRight = true;
                right = true;
//                System.out.println("RIGHT");
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int input = e.getKeyCode();
        switch(input) {
            case KeyEvent.VK_UP:
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                if (!jumpAnimation) {
                    frameNumber = 0;
                }
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                if (!jumpAnimation) {
                    frameNumber = 0;
                }
                break;
        }
    }
}
