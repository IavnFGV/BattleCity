package ua.drozda.battlecity.core;

import ua.drozda.battlecity.core.collisions.CollisionManager;
import ua.drozda.battlecity.core.interfaces.LoadableCells;

import java.util.ArrayList;
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
    private Integer tankWidthPixel;
    private Integer tankWidthCells;
    private Integer tankHeightPixel;
    private Integer tankHeightCells;
    private CollisionManager collisionManager;

    private List<GameUnit> unitList = new ArrayList<>(); // all units will be here

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
    }

    public List<GameUnit> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<GameUnit> unitList) {
        this.unitList = unitList;
    }

    public Boolean registrateUnit(GameUnit gameUnit) {
        if (unitList.contains(gameUnit)) {
            return false;
        } else {
            unitList.add(gameUnit);
            return true;
        }
    }

    public Boolean unRegistrateUnit(GameUnit gameUnit) {
        if (unitList.contains(gameUnit)) {
            unitList.remove(gameUnit);
            return true;
        } else {
            return false;
        }
    }

    public void scale(Integer x, Integer y) {
        for (GameUnit gu : unitList) {
            gu.scale(x, y);
        }
    }

    public Integer getWorldWiddthCells() {
        return worldWiddthCells;
    }

    public void setWorldWiddthCells(Integer worldWiddthCells) {
        this.worldWiddthCells = worldWiddthCells;
    }

    public Integer getWorldHeightCells() {
        return worldHeightCells;
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

    }

    public void loadLevel() {
        //LevelLoader levelLoader = new LevelLoader(String level);
    }

    public Boolean addCell(Integer x, Integer y, TileUnit.TileType tileType) {
        new TileUnit(x * cellWidth, y * cellHeight, cellWidth, cellHeight, 1l, 0l,
                tileType, this::registrateUnit, this::unRegistrateUnit);
        // gameCells[x][y] = new GameCell(x, y, tileType);
        //  gameCells[x][y].setCollisionBounds(new CollisionBounds(this, x, y));
        return true;
    }


}
