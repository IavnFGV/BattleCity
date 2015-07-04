package ua.drozda.battlecity.core.collisions;


import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import ua.drozda.battlecity.core.*;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public void fixPosition(ActiveUnit activeUnit) {
        Predicate<GameUnit> onlyTank = gameUnit -> (gameUnit instanceof TankUnit);
        Predicate<GameUnit> notMe = gameUnit -> (gameUnit != activeUnit);
        Predicate<GameUnit> onlyTankAndNotMe = onlyTank.and(notMe);
        List<GameUnit> tanks = world.getUnitList().stream().filter(onlyTankAndNotMe).collect(Collectors.toList());
        if (tanks.size() == 0) {// no more tanks
            activeUnit.setBounds(activeUnit.getNewBounds());
            return;
        }
        tanks.forEach(gameUnit -> {
            Bounds newBounds = new BoundingBox(activeUnit.getNewBounds().getMinX() + 1,
                    activeUnit.getNewBounds().getMinY() + 1,
                    activeUnit.getNewBounds().getWidth() - 2,
                    activeUnit.getNewBounds().getHeight() - 2);
            if (!newBounds.intersects(gameUnit.getBounds())) {
                activeUnit.setBounds(activeUnit.getNewBounds());
            }
        });
    }


    public void newPosition(ActiveUnit activeUnit) {
        world.getUnitList().stream().filter(u -> (u != activeUnit))
                .forEach(u -> {
                            Bounds newBounds = new BoundingBox(activeUnit.getNewBounds().getMinX() + 1,
                                    // newBounds will always intersects another bounds if we are absolutely near another
                                    // so we give 1 pixel on every direction to handle this situation
                                    activeUnit.getNewBounds().getMinY() + 1,
                                    activeUnit.getNewBounds().getWidth() - 2,
                                    activeUnit.getNewBounds().getHeight() - 2);
                            if (!canRide(u) && u.getBounds().intersects(newBounds)) {
                                System.out.println("OtherUnit =" + u.getClass() + ";activeUnit = [" + activeUnit
                                        .getClass() +
                                        "]");

                                switch (activeUnit.getDirection()) {
                                    case UP: {
                                        double deltaY = u.getBounds().getMaxY() - activeUnit.getNewBounds().getMinY();
                                        activeUnit.setNewBounds(new BoundingBox(activeUnit.getNewBounds().getMinX(),
                                                activeUnit.getNewBounds().getMinY() + (deltaY),
                                                activeUnit.getNewBounds().getWidth(),
                                                activeUnit.getNewBounds().getHeight()));
                                    }
                                    break;
                                    case LEFT: {
                                        double deltaX = u.getBounds().getMaxX() - activeUnit.getNewBounds().getMinX();
                                        activeUnit.setNewBounds(new BoundingBox(activeUnit.getNewBounds().getMinX() + deltaX,
                                                activeUnit.getNewBounds().getMinY(),
                                                activeUnit.getNewBounds().getWidth(),
                                                activeUnit.getNewBounds().getHeight()));
                                    }
                                    break;
                                    case DOWN: {
                                        double deltaY = activeUnit.getNewBounds().getMaxY() - u.getBounds().getMinY();
                                        activeUnit.setNewBounds(new BoundingBox(activeUnit.getNewBounds().getMinX(),
                                                activeUnit.getNewBounds().getMinY() - (deltaY),
                                                activeUnit.getNewBounds().getWidth(),
                                                activeUnit.getNewBounds().getHeight()));
                                    }
                                    break;
                                    case RIGHT: {
                                        double deltaX = activeUnit.getNewBounds().getMaxX() - u.getBounds().getMinX();
                                        activeUnit.setNewBounds(new BoundingBox(activeUnit.getNewBounds().getMinX() - deltaX,
                                                activeUnit.getNewBounds().getMinY(),
                                                activeUnit.getNewBounds().getWidth(),
                                                activeUnit.getNewBounds().getHeight()));
                                    }
                                    break;
                                }
                                return;
                            } else {
                                return;
                            }
                        }
                );
        if (activeUnit.isEngineOn()) {
            activeUnit.setBounds(activeUnit.getNewBounds());
        }

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
    //  private Bounds correctPosition
    private Boolean canRide(GameUnit unit) {
        Boolean result = false;
        if (unit instanceof TileUnit) {
            result = (rideTiles.contains(((TileUnit) unit).getTileType()));
        }
        return result;
    }


}
