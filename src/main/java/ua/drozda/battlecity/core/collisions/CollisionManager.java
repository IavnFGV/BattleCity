package ua.drozda.battlecity.core.collisions;


import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import ua.drozda.battlecity.core.ActiveUnit;
import ua.drozda.battlecity.core.TileUnit;
import ua.drozda.battlecity.core.World;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.round;

/**
 * Created by GFH on 14.05.2015.
 */
public class CollisionManager {
    private World world;
    //    private List<Actor> actorList;
    private Set<TileUnit.TileType> rideTiles = EnumSet.of(TileUnit.TileType.EMPTY, TileUnit.TileType.FOREST, TileUnit
            .TileType.ICE);

    public CollisionManager(World world) {
        this.world = world;
    }

    //    private GameCell[][] gameCellList;
//
//    {
//        rideTiles.add(TileType.ICE);
//        rideTiles.add(TileType.FOREST);
//        rideTiles.add(TileType.EMPTY);
//    }
//
//    public CollisionManager(List<Actor> actorList, GameCell[][] gameCellList) {
//        this.actorList = actorList;
//        this.gameCellList = gameCellList;
//    }
//
//    public CollisionManager() {
//    }
//
//    private static boolean intersect(CollisionBounds a, CollisionBounds b) {
//        return (a.getyU() < b.getyF() || a.getyF() > b.getyU() || a.getxF() < b.getxU() || a.getxU() > b.getxF());
//    }
//
    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    //
//    public List<Actor> getActorList() {
//        return actorList;
//    }
//
//    public void setActorList(List<Actor> actorList) {
//        this.actorList = actorList;
//    }
//
//    public GameCell[][] getGameCellList() {
//        return gameCellList;
//    }
//
//    public void setGameCellList(GameCell[][] gameCellList) {
//        this.gameCellList = gameCellList;
//    }
//
////    var intersects = function ( a, b ) {
////        return ( a.y < b.y1 || a.y1 > b.y || a.x1 < b.x || a.x > b.x1 );
////    }
//
//    //    public static Tile handleCollision(Tile curTile,Bullet bullet){
////        if(curTile instanceof Brick){
////
////        }
////    }
    public Bounds newPosition(ActiveUnit activeUnit) {

        // find all cells between cur position and new position

        Integer x = Integer.valueOf((int) round(activeUnit.getBounds().getMinX()) / world.getCellWidth());
        Integer y = Integer.valueOf((int) round(activeUnit.getBounds().getMinY()) / world.getCellHeight());
        Integer xn = Integer.valueOf((int) round(activeUnit.getNewBounds().getMinX()) / world.getCellWidth());
        Integer yn = Integer.valueOf((int) round(activeUnit.getNewBounds().getMinY()) / world.getCellHeight());


        x = Integer.valueOf((int) round(activeUnit.getBounds().getMinX()) % world.getCellWidth()) == 0 ? x + 1 : x;
        y = Integer.valueOf((int) round(activeUnit.getBounds().getMinY()) % world.getCellHeight()) == 0 ? y - 1 : y;
        xn = Integer.valueOf((int) round(activeUnit.getNewBounds().getMinX()) % world.getCellWidth()) == 0 ? xn + 1 : xn;
        yn = Integer.valueOf((int) round(activeUnit.getNewBounds().getMinY()) % world.getCellHeight()) == 0 ? yn - 1 : yn;

        Integer stopx = null;
        Integer stopy = null;

        Map<String, TileUnit> tileMap = world.getTileMap();

        switch (activeUnit.getDirection()) {
            case UP: {
                for (int i = y; i >= yn; i--) {
                    if (tileMap.containsKey(i + "-" + x) && !rideTiles.contains(tileMap.get(i + "-" + x).getTileType()
                            .getClass())) {
                        stopy = i;
                        stopx = x;
                        break;
                    }
                    if (tileMap.containsKey(i + "-" + x + 1) && !rideTiles.contains(tileMap.get(i + "-" + x + 1)
                            .getTileType().getClass())) {
                        stopy = i;
                        stopx = x + 1;
                        break;
                    }
                }
                if (stopx != null) {
                    break;
                }
                break;
            }
            case RIGHT: {
                for (int i = x + 1; i <= xn + 1; i++) {
                    if (tileMap.containsKey(i + "-" + x + 1) && !rideTiles.contains(tileMap.get(y + "-" + i).getTileType().getClass())) {
                        stopy = y;
                        stopx = i;
                        break;
                    }
                    if (tileMap.containsKey(i + "-" + x + 1) && !rideTiles.contains(tileMap.get(y + 1 + "-" + i).getTileType().getClass())) {
                        stopy = y + 1;
                        stopx = i;
                        break;
                    }
                }
                if (stopx != null) {
                    break;
                }
                break;
            }
            case DOWN: {
                for (int i = y + 1; i <= yn + 1; i++) {
                    if (tileMap.containsKey(i + "-" + x) && !rideTiles.contains(tileMap.get(i + "-" + x).getTileType().getClass())) {
                        stopy = i;
                        stopx = x;
                        break;
                    }
                    if (tileMap.containsKey(i + "-" + x + 1) && !rideTiles.contains(tileMap.get(i + "-" + x + 1).getTileType().getClass())) {
                        stopy = i;
                        stopx = x + 1;
                        break;
                    }
                }
                if (stopx != null) {
                    break;
                }
                break;
            }
            case LEFT: {
                for (int i = x; i >= xn; i--) {
                    if (tileMap.containsKey(y + "-" + i) && !rideTiles.contains(tileMap.get(y + "-" + i).getTileType().getClass())) {
                        stopy = y;
                        stopx = i;
                        break;
                    }
                    if (tileMap.containsKey(y + 1 + "-" + i) && !rideTiles.contains(tileMap.get(y + 1 + "-" + i).getTileType().getClass())) {
                        stopy = y + 1;
                        stopx = i;
                        break;
                    }
                }
                if (stopx != null) {
                    break;
                }
                break;
            }
        }
        if (stopx != null) {
            activeUnit.setBounds(new BoundingBox(stopx * world.getCellWidth(), stopy * world.getCellHeight(), world
                    .getCellWidth(), world.getCellHeight()));
        }
        return activeUnit.getBounds();

    }


}
