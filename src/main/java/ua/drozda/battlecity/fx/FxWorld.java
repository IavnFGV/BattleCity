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

    public static final Integer tileSize = 16;
    public static final Integer tankSize = 32;

    public static final Integer bulletExplosion = 32;
    public static final Integer tankExplosion = 64;
    public static final Integer baseExplosion = 64;


    public static final Integer tileZoneX = 28 * 32;
    public static final Integer firstPlayerZoneX = 20 * tankSize;
    public static final Integer firstPlayerZoneY = 0 * tankSize;
    public static final Integer secondPlayerZoneX = 24 * tankSize;
    public static final Integer secondPlayerZoneY = 0 * tankSize;

    public static final Integer borderZoneX = 15 * tankSize;
    public static final Integer borderZoneY = 8 * tankSize;

    public static final Integer brickZoneY = 0 * tileSize;
    public static final Integer steelZoneY = 1 * tileSize;
    public static final Integer waterZoneY = 2 * tileSize;
    public static final Integer forestZoneY = 3 * tileSize;
    public static final Integer iceZoneY = 4 * tileSize;

    public static final Integer emptyZoneY = 11 * tileSize;
    public static final Integer explozionZoneY = 17 * tileSize;


    //     private static InputStream spritesStream = FxWorld.class.getResourceAsStream("../images/sprites.png");
    private static InputStream spritesStream = FxWorld.class.getResourceAsStream("../images/graphics_debug.png");

    //    private static InputStream spritesStream = FxWorld.class.getResourceAsStream("../images/graphics.png");
    public static volatile Image sprites = new Image(spritesStream);
    List<FxGameUnit> fxGameUnitsList = new ArrayList<>();
    // private FxTank firstPlayerTank;
    private World world;

    public FxWorld() {
    }


    //   public FxTank getFirstPlayerTank() {
//        return firstPlayerTank;
//    }

    public void handleCollisions() {

    }

    @Override
    public void doToggle(Long now) {
        fxGameUnitsList.forEach(u -> u.toggle(now));
    }

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
