package ua.drozda.battlecity.fx;

import javafx.scene.image.Image;
import ua.drozda.battlecity.core.TileType;
import ua.drozda.battlecity.core.World;
import ua.drozda.battlecity.core.interfaces.LoadableCells;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GFH on 18.05.2015.
 */
public class FxWorld implements LoadableCells {
    static final Integer tileZoneX = 16 * 16;
    static final Integer steelZoneY = 4 * 8;
    static final Integer forestZoneY = 5 * 8;
    static final Integer iceZoneY = 6 * 8;
    static final Integer waterZoneY = 7 * 8;
    static final Integer brickZoneY = 8 * 8;
    static final Integer emptyZoneY = 11 * 8;


    static final Integer tileSize = 8;
    private static InputStream spritesStream = FxWorld.class.getResourceAsStream("../images/sprites.png");
    static Image sprites = new Image(spritesStream);
    List<FxCell> cellList = new ArrayList<>();
    private World world;

    public FxWorld() {
        this(1);
    }

    public FxWorld(Integer ratio) {
        world = new World(ratio);
        world.initializeWorld();
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public Boolean addCell(Integer x, Integer y, TileType tileType) {
        Boolean result = false;
        if (world.addCell(x, y, tileType)) {
            FxCell fxCell = new FxCell(x, y, world, world.getGameCells()[x][y]);
            cellList.add(fxCell);
            result = true;
        }
        return result;
    }
}
