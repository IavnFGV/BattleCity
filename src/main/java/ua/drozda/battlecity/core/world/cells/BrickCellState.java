package ua.drozda.battlecity.core.world.cells;

import ua.drozda.battlecity.core.actors.Bullet;
import ua.drozda.battlecity.core.interfaces.Collisionable;
import ua.drozda.battlecity.core.tiles.Empty;
import ua.drozda.battlecity.core.tiles.Tile;

/**
 * Created by GFH on 17.05.2015.
 */
public class BrickCellState extends CellState implements Collisionable<Bullet, Tile> {
    @Override
    public Tile collise(Bullet other) throws Exception {
        return Tile.getTile(Empty.class);
    }
}
