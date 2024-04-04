import javax.swing.*;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayPanel extends JPanel implements MouseListener {

    private Engine engine;
    private Frame frame;
    private Rectangle backButton;

    public PlayPanel(Frame frame) {
        addMouseListener(this);
        engine = frame.getEngine();
        addKeyListener(engine.getPlayer());
        setFocusable(true);
        this.frame = frame;
        backButton = new Rectangle(0, 0, 50, 50);
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
        g.setColor(Color.YELLOW);



        g.fillRect((int) backButton.getX(), (int) backButton.getY(), (int) backButton.getWidth(), (int) backButton.getHeight());
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
