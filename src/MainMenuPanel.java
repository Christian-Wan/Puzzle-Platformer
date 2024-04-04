import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenuPanel extends JPanel implements MouseListener {

    private Frame frame;
    private Rectangle playButton;
    private Engine engine;
    public MainMenuPanel(Frame frame) {
        addMouseListener(this);
        this.frame = frame;
        engine = frame.getEngine();
        playButton = new Rectangle(100, 100, 200, 50);
        setFocusable(true);
    }

    public void update() {
        engine.getTransitions().update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("PLAY", 105, 125);
        g.drawRect((int) playButton.getX(), (int) playButton.getY(), (int) playButton.getWidth(), (int) playButton.getHeight());
        engine.getTransitions().draw(g2);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point clicked = e.getPoint();

        if (e.getButton() == 1) {
            if (playButton.contains(clicked)) {
                engine.getTransitions().setDesiredLocation("Level Select");
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
