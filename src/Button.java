import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Button extends Opener {

    //Can only be placed on the ground
    //Player/Box/Necromancer can press button
    //Button is a key is hashmap

    private BufferedImage up, down;
    public Button(Engine engine, String buttonNumber, int x, int y) {
        super(engine, buttonNumber);
        super.setCollisionBox(new Rectangle(x, y, 16, 8));
        try {
            switch (super.getNumber()) {
                case 1:
                    up = ImageIO.read(new File("image/Level_Assets/Button_Red.png")).getSubimage(28, 45, 8, 3);
                    down = ImageIO.read(new File("image/Level_Assets/Button_Red_Down.png")).getSubimage(28, 45, 8, 3);
                    break;
                case 2:
                    up = ImageIO.read(new File("image/Level_Assets/Button_Blue.png")).getSubimage(28, 45, 8, 3);
                    down = ImageIO.read(new File("image/Level_Assets/Button_Blue_Down.png")).getSubimage(28, 45, 8, 3);
                    break;
                case 3:
                    up = ImageIO.read(new File("image/Level_Assets/Button_Green.png")).getSubimage(28, 45, 8, 3);
                    down = ImageIO.read(new File("image/Level_Assets/Button_Green_Down.png")).getSubimage(28, 45, 8, 3);
                    break;
            }
        } catch (IOException e) {}
    }
    public boolean touchingBox(ArrayList<Box> boxes) {
        for (Box box: boxes) {
            if (super.getCollisionBox().intersects(box.getCollisionBox())) {
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

    public void update() {
        if (super.touchingPlayer(super.getEngine().getLevelLayout().getAvailableCharacters()) || touchingBox(super.getEngine().getLevelLayout().getBoxes())) {
            super.setOpening(true);
            for (Door door: super.getEngine().getLevelLayout().getOpenersAndDoors().get(this)) {
                door.setButtonOpen(true);
                System.out.println("Working");
            }
        }
        //This if statement is just for the necromancer's skeleton
        else if (super.getEngine().getNecromancer() != null && super.getEngine().getNecromancer().getSummon() != null && touchingSummon(super.getEngine().getNecromancer().getSummon())) {
            super.setOpening(true);
            for (Door door: super.getEngine().getLevelLayout().getOpenersAndDoors().get(this)) {
                door.setButtonOpen(true);
            }
        }
        else {
            super.setOpening(false);
            for (Door door: super.getEngine().getLevelLayout().getOpenersAndDoors().get(this)) {
                if (!door.isOpenedThisFrame()) {
                    door.setButtonOpen(false);
                }
            }
        }
    }

    public void draw(Graphics2D g) {
        if (super.isOpening()) {
            g.drawImage(down, (int) super.getCollisionBox().getX(), (int) super.getCollisionBox().getY(), 16, 8, null);

        }
        else {
            g.drawImage(up, (int) super.getCollisionBox().getX(), (int) super.getCollisionBox().getY(), 16, 8, null);

        }
    }
}
