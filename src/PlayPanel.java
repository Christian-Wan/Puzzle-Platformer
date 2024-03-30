import javax.swing.*;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayPanel extends JPanel implements MouseListener {

    private Portal portal;
    private Player player;
    private final int SCALE = 5;
    private Frame frame;
    private Rectangle tempPlatform, tempPlatform2, tempPlatform3, backButton;

    public PlayPanel(Frame frame) {
        addMouseListener(this);
        player = new Player(this);
        addKeyListener(player);
        setFocusable(true);
        this.frame = frame;
        portal =  new Portal(this);
        tempPlatform = new Rectangle(10, 300, 400, 10);
        tempPlatform2 = new Rectangle(10, 220, 50, 150);
        tempPlatform3 = new Rectangle(400, 220, 50, 150);
        backButton = new Rectangle(0, 0, 50, 50);

    }

    public Rectangle getTempPlatform() {
        return tempPlatform;
    }

    public Rectangle getTempPlatform2() {
        return tempPlatform2;
    }

    public Rectangle getTempPlatform3() {
        return tempPlatform3;
    }

    public int getSCALE() {
        return SCALE;
    }

    public void update() {
        portal.update();
        player.update();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        portal.draw(g2);
        player.draw(g2);
        g2.fillRect(tempPlatform.x, tempPlatform.y, tempPlatform.width, tempPlatform.height);
        g2.fillRect(tempPlatform2.x, tempPlatform2.y, tempPlatform2.width, tempPlatform2.height);
        g2.fillRect(tempPlatform3.x, tempPlatform3.y, tempPlatform3.width, tempPlatform3.height);
        g.drawRect((int) backButton.getX(), (int) backButton.getY(), (int) backButton.getWidth(), (int) backButton.getHeight());

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
