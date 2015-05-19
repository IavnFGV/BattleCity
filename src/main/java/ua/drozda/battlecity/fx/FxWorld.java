package ua.drozda.battlecity.fx;

import javafx.scene.image.Image;
import ua.drozda.battlecity.core.World;

import java.io.InputStream;

/**
 * Created by GFH on 18.05.2015.
 */
public class FxWorld {
    static final Integer tileZoneX = 16 * 16;
    static final Integer steelZoneY = 4 * 8;
    static final Integer forestZoneY = 5 * 8;
    static final Integer iceZoneY = 6 * 8;
    static final Integer waterZoneY = 7 * 8;
    static final Integer brickZoneY = 8 * 8;

    static final Integer tileSize = 8;
    private static InputStream spritesStream = FxWorld.class.getResourceAsStream("../images/sprites.png");
    static Image sprites = new Image(spritesStream);
    private World world;

    public FxWorld() {
        this(1);
    }

    public FxWorld(Integer ratio) {
        world = new World(ratio);
    }
}
