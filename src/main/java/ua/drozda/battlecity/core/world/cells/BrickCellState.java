package ua.drozda.battlecity.core.world.cells;

import ua.drozda.battlecity.core.TileType;
import ua.drozda.battlecity.core.actors.Bullet;
import ua.drozda.battlecity.core.interfaces.Collisionable;

/**
 * Created by GFH on 17.05.2015.
 */
public class BrickCellState extends CellState implements Collisionable<Bullet, TileType> {
    @Override
    public TileType collise(Bullet other) throws Exception {
        return TileType.EMPTY;
    }
}
