import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;

public class LevelSelectionPanel extends JPanel implements MouseListener {

    private Rectangle backButton;
    private Rectangle[][] levels;
    private Frame frame;
    private Engine engine;
    private ArrayList<String> compleatedLevels;
    private ArrayList<Integer> compleatedLevelsDuringSession;
    public LevelSelectionPanel(Frame frame) {
        addMouseListener(this);
        setFocusable(true);
        engine = frame.getEngine();
        backButton = new Rectangle(50, 50, 50, 50);
        createLevels();
        this.frame = frame;
        compleatedLevelsDuringSession = new ArrayList<Integer>();
        Scanner s = null;
        try {
            s = new Scanner(new File("level_data/save"));

        } catch (IOException e) {}
        compleatedLevels = new ArrayList<String>(Arrays.asList(s.nextLine().split(",")));
        s.close();
    }

    public void createLevels() {
        levels = new Rectangle[4][5];
        levels[0][0] = new Rectangle(50, 150, 50, 50);
        levels[0][1] = new Rectangle(150, 150, 50, 50);
        levels[0][2] = new Rectangle(250, 150, 50, 50);
        levels[0][3] = new Rectangle(350, 150, 50, 50);
        levels[0][4] = new Rectangle(450, 150, 50, 50);
        levels[1][0] = new Rectangle(50, 250, 50, 50);
        levels[1][1] = new Rectangle(150, 250, 50, 50);
        levels[1][2] = new Rectangle(250, 250, 50, 50);
        levels[1][3] = new Rectangle(350, 250, 50, 50);
        levels[1][4] = new Rectangle(450, 250, 50, 50);
        levels[2][0] = new Rectangle(50, 350, 50, 50);
        levels[2][1] = new Rectangle(150, 350, 50, 50);
        levels[2][2] = new Rectangle(250, 350, 50, 50);
        levels[2][3] = new Rectangle(350, 350, 50, 50);
        levels[2][4] = new Rectangle(450, 350, 50, 50);
        levels[3][0] = new Rectangle(50, 450, 50, 50);
        levels[3][1] = new Rectangle(150, 450, 50, 50);

    }
    public void update() {
        engine.getTransitions().update();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawRect((int) backButton.getX(), (int) backButton.getY(), (int) backButton.getWidth(), (int) backButton.getHeight());
        int levelNumber = 0;
        for (Rectangle[] row: levels) {
            for (Rectangle level: row) {
                if (level != null) {
                    g2.drawRect((int) level.getX(), (int) level.getY(), (int) level.getWidth(), (int) level.getHeight());
                    levelNumber++;
                    if (compleatedLevels.contains(Integer.toString(levelNumber)) || compleatedLevelsDuringSession.contains(levelNumber)) {
                        g2.drawRect((int) level.getX() + 45, (int) level.getY() - 5, 10, 10);
                    }
                }
            }
        }

        engine.getTransitions().draw(g2);

    }
    //Many rectangles will be made like in the elevens game.
    //By dividing the y value and multiplying by the amount of things in the row and then adding the divided x value we can get the number of the level
    @Override
    public void mouseClicked(MouseEvent e) {
        Point clicked = e.getPoint();

        if (e.getButton() == 1) {
            if (backButton.contains(clicked)) {
                engine.getTransitions().setDesiredLocation("Main Menu");
                engine.getTransitions().setIn(true);
            }

            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 5; c ++) {

                    if (levels[r][c] != null) {
                        if (levels[r][c].contains(clicked)) {
                            //Row * 5  + Column + 1
                            engine.newLevelLayout(r * 5 + c + 1);
                            engine.getTransitions().setDesiredLocation("Play");
                            engine.getTransitions().setIn(true);
                        }
                    }
                }
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

    public void updateCompleatedLevels(Integer level) {
        compleatedLevelsDuringSession.add(level);
    }
}
