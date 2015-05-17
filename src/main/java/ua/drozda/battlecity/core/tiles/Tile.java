package ua.drozda.battlecity.core.tiles;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GFH on 12.05.2015.
 */
public class Tile {

    private static Map<Class<? extends Tile>, Tile> tileMap = new HashMap<Class<? extends Tile>, Tile>(6);

    static {
        //todo make adding interactive? load class files during work
        tileMap.put(Empty.class, null);
        tileMap.put(Brick.class, null);
        tileMap.put(Steel.class, null);
        tileMap.put(Water.class, null);
        tileMap.put(Forest.class, null);
        tileMap.put(Ice.class, null);
    }

    public static Tile getTile(Class<? extends Tile> aClassTile) throws Exception {
        try {
            if (!tileMap.containsKey(aClassTile)) {
                throw new ClassNotFoundException("No such tile class in factory map. Check Tile.java");
            }
            Tile resultTile = tileMap.get(aClassTile);
            if (resultTile == null) {
                resultTile = aClassTile.newInstance();
                tileMap.put(aClassTile, resultTile);
            }
            return resultTile;
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    @Override
    public String toString() {
        return "Tile{}";
    }
}
