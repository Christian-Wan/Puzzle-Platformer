import java.awt.*;
import java.awt.event.KeyEvent;

public class Skeleton extends Player {

    private boolean jumping;
    public Skeleton(Engine engine, int x, int y, boolean isRight, boolean isLeft, boolean isJump) throws InWallException {
        super(engine, x, y);
        if (inAnything()) {
            throw new InWallException("Skeleton in Wall");
        }
        super.setRight(isRight);
        super.setLeft(isLeft);
        jumping = isJump;
        if (isRight) {
            super.setFacingLeft(false);
            super.setFacingRight(true);
        }
        else if (isLeft) {
            super.setFacingRight(false);
            super.setFacingLeft(true);
        }

    }

    @Override
    public void update() {
        super.update();
        if (jumping && !super.isInAir()) {
            super.setFrameNumber(0);
            super.setUp(true);
            super.setJumpAnimation(true);
            super.setVelocity(-3);
            super.setVelocityTimer(0);
        }
    }

    public boolean inAnything() {
        for (Player player: super.getEngine().getLevelLayout().getAvailableCharacters()) {
            if (player.getCollisionBox().intersects(super.getCollisionBox())) {
                System.out.println("reason");
                return true;
            }
        }
        for (Rectangle wall: super.getEngine().getLevelLayout().getWalls()) {
            if (wall.intersects(super.getCollisionBox())) {
                System.out.println("reason2");
                return true;
            }
        }
        for (Box box: super.getEngine().getLevelLayout().getBoxes()) {
            if (box.getCollisionBox().intersects(super.getCollisionBox())) {
                System.out.println("reson3");
                return true;
            }
        }
        return false;
    }

    //makes it so that the skeleton can't reach portal
    public boolean reachEnd(Rectangle rectangle) {
        return false;
    }
    //makes it so that skeleton can't receive key inputs
    public void keyPressed(KeyEvent e) {

    }
    public void keyReleased(KeyEvent e) {

    }
}
