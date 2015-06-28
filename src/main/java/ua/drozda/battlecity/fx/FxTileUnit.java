package ua.drozda.battlecity.fx;

import javafx.geometry.Rectangle2D;
import ua.drozda.battlecity.core.GameUnit;
import ua.drozda.battlecity.core.TileUnit;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GFH on 15.06.2015.
 */
public class FxTileUnit extends FxGameUnit {

    private static Map<TileUnit.TileType, Rectangle2D[]> tileActiveMap = new HashMap();
    private static HashMap<TileUnit.TileType, Integer> toggleTileCountMap = new HashMap();

    static {
        toggleTileCountMap.put(TileUnit.TileType.BRICK, 1);
        toggleTileCountMap.put(TileUnit.TileType.WATER, 3);
        toggleTileCountMap.put(TileUnit.TileType.EMPTY, 1);
        toggleTileCountMap.put(TileUnit.TileType.FOREST, 1);
        toggleTileCountMap.put(TileUnit.TileType.ICE, 1);
        toggleTileCountMap.put(TileUnit.TileType.STEEL, 1);
    }
    //protected static HashMap<TankUnit.TankType, Integer> toggleTankCountMap = new HashMap();

    static {
        Rectangle2D[] rectangle2Ds = new Rectangle2D[15];
        for (int i = 0; i < 15; i++) {
            rectangle2Ds[i] = new Rectangle2D(FxWorld.tileZoneX + FxWorld.tileSize * i, FxWorld.brickZoneY, 8, 8);
        }
        tileActiveMap.put(TileUnit.TileType.BRICK, rectangle2Ds);

        rectangle2Ds = new Rectangle2D[3];
        for (int i = 0; i < 3; i++) {
            rectangle2Ds[i] = new Rectangle2D(FxWorld.tileZoneX + FxWorld.tileSize * i, FxWorld.waterZoneY, 8, 8);
        }
        tileActiveMap.put(TileUnit.TileType.WATER, rectangle2Ds);

        rectangle2Ds = new Rectangle2D[1];
        for (int i = 0; i < 1; i++) {
            rectangle2Ds[i] = new Rectangle2D(FxWorld.tileZoneX + FxWorld.tileSize * i, FxWorld.emptyZoneY, 8, 8);
        }
        tileActiveMap.put(TileUnit.TileType.EMPTY, rectangle2Ds);

        rectangle2Ds = new Rectangle2D[1];
        for (int i = 0; i < 1; i++) {
            rectangle2Ds[i] = new Rectangle2D(FxWorld.tileZoneX + FxWorld.tileSize * i, FxWorld.steelZoneY, 8, 8);
        }
        tileActiveMap.put(TileUnit.TileType.STEEL, rectangle2Ds);

        rectangle2Ds = new Rectangle2D[1];
        for (int i = 0; i < 1; i++) {
            rectangle2Ds[i] = new Rectangle2D(FxWorld.tileZoneX + FxWorld.tileSize * i, FxWorld.forestZoneY, 8, 8);
        }
        tileActiveMap.put(TileUnit.TileType.FOREST, rectangle2Ds);

        rectangle2Ds = new Rectangle2D[1];
        for (int i = 0; i < 1; i++) {
            rectangle2Ds[i] = new Rectangle2D(FxWorld.tileZoneX + FxWorld.tileSize * i, FxWorld.iceZoneY, 8, 8);
        }
        tileActiveMap.put(TileUnit.TileType.ICE, rectangle2Ds); // todo test

    }

    private Integer visualState = 0;

    public FxTileUnit(TileUnit tileUnit) {
        super(tileUnit);
        maxToggle = toggleTileCountMap.get(((TileUnit) gameUnit).getTileType());
        //updateSprite();
    }

    @Override
    protected void nextSprite() {
        if (getTile().getTileType() == TileUnit.TileType.BRICK) {
            setCurSprite(tileActiveMap.get((getTile().getTileType()))[this.visualState]);
            return;
        }
        if (gameUnit.getCurrentBasicState() == GameUnit.BasicState.ACTIVE) {
            setCurSprite(tileActiveMap.get((getTile().getTileType()))[this.curToggle]);
        }
    }

    @Override
    public Object toggle(Object o) {
        curToggle = ++curToggle % maxToggle;
        updateSprite();
        return null;
    }

    public TileUnit getTile() {
        return (TileUnit) this.gameUnit;
    }
}
