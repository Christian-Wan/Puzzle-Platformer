import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
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
    private BufferedImage image, star, backButtonImage;
    private Engine engine;
    private ArrayList<String> compleatedLevels;
    private ArrayList<Integer> compleatedLevelsDuringSession;
    public LevelSelectionPanel(Frame frame) {
        addMouseListener(this);
        setFocusable(true);
        engine = frame.getEngine();
        backButton = new Rectangle(-15, -15, 96, 96);
        createLevels();
        this.frame = frame;
        compleatedLevelsDuringSession = new ArrayList<Integer>();
        try {
            image = ImageIO.read(new File("image/Level_Selection.png"));
            star = ImageIO.read(new File("image/Star.png")).getSubimage(24, 22, 16, 16);
            backButtonImage = ImageIO.read(new File("image/Back_Button.png"));
        } catch (IOException e) {}
        Scanner s = null;
        try {
            s = new Scanner(new File("level_data/save"));
        } catch (IOException e) {}
        compleatedLevels = new ArrayList<String>(Arrays.asList(s.nextLine().split(",")));
        s.close();
    }

    public void createLevels() {
        levels = new Rectangle[5][8];
        levels[0][0] = new Rectangle(188, 160, 75, 75);
        levels[0][1] = new Rectangle(338, 160, 75, 75);
        levels[0][2] = new Rectangle(488, 160, 75, 75);
        levels[0][3] = new Rectangle(638, 160, 75, 75);
        levels[0][4] = new Rectangle(788, 160, 75, 75);
        levels[0][5] = new Rectangle(938, 160, 75, 75);
        levels[0][6] = new Rectangle(1088, 160, 75, 75);
        levels[0][7] = new Rectangle(1238, 160, 75, 75);
        levels[1][0] = new Rectangle(188, 310, 75, 75);
        levels[1][1] = new Rectangle(338, 310, 75, 75);
        levels[1][2] = new Rectangle(488, 310, 75, 75);
        levels[1][3] = new Rectangle(638, 310, 75, 75);
        levels[1][4] = new Rectangle(788, 310, 75, 75);
        levels[1][5] = new Rectangle(938, 310, 75, 75);
        levels[1][6] = new Rectangle(1088, 310, 75, 75);
        levels[1][7] = new Rectangle(1238, 310, 75, 75);
        levels[2][0] = new Rectangle(188, 460, 75, 75);
        levels[2][1] = new Rectangle(338, 460, 75, 75);
        levels[2][2] = new Rectangle(488, 460, 75, 75);
        levels[2][3] = new Rectangle(638, 460, 75, 75);




    }
    public void update() {
        engine.getTransitions().update();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, 0, 0, 1500, 900, null);
        g2.drawImage(backButtonImage, (int) backButton.getX(), (int) backButton.getY(), (int) backButton.getWidth(), (int) backButton.getHeight(), null);
        int levelNumber = 0;
        for (Rectangle[] row: levels) {
            for (Rectangle level: row) {
                if (level != null) {
                    g2.setColor(Color.PINK);
                    g2.drawRect((int) level.getX(), (int) level.getY(), (int) level.getWidth(), (int) level.getHeight());
                    levelNumber++;
                    if (compleatedLevels.contains(Integer.toString(levelNumber)) || compleatedLevelsDuringSession.contains(levelNumber)) {
                        g2.drawImage(star, (int) level.getX() + 67, (int) level.getY() - 8, 16, 16, null);
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

            for (int r = 0; r < 5; r++) {
                for (int c = 0; c < 8; c ++) {

                    if (levels[r][c] != null) {
                        if (levels[r][c].contains(clicked)) {
                            //Row * 5  + Column + 1
                            engine.newLevelLayout(r * 8 + c + 1);
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
