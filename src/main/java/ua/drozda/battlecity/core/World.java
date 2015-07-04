package ua.drozda.battlecity.core;

import ua.drozda.battlecity.core.collisions.CollisionManager;
import ua.drozda.battlecity.core.interfaces.LoadableCells;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.round;

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
    private WorldType worldType;
    private TankUnit firstPlayer;
    private TankUnit secondPlayer;
    private List<GameUnit> unitList = new ArrayList<>(); // all units will be here TODO Think about concurrency
    private Map<String, TileUnit> tileMap = new HashMap<>();

    public World(WorldType worldType) {
        this(worldType, 1);
    }

    public World(WorldType worldType, Integer gamePixel) {
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
        this.worldType = worldType;
    }

    public TankUnit getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(TankUnit secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public Map<String, TileUnit> getTileMap() {
        return tileMap;
    }

    public void setTileMap(Map<String, TileUnit> tileMap) {
        this.tileMap = tileMap;
    }

    public TankUnit getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(TankUnit firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Boolean registrateUnit(GameUnit gameUnit) {
        if (unitList.contains(gameUnit)) {
            return false;
        } else {
            unitList.add(gameUnit);
            return true;
        }
    }

    public void notifyObservers() {
        unitList.forEach(u -> u.notifyObservers());
    }

    public Boolean unRegistrateUnit(GameUnit gameUnit) {
        if (unitList.contains(gameUnit)) {
            unitList.remove(gameUnit);
            if (gameUnit instanceof TileUnit) {
                tileMap.remove(getTileId((TileUnit) gameUnit));
            }
            return true;
        } else {
            return false;
        }
    }

    public String getTileId(TileUnit tileUnit) {
        return round(tileUnit.getBounds().getMinX() / getCellWidth()) + "-" + round(tileUnit.getBounds().getMinY()
                / getCellHeight());
    }

    public Integer getCellWidth() {
        return cellWidth;
    }

    public Integer getCellHeight() {
        return cellHeight;
    }

    public void setCellHeight(Integer cellHeight) {
        this.cellHeight = cellHeight;
    }

    public void setCellWidth(Integer cellWidth) {
        this.cellWidth = cellWidth;
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

    public void heartBeat(Long now) {
        for (GameUnit gameUnit : getUnitList()) {
            gameUnit.heartBeat(now);
        }
    }

    public List<GameUnit> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<GameUnit> unitList) {
        this.unitList = unitList;
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

    public void initializeWorld(Long now) {
        setCollisionManager(new CollisionManager(this));
        TankUnit tank = new TankUnit(8 * getCellWidth(), 24 * getCellHeight(), tankWidthPixel, tankHeightPixel,
                1l,
                0l, GameUnit
                .BasicState.CREATING,
                ActiveUnit.Direction.UP, 1l, this::registrateUnit, this::unRegistrateUnit, TankUnit.TankType
                .FIRST_PLAYER, collisionManager);
        setFirstPlayer(tank);
        if (getWorldType() == WorldType.DoublePlayer) {
            tank = new TankUnit(16 * getCellWidth(), 24 * getCellHeight(), tankWidthPixel, tankHeightPixel,
                    1l,
                    0l, GameUnit
                    .BasicState.CREATING,
                    ActiveUnit.Direction.UP, 1l, this::registrateUnit, this::unRegistrateUnit, TankUnit.TankType
                    .SECOND_PLAYER, collisionManager);
            setSecondPlayer(tank);
        }
        getUnitList().forEach(u -> u.initUnit(now));


    }

    public WorldType getWorldType() {
        return worldType;
    }

    public void setWorldType(WorldType worldType) {
        this.worldType = worldType;
    }

    public void handleCollisions() {

    }

    public void updatePositions(Long now) { //TODE proofOfConcept
        unitList.stream().filter(u -> (u instanceof ActiveUnit)).forEach(u ->
                {
                    ((ActiveUnit) u).move(now);
                    //       ((ActiveUnit) u).setBounds(((ActiveUnit) u).getNewBounds());
                    collisionManager.newPosition((ActiveUnit) u);
                }
        );

    }

    public Boolean addCell(Integer x, Integer y, TileUnit.TileType tileType) {
        TileUnit tileUnit = new TileUnit(x * cellWidth, y * cellHeight, cellWidth, cellHeight, 1l, 0l,
                tileType, this::registrateUnit, this::unRegistrateUnit);
        tileMap.put(getTileId(tileUnit), tileUnit);
        return true;
    }

    public enum WorldType {
        SinglePlayer, DoublePlayer, Construction
    }


}
