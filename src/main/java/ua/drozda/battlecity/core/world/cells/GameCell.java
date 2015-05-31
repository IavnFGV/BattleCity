package ua.drozda.battlecity.core.world.cells;

import ua.drozda.battlecity.core.TileType;
import ua.drozda.battlecity.core.collisions.CollisionBounds;
import ua.drozda.battlecity.core.interfaces.Togglable;

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
    private TileType tile;
    //  private World world;
    private CollisionBounds collisionBounds;

    public GameCell(int x, int y, TileType tile) {
        this.x = x;
        this.y = y;
        this.tile = tile;
        state = CellState.getCellState(tile);
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

//    public World getWorld() {
//        return world;
//    }
//
//    public void setWorld(World world) {
//        this.world = world;
//    }

    public CollisionBounds getCollisionBounds() {
        return collisionBounds;
    }

    public void setCollisionBounds(CollisionBounds collisionBounds) {
        this.collisionBounds = collisionBounds;
    }

    public TileType getTile() {
        return tile;
    }

    public void setTile(TileType tile) {
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

    public GameCell toggle(Object o) throws Exception {
        Object parameter = null;
        if (o != null) {
            parameter = o;
        }
        if (state instanceof Togglable) {
            toggleTick++;
            tile = (TileType) ((Togglable) state).toggle(parameter); // todo maybe check changed of State??
            this.setChanged();
            this.notifyObservers();
        }
        return this;
    }


}
