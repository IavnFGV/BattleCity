package ua.drozda.battlecity.core.world;

import ua.drozda.battlecity.core.World;
import ua.drozda.battlecity.core.interfaces.Updatable;
import ua.drozda.battlecity.core.tiles.Tile;

import java.util.Observable;

/**
 * Created by GFH on 13.05.2015.
 */
public class GameCell extends Observable implements Updatable<GameCell> {
    private int x = 0;
    private int y = 0;
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

    public GameCell update(Object... args) throws Exception {
        if (tile instanceof Updatable) {
            curFrame++;
            lastUpdate = (Long) args[0];
            tile = (Tile) ((Updatable) tile).update(args);
            this.setChanged();
            this.notifyObservers();
            this.clearChanged();
        }
        return this;
    }

    public static class CollisionBounds {
        private int xF;//Floor
        private int yF;
        private int xU;//Up
        private int yU;

        public CollisionBounds(int x, int y) {
            this.xF = x * World.CELL_WIDTH;
            this.yF = yF * World.CELL_HEIGH;
            this.xU = xF + World.CELL_WIDTH;
            this.yU = yF + World.CELL_HEIGH;
        }

        public int getxF() {
            return xF;
        }

        public void setxF(int xF) {
            this.xF = xF;
        }

        public int getyF() {
            return yF;
        }

        public void setyF(int yF) {
            this.yF = yF;
        }

        public int getxU() {
            return xU;
        }

        public void setxU(int xU) {
            this.xU = xU;
        }

        public int getyU() {
            return yU;
        }

        public void setyU(int yU) {
            this.yU = yU;
        }

        @Override
        public String toString() {
            return "CollisionBounds{" +
                    "xF=" + xF +
                    ", yF=" + yF +
                    ", xU=" + xU +
                    ", yU=" + yU +
                    '}';
        }
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
