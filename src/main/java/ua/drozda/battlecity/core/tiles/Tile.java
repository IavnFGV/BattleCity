package ua.drozda.battlecity.core.tiles;

import javafx.scene.image.Image;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GFH on 12.05.2015.
 */
public class Tile {

    private static Map<Class<? extends Tile>, Tile> tileMap = new HashMap<Class<? extends Tile>, Tile>(6);

    static {
        tileMap.put(Empty.class, null);
        tileMap.put(Brick.class, null);
        tileMap.put(Steel.class, null);
        tileMap.put(Water.class, null);
        tileMap.put(Forest.class, null);
        tileMap.put(Ice.class, null);
    }

    protected Image curSprite;

    public Tile(Image curSprite) {
        this.curSprite = curSprite;
    }

    public static Tile createTile(Class<? extends Tile> aClassTile, Image image) throws Exception {
        try {
            if (!tileMap.containsKey(aClassTile)) {
                throw new ClassNotFoundException("No such tile class in factory map. Check Tile.java");
            }
            Tile result = tileMap.get(aClassTile);
            if (result == null) {
                Constructor constructor = aClassTile.getConstructor(new Class[]{Image.class});
                result = (Tile) constructor.newInstance(image);
                tileMap.put(aClassTile, result);
            }
            return result;
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }
}
