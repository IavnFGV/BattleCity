package ua.drozda.battlecity.core.world.cells;

import ua.drozda.battlecity.core.TileType;
import ua.drozda.battlecity.core.interfaces.Togglable;

/**
 * Created by GFH on 17.05.2015.
 */
public class WaterCellState extends CellState implements Togglable<TileType> {

    @Override
    public TileType toggle(Object o) throws Exception {
        state = ++state % 3;
        return TileType.WATER;
    }
}
