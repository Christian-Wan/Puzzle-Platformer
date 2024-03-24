import javax.swing.*;
import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayPanel extends JPanel {

    private Portal portal;
    private Player player;
    private final int SCALE = 5;
    private KeyEvent input;

    public PlayPanel() {
        player = new Player(this);
        addKeyListener(player);
        setFocusable(true);
        portal =  new Portal(this);
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
    }

}
