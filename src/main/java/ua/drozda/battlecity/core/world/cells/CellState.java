package ua.drozda.battlecity.core.world.cells;

import ua.drozda.battlecity.core.tiles.Brick;
import ua.drozda.battlecity.core.tiles.Tile;
import ua.drozda.battlecity.core.tiles.Water;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GFH on 17.05.2015.
 */
public class CellState {
    public static Map<Class<? extends Tile>, Class<? extends CellState>> tileStateMap =
            new HashMap<Class<? extends Tile>, Class<? extends CellState>>();

    static {
        tileStateMap.put(Brick.class, BrickCellState.class);
        tileStateMap.put(Water.class, WaterCellState.class);
        // tileStateMap.put(null, CellState.class);
    }

    protected Integer state;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public static CellState getCellState(Class<? extends Tile> aClassTile) {
        Class<? extends CellState> resultClass = tileStateMap.getOrDefault(aClassTile, CellState.class);
        try {
            return (CellState) resultClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return new CellState(); // todo SHIT!!
    }
}
