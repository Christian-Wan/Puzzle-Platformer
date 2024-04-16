import java.awt.*;

public class Key extends Opener {
    //Is floating like the portal
    //Can only be obtained by player and maybe necromancer minion
    //Is key in Hashmap

    public Key(Engine engine, String keyNumber, int x, int y) {
        super(engine, keyNumber);
        super.setCollisionBox(new Rectangle(x, y, 16, 10));
        //if there are multiple keys they have to be different colours so that the player knows which doors they open
        switch(super.getNumber()) {
            case 1:
                //set the sprite here
                break;
        }
    }

    public void update() {
        if (!super.isOpening()) {
            if (super.touchingPlayer(super.getEngine().getLevelLayout().getAvailableCharacters())) {
                super.setOpening(true);
                System.out.println(super.getEngine().getLevelLayout().getOpenersAndDoors().get(this).length);
                for (Door door: super.getEngine().getLevelLayout().getOpenersAndDoors().get(this)) {
                    door.setKeyOpen(true);
                }
            }
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        if (!super.isOpening()) {
            g.drawRect(super.getCollisionBox().x, super.getCollisionBox().y, super.getCollisionBox().width, super.getCollisionBox().height);
        }
    }
}
