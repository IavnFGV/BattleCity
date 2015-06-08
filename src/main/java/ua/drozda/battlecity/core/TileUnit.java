package ua.drozda.battlecity.core;

/**
 * Created by GFH on 09.06.2015.
 */
public class TileUnit extends GameUnit {
    private TileType tileType;

    public TileUnit(double x, double y, double width, double height, Long lives, Long currentTime, TileType tileType) {
        super(x, y, width, height, lives, currentTime, BasicState.ACTIVE);
        this.tileType = tileType;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public enum TileType {
        BRICK, EMPTY, FOREST, ICE, STEEL, WATER
    }
}
