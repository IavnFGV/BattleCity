package ua.drozda.battlecity.io;

import ua.drozda.battlecity.core.interfaces.LoadableCells;
import ua.drozda.battlecity.core.tiles.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GFH on 16.05.2015.
 */
public class LevelLoader {
    private static Map<Character, Class<? extends Tile>> map = new HashMap<Character, Class<? extends Tile>>();

    static {
        map.put('#', Brick.class);
        map.put('@', Steel.class);
        map.put('~', Water.class);
        map.put('%', Forest.class);
        map.put('-', Ice.class);
        map.put('.', Empty.class);
    }

    public static Boolean loadlevel(String level, LoadableCells world) throws Exception {
        InputStream is = LevelLoader.class.getResourceAsStream("/ua/drozda/battlecity/levels/" + level);
        if (is != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String sCurrentLine;
            Integer y = Integer.valueOf(0);
            Integer x = Integer.valueOf(0);
            while ((sCurrentLine = bufferedReader.readLine()) != null) {
                char[] chars = sCurrentLine.toCharArray();
                x = Integer.valueOf(0);
                for (char c : chars) {
                    world.addCell(x, y, Tile.getTile(map.get(c), 0));
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
