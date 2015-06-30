package ua.drozda.battlecity.fx;

import javafx.scene.image.Image;
import ua.drozda.battlecity.core.World;
import ua.drozda.battlecity.core.interfaces.Togglable;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GFH on 18.05.2015.
 */
public class FxWorld implements Togglable {

    static final Integer tileSize = 16;
    static final Integer tankSize = 32;

    static final Integer bulletExplosion = 32;
    static final Integer tankExplosion = 64;
    static final Integer baseExplosion = 64;


    static final Integer tileZoneX = 28 * 32;
    static final Integer firstPlayerZoneX = 20 * tankSize;
    static final Integer firstPlayerZoneY = 0 * tankSize;

    static final Integer brickZoneY = 0 * tileSize;
    static final Integer steelZoneY = 1 * tileSize;
    static final Integer waterZoneY = 2 * tileSize;
    static final Integer forestZoneY = 3 * tileSize;
    static final Integer iceZoneY = 4 * tileSize;

    static final Integer emptyZoneY = 11 * tileSize;
    static final Integer explozionZoneY = 17 * tileSize;


    // private static InputStream spritesStream = FxWorld.class.getResourceAsStream("../images/sprites.png");
    private static InputStream spritesStream = FxWorld.class.getResourceAsStream("../images/graphics_debug.png");
    static Image sprites = new Image(spritesStream);
    List<FxGameUnit> fxGameUnitsList = new ArrayList<>();
    // private FxTank firstPlayerTank;
    private World world;

    public FxWorld() {
    }


    //   public FxTank getFirstPlayerTank() {
//        return firstPlayerTank;
//    }

    public void handleCollisions() {

    }    //    public void setFirstPlayerTank(FxTank firstPlayerTank) {

    @Override
    public void doToggle(Long now) {
        for (FxGameUnit fxGameUnit : fxGameUnitsList) {
            fxGameUnit.toggle(now);
        }
    }//        this.firstPlayerTank = firstPlayerTank;

    //    }
//
    public World getWorld() {
        return world;
    }


    public void setWorld(World world) {
        this.world = world;

        getWorld().getUnitList().forEach(u -> {
            fxGameUnitsList.add(FxGameUnit.createFxGameUnit(u));
        });
    }


}
