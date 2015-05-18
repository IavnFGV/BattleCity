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
    private Integer gamePixel;
    private Integer worldWiddthCells;
    private Integer worldHeightCells;
    private Integer worldSizeCells;
    private Integer cellHeight;
    private Integer cellWidth;
    private Integer worldHeightPixel;
    private Integer worldWiddthPixel;
    private Integer actorWidthPixel;
    private Integer actorWidthCells;
    private Integer actorHeightPixel;
    private Integer actorHeightCells;
    private List<Actor> actorList = new ArrayList<Actor>();
    private GameCell[][] gameCells;
    private CollisionManager collisionManager;

    public World() {
        this(1);
    }

    public World(Integer gamePixel) {
        this.gamePixel = gamePixel;
        worldWiddthCells = 26;
        worldHeightCells = 26;
        worldSizeCells = worldWiddthCells * worldHeightCells;
        cellHeight = 8 * gamePixel;
        cellWidth = 8 * gamePixel;
        worldWiddthPixel = worldWiddthCells * cellWidth;
        worldHeightPixel = worldHeightPixel * cellHeight;
        actorHeightCells = 2;
        actorWidthCells = 2;
        actorHeightPixel = actorHeightCells * cellHeight;
        actorWidthPixel = actorWidthCells * cellWidth;
        gameCells = new GameCell[getWorldHeightCells()][getWorldWiddthCells()];
    }

    public Integer getWorldHeightCells() {
        return worldHeightCells;
    }

    public Integer getWorldWiddthCells() {
        return worldWiddthCells;
    }

    public void setWorldWiddthCells(Integer worldWiddthCells) {
        this.worldWiddthCells = worldWiddthCells;
    }

    public void setWorldHeightCells(Integer worldHeightCells) {
        this.worldHeightCells = worldHeightCells;
    }

    public Integer getWorldWiddthPixel() {
        return worldWiddthPixel;
    }

    public void setWorldWiddthPixel(Integer worldWiddthPixel) {
        this.worldWiddthPixel = worldWiddthPixel;
    }

    public Integer getActorWidthPixel() {
        return actorWidthPixel;
    }

    public void setActorWidthPixel(Integer actorWidthPixel) {
        this.actorWidthPixel = actorWidthPixel;
    }

    public Integer getActorWidthCells() {
        return actorWidthCells;
    }

    public void setActorWidthCells(Integer actorWidthCells) {
        this.actorWidthCells = actorWidthCells;
    }

    public Integer getActorHeightPixel() {
        return actorHeightPixel;
    }

    public void setActorHeightPixel(Integer actorHeightPixel) {
        this.actorHeightPixel = actorHeightPixel;
    }

    public Integer getActorHeightCells() {
        return actorHeightCells;
    }

    public void setActorHeightCells(Integer actorHeightCells) {
        this.actorHeightCells = actorHeightCells;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }

    public GameCell[][] getGameCells() {
        return gameCells;
    }

    public void setGameCells(GameCell[][] gameCells) {
        this.gameCells = gameCells;
    }

    public CollisionManager getCollisionManager() {
        return collisionManager;
    }

    public void setCollisionManager(CollisionManager collisionManager) {
        this.collisionManager = collisionManager;
    }

    public Integer getWorldSizeCells() {
        return worldSizeCells;
    }

    public void setWorldSizeCells(Integer worldSizeCells) {
        this.worldSizeCells = worldSizeCells;
    }

    public Integer getCellHeight() {
        return cellHeight;
    }

    public void setCellHeight(Integer cellHeight) {
        this.cellHeight = cellHeight;
    }

    public Integer getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(Integer cellWidth) {
        this.cellWidth = cellWidth;
    }

    public Integer getWorldHeightPixel() {
        return worldHeightPixel;
    }

    public void setWorldHeightPixel(Integer worldHeightPixel) {
        this.worldHeightPixel = worldHeightPixel;
    }

    public Integer getGamePixel() {
        return gamePixel;
    }

    public void setGamePixel(Integer gamePixel) {
        this.gamePixel = gamePixel;
    }

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
