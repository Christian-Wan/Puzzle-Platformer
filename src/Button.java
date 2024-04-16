import java.awt.*;
import java.util.ArrayList;

public class Button extends Opener {

    //Can only be placed on the ground
    //Player/Box/Necromancer can press button
    //Button is a key is hashmap
    public Button(Engine engine, String buttonNumber, int x, int y) {
        super(engine, buttonNumber);
        super.setCollisionBox(new Rectangle(x, y, 16, 8));
        switch(super.getNumber()) {
            case 1:
                //set the sprite here
                break;
        }
    }
    public boolean touchingBox(ArrayList<Box> boxes) {
        for (Box box: boxes) {
            if (super.getCollisionBox().intersects(box.getCollisionBox())) {
                return true;
            }
        }
        return false;
    }

    public void update() {
        if (super.touchingPlayer(super.getEngine().getLevelLayout().getAvailableCharacters()) || touchingBox(super.getEngine().getLevelLayout().getBoxes())) {
            super.setOpening(true);
            for (Door door: super.getEngine().getLevelLayout().getOpenersAndDoors().get(this)) {
                door.setButtonOpen(true);
            }
        }
        else {
            super.setOpening(false);
            for (Door door: super.getEngine().getLevelLayout().getOpenersAndDoors().get(this)) {
                door.setButtonOpen(false);
            }
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.YELLOW);
        g.drawRect(super.getCollisionBox().x, super.getCollisionBox().y, super.getCollisionBox().width, super.getCollisionBox().height);
    }
}
