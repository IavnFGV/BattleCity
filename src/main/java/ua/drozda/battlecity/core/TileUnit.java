package ua.drozda.battlecity.core;

import java.util.function.Function;

/**
 * Created by GFH on 09.06.2015.
 */
public class TileUnit extends GameUnit {
    private TileType tileType;

    public TileUnit(double x, double y, double width, double height, Integer lives, Long currentTime, TileType tileType,
                    Function<GameUnit, Boolean> registerAction, Function<GameUnit, Boolean> unRegisterAction) {
        super(x, y, width, height, lives, BasicState.ACTIVE, registerAction, unRegisterAction, true);
        this.setTileType(tileType);
    }

    @Override
    public String toString() {
        return "TileUnit{" +
                "tileType=" + tileType +
                "} " + super.toString();
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
