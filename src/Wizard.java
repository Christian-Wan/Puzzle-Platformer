import java.awt.*;

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
            setX(storedX);
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
}
