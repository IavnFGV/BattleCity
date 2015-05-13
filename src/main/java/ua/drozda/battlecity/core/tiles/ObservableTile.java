package ua.drozda.battlecity.core.tiles;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by GFH on 14.05.2015.
 */
public class ObservableTile<T extends Tile, O extends Observer> extends Observable  {
    private T tile;

    public ObservableTile(T tile, O observer ) {
        this.tile = tile;
        addObserver(observer);
    }

    public T getTile() {
        return tile;
    }
}
