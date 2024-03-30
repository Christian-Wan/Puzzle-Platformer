import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;

public class LevelSelectionPanel extends JPanel implements MouseListener {

    private Rectangle backButton, startLevelButton;
    private Frame frame;
    public LevelSelectionPanel(Frame frame) {
        addMouseListener(this);
        setFocusable(true);
        backButton = new Rectangle(50, 50, 50, 50);
        startLevelButton = new Rectangle(150, 150, 100, 100);
        this.frame = frame;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect((int) backButton.getX(), (int) backButton.getY(), (int) backButton.getWidth(), (int) backButton.getHeight());
        g.drawRect((int) startLevelButton.getX(), (int) startLevelButton.getY(), (int) startLevelButton.getWidth(), (int) startLevelButton.getHeight());

    }
    //Many rectangles will be made like in the elevens game.
    //By dividing the y value and multiplying by the amount of things in the row and then adding the divided x value we can get the number of the level
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point clicked = e.getPoint();

        if (e.getButton() == 1) {
            if (backButton.contains(clicked)) {
                frame.setActivePanel("Main Menu");
            }

            if (startLevelButton.contains(clicked)) {
                frame.setActivePanel("Play");
            }
        }
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
