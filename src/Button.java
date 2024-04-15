public class Button extends Opener {

    //Can only be placed on the ground
    //Player/Box/Necromancer can press button
    //Button is a key is hashmap
    public Button(Engine engine, String buttonNumber) {
        super(engine, buttonNumber);
        switch(super.getNumber()) {
            case 1:
                //set the sprite here
                break;
        }
    }
}
