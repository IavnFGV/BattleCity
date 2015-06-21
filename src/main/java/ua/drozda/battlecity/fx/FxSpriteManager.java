package ua.drozda.battlecity.fx;

import javafx.geometry.Rectangle2D;
import ua.drozda.battlecity.core.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GFH on 14.06.2015.
 */
public class FxSpriteManager {

    private static Map<TileUnit.TileType, Rectangle2D[]> tileActiveMap = new HashMap<>();

    private static Map<TankUnit.TankType, HashMap<ActiveUnit.Direction, Rectangle2D[][]>> tankActiveMap = new
            HashMap<>();
    //2d array for starcount of tank;

    private static Map<Class<? extends ActiveUnit>, Rectangle2D[]> explosiveMap = new HashMap<>();

    static {
        //for player tank
        HashMap<ActiveUnit.Direction, Rectangle2D[][]> bufHashMap = new HashMap<>();
        //UP
        Rectangle2D[][] rectangle2Ds = new Rectangle2D[4][2];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                rectangle2Ds[i][j] = new Rectangle2D(FxWorld.firstPlayerZoneX + FxWorld.tankSize * j, FxWorld
                        .firstPlayerZoneY + FxWorld.tankSize * i, FxWorld.tankSize, FxWorld.tankSize);
            }
        }
        bufHashMap.put(ActiveUnit.Direction.UP, rectangle2Ds);
        //LEFT
        rectangle2Ds = new Rectangle2D[4][2];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                rectangle2Ds[i][j] = new Rectangle2D(FxWorld.firstPlayerZoneX + FxWorld.tankSize * 2 + FxWorld.tankSize *
                        j, FxWorld
                        .firstPlayerZoneY + FxWorld.tankSize * i, FxWorld.tankSize, FxWorld.tankSize);
            }
        }
        bufHashMap.put(ActiveUnit.Direction.LEFT, rectangle2Ds);
        //DOWN
        rectangle2Ds = new Rectangle2D[4][2];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                rectangle2Ds[i][j] = new Rectangle2D(FxWorld.firstPlayerZoneX + FxWorld.tankSize * 4 + FxWorld.tankSize *
                        j, FxWorld
                        .firstPlayerZoneY + FxWorld.tankSize * i, FxWorld.tankSize, FxWorld.tankSize);
            }
        }
        bufHashMap.put(ActiveUnit.Direction.DOWN, rectangle2Ds);
        //RIGHT
        rectangle2Ds = new Rectangle2D[4][2];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                rectangle2Ds[i][j] = new Rectangle2D(FxWorld.firstPlayerZoneX + FxWorld.tankSize * 6 + FxWorld.tankSize *
                        j, FxWorld
                        .firstPlayerZoneY + FxWorld.tankSize * i, FxWorld.tankSize, FxWorld.tankSize);
            }
        }
        bufHashMap.put(ActiveUnit.Direction.RIGHT, rectangle2Ds);
        tankActiveMap.put(TankUnit.TankType.FIRST_PLAYER, bufHashMap);
    }

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

    static {
        Rectangle2D[] rectangle2Ds = new Rectangle2D[3];
        for (int i = 0; i < 3; i++) {
            rectangle2Ds[i] = new Rectangle2D(FxWorld.tileZoneX + FxWorld.bulletExplosion * i, FxWorld.explozionZoneY, FxWorld.bulletExplosion, FxWorld.bulletExplosion);
        }
        explosiveMap.put(BulletUnit.class, rectangle2Ds);
    }

    public static Rectangle2D getNextSprite(FxGameUnit fxGameUnit) {
        if (fxGameUnit.gameUnit.getCurrentBasicState() == GameUnit.BasicState.ACTIVE) {
            if (fxGameUnit.gameUnit instanceof TileUnit) {
                return tileActiveMap.get(((TileUnit) fxGameUnit.gameUnit).getTileType())[fxGameUnit.curToggle];
            }
            if (fxGameUnit.gameUnit instanceof TankUnit) {
                TankUnit tankUnit = (TankUnit) fxGameUnit.gameUnit;
                return tankActiveMap.get(tankUnit.getTankType()).get(tankUnit.getDirection())[tankUnit.getStarCount()
                        ][fxGameUnit.curToggle];
            }
            if (fxGameUnit.gameUnit instanceof BulletUnit) {

            }
        }
        if (fxGameUnit.gameUnit.getCurrentBasicState() == GameUnit.BasicState.EXPLODING) {
            if (fxGameUnit.gameUnit instanceof BulletUnit) {
                //    BulletUnit
            }

        }

        return null;
    }

}
