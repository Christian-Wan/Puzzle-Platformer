import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenuPanel extends JPanel implements MouseListener {

    private Frame frame;
    private Rectangle playButton;
    public MainMenuPanel(Frame frame) {
        addMouseListener(this);
        this.frame = frame;
        playButton = new Rectangle(100, 100, 200, 50);
        setFocusable(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Courier New", Font.BOLD, 20));
        g.drawString("PLAY", 105, 125);
        g.drawRect((int) playButton.getX(), (int) playButton.getY(), (int) playButton.getWidth(), (int) playButton.getHeight());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point clicked = e.getPoint();

        if (e.getButton() == 1) {
            if (playButton.contains(clicked)) {
                frame.setActivePanel("Level Select");
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
