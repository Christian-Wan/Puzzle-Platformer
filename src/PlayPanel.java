import javax.swing.*;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayPanel extends JPanel implements MouseListener {

    Engine engine;
    private Frame frame;
    private Rectangle backButton;
    private String level;

    public PlayPanel(Frame frame, String level) {
        addMouseListener(this);
        this.level = level;
        engine = new Engine(this);
        addKeyListener(engine.getPlayer());
        setFocusable(true);
        this.frame = frame;

        backButton = new Rectangle(0, 0, 50, 50);

    }

    public void update() {
        engine.getLevelLayout().update();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        engine.getLevelLayout().draw(g2);
        g.setColor(Color.BLACK);



        g.drawRect((int) backButton.getX(), (int) backButton.getY(), (int) backButton.getWidth(), (int) backButton.getHeight());

    }

    public String getLevel() {
        return level;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point clicked = e.getPoint();

        if (e.getButton() == 1) {
            System.out.println("ASKJDH");
            if (backButton.contains(clicked)) {
                frame.setActivePanel("Level Select");
                System.out.println("LKJ");
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
