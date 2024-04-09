import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Knight extends Player {
    public Knight (Engine engine) {
        super(engine);
    }

    public Knight(Engine engine, int x, int y) {
        super(engine, x, y);
        try {
            super.setStandR(ImageIO.read(new File("image/Knight/Knight_StandR.png")));
            super.setStandL(ImageIO.read(new File("image/Knight/Knight_StandL.png")));
            super.setWalkR(ImageIO.read(new File("image/Knight/Knight_WalkR.png")));
            super.setWalkL(ImageIO.read(new File("image/Knight/Knight_WalkL.png")));
        } catch (IOException e) {}
    }

    public void update() {
        super.update();
        if (super.getConnectedBox() != null) {
            if (super.isFacingRight()) {
                super.getConnectedBox().changeX(2);
            }
            else if (super.isFacingLeft()) {
                super.getConnectedBox().changeX(-2);
            }
        }
    }
}
