package ua.drozda.battlecity.core.tiles;

import ua.drozda.battlecity.core.interfaces.NonStatic;

/**
 * Created by GFH on 12.05.2015.
 */
public class Water extends Tile implements NonStatic<Tile> {


    public Water(Integer tileState) {
        super(tileState);
    }

    public static int getMaxState() {
        return 2;
    }

    @Override
    public String toString() {
        return "Water{}+super = " +
                super.toString();
    }

    public Tile heartBeat() throws Exception {
        return getTile(this.getClass(), (tileState + 1) % getMaxState());
    }
}
