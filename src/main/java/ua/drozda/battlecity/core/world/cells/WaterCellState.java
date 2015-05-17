package ua.drozda.battlecity.core.world.cells;

import ua.drozda.battlecity.core.interfaces.Togglable;
import ua.drozda.battlecity.core.tiles.Tile;
import ua.drozda.battlecity.core.tiles.Water;

/**
 * Created by GFH on 17.05.2015.
 */
public class WaterCellState extends CellState implements Togglable<Tile> {


    @Override
    public Tile toggle(Object o) throws Exception {
        state = (state == 0) ? 1 : 0;
        return Tile.getTile(Water.class);
    }
}
