import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Key extends Opener {
    //Is floating like the portal
    //Can only be obtained by player and maybe necromancer minion
    //Is key in Hashmap

    private BufferedImage image;
    private int timer;

    public Key(Engine engine, String keyNumber, int x, int y) {
        super(engine, keyNumber);
        timer = 0;
        super.setCollisionBox(new Rectangle(x, y, 28, 12));
        //if there are multiple keys they have to be different colours so that the player knows which doors they open
        switch(super.getNumber()) {
            case 1:
                try {
                    image = ImageIO.read(new File("image/Level_Assets/Key_Red.png")).getSubimage(26, 29, 14, 6);
                } catch (IOException e) {}
                break;
            case 2:
                try {
                    image = ImageIO.read(new File("image/Level_Assets/Key_Blue.png")).getSubimage(26, 29, 14, 6);
                } catch (IOException e) {}
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

        timer++;
        if (timer == 30) {
            super.getCollisionBox().setLocation((int) super.getCollisionBox().getX(), (int) super.getCollisionBox().getY() - 5);
        }
        else if (timer == 60) {
            super.getCollisionBox().setLocation((int) super.getCollisionBox().getX(), (int) super.getCollisionBox().getY() + 5);
            timer = 0;
        }

    }

    public void draw(Graphics2D g) {
        if (!super.isOpening()) {
            g.drawImage(image, (int) super.getCollisionBox().getX(), (int) super.getCollisionBox().getY(), 28, 12, null);
        }
    }
}
