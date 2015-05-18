package ua.drozda.battlecity.fx;

import javafx.geometry.Rectangle2D;
import ua.drozda.battlecity.core.tiles.Brick;
import ua.drozda.battlecity.core.tiles.Tile;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by GFH on 18.05.2015.
 */
public class FxCell implements Observer {

    private static HashMap<Class<? extends Tile>, Rectangle2D[]> spritesMap = new HashMap();

    static {
        Rectangle2D[] rectangle2Ds = new Rectangle2D[5];
        for (int i = 0; i < 5; i++) {
            rectangle2Ds[i] = new Rectangle2D(FxWorld.tileZoneX, FxWorld.brickZoneY + FxWorld.tileSize * i, 8, 8);
        }
        spritesMap.put(Brick.class, rectangle2Ds);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
