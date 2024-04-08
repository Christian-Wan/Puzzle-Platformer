import java.awt.*;

public class Box {

    private Rectangle collision;
    private Engine engine;
    private int x, y;
    public Box(Engine engine, int x, int y) {
        this.engine = engine;
        this.x = x;
        this.y = y;
        collision = new Rectangle(x, y, 32, 32);
    }
}
