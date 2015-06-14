package ua.drozda.battlecity.fx;

import javafx.scene.image.Image;
import ua.drozda.battlecity.core.GameUnit;
import ua.drozda.battlecity.core.World;
import ua.drozda.battlecity.core.interfaces.Togglable;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GFH on 18.05.2015.
 */
public class FxWorld implements Togglable {

    static final Integer tileSize = 8;
    static final Integer tankSize = 16;

    static final Integer bulletExplosion = 16;
    static final Integer tankExplosion = 32;
    static final Integer baseExplosion = 32;


    static final Integer tileZoneX = 16 * 16;
    static final Integer firstPlayerZoneX = 0 * tankSize;
    static final Integer firstPlayerZoneY = 0 * tankSize;

    static final Integer steelZoneY = 4 * tileSize;
    static final Integer forestZoneY = 5 * tileSize;
    static final Integer iceZoneY = 6 * tileSize;
    static final Integer waterZoneY = 7 * tileSize;
    static final Integer brickZoneY = 8 * tileSize;
    static final Integer emptyZoneY = 11 * tileSize;
    static final Integer explozionZoneY = 17 * tileSize;


    private static InputStream spritesStream = FxWorld.class.getResourceAsStream("../images/sprites.png");
    static Image sprites = new Image(spritesStream);
    List<FxGameUnit> fxGameUnitsList = new ArrayList<>();
    // private FxTank firstPlayerTank;
    private World world;

    public FxWorld() {
    }


    //   public FxTank getFirstPlayerTank() {
//        return firstPlayerTank;
//    }

    //    public void setFirstPlayerTank(FxTank firstPlayerTank) {
//        this.firstPlayerTank = firstPlayerTank;
//    }
//
    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
        for (GameUnit gameUnit : world.getUnitList()) {
            fxGameUnitsList.add(FxGameUnit.createFxGameUnit(gameUnit));
        }


    }

    @Override
    public Object toggle(Object o) {
        for (FxGameUnit fxGameUnit : fxGameUnitsList) {
            fxGameUnit.toggle(null);
        }
        return null;
    }
}
