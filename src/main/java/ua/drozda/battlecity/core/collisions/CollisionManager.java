package ua.drozda.battlecity.core.collisions;


import javafx.geometry.Point2D;
import ua.drozda.battlecity.core.World;
import ua.drozda.battlecity.core.actors.Actor;
import ua.drozda.battlecity.core.tiles.Empty;
import ua.drozda.battlecity.core.tiles.Forest;
import ua.drozda.battlecity.core.tiles.Ice;
import ua.drozda.battlecity.core.tiles.Tile;
import ua.drozda.battlecity.core.world.GameCell;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Math.round;

/**
 * Created by GFH on 14.05.2015.
 */
public class CollisionManager {

    private List<Actor> actorList;

    private Set<Class<? extends Tile>> rideTiles = new HashSet<Class<? extends Tile>>();

    {
        rideTiles.add(Ice.class);
        rideTiles.add(Forest.class);
        rideTiles.add(Empty.class);
    }

    private List<List<GameCell>> gameCellList;

    public CollisionManager(List<Actor> actorList, List<List<GameCell>> gameCellList) {
        this.actorList = actorList;
        this.gameCellList = gameCellList;
    }

    public CollisionManager() {
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }

    public List<List<GameCell>> getGameCellList() {
        return gameCellList;
    }

    public void setGameCellList(List<List<GameCell>> gameCellList) {
        this.gameCellList = gameCellList;
    }

    private static boolean intersect(CollisionBounds a, CollisionBounds b) {
        return (a.getyU() < b.getyF() || a.getyF() > b.getyU() || a.getxF() < b.getxU() || a.getxU() > b.getxF());
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

        Integer x = Integer.valueOf((int) round(actor.getPosition().getX()) / World.CELL_WIDTH);
        Integer y = Integer.valueOf((int) round(actor.getPosition().getY()) / World.CELL_HEIGH);
        Integer xn = Integer.valueOf((int) round(newPosition.getX()) / World.CELL_WIDTH);
        Integer yn = Integer.valueOf((int) round(newPosition.getY()) / World.CELL_HEIGH);


        x = Integer.valueOf((int) round(actor.getPosition().getX()) % World.CELL_WIDTH) == 0 ? x + 1 : x;
        y = Integer.valueOf((int) round(actor.getPosition().getY()) % World.CELL_HEIGH) == 0 ? y - 1 : y;
        xn = Integer.valueOf((int) round(newPosition.getX()) % World.CELL_WIDTH) == 0 ? xn + 1 : xn;
        yn = Integer.valueOf((int) round(newPosition.getY()) % World.CELL_HEIGH) == 0 ? yn - 1 : yn;

        Integer stopx = null;
        Integer stopy = null;


        switch (actor.getDirection()) {
            case UP: {
                for (int i = y; i >= yn; i--) {
                    if (!rideTiles.contains(gameCellList.get(i).get(x).getClass())) {
                        stopy = i;
                        stopx = x;
                        break;
                    }
                    if (!rideTiles.contains(gameCellList.get(i).get(x + 1).getClass())) {
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
                    if (!rideTiles.contains(gameCellList.get(y).get(i).getClass())) {
                        stopy = y;
                        stopx = i;
                        break;
                    }
                    if (!rideTiles.contains(gameCellList.get(y + 1).get(i).getClass())) {
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
                    if (!rideTiles.contains(gameCellList.get(i).get(x).getClass())) {
                        stopy = i;
                        stopx = x;
                        break;
                    }
                    if (!rideTiles.contains(gameCellList.get(i).get(x + 1).getClass())) {
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
                    if (!rideTiles.contains(gameCellList.get(y).get(i).getClass())) {
                        stopy = y;
                        stopx = i;
                        break;
                    }
                    if (!rideTiles.contains(gameCellList.get(y + 1).get(i).getClass())) {
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
            return new Point2D(stopx * World.CELL_WIDTH, stopy * World.CELL_HEIGH);
        } else {
            return newPosition;
        }
    }


}
