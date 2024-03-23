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
            }
            else {
                frameNumber = 0;
            }
            framesPassed = 0;
        }
    }
    public void draw(Graphics2D g) {
        BufferedImage image = portal.getSubimage(21 + (64 * (frameNumber)), 14, 22, 34);

        g.drawImage(image, x, y, 22 * playPanel.getSCALE(), 32 * 5, null);
    }

}
