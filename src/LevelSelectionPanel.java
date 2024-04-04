import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;

public class LevelSelectionPanel extends JPanel implements MouseListener {

    private Rectangle backButton, startLevelButton;
    private Frame frame;
    private Engine engine;
    public LevelSelectionPanel(Frame frame) {
        addMouseListener(this);
        setFocusable(true);
        engine = frame.getEngine();
        backButton = new Rectangle(50, 50, 50, 50);
        startLevelButton = new Rectangle(150, 150, 100, 100);
        this.frame = frame;
    }

    public void update() {
        engine.getTransitions().update();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawRect((int) backButton.getX(), (int) backButton.getY(), (int) backButton.getWidth(), (int) backButton.getHeight());
        g2.drawRect((int) startLevelButton.getX(), (int) startLevelButton.getY(), (int) startLevelButton.getWidth(), (int) startLevelButton.getHeight());
        engine.getTransitions().draw(g2);

    }
    //Many rectangles will be made like in the elevens game.
    //By dividing the y value and multiplying by the amount of things in the row and then adding the divided x value we can get the number of the level
    @Override
    public void mouseClicked(MouseEvent e) {
        Point clicked = e.getPoint();

        if (e.getButton() == 1) {
            if (backButton.contains(clicked)) {
                engine.getTransitions().setDesiredLocation("Main Menu");
                engine.getTransitions().setIn(true);
            }

            if (startLevelButton.contains(clicked)) {
                engine.getTransitions().setDesiredLocation("Play");
                engine.getTransitions().setIn(true);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
