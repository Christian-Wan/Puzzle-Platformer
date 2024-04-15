public class Key extends Opener {
    //Is floating like the portal
    //Can only be obtained by player and maybe necromancer minion
    //Is key in Hashmap

    public Key(Engine engine) {
        super(engine);
    }

    public void update() {
        if (!super.isOpening()) {
            if (super.touchingPlayer(super.getEngine().getLevelLayout().getAvailableCharacters())) {
                super.setOpening(true);
            }
        }
    }
}
