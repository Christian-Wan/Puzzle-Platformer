import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Knight extends Player {

    private boolean usingAbility;
    private Box connectedBox;

    public Knight (Engine engine) {
        super(engine);
    }

    public Knight(Engine engine, int x, int y) {
        super(engine, x, y);
        usingAbility = false;
        try {
            super.setStandR(ImageIO.read(new File("image/Knight/Knight_StandR.png")));
            super.setStandL(ImageIO.read(new File("image/Knight/Knight_StandL.png")));
            super.setRisingR(ImageIO.read(new File("image/Knight/Knight_RisingR.png")));
            super.setRisingL(ImageIO.read(new File("image/Knight/Knight_RisingL.png")));
            super.setFallingR(ImageIO.read(new File("image/Knight/Knight_FallingR.png")));
            super.setFallingL(ImageIO.read(new File("image/Knight/Knight_FallingL.png")));
            super.setWalkR(ImageIO.read(new File("image/Knight/Knight_WalkR.png")));
            super.setWalkL(ImageIO.read(new File("image/Knight/Knight_WalkL.png")));
        } catch (IOException e) {}
    }
    public boolean boxOnLeft(ArrayList<Box> boxes) {
        for (Box box: boxes) {
            if ((box.getX() + 32 >= super.getCollisionBox().getX()) && (box.getX() <= super.getCollisionBox().getX()) && ((box.getY() < super.getCollisionBox().getY() && box.getY() + 32 > super.getCollisionBox().getY()) || (box.getY() < (super.getCollisionBox().getY() + super.getCollisionBox().getHeight()) && box.getY() + 32 > super.getCollisionBox().getY() + super.getCollisionBox().getHeight()) || (box.getY() <= super.getCollisionBox().getCenterY() && box.getY() + 32 >= super.getCollisionBox().getCenterY()))) {
                connectedBox = box;
                return true;
            }
        }
        if (!usingAbility) {
            connectedBox = null;
        }
        return false;
    }

    public boolean boxOnRight(ArrayList<Box> boxes) {
        for (Box box: boxes) {
            if ((box.getX() <= super.getCollisionBox().getX() + super.getCollisionBox().getWidth()) && (box.getX() + 32 >= super.getCollisionBox().getX() + super.getCollisionBox().getWidth()) && ((box.getY() < super.getCollisionBox().getY() && box.getY() + 32 > super.getCollisionBox().getY()) || (box.getY() < super.getCollisionBox().getY() + super.getCollisionBox().getHeight() && box.getY() + 32 > super.getCollisionBox().getY() + super.getCollisionBox().getHeight()) || (box.getY() <= super.getCollisionBox().getCenterY() && box.getY() + 32 >= super.getCollisionBox().getCenterY()))) {
                connectedBox = box;
                return true;
            }

        }
        if (!usingAbility) {
            connectedBox = null;
        }
        return false;
    }
    public void doAbility() {
        if (boxOnLeft(super.getEngine().getLevelLayout().getBoxes()) || boxOnRight(super.getEngine().getLevelLayout().getBoxes())) {
            usingAbility = true;
        }
        else {
            usingAbility = false;
            connectedBox = null;
        }
    }
    public void update() {
        if (connectedBox != null) {
            if (super.isRight()) {
                connectedBox.checkChangeX(2);
            }
            else if (super.isLeft()) {
                connectedBox.checkChangeX(-2);
            }
        }
        super.update();
        if (usingAbility) {
            if (super.isRight() && super.isFacingLeft()) {
                connectedBox.checkChangeX(2);
            }
            else if (super.isLeft() && super.isFacingRight()) {
                connectedBox.checkChangeX(-2);
            }
            doAbility();
        }
    }
    public void keyPressed(KeyEvent e) {
        if (usingAbility) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT && super.isFacingRight()) {
                if (!super.isLeft() && !super.isInAir()) {
                    setFrameNumber(0);
                }
                super.setLeft(true);
            }
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT && super.isFacingLeft()) {
                if (!super.isRight() && !super.isInAir()) {
                    setFrameNumber(0);
                }
                super.setRight(true);

            }
        }
        else {
            super.keyPressed(e);
            if (e.getKeyCode() == KeyEvent.VK_E) {
                super.setLeft(false);
                super.setRight(false);
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        if (e.getKeyCode() == KeyEvent.VK_E) {
            usingAbility = false;
        }
    }
}
