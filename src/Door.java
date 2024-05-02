import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Door {
    //Is going to be the value in the Hashmap
    //When Key/Button is true Door opens and the door opens
    //When key is picked up the door stays open
    //When Button is pressed the Door opens, when you get off the door closes

    private Engine engine;
    private int number;
    private boolean buttonOpen, keyOpen, wasOpen;
    private Rectangle collisionBox;
    private BufferedImage door;
    public Door(Engine engine, String doorNumber, int x , int y) {
        this.engine = engine;
        number = Integer.parseInt(doorNumber.substring(1));
        keyOpen = false;
        buttonOpen = false;
        collisionBox = new Rectangle(x, y, 32, 32);
        wasOpen = false;
        try {
            if (number == 1) {
                door = ImageIO.read(new File("image/Level_Assets/Walls_Tileset.png")).getSubimage(160, 112, 32, 32);
            }
            else {
                door = ImageIO.read(new File("image/Level_Assets/Walls_Tileset.png")).getSubimage(208, 112, 32, 32);
            }
        } catch (IOException e) {}
    }

    public int getNumber() {
        return number;
    }

    public void setKeyOpen(Boolean open) {
        keyOpen = open;
    }
    public void setButtonOpen(Boolean open) {
        buttonOpen = open;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public boolean touchingBox(ArrayList<Box> boxes) {
        for (Box box: boxes) {
            if (getCollisionBox().intersects(box.getCollisionBox())) {
                return true;
            }
        }
        return false;
    }

    public boolean touchingSummon(Skeleton skeleton) {
        if (skeleton != null) {
            if (skeleton.getCollisionBox().intersects(getCollisionBox())) {
                return true;
            }
        }
        return false;
    }

    public boolean touchingPlayer(ArrayList<Player> players) {
        for (Player player: players) {
            if (collisionBox.intersects(player.getCollisionBox())) {
                return true;
            }
        }
        return false;
    }

    public void update() {
        if (keyOpen || buttonOpen) {
            collisionBox.setBounds(collisionBox.x, collisionBox.y, 0, 0);
            wasOpen = true;
        }
        else {
            if (wasOpen) {
                if (!touchingPlayer(engine.getLevelLayout().getAvailableCharacters()) && !touchingBox(engine.getLevelLayout().getBoxes())) {
                    if (engine.getNecromancer() != null && engine.getNecromancer().getSummon() != null) {
                        if (!touchingSummon(engine.getNecromancer().getSummon())) {
                            collisionBox.setBounds(collisionBox.x, collisionBox.y, 32, 32);
                            wasOpen = false;
                        }
                    }
                    else {
                        collisionBox.setBounds(collisionBox.x, collisionBox.y, 32, 32);
                        wasOpen = false;
                    }
                }
                else {
                    System.out.println("Problem 1");
                }

            }
            else {
                collisionBox.setBounds(collisionBox.x, collisionBox.y, 32, 32);
            }
        }
    }

    public void draw(Graphics2D g) {
        if (keyOpen || buttonOpen || wasOpen) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .25f));
        }
        else {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
        g.drawImage(door, collisionBox.x, collisionBox.y, 32, 32, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
