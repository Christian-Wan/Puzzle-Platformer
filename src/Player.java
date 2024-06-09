import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


//IMPORTANT: MAKE SEPARATE ANIMATIONS FOR PLAYER MOVING UP, MOVING DOWN, AND LANDING
public class Player implements KeyListener{

    private BufferedImage walkR, standR, walkL, standL, risingR, risingL, fallingR, fallingL;
    private Engine engine;
    private int x, y, frameNumber, framesPassed, velocity, velocityTimer, timeInAir, pastX, pastY;
    final private int SCALE = 2;
    private boolean up, down, left, right, facingRight, facingLeft, inAir, available, active;
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
        timeInAir = 0;
        collisionBox = new Rectangle(x + SCALE, y, 14 * SCALE, 22 * SCALE);
        try {
            walkR = ImageIO.read(new File("image/Mage/Mage_WalkR.png"));
            walkL = ImageIO.read(new File("image/Mage/Mage_WalkL.png"));
            risingR = ImageIO.read(new File("image/Mage/Mage_RisingR.png"));
            fallingR = ImageIO.read(new File("image/Mage/Mage_FallingR.png"));
            risingL = ImageIO.read(new File("image/Mage/Mage_RisingL.png"));
            fallingL = ImageIO.read(new File("image/Mage/Mage_FallingL.png"));
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
        timeInAir = 0;
        pastY = 0;
        pastY = 0;
        collisionBox = new Rectangle(x + SCALE, y, 14 * SCALE, 22 * SCALE);
        try {
            walkR = ImageIO.read(new File("image/Mage/Mage_WalkR.png"));
            walkL = ImageIO.read(new File("image/Mage/Mage_WalkL.png"));
            risingR = ImageIO.read(new File("image/Mage/Mage_RisingR.png"));
            fallingR = ImageIO.read(new File("image/Mage/Mage_FallingR.png"));
            risingL = ImageIO.read(new File("image/Mage/Mage_RisingL.png"));
            fallingL = ImageIO.read(new File("image/Mage/Mage_FallingL.png"));
            standR = ImageIO.read(new File("image/Mage/Mage_StandR.png"));
            standL = ImageIO.read(new File("image/Mage/Mage_StandL.png"));
        } catch (IOException e) {}
    }

    public boolean isAvailable() {
        return available;
    }

    public void update() {
        framesPassed++;
        //Physics
        velocityTimer++;

        if (!active && !(this instanceof Skeleton)) {
            left = false;
            right = false;
        }

        touchingSpike(engine.getLevelLayout().getSpikes());
        if (reachEnd(engine.getPortal().getCollision())) {
            available = false;
            engine.getLevelLayout().checkLevelDone();
            engine.getLevelLayout().changeActive();
        }

        //causes the player to fall after a while
        if (timeInAir == 6) {
            up = false;
            velocity = 4;
            velocityTimer = 0;
        }

        //if touching platform send player to top of the platform
        if (touchingPlatform(engine.getLevelLayout().getWalls()) || touchingBox(engine.getLevelLayout().getBoxes()) || onPlayer(engine.getLevelLayout().getAvailableCharacters()) || onTopOfDoor(engine.getLevelLayout().getDoors()) || onOneWayPlatform(engine.getLevelLayout().getOneWayPlatforms())) {
            if (inAir) {
                y = (int) (onCollisionBox.getY() - collisionBox.getHeight());
                collisionBox.setLocation(x, y);
                velocity = 4;
                System.out.println("HAPPEN: " + (onCollisionBox.getY()));

            }
            inAir = false;
        }
        else if (engine.getNecromancer() != null && engine.getNecromancer().getSummon() != null && skeletonBelow(engine.getNecromancer().getSummon())) {
            if (inAir) {
                y = (int) (onCollisionBox.getY() - collisionBox.getHeight());
                velocity = 4;
            }
            inAir = false;

        }
        else {
            System.out.println("IN AIR");
            inAir = true;
            frameNumber = 0;
        }

        if (right && !wallOnRight(engine.getLevelLayout().getWalls()) && !boxOnRight(engine.getLevelLayout().getBoxes()) && !playerOnRight(engine.getLevelLayout().getAvailableCharacters()) && !doorOnRight(engine.getLevelLayout().getDoors())) {
            if (engine.getNecromancer() == null || engine.getNecromancer().getSummon() == null) {
                x += 2;
            }
            else if (!skeletonOnRight(engine.getNecromancer().getSummon())){
                x += 2;
            }
        }
        else if (left && !wallOnLeft(engine.getLevelLayout().getWalls()) && !boxOnLeft(engine.getLevelLayout().getBoxes()) && !playerOnLeft(engine.getLevelLayout().getAvailableCharacters()) && !doorOnLeft(engine.getLevelLayout().getDoors())) {
            if (engine.getNecromancer() == null || engine.getNecromancer().getSummon() == null) {
                x -= 2;
            }
            else if (!skeletonOnLeft(engine.getNecromancer().getSummon())){
                x -= 2;
            }
        }

        if (touchingCeiling(engine.getLevelLayout().getWalls()) || boxOnTop(engine.getLevelLayout().getBoxes()) || playerOnTop(engine.getLevelLayout().getAvailableCharacters()) || touchingBottomOfDoor(engine.getLevelLayout().getDoors())) {
            up = false;
            velocity = 4;
            velocityTimer = 0;
        }
        else if (engine.getNecromancer() != null && engine.getNecromancer().getSummon() != null && skeletonOnTop(engine.getNecromancer().getSummon())) {
            up = false;
            velocity = 4;
            velocityTimer = 0;
        }
        else if (!up && inAir) {
            y += velocity;
        }
        else if (up) {
            y += velocity;
        }
        if (velocityTimer % 20 == 0 && inAir) {
            if (velocity != 7 && velocity > 0) {
                velocity++;
            }
            else if (velocity < 0) {
                velocity++;
            }
        }
        pastX = collisionBox.x;
        pastY = collisionBox.y;
        collisionBox.setLocation(x + SCALE, y);

        //Animations
        if (framesPassed == 6) {
            if (inAir) {
                timeInAir++;
                if (frameNumber != 1) {
                    frameNumber++;
                }
                else {
                    frameNumber = 0;
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
        if (inAir && velocity > 0) {
            if (facingRight) {
                image = fallingR.getSubimage(24 + (64 * frameNumber), 21, 15, 23);
            }
            else {
                image = fallingL.getSubimage(24 + (64 * frameNumber), 21, 15, 23);
            }
        }
        else if (inAir && velocity < 0) {
            if (facingRight) {
                image = risingR.getSubimage(24 + (64 * frameNumber), 21, 15, 23);
            }
            else {
                image = risingL.getSubimage(24 + (64 * frameNumber), 21, 15, 23);
            }
        }
        else if ((left || right) && facingRight) {
            image = walkR.getSubimage(24 + (64 * frameNumber), 21, 15, 23);
//            System.out.println("WALK");
        }
        else if ((left || right) && facingLeft) {
            image = walkL.getSubimage(24 + (64 * frameNumber), 21, 15, 23);
//            System.out.println("WALK");
        }
        //The game breaks here after the necromancer summons a jumping skeleton, resetting the level, and then moving right
        else if (facingRight) {
            image = standR.getSubimage(24 + (64 * (frameNumber / 3)), 21, 15, 23);
        }
        else {
            image = standL.getSubimage(24 + (64 * (frameNumber / 3)), 21, 15, 23);
        }
        g.drawImage(image, x, y, 15 * SCALE, 23 * SCALE, null);
        g.setColor(Color.BLUE);
//        g.drawRect(collisionBox.x, collisionBox.y, collisionBox.width, collisionBox.height);
    }


    public boolean onOneWayPlatform(ArrayList<Rectangle> oneWayPlatforms) {
        if (!down) {
            for (Rectangle platform : oneWayPlatforms) {
                if (pastY + collisionBox.getHeight() <= platform.y) {
                    if ((platform.getY() + platform.getHeight() >= collisionBox.getY() + collisionBox.getHeight()) && (platform.getY() <= collisionBox.getY() + collisionBox.getHeight()) && ((platform.getX() < collisionBox.getX() && platform.getX() + platform.getWidth() > collisionBox.getX()) || (platform.getX() < collisionBox.getX() + collisionBox.getWidth() && platform.getX() + platform.getWidth() > collisionBox.getX() + collisionBox.getWidth()))) {
                        onCollisionBox = platform;
                        return true;
                    }
                }
            }
        }
        return false;
    }
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
                if (inAir) {
                    y = (int) (rect.getY() + rect.getHeight() + 1);
                }
                return true;
            }
        }
        return false;
    }
//Problem is after the player touches a corer, ends on top of the box, and holds left as they land they
//solution: make everything pixel perfect
    public boolean wallOnLeft(ArrayList<Rectangle> rectangles) {
        for (Rectangle rect: rectangles) {
            if ((rect.getX() + rect.getWidth() >= collisionBox.getX()) && (rect.getX() <= collisionBox.getX()) && ((rect.getY() < collisionBox.getY() && rect.getY() + rect.getHeight() > collisionBox.getY()) || (rect.getY() < (collisionBox.getY() + collisionBox.getHeight()) && rect.getY() + rect.getHeight() > collisionBox.getY() + collisionBox.getHeight()) || (rect.getY() < collisionBox.getCenterY() && rect.getY() + rect.getHeight() > collisionBox.getCenterY()))) {
                return true;
            }
        }
        //first two statements determine if the player has clipped into the wall. The next one determines if the player is on the same y level as the wall
        return false;
    }

    public boolean wallOnRight(ArrayList<Rectangle> rectangles) {
        for (Rectangle rect: rectangles) {
            if ((rect.getX() - 2 + rect.getWidth() >= collisionBox.getX() + collisionBox.getWidth()) && (rect.getX() - 2 <= collisionBox.getX() + collisionBox.getWidth()) && ((rect.getY() <= collisionBox.getY() && rect.getY() + rect.getHeight() > collisionBox.getY()) || (rect.getY() < (collisionBox.getY() + collisionBox.getHeight()) && rect.getY() + rect.getHeight() >= collisionBox.getY() + collisionBox.getHeight()) || (rect.getY() < collisionBox.getCenterY() && rect.getY() + rect.getHeight() > collisionBox.getCenterY()))) {
                return true;
            }
        }
        return false;
    }

    public boolean onTopOfDoor(ArrayList<Door> doors) {
        for (Door door: doors) {
            if ((door.getCollisionBox().getY() + door.getCollisionBox().getHeight() >= collisionBox.getY() + collisionBox.getHeight()) && (door.getCollisionBox().getY() <= collisionBox.getY() + collisionBox.getHeight()) && ((door.getCollisionBox().getX() < collisionBox.getX() && door.getCollisionBox().getX() + door.getCollisionBox().getWidth() > collisionBox.getX()) || (door.getCollisionBox().getX() < collisionBox.getX() + collisionBox.getWidth() && door.getCollisionBox().getX() + door.getCollisionBox().getWidth() > collisionBox.getX() + collisionBox.getWidth()))) {
                onCollisionBox = door.getCollisionBox();
                System.out.println("On Top of DOor");
                return true;
            }
        }
        return false;
    }
    public boolean touchingBottomOfDoor(ArrayList<Door> doors) {
        for (Door door: doors) {
            if (door.getCollisionBox().getY() + door.getCollisionBox().getHeight() >= collisionBox.getY() && door.getCollisionBox().getY() <= collisionBox.getY() && ((door.getCollisionBox().getX() < collisionBox.getX() && door.getCollisionBox().getX() + door.getCollisionBox().getWidth() > collisionBox.getX()) || (door.getCollisionBox().getX() < collisionBox.getX() + collisionBox.getWidth() && door.getCollisionBox().getX() + door.getCollisionBox().getWidth() > collisionBox.getX() + collisionBox.getWidth()))) {
                if (inAir) {
                    y = (int) (door.getCollisionBox().getY() + door.getCollisionBox().getHeight() + 1);
                }
                return true;
            }
        }
        return false;
    }
    public boolean doorOnLeft(ArrayList<Door> doors) {
        for (Door door: doors) {
            if ((door.getCollisionBox().getX() + door.getCollisionBox().getWidth() >= collisionBox.getX()) && (door.getCollisionBox().getX() <= collisionBox.getX()) && ((door.getCollisionBox().getY() < collisionBox.getY() && door.getCollisionBox().getY() + door.getCollisionBox().getHeight() > collisionBox.getY()) || (door.getCollisionBox().getY() < (collisionBox.getY() + collisionBox.getHeight()) && door.getCollisionBox().getY() + door.getCollisionBox().getHeight() > collisionBox.getY() + collisionBox.getHeight()) || (door.getCollisionBox().getY() < collisionBox.getCenterY() && door.getCollisionBox().getY() + door.getCollisionBox().getHeight() > collisionBox.getCenterY()))) {
                return true;
            }
        }
        //first two statements determine if the player has clipped into the wall. The next one determines if the player is on the same y level as the wall
        return false;
    }

    public boolean doorOnRight(ArrayList<Door> doors) {
        for (Door door: doors) {
            if ((door.getCollisionBox().getX() - 2 <= collisionBox.getX() + collisionBox.getWidth()) && (door.getCollisionBox().getX() - 2 + door.getCollisionBox().getWidth() >= collisionBox.getX() + collisionBox.getWidth()) && ((door.getCollisionBox().getY() < collisionBox.getY() && door.getCollisionBox().getY() + door.getCollisionBox().getHeight() > collisionBox.getY()) || (door.getCollisionBox().getY() < collisionBox.getY() + collisionBox.getHeight() && door.getCollisionBox().getY() + door.getCollisionBox().getHeight() > collisionBox.getY() + collisionBox.getHeight()) || (door.getCollisionBox().getY() < collisionBox.getCenterY() && door.getCollisionBox().getY() + door.getCollisionBox().getHeight() > collisionBox.getCenterY()))) {
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
    public boolean boxOnTop(ArrayList<Box> boxes) {
        for (Box box: boxes) {
            if ((box.getY() + 32 >= collisionBox.getY()) && (box.getY() <= collisionBox.getY()) && ((box.getX() < collisionBox.getX() && box.getX() + 32 > collisionBox.getX()) || (box.getX() < collisionBox.getX() + collisionBox.getWidth() && box.getX() + 32 > collisionBox.getX() + collisionBox.getWidth()))) {
                if (inAir) {
                    y = (int) (box.getY() + box.getCollisionBox().getHeight() + 1);
                }
                return true;
            }
        }
        return false;
    }
    public boolean boxOnLeft(ArrayList<Box> boxes) {
        for (Box box: boxes) {
            if ((box.getX() + 32 >= collisionBox.getX()) && (box.getX() <= collisionBox.getX()) && ((box.getY() < collisionBox.getY() && box.getY() + 32 > collisionBox.getY()) || (box.getY() < (collisionBox.getY() + collisionBox.getHeight()) && box.getY() + 32 > collisionBox.getY() + collisionBox.getHeight()) || (box.getY() <= collisionBox.getCenterY() && box.getY() + 32 >= collisionBox.getCenterY()))) {
                return true;
            }

        }
        return false;
    }

    public boolean boxOnRight(ArrayList<Box> boxes) {
        for (Box box: boxes) {
            if ((box.getX() - 2 <= collisionBox.getX() + collisionBox.getWidth()) && (box.getX() + 30 >= collisionBox.getX() + collisionBox.getWidth()) && ((box.getY() < collisionBox.getY() && box.getY() + 32 > collisionBox.getY()) || (box.getY() < collisionBox.getY() + collisionBox.getHeight() && box.getY() + 32 > collisionBox.getY() + collisionBox.getHeight()) || (box.getY() <= collisionBox.getCenterY() && box.getY() + 32 >= collisionBox.getCenterY()))) {
                return true;
            }
        }
        return false;
    }

    public boolean onPlayer(ArrayList<Player> players) {
        for (Player player: players) {
            if (player.isAvailable()) {
                if (player != this && (player.getCollisionBox().getY() + player.getCollisionBox().getHeight() >= collisionBox.getY() + collisionBox.getHeight()) && (player.getCollisionBox().getY() <= collisionBox.getY() + collisionBox.getHeight()) && ((player.getCollisionBox().getX() < collisionBox.getX() && player.getCollisionBox().getX() + player.getCollisionBox().getWidth() > collisionBox.getX()) || (player.getCollisionBox().getX() < collisionBox.getX() + collisionBox.getWidth() && player.getCollisionBox().getX() + player.getCollisionBox().getWidth() > collisionBox.getX() + collisionBox.getWidth()) || (player.getCollisionBox().getX() < collisionBox.getCenterX() && player.getCollisionBox().getX() + player.getCollisionBox().getWidth() > collisionBox.getCenterX()))) {
                    onCollisionBox = player.getCollisionBox();
                    return true;
                }
            }
        }
        return false;
    }
    public boolean playerOnTop(ArrayList<Player> players) {
        for (Player player: players) {
            if (player.isAvailable()) {
                if (player != this && (player.getCollisionBox().getY() + player.getCollisionBox().getHeight() >= collisionBox.getY()) && (player.getCollisionBox().getY() <= collisionBox.getY()) && ((player.getCollisionBox().getX() < collisionBox.getX() && player.getCollisionBox().getX() + player.getCollisionBox().getWidth() > collisionBox.getX()) || (player.getCollisionBox().getX() < collisionBox.getX() + collisionBox.getWidth() && player.getCollisionBox().getX() + player.getCollisionBox().getWidth() > collisionBox.getX() + collisionBox.getWidth()) || (player.getCollisionBox().getX() < collisionBox.getCenterX() && player.getCollisionBox().getX() + player.getCollisionBox().getWidth() > collisionBox.getCenterX()))) {
                    if (inAir) {
                        y = (int) (player.getY() + player.getCollisionBox().getHeight() + 1);
                    }
                    return true;
                }
            }
        }
        return false;
    }
    public boolean playerOnLeft(ArrayList<Player> players) {
        for (Player player: players) {
            if (player.isAvailable()) {
                if (player != this && (player.getCollisionBox().getX() + player.getCollisionBox().getWidth() >= collisionBox.getX()) && (player.getCollisionBox().getX() <= collisionBox.getX()) && ((player.getCollisionBox().getY() < collisionBox.getY() && player.getCollisionBox().getY() + player.getCollisionBox().getHeight() > collisionBox.getY()) || (player.getCollisionBox().getY() < (collisionBox.getY() + collisionBox.getHeight()) && player.getCollisionBox().getY() + player.getCollisionBox().getHeight() > collisionBox.getY() + collisionBox.getHeight()) || (player.getCollisionBox().getY() <= collisionBox.getCenterY() && player.getCollisionBox().getY() + player.getCollisionBox().getHeight() >= collisionBox.getCenterY()))) {
                    return true;
                }
            }
        }
        //first two statements determine if the player has clipped into the wall. The next one determines if the player is on the same y level as the wall
        return false;
    }

    public boolean playerOnRight(ArrayList<Player> players) {
        for (Player player: players) {
            if (player.isAvailable()) {
                if (player != this && (player.getCollisionBox().getX() - 2 <= collisionBox.getX() + collisionBox.getWidth()) &&
                        (player.getCollisionBox().getX() - 2 + player.getCollisionBox().getWidth() >= collisionBox.getX() + collisionBox.getWidth()) &&
                        ((player.getCollisionBox().getY() < collisionBox.getY() && player.getCollisionBox().getY() + player.getCollisionBox().getHeight() > collisionBox.getY()) ||
                                (player.getCollisionBox().getY() < collisionBox.getY() + collisionBox.getHeight() && player.getCollisionBox().getY() + player.getCollisionBox().getHeight() > collisionBox.getY() + collisionBox.getHeight()) ||
                                (player.getCollisionBox().getY() <= collisionBox.getCenterY() && player.getCollisionBox().getY() + player.getCollisionBox().getHeight() >= collisionBox.getCenterY()))) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean skeletonBelow(Skeleton skeleton) {
        if (engine.getNecromancer().getSummon() != null) {
            if (skeleton != this && (skeleton.getCollisionBox().getY() + skeleton.getCollisionBox().getHeight() >= collisionBox.getY() + collisionBox.getHeight()) && (skeleton.getCollisionBox().getY() <= collisionBox.getY() + collisionBox.getHeight()) && ((skeleton.getCollisionBox().getX() < collisionBox.getX() && skeleton.getCollisionBox().getX() + skeleton.getCollisionBox().getWidth() > collisionBox.getX()) || (skeleton.getCollisionBox().getX() < collisionBox.getX() + collisionBox.getWidth() && skeleton.getCollisionBox().getX() + skeleton.getCollisionBox().getWidth() > collisionBox.getX() + collisionBox.getWidth()) || (skeleton.getCollisionBox().getX() < collisionBox.getCenterX() && skeleton.getCollisionBox().getX() + skeleton.getCollisionBox().getWidth() > collisionBox.getCenterX()))) {
                onCollisionBox = skeleton.getCollisionBox();
                return true;
            }
        }
        return false;
    }
    public boolean skeletonOnTop(Skeleton skeleton) {
        if ((skeleton.getCollisionBox().getY() + skeleton.getCollisionBox().getHeight() >= collisionBox.getY()) && (skeleton.getCollisionBox().getY() <= collisionBox.getY()) && ((skeleton.getCollisionBox().getX() < collisionBox.getX() && skeleton.getCollisionBox().getX() + skeleton.getCollisionBox().getWidth() > collisionBox.getX()) || (skeleton.getCollisionBox().getX() < collisionBox.getX() + collisionBox.getWidth() && skeleton.getCollisionBox().getX() + skeleton.getCollisionBox().getWidth() > collisionBox.getX() + collisionBox.getWidth()) || (skeleton.getCollisionBox().getX() < collisionBox.getCenterX() && skeleton.getCollisionBox().getX() + skeleton.getCollisionBox().getWidth() > collisionBox.getCenterX()))) {
            if (inAir) {
                y = (int) (skeleton.getY() + skeleton.getCollisionBox().getHeight() + 1);
            }
            return true;
        }
        return false;
    }
    public boolean skeletonOnLeft(Skeleton skeleton) {
        if ((skeleton.getCollisionBox().getX() + skeleton.getCollisionBox().getWidth() >= collisionBox.getX()) && (skeleton.getCollisionBox().getX() <= collisionBox.getX()) && ((skeleton.getCollisionBox().getY() <= collisionBox.getY() && skeleton.getCollisionBox().getY() + skeleton.getCollisionBox().getHeight() > collisionBox.getY()) || (skeleton.getCollisionBox().getY() < (collisionBox.getY() + collisionBox.getHeight()) && skeleton.getCollisionBox().getY() + skeleton.getCollisionBox().getHeight() > collisionBox.getY() + collisionBox.getHeight()) || (skeleton.getCollisionBox().getY() <= collisionBox.getCenterY() && skeleton.getCollisionBox().getY() + skeleton.getCollisionBox().getHeight() >= collisionBox.getCenterY()))) {
            return true;
        }
        //first two statements determine if the player has clipped into the wall. The next one determines if the player is on the same y level as the wall
        return false;
    }

    public boolean skeletonOnRight(Skeleton skeleton) {
        if ((skeleton.getCollisionBox().getX() - 2 <= collisionBox.getX() - 2 + collisionBox.getWidth()) &&
                (skeleton.getCollisionBox().getX() + skeleton.getCollisionBox().getWidth() >= collisionBox.getX() + collisionBox.getWidth()) &&
                ((skeleton.getCollisionBox().getY() < collisionBox.getY() && skeleton.getCollisionBox().getY() + skeleton.getCollisionBox().getHeight() > collisionBox.getY()) ||
                        (skeleton.getCollisionBox().getY() < collisionBox.getY() + collisionBox.getHeight() && skeleton.getCollisionBox().getY() + skeleton.getCollisionBox().getHeight() > collisionBox.getY() + collisionBox.getHeight()) ||
                        (skeleton.getCollisionBox().getY() <= collisionBox.getCenterY() && skeleton.getCollisionBox().getY() + skeleton.getCollisionBox().getHeight() >= collisionBox.getCenterY()))) {
            return true;
        }

        return false;
    }


    public void touchingSpike(ArrayList<Rectangle> spikes) {
        for (Rectangle spike: spikes) {
            if (spike.intersects(collisionBox)) {
                available = false;
                engine.getLevelLayout().resetStage();
                break;
            }
        }
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

    public BufferedImage getRisingR() {
        return risingR;
    }

    public void setRisingR(BufferedImage risingR) {
        this.risingR = risingR;
    }

    public BufferedImage getRisingL() {
        return risingL;
    }

    public void setRisingL(BufferedImage risingL) {
        this.risingL = risingL;
    }

    public BufferedImage getFallingR() {
        return fallingR;
    }

    public void setFallingR(BufferedImage fallingR) {
        this.fallingR = fallingR;
    }

    public BufferedImage getFallingL() {
        return fallingL;
    }

    public void setFallingL(BufferedImage fallingL) {
        this.fallingL = fallingL;
    }

    public int getTimeInAir() {
        return timeInAir;
    }

    public void setTimeInAir(int timeInAir) {
        this.timeInAir = timeInAir;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getVelocityTimer() {
        return velocityTimer;
    }

    public void setVelocityTimer(int velocityTimer) {
        this.velocityTimer = velocityTimer;
    }

    public void setOnCollisionBox(Rectangle onCollisionBox) {
        this.onCollisionBox = onCollisionBox;
    }

    public Rectangle getOnCollisionBox() {
        return onCollisionBox;
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
                        up = true;
                        timeInAir = 0;
                        velocity = -3;
                        velocityTimer = 0;
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (!left && !inAir) {
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
                    if (!right && !inAir) {
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
//                    System.out.println("ASD" + this);
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
                case KeyEvent.VK_LEFT:
                    left = false;
                    if (!inAir) {
                        frameNumber = 0;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    down = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    right = false;
                    if (!inAir) {
                        frameNumber = 0;
                    }
                    break;
            }
        }
    }
}
