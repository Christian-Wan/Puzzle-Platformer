import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SoundControl {

    private float sfxSound, musicSound;
    private FloatControl musicControl, jumpControl, clickControl, portalControl, buttonPressControl;
    private Point firstPoint;
    private int firstX;
    private BufferedImage settings, slider;
    private Rectangle sfxSlider, musicSlider, close;
    private Clip music, jump, click, portal, buttonPress;

    public SoundControl() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        sfxSlider = new Rectangle(775, 393, 12, 46);
        musicSlider = new Rectangle(775, 307, 12, 46);
        close = new Rectangle(942, 246, 19, 19);
        firstX = 0;
        firstPoint = new Point();

        settings = ImageIO.read(new File("image/Settings_Menu.png"));
        slider = ImageIO.read(new File("image/Slider.png")).getSubimage(30, 20, 4, 23);

        music = AudioSystem.getClip();
        music.open(AudioSystem.getAudioInputStream(new File("audio/Yonder Hill and Dale - Aaron Kenny.wav").getAbsoluteFile()));
        jump = AudioSystem.getClip();
        jump.open(AudioSystem.getAudioInputStream(new File("audio/Jump.wav").getAbsoluteFile()));
        click = AudioSystem.getClip();
        click.open(AudioSystem.getAudioInputStream(new File("audio/Click.wav").getAbsoluteFile()));
        portal = AudioSystem.getClip();
        portal.open(AudioSystem.getAudioInputStream(new File("audio/Portal_Enter.wav").getAbsoluteFile()));
        buttonPress = AudioSystem.getClip();
        buttonPress.open(AudioSystem.getAudioInputStream(new File("audio/Button_Press.wav").getAbsoluteFile()));

        musicSound = -14.0f;
        musicControl = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
        musicControl.setValue(musicSound);
        music.loop(Clip.LOOP_CONTINUOUSLY);

        sfxSound = -14.0f;
        jumpControl = (FloatControl) jump.getControl((FloatControl.Type.MASTER_GAIN));
        clickControl = (FloatControl) click.getControl((FloatControl.Type.MASTER_GAIN));
        portalControl = (FloatControl) portal.getControl((FloatControl.Type.MASTER_GAIN));
        buttonPressControl = (FloatControl) buttonPress.getControl((FloatControl.Type.MASTER_GAIN));
    }

    public void update() {
        musicControl.setValue(musicSound);
        jumpControl.setValue(sfxSound);
        clickControl.setValue(sfxSound);
        portalControl.setValue(sfxSound);
        buttonPressControl.setValue(sfxSound);
    }

    public void draw(Graphics2D g) {
        g.drawImage(settings, 0, 0, 1500, 900, null);
        g.setColor(Color.GRAY);
        g.fillRect(621, 327, (int) musicSlider.getX() - 621, 9);
        g.fillRect(621, 414, (int) sfxSlider.getX() - 621, 9);
        g.drawImage(slider, (int) musicSlider.getX(), (int) musicSlider.getY(), 12, 46, null);
        g.drawImage(slider, (int) sfxSlider.getX(), (int) sfxSlider.getY(), 12, 46, null);
    }


    public void changeMusicSlider(Point p, boolean firstClick){
        if (!firstClick) {
            firstPoint = p;
            firstX = (int) musicSlider.getX() - 308;
        }
        int newX = firstX - (int) (firstPoint.getX() - p.getX());
        if (newX < 615) {
            newX = 615;
        }
        else if (newX > 935) {
            newX = 935;
        }
        musicSlider.setLocation(newX, (int) musicSlider.getY());

        if (newX == 615) {
            musicSound = -99999999.0f;
        }
        else {
            musicSound = ((newX - 775) * (float) .125) - 14;
        }
    }

    public void changeSfxSlider(Point p, boolean firstClick){
        if (!firstClick) {
            firstPoint = p;
            firstX = (int) sfxSlider.getX() - 308;
        }
        int newX = firstX - (int) (firstPoint.getX() - p.getX());
        if (newX < 615) {
            newX = 615;
        }
        else if (newX > 935) {
            newX = 935;
        }
        sfxSlider.setLocation(newX, (int) sfxSlider.getY());

        if (newX == 615) {
            sfxSound = -99999999.0f;
        }
        else {
            sfxSound = ((newX - 775) * (float) .125) - 14;
        }
    }

    public void playJump() {
        jump.setMicrosecondPosition(0);
        jump.start();
    }

    public void playClick() {
        click.setMicrosecondPosition(0);
        click.start();
    }

    public void playPortal() {
        portal.setMicrosecondPosition(0);
        portal.start();
    }

    public void playButton() {
        buttonPress.setMicrosecondPosition(0);
        buttonPress.start();
    }

    public Rectangle getSfxSlider() {
        return sfxSlider;
    }

    public Rectangle getMusicSlider() {
        return musicSlider;
    }

    public Rectangle getClose() {
        return close;
    }
}
