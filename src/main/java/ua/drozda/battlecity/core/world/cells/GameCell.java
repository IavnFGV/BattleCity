package ua.drozda.battlecity.core.world.cells;

import ua.drozda.battlecity.core.collisions.CollisionBounds;
import ua.drozda.battlecity.core.interfaces.Togglable;
import ua.drozda.battlecity.core.tiles.Tile;

import java.util.Observable;

/**
 * Created by GFH on 13.05.2015.
 */
public class GameCell extends Observable implements Togglable<GameCell> {
    private Integer x = 0;
    private Integer y = 0;
    private Long toggleTick = 0l;
    private Long lastUpdate = 0l;
    private CellState state;
    private Tile tile;

    private CollisionBounds collisionBounds;

    public CollisionBounds getCollisionBounds() {
        return collisionBounds;
    }

    public void setCollisionBounds(CollisionBounds collisionBounds) {
        this.collisionBounds = collisionBounds;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Long getLastUpdate() {
        return lastUpdate;
    }


    public void setLastUpdate(Long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public GameCell(int x, int y, Tile tile) {
        this.x = x;
        this.y = y;
        this.tile = tile;
        collisionBounds = new CollisionBounds(x, y);
        state = CellState.getCellState(tile.getClass());// todo shit
    }

    public GameCell toggle(Object o) throws Exception {
        Object parameter = null;
        if (o != null) {
            parameter = o;
        }
        if (state instanceof Togglable) {
            toggleTick++;
            tile = (Tile) ((Togglable) state).toggle(parameter); // todo maybe check changed of State??
            this.setChanged();
            this.notifyObservers();
        }
        return this;
    }


}
