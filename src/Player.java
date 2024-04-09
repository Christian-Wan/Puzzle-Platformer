import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;


//IMPORTANT: MAKE SEPARATE ANIMATIONS FOR PLAYER MOVING UP, MOVING DOWN, AND LANDING
public class Player implements KeyListener{

    private BufferedImage walkR, jumpR, standR, walkL, jumpL, standL;
    private Engine engine;
    private int x, y, frameNumber, framesPassed, velocity, velocityTimer;
    final private int SCALE = 2;
    private boolean up, down, left, right, facingRight, facingLeft, jumpAnimation, inAir, available, active;
    private Rectangle collisionBox, onCollisionBox;
    //3 child classes that will be the mage knight and ?
    public Player(Engine engine) {
        this.engine = engine;
        x = 100;
        y = 100;
        frameNumber = 0;
        framesPassed = 0;
        up = false;
        down = false;
        left = false;
        right = false;
        facingLeft = false;
        facingRight = true;
        inAir = false;
        collisionBox = new Rectangle(x + SCALE, y, 14 * SCALE, 22 * SCALE);
        try {
            walkR = ImageIO.read(new File("image/Mage/Mage_WalkR.png"));
            walkL = ImageIO.read(new File("image/Mage/Mage_WalkL.png"));
            jumpR = ImageIO.read(new File("image/Mage/Mage_JumpR.png"));
            jumpL = ImageIO.read(new File("image/Mage/Mage_JumpL.png"));
            standR = ImageIO.read(new File("image/Mage/Mage_StandR.png"));
            standL = ImageIO.read(new File("image/Mage/Mage_StandL.png"));
        } catch (IOException e) {}
    }

    public Player(Engine engine, int x, int y) {
        this.engine = engine;
        velocity = 3;
        this.x = x;
        this.y = y;
        frameNumber = 0;
        framesPassed = 0;
        up = false;
        down = false;
        left = false;
        right = false;
        facingLeft = false;
        facingRight = true;
        inAir = false;
        available = true;
        active = false;
        collisionBox = new Rectangle(x + SCALE, y, 14 * SCALE, 22 * SCALE);
        try {
            walkR = ImageIO.read(new File("image/Mage/Mage_WalkR.png"));
            walkL = ImageIO.read(new File("image/Mage/Mage_WalkL.png"));
            jumpR = ImageIO.read(new File("image/Mage/Mage_JumpR.png"));
            jumpL = ImageIO.read(new File("image/Mage/Mage_JumpL.png"));
            standR = ImageIO.read(new File("image/Mage/Mage_StandR.png"));
            standL = ImageIO.read(new File("image/Mage/Mage_StandL.png"));
        } catch (IOException e) {}
    }

    public boolean isAvailable() {
        return available;
    }

    public void update() {
        framesPassed++;
        velocityTimer++;
        if (reachEnd(engine.getPortal().getCollision())) {
            available = false;
            engine.getLevelLayout().checkLevelDone();
            engine.getLevelLayout().changeActive();
            System.out.println("ASDKH");
        }
        if (right && !wallOnRight(engine.getLevelLayout().getWalls()) && !boxOnRight(engine.getLevelLayout().getBoxes())) {
            x += 2;
        }
        else if (left && !wallOnLeft(engine.getLevelLayout().getWalls()) && !boxOnLeft(engine.getLevelLayout().getBoxes())) {
            x -= 2;
        }

        //if touching platform send player to top of the platform
        if (touchingPlatform(engine.getLevelLayout().getWalls()) || touchingBox(engine.getLevelLayout().getBoxes())) {
            if (inAir) {
                y = (int) (onCollisionBox.getY() - collisionBox.getHeight());
                velocity = 4;
            }
            inAir = false;
        }
        else {
            inAir = true;
        }

        if (touchingCeiling(engine.getLevelLayout().getWalls())) {
            up = false;
            velocity = 3;
            velocityTimer = 0;
        }
        else if (!up && inAir) {
            y += velocity;
        }
        else if (up) {
            y += velocity;
        }
        if (velocityTimer % 18 == 0 && inAir) {
            if (velocity != 6 && velocity > 0) {
                velocity++;
            }
            else if (velocity < 0) {
                velocity++;
            }
        }
        collisionBox.setLocation(x + SCALE, y);

        if (framesPassed == 6) {
            if (jumpAnimation) {
                if (frameNumber != 10) {
                    frameNumber++;
                    if (frameNumber == 6) {
                        up = false;
                        velocity = 3;
                        velocityTimer = 0;
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
        g.drawImage(image, x, y, 15 * SCALE, 23 * SCALE, null);
        g.drawRect(collisionBox.x, collisionBox.y, collisionBox.width, collisionBox.height);
    }


    //Don't know if true but the player can most likely walk through walls if the wall is smaller than the player and the wall is at player's chest level
    public boolean touchingPlatform(ArrayList<Rectangle> rectangles) {
        for (Rectangle rect: rectangles) {
            if ((rect.getY() + rect.getHeight() >= collisionBox.getY() + collisionBox.getHeight()) && (rect.getY() <= collisionBox.getY() + collisionBox.getHeight()) && ((rect.getX() < collisionBox.getX() && rect.getX() + rect.getWidth() > collisionBox.getX()) || (rect.getX() < collisionBox.getX() + collisionBox.getWidth() && rect.getX() + rect.getWidth() > collisionBox.getX() + collisionBox.getWidth()))) {
                onCollisionBox = rect;
                return true;
            }
        }
        return false;
    }
    public boolean touchingCeiling(ArrayList<Rectangle> rectangles) {
        for (Rectangle rect: rectangles) {
            if (rect.getY() + rect.getHeight() >= collisionBox.getY() && rect.getY() <= collisionBox.getY() && ((rect.getX() < collisionBox.getX() && rect.getX() + rect.getWidth() > collisionBox.getX()) || (rect.getX() < collisionBox.getX() + collisionBox.getWidth() && rect.getX() + rect.getWidth() > collisionBox.getX() + collisionBox.getWidth()))) {
                System.out.println(y);
                y = (int) (rect.getY() + rect.getHeight() + 1);
                System.out.println(y + "ASD");
                System.out.println("Touched");
                return true;
            }
        }
        return false;
    }
//Problem is after the player touches a corer, ends on top of the box, and holds left as they land they
//solution: make everything pixel perfect
    public boolean wallOnLeft(ArrayList<Rectangle> rectangles) {
        for (Rectangle rect: rectangles) {
            if ((rect.getX() + rect.getWidth() >= collisionBox.getX()) && (rect.getX() <= collisionBox.getX()) && ((rect.getY() < collisionBox.getY() && rect.getY() + rect.getHeight() > collisionBox.getY()) || (rect.getY() < (collisionBox.getY() + collisionBox.getHeight()) && rect.getY() + rect.getHeight() > collisionBox.getY()  + collisionBox.getHeight()))) {
                return true;
            }
        }
        //first two statements determine if the player has clipped into the wall. The next one determines if the player is on the same y level as the wall
        return false;
    }

    public boolean wallOnRight(ArrayList<Rectangle> rectangles) {
        for (Rectangle rect: rectangles) {
            if ((rect.getX() <= collisionBox.getX() + collisionBox.getWidth()) && (rect.getX() + rect.getWidth() >= collisionBox.getX() + collisionBox.getWidth()) && ((rect.getY() < collisionBox.getY() && rect.getY() + rect.getHeight() > collisionBox.getY()) || (rect.getY() < collisionBox.getY() + collisionBox.getHeight() && rect.getY() + rect.getHeight() > collisionBox.getY()  + collisionBox.getHeight()))) {
                return true;
            }
        }
        return false;
    }


    public boolean touchingBox(ArrayList<Box> boxes) {
        for (Box box: boxes) {
            if ((box.getY() + 32 >= collisionBox.getY() + collisionBox.getHeight()) && (box.getY() <= collisionBox.getY() + collisionBox.getHeight()) && ((box.getX() < collisionBox.getX() && box.getX() + 32 > collisionBox.getX()) || (box.getX() < collisionBox.getX() + collisionBox.getWidth() && box.getX() + 32 > collisionBox.getX() + collisionBox.getWidth()))) {
                onCollisionBox = box.getCollisionBox();
                return true;
            }
        }
        return false;
    }
    public boolean boxOnLeft(ArrayList<Box> boxes) {
        for (Box box: boxes) {
//            System.out.println(box.getX() + 32 + "    " + x);
            if ((box.getX() + 32 >= collisionBox.getX()) && (box.getX() <= collisionBox.getX()) && ((box.getY() < collisionBox.getY() && box.getY() + 32 > collisionBox.getY()) || (box.getY() < (collisionBox.getY() + collisionBox.getHeight()) && box.getY() + 32 > collisionBox.getY()  + collisionBox.getHeight()))) {
                System.out.println("ASD");
                return true;
            }
        }
        //first two statements determine if the player has clipped into the wall. The next one determines if the player is on the same y level as the wall
        return false;
    }

    public boolean boxOnRight(ArrayList<Box> boxes) {
        for (Box box: boxes) {
            if ((box.getX() <= collisionBox.getX() + collisionBox.getWidth()) && (box.getX() + 32 >= collisionBox.getX() + collisionBox.getWidth()) && ((box.getY() < collisionBox.getY() && box.getY() + 32 > collisionBox.getY()) || (box.getY() < collisionBox.getY() + collisionBox.getHeight() && box.getY() + 32 > collisionBox.getY()  + collisionBox.getHeight()))) {
                return true;
            }
        }
        return false;
    }

    public boolean reachEnd(Rectangle portal) {
        if (portal.contains(collisionBox.getCenterX(), collisionBox.getCenterY())) {
            left = false;
            right = false;
            up = false;
            frameNumber = 0;
            return true;
        }
        return false;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BufferedImage getWalkR() {
        return walkR;
    }

    public void setWalkR(BufferedImage walkR) {
        this.walkR = walkR;
    }

    public BufferedImage getJumpR() {
        return jumpR;
    }

    public void setJumpR(BufferedImage jumpR) {
        this.jumpR = jumpR;
    }

    public BufferedImage getStandR() {
        return standR;
    }

    public void setStandR(BufferedImage standR) {
        this.standR = standR;
    }

    public BufferedImage getWalkL() {
        return walkL;
    }

    public void setWalkL(BufferedImage walkL) {
        this.walkL = walkL;
    }

    public BufferedImage getJumpL() {
        return jumpL;
    }

    public void setJumpL(BufferedImage jumpL) {
        this.jumpL = jumpL;
    }

    public BufferedImage getStandL() {
        return standL;
    }

    public void setStandL(BufferedImage standL) {
        this.standL = standL;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(int frameNumber) {
        this.frameNumber = frameNumber;
    }

    public int getFramesPassed() {
        return framesPassed;
    }

    public void setFramesPassed(int framesPassed) {
        this.framesPassed = framesPassed;
    }

    public int getSCALE() {
        return SCALE;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }

    public boolean isFacingLeft() {
        return facingLeft;
    }

    public void setFacingLeft(boolean facingLeft) {
        this.facingLeft = facingLeft;
    }

    public boolean isJumpAnimation() {
        return jumpAnimation;
    }

    public void setJumpAnimation(boolean jumpAnimation) {
        this.jumpAnimation = jumpAnimation;
    }

    public boolean isInAir() {
        return inAir;
    }

    public void setInAir(boolean inAir) {
        this.inAir = inAir;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public void setCollisionBox(Rectangle collisionBox) {
        this.collisionBox = collisionBox;
    }

    public void doAbility() {
//        System.out.println("Ability");
    }

    public void resetStage() {
        System.out.println("Reset" + engine.getLevelLayout().getAvailableCharacters());
        engine.getLevelLayout().resetStage();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (active) {
            int input = e.getKeyCode();
            switch (input) {
                case KeyEvent.VK_UP:
                    if (!up && !inAir) {
                        frameNumber = 0;
                        System.out.println(y);
                        up = true;
                        jumpAnimation = true;
                        velocity = -3;
                        velocityTimer = 0;
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
                case KeyEvent.VK_E:
                    doAbility();
                    break;
                case KeyEvent.VK_R:
                    System.out.println("ASD" + this);
                    resetStage();
                    break;
                case KeyEvent.VK_Q:
                    System.out.println("ASD" + this);
                    if (!inAir) {
                        frameNumber = 0;
                    }
                    right = false;
                    left = false;
                    engine.getLevelLayout().changeActive();
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (active) {
            int input = e.getKeyCode();
            switch (input) {
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
}
