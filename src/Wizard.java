import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

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
            BufferedImage image = null;
            if (super.isFacingRight()) {
                image = super.getStandR().getSubimage(24, 21, 15, 23);
            }
            else {
                image = super.getStandL().getSubimage(24, 21, 15, 23);
            }
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
            g.drawImage(image, storedX, storedY, 30, 46, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
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
