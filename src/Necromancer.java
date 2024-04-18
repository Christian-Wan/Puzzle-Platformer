import java.awt.*;
import java.awt.event.KeyEvent;

public class Necromancer extends Player {

    private Skeleton summon;
    public Necromancer(Engine engine, int x, int y) {
        super(engine, x, y);
    }

    public void doAbility() {
        if (super.isFacingRight()) {
            try {
                summon = new Skeleton(super.getEngine(), (int) (super.getCollisionBox().getX() + super.getCollisionBox().getWidth() + 4), super.getY(), super.isRight(), super.isLeft(), super.isUp());

            } catch (InWallException e) {}
        }
        else {
            try {
                summon = new Skeleton(super.getEngine(), (int) (super.getCollisionBox().getX() - super.getCollisionBox().getWidth() - 6), super.getY(), super.isRight(), super.isLeft(), super.isUp());
            } catch (InWallException e) {}
        }
    }

    public void update() {
        super.update();
        if (summon != null) {
            summon.update();
        }
    }
    public void draw(Graphics2D g) {
        super.draw(g);
        if (summon != null) {
            summon.draw(g);
        }
    }

    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_R) {
            summon = null;
        }
    }

    public Skeleton getSummon() {
        return summon;
    }
}
