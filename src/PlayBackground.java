import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlayBackground {

    private BufferedImage background1;

    public PlayBackground() {
        try {
            background1 = ImageIO.read(new File("image/Level_Assets/Background.png"));
        } catch (IOException e) {}
    }

    public void draw(Graphics2D g) {
        g.drawImage(background1, 0, 0, 1500, 900, null);
    }
}
