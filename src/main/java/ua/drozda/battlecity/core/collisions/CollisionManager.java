package ua.drozda.battlecity.core.collisions;


import javafx.geometry.Point2D;
import ua.drozda.battlecity.core.TileType;
import ua.drozda.battlecity.core.World;
import ua.drozda.battlecity.core.actors.Actor;
import ua.drozda.battlecity.core.world.cells.GameCell;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Math.round;

/**
 * Created by GFH on 14.05.2015.
 */
public class CollisionManager {

    private World world;
    private List<Actor> actorList;
    private Set<TileType> rideTiles = new HashSet<TileType>();
    private GameCell[][] gameCellList;

    {
        rideTiles.add(TileType.ICE);
        rideTiles.add(TileType.FOREST);
        rideTiles.add(TileType.EMPTY);
    }

    public CollisionManager(List<Actor> actorList, GameCell[][] gameCellList) {
        this.actorList = actorList;
        this.gameCellList = gameCellList;
    }

    public CollisionManager() {
    }

    private static boolean intersect(CollisionBounds a, CollisionBounds b) {
        return (a.getyU() < b.getyF() || a.getyF() > b.getyU() || a.getxF() < b.getxU() || a.getxU() > b.getxF());
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }

    public GameCell[][] getGameCellList() {
        return gameCellList;
    }

    public void setGameCellList(GameCell[][] gameCellList) {
        this.gameCellList = gameCellList;
    }

//    var intersects = function ( a, b ) {
//        return ( a.y < b.y1 || a.y1 > b.y || a.x1 < b.x || a.x > b.x1 );
//    }

    //    public static Tile handleCollision(Tile curTile,Bullet bullet){
//        if(curTile instanceof Brick){
//
//        }
//    }
    public Point2D newPosition(Actor actor, Point2D newPosition) {

        // find all cells between cur position and new position

        Integer x = Integer.valueOf((int) round(actor.getPosition().getX()) / world.getCellWidth());
        Integer y = Integer.valueOf((int) round(actor.getPosition().getY()) / world.getCellHeight());
        Integer xn = Integer.valueOf((int) round(newPosition.getX()) / world.getCellWidth());
        Integer yn = Integer.valueOf((int) round(newPosition.getY()) / world.getCellHeight());


        x = Integer.valueOf((int) round(actor.getPosition().getX()) % world.getCellWidth()) == 0 ? x + 1 : x;
        y = Integer.valueOf((int) round(actor.getPosition().getY()) % world.getCellHeight()) == 0 ? y - 1 : y;
        xn = Integer.valueOf((int) round(newPosition.getX()) % world.getCellWidth()) == 0 ? xn + 1 : xn;
        yn = Integer.valueOf((int) round(newPosition.getY()) % world.getCellHeight()) == 0 ? yn - 1 : yn;

        Integer stopx = null;
        Integer stopy = null;


        switch (actor.getDirection()) {
            case UP: {
                for (int i = y; i >= yn; i--) {
                    if (!rideTiles.contains(gameCellList[i][x].getClass())) {
                        stopy = i;
                        stopx = x;
                        break;
                    }
                    if (!rideTiles.contains(gameCellList[i][x + 1].getClass())) {
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
                    if (!rideTiles.contains(gameCellList[y][i].getClass())) {
                        stopy = y;
                        stopx = i;
                        break;
                    }
                    if (!rideTiles.contains(gameCellList[y + 1][i].getClass())) {
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
                    if (!rideTiles.contains(gameCellList[i][x].getClass())) {
                        stopy = i;
                        stopx = x;
                        break;
                    }
                    if (!rideTiles.contains(gameCellList[i][x + 1].getClass())) {
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
                    if (!rideTiles.contains(gameCellList[y][i].getClass())) {
                        stopy = y;
                        stopx = i;
                        break;
                    }
                    if (!rideTiles.contains(gameCellList[y + 1][i].getClass())) {
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
            return new Point2D(stopx * world.getCellWidth(), stopy * world.getCellHeight());
        } else {
            return newPosition;
        }
    }


}
