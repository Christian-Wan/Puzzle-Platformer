public class Key extends Opener {
    //Is floating like the portal
    //Can only be obtained by player and maybe necromancer minion
    //Is key in Hashmap

    public Key(Engine engine, String keyNumber) {
        super(engine, keyNumber);
        //if there are multiple keys they have to be different colours so that the player knows which doors they open
        switch(super.getNumber()) {
            case 1:
                //set the sprite here
                break;
        }
    }

    public void update() {
        if (!super.isOpening()) {
            if (super.touchingPlayer(super.getEngine().getLevelLayout().getAvailableCharacters())) {
                super.setOpening(true);
            }
        }
    }
}
