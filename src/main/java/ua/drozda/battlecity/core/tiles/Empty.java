package ua.drozda.battlecity.core.tiles;

/**
 * Created by GFH on 12.05.2015.
 */
public class Empty extends Tile {
    public Empty(Integer tileState) {
        super(tileState);
    }

    @Override
    public String toString() {
        return "Empty{} " + super.toString();
    }
}
