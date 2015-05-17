package ua.drozda.battlecity.core.world;

import ua.drozda.battlecity.core.collisions.CollisionBounds;
import ua.drozda.battlecity.core.interfaces.NonStatic;
import ua.drozda.battlecity.core.tiles.Tile;

import java.util.Observable;

/**
 * Created by GFH on 13.05.2015.
 */
public class GameCell extends Observable implements NonStatic<GameCell> {
    private Integer x = 0;
    private Integer y = 0;
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

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public long getCurFrame() {
        return curFrame;
    }

    public void setCurFrame(long curFrame) {
        this.curFrame = curFrame;
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

    private Tile tile;
    private long lastUpdate;
    private long curFrame;

    public GameCell(int x, int y, Tile tile) {
        this.x = x;
        this.y = y;
        this.tile = tile;
        collisionBounds = new CollisionBounds(x, y);
    }

    public GameCell heartBeat(Object o) throws Exception {
        Object parameter = null;
        if (o != null) {
            parameter = o;
        }
        if (tile instanceof NonStatic) {
            curFrame++;
            tile = (Tile) ((NonStatic) tile).heartBeat(parameter);
            this.setChanged();
            this.notifyObservers();
            this.clearChanged();
        }
        return this;
    }


    @Override
    public String toString() {
        return "GameCell{" +
                "x=" + x +
                ", y=" + y +
                ", collisionBounds=" + collisionBounds +
                ", tile=" + tile +
                ", lastUpdate=" + lastUpdate +
                ", curFrame=" + curFrame +
                '}';
    }
}
