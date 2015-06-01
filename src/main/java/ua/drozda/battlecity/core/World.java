package ua.drozda.battlecity.core;

import ua.drozda.battlecity.core.actors.Actor;
import ua.drozda.battlecity.core.actors.PlayerTank;
import ua.drozda.battlecity.core.collisions.CollisionBounds;
import ua.drozda.battlecity.core.collisions.CollisionManager;
import ua.drozda.battlecity.core.interfaces.LoadableCells;
import ua.drozda.battlecity.core.interfaces.Togglable;
import ua.drozda.battlecity.core.world.cells.GameCell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by GFH on 11.05.2015.
 */
public class World implements LoadableCells, Togglable<Object> {
    public static final Long TIME_IN_SPAWNING = 500000000L;
    public static final Long TIME_IN_FRIENDLY_FIRE = 3000000000L;
    private Integer gamePixel;
    private Integer worldWiddthCells;
    private Integer worldHeightCells;
    private Integer worldSizeCells;
    private Integer cellHeight;
    private Integer cellWidth;
    private Integer worldHeightPixel;
    private Integer worldWiddthPixel;
    private Integer tankWidthPixel;
    private Integer tankWidthCells;
    private Integer tankHeightPixel;
    private Integer tankHeightCells;
    private List<Actor> actorList = new ArrayList<Actor>();
    private GameCell[][] gameCells;
    private CollisionManager collisionManager;
    private List<PlayerTank> playerTanks = new ArrayList<PlayerTank>();

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
        worldHeightPixel = worldHeightCells * cellHeight;
        tankHeightCells = 2;
        tankWidthCells = 2;
        tankHeightPixel = tankHeightCells * cellHeight;
        tankWidthPixel = tankWidthCells * cellWidth;
        gameCells = new GameCell[getWorldWiddthCells()][getWorldHeightCells()];
    }

    public Integer getWorldWiddthCells() {
        return worldWiddthCells;
    }

    public Integer getWorldHeightCells() {
        return worldHeightCells;
    }

    public void setWorldHeightCells(Integer worldHeightCells) {
        this.worldHeightCells = worldHeightCells;
    }

    public void setWorldWiddthCells(Integer worldWiddthCells) {
        this.worldWiddthCells = worldWiddthCells;
    }

    public List<PlayerTank> getPlayerTanks() {
        return playerTanks;
    }

    public void setPlayerTanks(List<PlayerTank> playerTanks) {
        this.playerTanks = playerTanks;
    }

    public Integer getWorldWiddthPixel() {
        return worldWiddthPixel;
    }

    public void setWorldWiddthPixel(Integer worldWiddthPixel) {
        this.worldWiddthPixel = worldWiddthPixel;
    }

    public Integer getTankWidthPixel() {
        return tankWidthPixel;
    }

    public void setTankWidthPixel(Integer tankWidthPixel) {
        this.tankWidthPixel = tankWidthPixel;
    }

    public Integer getTankWidthCells() {
        return tankWidthCells;
    }

    public void setTankWidthCells(Integer tankWidthCells) {
        this.tankWidthCells = tankWidthCells;
    }

    public Integer getTankHeightPixel() {
        return tankHeightPixel;
    }

    public void setTankHeightPixel(Integer tankHeightPixel) {
        this.tankHeightPixel = tankHeightPixel;
    }

    public Integer getTankHeightCells() {
        return tankHeightCells;
    }

    public void setTankHeightCells(Integer tankHeightCells) {
        this.tankHeightCells = tankHeightCells;
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
        playerTanks.add(new PlayerTank(collisionManager, TankType.FirstPlayer));
    }

    public void loadLevel() {
        //LevelLoader levelLoader = new LevelLoader(String level);
    }


    public Boolean addCell(Integer x, Integer y, TileType tileType) {
        gameCells[x][y] = new GameCell(x, y, tileType);
        gameCells[x][y].setCollisionBounds(new CollisionBounds(this, x, y));
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

    @Override
    public Object toggle(Object o) throws Exception {
        for (GameCell[] cells : gameCells) {
            for (GameCell cell : cells) {
                cell.toggle(null);
            }
        }
        return o;
    }
}
