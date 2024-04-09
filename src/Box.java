import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Box {

    private Rectangle collisionBox, onCollisionBox;
    private Engine engine;
    private int x, y, velocity, velocityTimer;
    private BufferedImage visual;
    private boolean inAir;
    public Box(Engine engine, int x, int y) {
        this.engine = engine;
        this.x = x;
        this.y = y;
        velocity = 4;
        inAir = false;
        collisionBox = new Rectangle(x, y, 32, 32);
        try {
            visual = ImageIO.read(new File("image/Level_Assets/Box.png"));
        } catch (IOException e) {}
    }

    public boolean touchingPlatform(ArrayList<Rectangle> rectangles) {
        for (Rectangle rect: rectangles) {
            if ((rect.getY() + rect.getHeight() >= collisionBox.getY() + collisionBox.getHeight()) && (rect.getY() <= collisionBox.getY() + collisionBox.getHeight()) && ((rect.getX() < collisionBox.getX() && rect.getX() + rect.getWidth() > collisionBox.getX()) || (rect.getX() < collisionBox.getX() + collisionBox.getWidth() && rect.getX() + rect.getWidth() > collisionBox.getX() + collisionBox.getWidth()))) {
                onCollisionBox = rect;
                velocity = 4;
                return true;
            }
        }
        return false;
    }
    public void update() {
        velocity++;
        if (!touchingPlatform(engine.getLevelLayout().getWalls())) {
            y += velocity;
        }
        else {
            if (inAir) {
                inAir = false;
                velocity = 4;
                y = (int) onCollisionBox.getY() + 32;
            }
        }

        if (velocityTimer == 18) {
            velocityTimer = 0;
            velocity += 1;
        }
    }
    public void draw(Graphics2D g) {
        g.drawImage(visual.getSubimage(16, 16, 32, 32), x, y, 32, 32, null);
        g.drawRect(x, y, 32, 32);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }
}
