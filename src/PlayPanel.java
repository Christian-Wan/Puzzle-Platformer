import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//When the player presses q it changes to the next character this might be able to be done if there is a method that changes the key listener using a parameter and addKeyListener()
public class PlayPanel extends JPanel implements MouseListener {

    private Engine engine;
    private Frame frame;
    private Rectangle backButton;
    private BufferedImage backButtonImage;

    public PlayPanel(Frame frame) {
        addMouseListener(this);
        engine = frame.getEngine();
        setFocusable(true);
        addKeyListener(engine.getPlayer());
        this.frame = frame;
        backButton = new Rectangle(-15, -15, 96, 96);
        try {
            backButtonImage = ImageIO.read(new File("image/Back_Button.png"));
        } catch (IOException e) {}
    }

    public void update() {

        engine.getLevelLayout().update();
        engine.getTransitions().update();
//        System.out.println("ZXC");
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        engine.getLevelLayout().draw(g2);
        g.drawImage(backButtonImage ,(int) backButton.getX(), (int) backButton.getY(), (int) backButton.getWidth(), (int) backButton.getHeight(), null);
        engine.getTransitions().draw(g2);
//        System.out.println("ASD");
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        Point clicked = e.getPoint();

        if (e.getButton() == 1) {
            System.out.println("ASKJDH");
            if (backButton.contains(clicked)) {
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
