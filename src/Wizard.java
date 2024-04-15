import java.awt.*;
import java.awt.event.KeyEvent;

public class Wizard extends Player {

    private int storedX, storedY;
    private boolean abilityActive;
    public Wizard(Engine engine) {
        super(engine);
    }

    public Wizard(Engine engine, int x, int y) {
        super(engine, x, y);
    }

    @Override
    public void doAbility() {
        if (!abilityActive) {
            storedX = getX();
            storedY = getY();
            abilityActive = true;
        }
        else {
            if (isRight()) {
                setX(storedX - 2);
            }
            else if (isLeft()) {
                setX(storedX + 2);
            }
            else {
                setX(storedX);
            }
            setY(storedY);
            abilityActive = false;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        if (abilityActive) {
            g.drawRect(storedX, storedY, 10, 10);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_R) {
            abilityActive = false;
        }
    }
}
