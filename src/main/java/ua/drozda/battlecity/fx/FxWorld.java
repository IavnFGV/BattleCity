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

    static final Integer tileSize = 8;
    static final Integer tankSize = 16;

    static final Integer tileZoneX = 16 * 16;
    static final Integer firstPlayerZoneX = 0 * tankSize;
    static final Integer firstPlayerZoneY = 0 * tankSize;

    static final Integer steelZoneY = 4 * tileSize;
    static final Integer forestZoneY = 5 * tileSize;
    static final Integer iceZoneY = 6 * tileSize;
    static final Integer waterZoneY = 7 * tileSize;
    static final Integer brickZoneY = 8 * tileSize;
    static final Integer emptyZoneY = 11 * tileSize;


    private static InputStream spritesStream = FxWorld.class.getResourceAsStream("../images/sprites.png");
    static Image sprites = new Image(spritesStream);
    List<FxCell> cellList = new ArrayList<>();
    private FxTank firstPlayerTank;
    private World world;

    public FxWorld() {
        this(1);
    }

    public FxWorld(Integer ratio) {
        world = new World(ratio);
        world.initializeWorld();
        firstPlayerTank = new FxTank(world.getPlayerTanks().get(0));
    }

    public FxTank getFirstPlayerTank() {
        return firstPlayerTank;
    }

    public void setFirstPlayerTank(FxTank firstPlayerTank) {
        this.firstPlayerTank = firstPlayerTank;
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
            FxCell fxCell = new FxCell(x, y, world.getGameCells()[x][y]);
            cellList.add(fxCell);
            result = true;
        }
        return result;
    }
}
