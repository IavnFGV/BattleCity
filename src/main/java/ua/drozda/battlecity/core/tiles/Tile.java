package ua.drozda.battlecity.core.tiles;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

/**
 * Created by GFH on 12.05.2015.
 */
public class Tile {

    private static Map<Class<? extends Tile>, Tile[]> tileMap = new HashMap<Class<? extends Tile>, Tile[]>(6);

    public Tile(int tileState) {
        this.tileState = tileState;
    }

    static {
        tileMap.put(Empty.class, null);
        tileMap.put(Brick.class, null);
        tileMap.put(Steel.class, null);
        tileMap.put(Water.class, null);
        tileMap.put(Forest.class, null);
        tileMap.put(Ice.class, null);
    }

    protected int tileState;


    public static int getMaxState() {
        return 1;
    }

    public int getTileState() {
        return tileState;
    }

    public void setTileState(int tileState) {
        this.tileState = tileState;
    }

    public static Tile getTile(Class<? extends Tile> aClassTile, int tileState, Observer observer) throws Exception {
        try {
            if (!tileMap.containsKey(aClassTile)) {
                throw new ClassNotFoundException("No such tile class in factory map. Check Tile.java");
            }

            Tile[] resultArray = tileMap.get(aClassTile);
            if (resultArray == null) {
                Method getMax = aClassTile.getMethod("getMaxState");
                int getMaxResult = (Integer) getMax.invoke(null);
                tileMap.put(aClassTile, (Tile[]) Array.newInstance(aClassTile, getMaxResult));
                Constructor constructor = aClassTile.getConstructor(new Class[]{Integer.class});
                for (int i = 0; i < getMaxResult; i++) {
                    Array.set(tileMap.get(aClassTile), i, constructor.newInstance(i));
                }
            }
            Tile resultTile = resultArray[tileState];
            return resultTile;
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }
}
