import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;

public class LevelLayout {

    private BufferedImage topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner, upWall, leftWall, rightWall, downWall, darkness, topLeftDarkness, topRightDarkness, bottomLeftDarkness, bottomRightDarkness;
    private BufferedImage combined;
    private ArrayList<Rectangle> walls;
    private String[][] levelData;
    private Engine engine;
    private ArrayList<Player> availableCharacters;
    private boolean levelDone, swapped, resetting;
    private int characterInControl;
    private ArrayList<Box> boxes;

    //Have like a list of all the characters that the player can control and when a character reaches the portal remove them from the list

    public LevelLayout(Engine engine, String fileName) {
        this.engine = engine;
        characterInControl = 0;
        availableCharacters = new ArrayList<Player>();
        levelData = getLevelData(fileName);
        levelDone = false;
        characterInControl = 0;
        swapped = false;
        combined = new BufferedImage(1500, 900, BufferedImage.TYPE_INT_ARGB);
        setTileSet();
        setWalls();
    }

    public LevelLayout(Engine engine) {
        this.engine = engine;
    }

    private void setTileSet() {
        BufferedImage tileset = null;
        try {
            tileset = ImageIO.read(new File("image/Level_Assets/Walls_Tileset.png"));
        } catch (IOException e) {}
        topLeftCorner = tileset.getSubimage(16, 16, 32, 32);
        upWall = tileset.getSubimage(64, 16, 32, 32);
        topRightCorner = tileset.getSubimage(112, 16, 32, 32);
        leftWall = tileset.getSubimage(16, 64, 32, 32);
        darkness = tileset.getSubimage(64, 64, 32, 32);
        rightWall = tileset.getSubimage(112, 64, 32, 32);
        bottomLeftCorner = tileset.getSubimage(16, 112, 32, 32);
        downWall = tileset.getSubimage(64, 112, 32, 32);
        bottomRightCorner = tileset.getSubimage(112, 112, 32, 32);
        topLeftDarkness = tileset.getSubimage(160, 16, 32, 32);
        topRightDarkness = tileset.getSubimage(208, 16, 32, 32);
        bottomLeftDarkness = tileset.getSubimage(160, 64, 32, 32);
        bottomRightDarkness = tileset.getSubimage(208, 64, 32, 32);
    }

    public void setWalls() {
        Graphics g = combined.getGraphics();
        walls = new ArrayList<Rectangle>();
        //make sure to put the things that won't be counted as walls here
        String nonWallTiles = "pzekb";
        for (int r = 0; r < 12; r++) {
            for (int c = 0; c < 20; c++) {
                if (!nonWallTiles.contains(levelData[r][c])) {
                    //make sure that this has the right values
                    walls.add(new Rectangle(c * 76, r * 76, 76, 76));
                }
                switch (levelData[r][c]) {
                    case "0":
                        g.drawImage(topLeftCorner, c * 76, r * 76, 76, 76, null);
                        break;
                    case "1":
                        g.drawImage(upWall, c * 76, r * 76, 76, 76, null);
                        break;
                    case "2":
                        g.drawImage(topRightCorner, c * 76, r * 76, 76, 76, null);
                        break;
                    case "3":
                        g.drawImage(leftWall, c * 76, r * 76, 76, 76, null);
                        break;
                    case "4":
                        g.drawImage(darkness, c * 76, r * 76, 76, 76, null);
                        break;
                    case "5":
                        g.drawImage(rightWall, c * 76, r * 76, 76, 76, null);
                        break;
                    case "6":
                        g.drawImage(bottomLeftCorner, c * 76, r * 76, 76, 76, null);
                        break;
                    case "7":
                        g.drawImage(downWall, c * 76, r * 76, 76, 76, null);
                        break;
                    case "8":
                        g.drawImage(bottomRightCorner, c * 76, r * 76, 76, 76, null);
                        break;
                    case "q":
                        g.drawImage(topLeftDarkness, c * 76, r * 76, 76, 76, null);
                        break;
                    case "w":
                        g.drawImage(topRightDarkness, c * 76, r * 76, 76, 76, null);
                        break;
                    case "a":
                        g.drawImage(bottomLeftDarkness, c * 76, r * 76, 76, 76, null);
                        break;
                    case "s":
                        g.drawImage(bottomRightDarkness, c * 76, r * 76, 76, 76, null);
                        break;
                    case "p":
                        engine.newWizard(c * 76 + 14, r * 76 + 32);
                        availableCharacters.add(engine.getWizard());
                        break;
                    case "e":
                        engine.getPortal().setX(c * 76 + 16);
                        engine.getPortal().setY(r * 76 + 10);
                        break;
                    case "k":
                        engine.newKnight(c * 76 + 14, r * 76 + 32);
                        availableCharacters.add(engine.getKnight());
                        break;
                    case "b":
                        boxes.add(new Box(engine, c * 76 + 16, r * 76 + 32));
                }
            }
        }
        System.out.println(walls.size());
        try {
            ImageIO.write(combined, "PNG", new File("image/test.png"));
        } catch (IOException e) {
            System.out.println("fail");
        }
        availableCharacters.get(0).setActive(true);
    }
    private String[][] getLevelData(String fileName) {
        String[][] data = new String[12][20];
        File f = new File("level_data/" + fileName);
        Scanner s = null;
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException e) {}
        for (int c = 0; c < 12; c++) {
            String[] tiles = s.nextLine().split(" ");
            for (int r = 0; r < 20; r++) {
                data[c][r] = tiles[r];
            }
        }
        return data;
    }

    public void resetStage() {
        int counter = 0;
        String characters = "pk";
        for (int r = 0; r < 12; r++) {
            for (int c = 0; c < 12; c++) {
                if (characters.contains(levelData[r][c])) {
                    availableCharacters.get(counter).setX(c * 76 + 14);
                    availableCharacters.get(counter).setY(r * 76 + 32);
                    availableCharacters.get(counter).setAvailable(true);
                    resetting = true;
                    counter++;
                }
            }
        }
    }

    public void checkLevelDone() {
        boolean check = true;
        for (Player character: availableCharacters) {
            if (character.isAvailable()) {
                check = false;
            }
        }
        levelDone = check;
    }

    public void changeActive() {
        if (!swapped && !levelDone) {
            int current = characterInControl + 1;
            while (current != characterInControl) {
                if (current == availableCharacters.size()) {
                    current = -1;
                }
                else if(availableCharacters.get(current).isAvailable()) {
                    availableCharacters.get(characterInControl).setActive(false);
                    availableCharacters.get(current).setActive(true);
                    characterInControl = current;
                    swapped = true;
                    break;
                }
                current++;
                System.out.println("current: " + current);
                System.out.println(characterInControl);
            }
        }
    }

    public void draw(Graphics2D g) {
        engine.getPlayBackground().draw(g);
        g.drawImage(combined, 0, 0, null);
        g.setColor(Color.BLUE);
        for (int i = 0; i < walls.size(); i++) {
            g.drawRect((int) walls.get(i).getX(), (int) walls.get(i).getY(), (int) walls.get(i).getWidth(), (int) walls.get(i).getHeight());
        }
        engine.getPortal().draw(g);
        for (Player character: availableCharacters) {
            if (character.isAvailable()) {
                character.draw(g);
            }
        }
//        System.out.println("QWE");
    }

    public void update() {
        if (!levelDone) {
            for (Player character : availableCharacters) {
                if (character.isAvailable()) {
                    character.update();
                }
            }
            //not really sure why this is needed. For some reason it needs two separate updates to actually bring back the player
            if (resetting) {
                resetStage();
                resetting = false;
            }
            engine.getPortal().update();
//        System.out.println("LKJ");
        }
        else {
            engine.getTransitions().setDesiredLocation("Level Select");
            engine.getTransitions().setIn(true);
        }
        swapped = false;
    }

    public ArrayList<Rectangle> getWalls() {
        return walls;
    }

    public ArrayList<Player> getAvailableCharacters() {
        return availableCharacters;
    }
}
