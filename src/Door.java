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
    private boolean buttonOpen, keyOpen, wasOpen, innateOn, openedThisFrame;
    private Rectangle collisionBox, location;
    private BufferedImage door;
    public Door(Engine engine, String doorNumber, int x , int y, boolean on) {
        this.engine = engine;
        number = Integer.parseInt(doorNumber.substring(1));
        innateOn = on;
        keyOpen = false;
        buttonOpen = false;
        collisionBox = new Rectangle(x, y, 32, 32);
        location = new Rectangle(x, y, 32, 32);
        wasOpen = false;
        openedThisFrame = false;
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
        openedThisFrame = true;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public boolean touchingBox(ArrayList<Box> boxes) {
        for (Box box: boxes) {
            if (location.intersects(box.getCollisionBox())) {
                return true;
            }
        }
        return false;
    }

    public boolean touchingSummon(Skeleton skeleton) {
        if (skeleton != null) {
            if (skeleton.getCollisionBox().intersects(location)) {
                return true;
            }
        }
        return false;
    }

    public boolean touchingPlayer(ArrayList<Player> players) {
        for (Player player: players) {
            if (location.intersects(player.getCollisionBox())) {
                return true;
            }
        }
        return false;
    }

    public void update() {
        openedThisFrame = false;
        if (innateOn) {
            if (keyOpen || buttonOpen) {
                collisionBox.setBounds(collisionBox.x, collisionBox.y, 0, 0);
                wasOpen = true;
            } else {
                if (wasOpen) {
                    System.out.println("Touching player: " + touchingPlayer(engine.getLevelLayout().getAvailableCharacters()));
                    System.out.println(engine.getLevelLayout().getAvailableCharacters());
                    if (!touchingPlayer(engine.getLevelLayout().getAvailableCharacters()) && !touchingBox(engine.getLevelLayout().getBoxes())) {
                        if (engine.getNecromancer() != null && engine.getNecromancer().getSummon() != null) {
                            if (!touchingSummon(engine.getNecromancer().getSummon())) {
                                collisionBox.setBounds(collisionBox.x, collisionBox.y, 32, 32);
                                wasOpen = false;
                            }
                        } else {
                            collisionBox.setBounds(collisionBox.x, collisionBox.y, 32, 32);
                            wasOpen = false;
                        }
                    } else {
                        System.out.println("Problem 1");
                    }

                } else {
                    collisionBox.setBounds(collisionBox.x, collisionBox.y, 32, 32);
                }
            }
        }
        else {
            if (keyOpen || buttonOpen) {
                if (wasOpen) {
                    System.out.println("Touching player: " + touchingPlayer(engine.getLevelLayout().getAvailableCharacters()));
                    System.out.println(engine.getLevelLayout().getAvailableCharacters());
                    if (!touchingPlayer(engine.getLevelLayout().getAvailableCharacters()) && !touchingBox(engine.getLevelLayout().getBoxes())) {
                        if (engine.getNecromancer() != null && engine.getNecromancer().getSummon() != null) {
                            if (!touchingSummon(engine.getNecromancer().getSummon())) {
                                collisionBox.setBounds(collisionBox.x, collisionBox.y, 32, 32);
                                System.out.println("Set Something");
                                wasOpen = false;
                            }
                        } else {
                            collisionBox.setBounds(collisionBox.x, collisionBox.y, 32, 32);
                            System.out.println("Set Something");
                            wasOpen = false;
                        }
                    } else {
                        System.out.println("Problem 1");
                    }

                } else {
                    collisionBox.setBounds(collisionBox.x, collisionBox.y, 32, 32);
                    System.out.println("Set Something");
                }
            }
            else {
                collisionBox.setBounds(collisionBox.x, collisionBox.y, 0, 0);
                wasOpen = true;
            }
        }
    }

    public void draw(Graphics2D g) {
        if (innateOn) {
            if (keyOpen || buttonOpen || wasOpen) {
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .25f));
            } else {
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }
        }
        else {
            if (keyOpen || buttonOpen) {
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            } else {
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .25f));
            }
        }
        g.drawImage(door, collisionBox.x, collisionBox.y, 32, 32, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g.setColor(Color.GREEN);
        g.drawRect(collisionBox.x, collisionBox.y, collisionBox.width, collisionBox.height);

    }

    public int getX() {
        return collisionBox.x;
    }

    public int getY() {
        return collisionBox.y;
    }

    public boolean isButtonOpen() {
        return buttonOpen;
    }

    public boolean isKeyOpen() {
        return keyOpen;
    }

    public boolean isOpenedThisFrame() {
        return openedThisFrame;
    }
}
