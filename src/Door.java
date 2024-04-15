public class Door {
    //Is going to be the value in the Hashmap
    //When Key/Button is true Door opens and the door opens
    //When key is picked up the door stays open
    //When Button is pressed the Door opens, when you get off the door closes

    private int number;
    public Door(Engine engine, String doorNumber) {
        number = Integer.parseInt(doorNumber.substring(1));
    }

    public int getNumber() {
        return number;
    }

    public void update() {

    }

    public void draw() {

    }
}
