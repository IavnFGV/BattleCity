package ua.drozda.battlecity.core.tiles;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by GFH on 14.05.2015.
 */
public class ObservableTile<T extends Tile> extends Observable {
    private T tile;
    private long lastUpdate;
    private long curFrame;

    public ObservableTile(T tile, Observer observer) {
        super();
        this.tile = tile;
        addObserver(observer);
    }

    public ObservableTile(T tile) {
        this.tile = tile;
    }

    public T getTile() {
        return tile;
    }
}
