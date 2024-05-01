import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class Skeleton extends Player {

    private boolean jumping;
    public Skeleton(Engine engine, int x, int y, boolean isRight, boolean isLeft, boolean isJump) throws InWallException {
        super(engine, x, y);
        if (inAnything()) {
            throw new InWallException("Skeleton in Wall");
        }
        //the skeleton is thinner than the player so make sure to change the collision box
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
        try {
            super.setStandR(ImageIO.read(new File("image/Necromancer/Skeleton/Skeleton_StandR.png")));
            super.setStandL(ImageIO.read(new File("image/Necromancer/Skeleton/Skeleton_StandL.png")));
            super.setRisingR(ImageIO.read(new File("image/Necromancer/Skeleton/Skeleton_RisingR.png")));
            super.setRisingL(ImageIO.read(new File("image/Necromancer/Skeleton/Skeleton_RisingL.png")));
            super.setFallingR(ImageIO.read(new File("image/Necromancer/Skeleton/Skeleton_FallingR.png")));
            super.setFallingL(ImageIO.read(new File("image/Necromancer/Skeleton/Skeleton_FallingL.png")));
            super.setWalkR(ImageIO.read(new File("image/Necromancer/Skeleton/Skeleton_WalkR.png")));
            super.setWalkL(ImageIO.read(new File("image/Necromancer/Skeleton/Skeleton_WalkL.png")));
        } catch (IOException e) {}
    }

    @Override
    public void update() {
        super.update();
        if (jumping && !super.isInAir()) {
            super.setFrameNumber(0);
            super.setUp(true);
            super.setVelocity(-3);
            super.setVelocityTimer(0);
            super.setTimeInAir(0);
            System.out.println("RESET");
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
        for (Door door: super.getEngine().getLevelLayout().getDoors()) {
            if (door.getCollisionBox().intersects(super.getCollisionBox()));
        }
        return false;
    }

    //makes it so that the skeleton can't reach portal
    public boolean reachEnd(Rectangle rectangle) {
        return false;
    }
    public boolean skeletonBelow(Skeleton skeleton) {
        return false;
    }
    public boolean skeletonOnTop(Skeleton skeleton) {
        return false;
    }
    public boolean skeletonOnLeft(Skeleton skeleton) {
        return false;
    }

    public boolean skeletonOnRight(Skeleton skeleton) {
        return false;
    }
    //makes it so that skeleton can't receive key inputs
    public void keyPressed(KeyEvent e) {

    }
    public void keyReleased(KeyEvent e) {

    }
}
