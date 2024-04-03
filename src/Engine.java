public class Engine {
    private Player player;
    private Portal portal;
    private LevelLayout levelLayout;
    private PlayPanel playPanel;
    final private int SCALE = 2;

    public Engine(PlayPanel playPanel) {
        this.playPanel = playPanel;
        this.player = new Player(this);
        this.portal = new Portal(this);
        this.levelLayout = new LevelLayout(this);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Portal getPortal() {
        return portal;
    }

    public void setPortal(Portal portal) {
        this.portal = portal;
    }

    public LevelLayout getLevelLayout() {
        return levelLayout;
    }

    public void setLevelLayout(LevelLayout levelLayout) {
        this.levelLayout = levelLayout;
    }

    public PlayPanel getPlayPanel() {
        return playPanel;
    }

    public void setPlayPanel(PlayPanel playPanel) {
        this.playPanel = playPanel;
    }

    public int getSCALE() {
        return SCALE;
    }
}
