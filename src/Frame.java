import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.IOException;


public class Frame extends JFrame implements Runnable{

    private Engine engine;
    private Thread windowThread;
    private String activePanel;
    public Frame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        engine = new Engine(this);
        engine.makePanels();
        activePanel = "Main Menu";
        this.add(engine.getPlayPanel());
        this.add(engine.getLevelSelectionPanel());
        this.add(engine.getMainMenuPanel());
        engine.getPlayPanel().setVisible(false);
        engine.getLevelSelectionPanel().setVisible(false);
        engine.getMainMenuPanel().setVisible(true);
        int frameWidth = 1500;
        int frameHeight = 900;
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(frameWidth, frameHeight);
        this.setLocation(300, 100);
        this.setVisible(true);
        startThread();
    }

    public void changeActivePanel(String activePanel) {
        this.activePanel = activePanel;
        if (activePanel.equals("Play")) {
            engine.getTransitions().setOut(true);
            this.remove(engine.getLevelSelectionPanel());
            engine.getLevelSelectionPanel().setVisible(false);
            this.add(engine.getPlayPanel());
            engine.getPlayPanel().setVisible(true);
            engine.getPlayPanel().requestFocus();
        }
        else if (activePanel.equals("Level Select")) {
            engine.getTransitions().setOut(true);
            this.remove(engine.getPlayPanel());
            this.remove(engine.getMainMenuPanel());
            engine.getPlayPanel().setVisible(false);
            engine.getMainMenuPanel().setVisible(false);
            this.add(engine.getLevelSelectionPanel());
            engine.getLevelSelectionPanel().setVisible(true);
            engine.getLevelSelectionPanel().requestFocus();
        }
        else {
            engine.getTransitions().setOut(true);
            this.remove(engine.getLevelSelectionPanel());
            engine.getLevelSelectionPanel().setVisible(false);
            this.add(engine.getMainMenuPanel());
            engine.getMainMenuPanel().setVisible(true);
            engine.getMainMenuPanel().requestFocus();
        }
    }


    public void startThread() {
        windowThread = new Thread(this);
        windowThread.start();
    }

    public Engine getEngine() {
        return engine;
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / 60;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (true) {
            engine.getSoundControl().update();

            if (activePanel.equals("Play")) {
                engine.getPlayPanel().update();
                engine.getPlayPanel().repaint();
            }
            else if (activePanel.equals("Main Menu")) {
                engine.getMainMenuPanel().update();
                engine.getMainMenuPanel().repaint();
            }
            else {
                engine.getLevelSelectionPanel().update();
                engine.getLevelSelectionPanel().repaint();
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
