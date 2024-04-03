import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Portal {

    private BufferedImage portal;
    private int x, y, frameNumber, framesPassed;
    private PlayPanel playPanel;


    public Portal(PlayPanel p) {
        try {
            portal = ImageIO.read(new File("image/portal.png"));
        } catch (IOException e) {}
        x = 100;
        y = 100;
        framesPassed = 0;
        frameNumber = 0;
        playPanel = p;
    }

    public void update() {
        framesPassed++;
        if (framesPassed == 4) {
            if (frameNumber != 19) {
                frameNumber++;
                if (frameNumber == 5 || frameNumber == 10) {
                    y -= 5;
                }
                else if (frameNumber == 15) {
                    y += 5;
                }
            }
            else {
                frameNumber = 0;
                y += 5;
            }
            framesPassed = 0;
        }
    }
    public void draw(Graphics2D g) {
        BufferedImage image = portal.getSubimage(21 + (64 * (frameNumber)), 16, 22, 32);

        g.drawImage(image, x, y, 22 * playPanel.getSCALE(), 32 * playPanel.getSCALE(), null);
    }

}
