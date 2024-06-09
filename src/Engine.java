import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Engine {
    private Player player;
    private Wizard wizard;
    private Knight knight;
    private Necromancer necromancer;
    private Portal portal;
    private LevelLayout levelLayout;
    private PlayBackground playBackground;
    private Frame frame;
    private PlayPanel playPanel;
    private MainMenuPanel mainMenuPanel;
    private LevelSelectionPanel levelSelectionPanel;
    private Transitions transitions;
    private SoundControl soundControl;
    final private int SCALE = 2;

    public Engine(Frame frame) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        transitions = new Transitions(this);
        this.frame = frame;
        this.playBackground = new PlayBackground();
        this.player = new Player(this);
        this.portal = new Portal(this);
        this.levelLayout = new LevelLayout(this);
        soundControl = new SoundControl();
    }

    public void makePanels() {
        playPanel = new PlayPanel(frame);
        mainMenuPanel = new MainMenuPanel(frame);
        levelSelectionPanel = new LevelSelectionPanel(frame);
    }

    public void newLevelLayout(int level) {
        levelLayout = new LevelLayout(this, "level" + level);
    }

    public void newPlayer(int x, int y) {
        player = new Player(this, x, y);
        playPanel.addKeyListener(player);
    }
    public void newWizard(int x, int y) {
        wizard = new Wizard(this, x, y);
        playPanel.addKeyListener(wizard);
    }

    public void newKnight(int x, int y) {
        knight = new Knight(this, x, y);
        playPanel.addKeyListener(knight);
    }

    public void newNecromancer(int x, int y) {
        necromancer = new Necromancer(this, x, y);
        playPanel.addKeyListener(necromancer);
    }


    public Necromancer getNecromancer() {
        return necromancer;
    }

    public Player getPlayer() {
        return player;
    }

    public Portal getPortal() {
        return portal;
    }

    public LevelLayout getLevelLayout() {
        return levelLayout;
    }

    public PlayBackground getPlayBackground() {
        return playBackground;
    }

    public Transitions getTransitions() {
        return transitions;
    }

    public PlayPanel getPlayPanel() {
        return playPanel;
    }

    public int getSCALE() {
        return SCALE;
    }

    public Frame getFrame() {
        return frame;
    }

    public MainMenuPanel getMainMenuPanel() {
        return mainMenuPanel;
    }

    public LevelSelectionPanel getLevelSelectionPanel() {
        return levelSelectionPanel;
    }

    public Wizard getWizard() {
        return wizard;
    }

    public Knight getKnight() {
        return knight;
    }

    public SoundControl getSoundControl() {
        return soundControl;
    }

}
