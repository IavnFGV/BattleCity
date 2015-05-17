package ua.drozda.battlecity.core;

import ua.drozda.battlecity.core.actors.Actor;
import ua.drozda.battlecity.core.collisions.CollisionManager;
import ua.drozda.battlecity.core.interfaces.LoadableCells;
import ua.drozda.battlecity.core.tiles.Tile;
import ua.drozda.battlecity.core.world.cells.GameCell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by GFH on 11.05.2015.
 */
public class World implements LoadableCells {
    // todo load from properties
    public static final Integer GAME_PIXEL = 1;
    public static final Integer WORLD_HEIGHT_CELL = 26;
    public static final Integer WORLD_WIDTH_CELL = 26;
    public static final Integer WORLD_SIZE = WORLD_HEIGHT_CELL * WORLD_WIDTH_CELL;
    public static final Integer CELL_HEIGH = 8 * GAME_PIXEL;
    public static final Integer CELL_WIDTH = 8 * GAME_PIXEL;
    public static final Integer WORLD_HEIGHT_PIXEL = WORLD_HEIGHT_CELL * CELL_HEIGH * GAME_PIXEL;
    public static final Integer WORLD_WIDTH_PIXEL = WORLD_WIDTH_CELL * CELL_WIDTH * GAME_PIXEL;

    private List<Actor> actorList = new ArrayList<Actor>();
    private GameCell[][] gameCells = new GameCell[WORLD_HEIGHT_CELL][WORLD_WIDTH_CELL];


    private CollisionManager collisionManager;

    public void initializeWorld() {
        World world = new World();
        collisionManager = new CollisionManager(world.actorList, world.gameCells);

    }

    public void loadLevel() {
        //LevelLoader levelLoader = new LevelLoader(String level);
    }


    public Boolean addCell(Integer x, Integer y, Tile tile) {
        gameCells[y][x] = new GameCell(x, y, tile);
        return true;
    }

    @Override
    public String toString() {
        return "World{" +
                "actorList=" + actorList +
                ", gameCells=" + Arrays.deepToString(gameCells) +
                ", collisionManager=" + collisionManager +
                '}';
    }
}
