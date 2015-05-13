package ua.drozda.battlecity.core.tiles;


/**
 * Created by GFH on 12.05.2015.
 */
public class Brick extends Tile {
    public Brick(int tileState) {
        super(tileState);
    }

    public static int getMaxState() {
        return 16;
    }
}
