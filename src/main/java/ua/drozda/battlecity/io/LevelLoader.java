package ua.drozda.battlecity.io;

import ua.drozda.battlecity.core.TileType;
import ua.drozda.battlecity.core.interfaces.LoadableCells;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GFH on 16.05.2015.
 */
public class LevelLoader {
    private static Map<Character, TileType> map = new HashMap<Character, TileType>();

    static {
        map.put('#', TileType.BRICK);
        map.put('@', TileType.STEEL);
        map.put('~', TileType.WATER);
        map.put('%', TileType.FOREST);
        map.put('-', TileType.ICE);
        map.put('.', TileType.EMPTY);
    }

    public static Boolean loadlevel(String level, LoadableCells world) throws Exception {
        InputStream is = LevelLoader.class.getResourceAsStream("/ua/drozda/battlecity/levels/" + level);
        if (is != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String sCurrentLine;
            Integer y = Integer.valueOf(0);
            while ((sCurrentLine = bufferedReader.readLine()) != null) {
                char[] chars = sCurrentLine.toCharArray();
                Integer x = Integer.valueOf(0);
                for (char c : chars) {
                    world.addCell(x, y, map.get(c));
                    System.out.print(c);
                    x++;
                }
                y++;
                System.out.println();
            }
        }
        return null;
    }


}
