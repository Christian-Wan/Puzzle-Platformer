import javax.swing.*;
import java.awt.event.KeyListener;

public class Frame extends JFrame implements Runnable{

    private PlayPanel playPanel;
    private MainMenuPanel mainMenuPanel;
    private LevelSelectionPanel levelSelectionPanel;
    private Thread windowThread;
    private String activePanel;
    public Frame() {
        activePanel = "Main Menu";
        mainMenuPanel = new MainMenuPanel(this);
        playPanel = new PlayPanel(this, "level1");
        levelSelectionPanel = new LevelSelectionPanel(this);
        this.add(playPanel);
        this.add(levelSelectionPanel);
        this.add(mainMenuPanel);
        playPanel.setVisible(false);
        levelSelectionPanel.setVisible(false);
        mainMenuPanel.setVisible(true);
        int frameWidth = 1400;
        int frameHeight = 900;
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(frameWidth, frameHeight);
        this.setLocation(300, 100);
        this.setVisible(true);
        startThread();
    }

    public void setActivePanel(String activePanel) {
        this.activePanel = activePanel;
        if (activePanel.equals("Play")) {
            levelSelectionPanel.setVisible(false);
            playPanel = new PlayPanel(this, "level1");
            this.add(playPanel);
            playPanel.requestFocus();
        }
        else if (activePanel.equals("Level Select")) {
            playPanel.setVisible(false);
            mainMenuPanel.setVisible(false);
            levelSelectionPanel = new LevelSelectionPanel(this);
            this.add(levelSelectionPanel);
            levelSelectionPanel.requestFocus();
        }
        else {
            levelSelectionPanel.setVisible(false);
            mainMenuPanel = new MainMenuPanel(this);
            this.add(mainMenuPanel);
            mainMenuPanel.requestFocus();
        }
    }

    public void startThread() {
        windowThread = new Thread(this);
        windowThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 /60;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (true) {
            if (activePanel.equals("Play")) {
                playPanel.update();
                playPanel.repaint();
            }
            else if (activePanel.equals("Main Menu")) {
                mainMenuPanel.repaint();
            }

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }


                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
