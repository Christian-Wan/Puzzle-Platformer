import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Opener {
    //Parent class of the Key and Button class

    private boolean opening;
    private Rectangle collisionBox;
    private BufferedImage sprite;
    private Engine engine;
    private int number;

    public Opener(Engine engine, String correspondingNumber) {
        this.engine = engine;
        opening = false;
        number = Integer.parseInt(correspondingNumber.substring(1));
        //Collision box is set in child classes
        //sprite is set in the child classes
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

    }
    public void draw(Graphics2D g) {

    }

    public boolean isOpening() {
        return opening;
    }

    public void setOpening(boolean opening) {
        this.opening = opening;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public void setCollisionBox(Rectangle collisionBox) {
        this.collisionBox = collisionBox;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public int getNumber() {
        return number;
    }


}
