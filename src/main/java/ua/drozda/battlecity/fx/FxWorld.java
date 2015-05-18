package ua.drozda.battlecity.fx;

import javafx.scene.image.Image;
import ua.drozda.battlecity.core.World;

import java.io.InputStream;

/**
 * Created by GFH on 18.05.2015.
 */
public class FxWorld {
    static Integer tileZoneX = 16 * 16;
    static Integer brickZoneY = 8 * 8;
    static Integer tileSize = 8;
    private static InputStream spritesStream = FxWorld.class.getResourceAsStream("fx/images/sprites.bmp");
    static Image sprites = new Image(spritesStream);
    private World world;

    public FxWorld() {
        this(1);
    }

    public FxWorld(Integer ratio) {
        world = new World(ratio);
    }
}
