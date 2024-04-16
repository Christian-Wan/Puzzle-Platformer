import java.awt.*;

public class Door {
    //Is going to be the value in the Hashmap
    //When Key/Button is true Door opens and the door opens
    //When key is picked up the door stays open
    //When Button is pressed the Door opens, when you get off the door closes

    private Engine engine;
    private int number;
    private boolean buttonOpen, keyOpen;
    private Rectangle collisionBox;
    public Door(Engine engine, String doorNumber, int x , int y) {
        this.engine = engine;
        number = Integer.parseInt(doorNumber.substring(1));
        keyOpen = false;
        buttonOpen = false;
        collisionBox = new Rectangle(x, y, 32, 32);
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

    public void update() {
        if (keyOpen || buttonOpen) {
            collisionBox.setBounds(collisionBox.x, collisionBox.y, 0, 0);
        }
        else {
            collisionBox.setBounds(collisionBox.x, collisionBox.y, 32, 32);
        }
    }

    public void draw(Graphics2D g) {
        if (keyOpen || buttonOpen) {
            g.setColor(Color.BLACK);
        }
        else {
            g.setColor(Color.CYAN);
        }
        g.drawRect(collisionBox.x, collisionBox.y, 32, 32);
    }
}
