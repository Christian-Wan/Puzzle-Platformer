import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Transitions {


    //Make a transition for after the player wins
    private BufferedImage transitionAnimation;
    private boolean in, out;
    private int frameNumber, framesPassed;
    private Engine engine;
    private String desiredLocation;

    public Transitions(Engine engine) {
        try {
            transitionAnimation = ImageIO.read(new File("image/Transition.png"));
        } catch (IOException e) {}
        this.engine = engine;
        desiredLocation = "";
        frameNumber = 0;
        framesPassed = 0;
    }

    public void setIn(boolean in) {
        this.in = in;
    }

    public void setOut(boolean out) {
        this.out = out;
    }

    public void update() {
        if (in) {
            framesPassed++;

            if (framesPassed == 1) {
                frameNumber++;

                if (frameNumber == 29) {
                    frameNumber = 0;
                    in = false;
                    engine.getFrame().changeActivePanel(desiredLocation);
                }
                framesPassed = 0;
            }
        }
        else if (out) {
            framesPassed++;

            if (framesPassed == 1) {
                frameNumber++;

                if (frameNumber == 29) {
                    frameNumber = 0;
                    out = false;
                }
                framesPassed = 0;
            }
        }
    }

    public void setDesiredLocation(String desiredLocation) {
        this.desiredLocation = desiredLocation;
    }

    public void draw(Graphics2D g) {
        BufferedImage image = null;
        if (in) {
            image = transitionAnimation.getSubimage(frameNumber * 1400, 0, 1400, 900);

        }
        else if (out) {
            image = transitionAnimation.getSubimage(40600 - (frameNumber * 1400), 0, 1400, 900);
        }

        g.drawImage(image, 0, 0, 1400, 900, null);
    }
}
