import java.awt.*;
import java.awt.image.BufferedImage;

public class Spike {

    private Rectangle collisionBox;

    public Spike (int x, int y) {
        collisionBox = new Rectangle(x, y, 32, 5);
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }
}
