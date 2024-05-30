import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenuPanel extends JPanel implements MouseListener {

    private Frame frame;
    private Rectangle playButton;
    private Engine engine;
    private int frameNumber, framesPassed;
    private BufferedImage menu;
    public MainMenuPanel(Frame frame) {
        addMouseListener(this);
        this.frame = frame;
        engine = frame.getEngine();
        playButton = new Rectangle(600, 247, 298, 78);
        frameNumber = 0;
        framesPassed = 0;
        try {
            menu = ImageIO.read(new File("image/Main_Menu.png"));
        } catch (IOException e) {}
        setFocusable(true);
    }

    public void update() {
        engine.getTransitions().update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        framesPassed++;
        if (framesPassed % 4 == 0) {
            frameNumber++;
        }
        if (frameNumber == 30) {
            frameNumber = 0;
        }
        BufferedImage image = menu.getSubimage(frameNumber * 1500, 0, 1500, 900);
        g.drawImage(image, 0, 0, 1500, 900, null);
        g.setColor(Color.PINK);
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
