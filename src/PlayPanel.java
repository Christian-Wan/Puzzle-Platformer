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
    private LevelLayout levelLayout;
    private final int SCALE = 2;
    private Frame frame;
    private Rectangle tempPlatform, tempPlatform2, tempPlatform3, backButton;

    public PlayPanel(Frame frame) {
        addMouseListener(this);
        player = new Player(this);
        portal = new Portal(this);
        levelLayout = new LevelLayout("level1", this);
        player.setLevelLayout(levelLayout);
        addKeyListener(player);
        setFocusable(true);
        this.frame = frame;

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

    public Portal getPortal() {
        return portal;
    }

    public Player getPlayer() {
        return player;
    }

    public LevelLayout getLevelLayout() {
        return levelLayout;
    }

    public void update() {
        levelLayout.update();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        levelLayout.draw(g2);
        g.setColor(Color.BLACK);



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
