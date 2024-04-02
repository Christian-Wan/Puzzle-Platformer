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
    private BufferedImage combined;
    private ArrayList<Rectangle> walls;
    private String[][] levelData;
    public LevelLayout(String fileName) {
        levelData = getLevelData(fileName);
        combined = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);

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
        topRightDarkness = tileset.getSubimage(208, 16, 32, 32);
        bottomLeftDarkness = tileset.getSubimage(160, 64, 32, 32);
        bottomRightDarkness = tileset.getSubimage(208, 64, 32, 32);
    }

    private void setWalls() {
        Graphics g = combined.getGraphics();
        walls = new ArrayList<Rectangle>();
        //make sure to put the things that won't be counted as walls here
        String nonWallTiles = "";
        for (int r = 0; r < 1; r++) {
            for (int c = 0; c < 1; c++) {
                if (!nonWallTiles.contains(levelData[r][c])) {
                    //make sure that this has the right values
                    walls.add(new Rectangle());
                }
                switch (levelData[r][c]) {
                    case "0":
                        g.drawImage(topLeftCorner, r * 64, c * 64, 64, 64, null);
                        break;
                    case "1":
                        g.drawImage(upWall, r * 64, c * 64, 64, 64, null);
                        break;
                    case "2":
                        g.drawImage(topRightCorner, r * 64, c * 64, 64, 64, null);
                        break;
                    case "3":
                        g.drawImage(leftWall, r * 64, c * 64, 64, 64, null);
                        break;
                    case "4":
                        g.drawImage(darkness, r * 64, c * 64, 64, 64, null);
                        break;
                    case "5":
                        g.drawImage(rightWall, r * 64, c * 64, 64, 64, null);
                        break;
                    case "6":
                        g.drawImage(bottomLeftCorner, r * 64, c * 64, 64, 64, null);
                        break;
                    case "7":
                        g.drawImage(downWall, r * 64, c * 64, 64, 64, null);
                        break;
                    case "8":
                        g.drawImage(bottomRightCorner, r * 64, c * 64, 64, 64, null);
                        break;
                    case "q":
                        g.drawImage(topLeftDarkness, r * 64, c * 64, 64, 64, null);
                        break;
                    case "w":
                        g.drawImage(topRightDarkness, r * 64, c * 64, 64, 64, null);
                        break;
                    case "a":
                        g.drawImage(topLeftDarkness, r * 64, c * 64, 64, 64, null);
                        break;
                    case "s":
                        g.drawImage(topRightDarkness, r * 64, c * 64, 64, 64, null);
                        break;
                }
            }
        }
        try {
            ImageIO.write(combined, "PNG", new File("image/test.png"));
        } catch (IOException e) {
            System.out.println("fail");
        }
    }
    private String[][] getLevelData(String fileName) {
        String[][] data = new String[1][1];
        File f = new File("level_data/" + fileName);
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
        g.drawImage(combined, 0, 0, null);

    }
}
