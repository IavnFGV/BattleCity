package ua.drozda.battlecity.core;

import ua.drozda.battlecity.core.world.GameCell;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GFH on 11.05.2015.
 */
public class World {
    // todo load from properties
    public static final Integer GAME_PIXEL = 1;
    public static final Integer WORLD_HEIGHT_CELL = 26;
    public static final Integer WORLD_WIDTH_CELL = 26;
    public static final Integer CELL_HEIGH = 8 * GAME_PIXEL;
    public static final Integer CELL_WIDTH = 8 * GAME_PIXEL;
    public static final Integer WORLD_HEIGHT_PIXEL = WORLD_HEIGHT_CELL * CELL_HEIGH * GAME_PIXEL;
    public static final Integer WORLD_WIDTH_PIXEL = WORLD_WIDTH_CELL * CELL_WIDTH * GAME_PIXEL;

    private List<GameCell> gameCells = new ArrayList<GameCell>(26 * 26);

    public void initializeWorld() {

    }


}
