import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LevelLayout {

    private BufferedImage topLeftCorner, topRightCorner, bottomLeftCorner, bottomRightCorner, upWall, leftWall, rightWall, downWall, darkness, topLeftDarkness, topRightDarkness, bottomLeftDarkness, bottomRightDarkness;
    private ArrayList<Rectangle> walls;
    private String[][] levelData;
    public LevelLayout(String fileName) {
        levelData = getLevelData(fileName);
        setTileSet();
        setWalls();
    }

    private void setTileSet() {
        BufferedImage tileset = null;
        try {
            tileset = ImageIO.read(new File("image/Walls_Tileset.png"));
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
        topRightDarkness = tileset.getSubimage(208, 64, 32, 32);
        bottomLeftDarkness = tileset.getSubimage(160, 16, 32, 32);
        bottomRightDarkness = tileset.getSubimage(208, 64, 32, 32);
    }

    private void setWalls() {
        String nonWallTiles = "";
        for (int r = 0; r < 1; r++) {
            for (int c = 0; c < 1; c++) {
                if (!nonWallTiles.contains(levelData[r][c])) {
                    //make sure that this has the right values
                    walls.add(new Rectangle());
                }
            }
        }
    }
    private String[][] getLevelData(String fileName) {
        String[][] data = new String[1][1];
        File f = new File("level_data/fileName");
        Scanner s = null;
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException e) {}
        for (int c = 0; c < 1; c++) {
            String[] tiles = s.nextLine().split(" ");
            for (int r = 0; r < 1; r++) {
                data[c][r] = tiles[r];
            }
        }
        return data;
    }
    public void draw(Graphics2D g) {

    }
}
