package ua.drozda.battlecity.core.world.cells;

import ua.drozda.battlecity.core.TileType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GFH on 17.05.2015.
 */
public class CellState {
    public static Map<TileType, Class<? extends CellState>> tileStateMap =
            new HashMap<TileType, Class<? extends CellState>>();

    static {
        tileStateMap.put(TileType.BRICK, BrickCellState.class);
        tileStateMap.put(TileType.WATER, WaterCellState.class);
        // tileStateMap.put(null, CellState.class);
    }

    protected Integer state = 0;

    public static CellState getCellState(TileType tileType) {
        Class<? extends CellState> resultClass = tileStateMap.getOrDefault(tileType, CellState.class);
        CellState result = new CellState();
        try {
            result = (CellState) resultClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
