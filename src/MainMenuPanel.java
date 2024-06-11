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
    private Rectangle playButton, settingsButton;
    private boolean settingsOpen, firstPress, pressedMusicSlider, pressedSfxSlider;
    private Engine engine;
    private int frameNumber, framesPassed;
    private BufferedImage menu;
    public MainMenuPanel(Frame frame) {
        addMouseListener(this);
        this.frame = frame;
        engine = frame.getEngine();
        playButton = new Rectangle(602, 247, 295, 77);
        settingsButton = new Rectangle(602, 378, 295, 77);
        frameNumber = 0;
        framesPassed = 0;
        settingsOpen = false;
        firstPress = false;
        pressedMusicSlider = false;
        pressedSfxSlider = false;
        try {
            menu = ImageIO.read(new File("image/Main_Menu.png"));
        } catch (IOException e) {}
        setFocusable(true);
    }

    public void update() {
        engine.getTransitions().update();
        if (pressedMusicSlider) {
            engine.getSoundControl().changeMusicSlider(MouseInfo.getPointerInfo().getLocation(), firstPress);
        }
        else if (pressedSfxSlider) {
            engine.getSoundControl().changeSfxSlider(MouseInfo.getPointerInfo().getLocation(), firstPress);
        }
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
        if (settingsOpen) {
            engine.getSoundControl().draw(g2);
        }
        engine.getTransitions().draw(g2);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point clicked = e.getPoint();

        if (e.getButton() == 1) {
            if (!settingsOpen) {
                if (playButton.contains(clicked)) {
                    engine.getSoundControl().playClick();
                    engine.getTransitions().setDesiredLocation("Level Select");
                    engine.getTransitions().setIn(true);
                }
                else if (settingsButton.contains(clicked)) {
                    engine.getSoundControl().playClick();
                    settingsOpen = true;
                }
            }
            else {
                if (engine.getSoundControl().getClose().contains(clicked)) {
                    engine.getSoundControl().playClick();
                    settingsOpen = false;
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point clicked = e.getPoint();
        if (e.getButton() == 1) {
            if (settingsOpen) {
                if (!firstPress) {
                    if (engine.getSoundControl().getSfxSlider().contains(clicked)) {
                        engine.getSoundControl().playClick();
                        pressedSfxSlider = true;
                        engine.getSoundControl().changeSfxSlider(clicked, firstPress);
                    }
                    else if (engine.getSoundControl().getMusicSlider().contains(clicked)) {
                        engine.getSoundControl().playClick();
                        pressedMusicSlider = true;
                        engine.getSoundControl().changeMusicSlider(clicked, firstPress);
                    }
                }
            }
        }
        firstPress = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == 1) {
            firstPress = false;
            pressedMusicSlider = false;
            pressedSfxSlider = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
