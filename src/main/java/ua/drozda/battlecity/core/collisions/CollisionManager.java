package ua.drozda.battlecity.core.collisions;


import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import ua.drozda.battlecity.core.ActiveUnit;
import ua.drozda.battlecity.core.TileUnit;
import ua.drozda.battlecity.core.World;

import java.util.EnumSet;
import java.util.Set;

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
    //  private Bounds correctPosition

    public void newPosition(ActiveUnit activeUnit) {
        final boolean stop;
        world.getUnitList().stream().filter(u -> (u instanceof TileUnit)).forEach(u -> {
                    TileUnit tileUnit = (TileUnit) u;
                    Bounds newBounds = new BoundingBox(activeUnit.getNewBounds().getMinX() + 1,
                            activeUnit.getNewBounds().getMinY() + 1,
                            activeUnit.getNewBounds().getWidth() - 2,
                            activeUnit.getNewBounds().getHeight() - 2);
                    if ((!rideTiles.contains(tileUnit.getTileType())) && tileUnit.getBounds().intersects(newBounds)) {
                        System.out.println("TileUnit =" + tileUnit + ";activeUnit = [" + activeUnit + "]");

                        ActiveUnit.Direction direction;
                        if (tileUnit.getBounds().getMinX() > activeUnit.getBounds().getMinX()) {
                            direction = ActiveUnit.Direction.LEFT;
                        } else {
                            direction = ActiveUnit.Direction.RIGHT;
                        }
                        if (tileUnit.getBounds().getMinY() > activeUnit.getBounds().getMinY()) {
                            direction = ActiveUnit.Direction.UP;
                        } else {
                            direction = ActiveUnit.Direction.DOWN;
                        }

                        switch (activeUnit.getDirection()) {
                            case UP: {
                                double deltaY = tileUnit.getBounds().getMaxY() - activeUnit.getNewBounds().getMinY();
                                activeUnit.setNewBounds(new BoundingBox(activeUnit.getNewBounds().getMinX(),
                                        activeUnit.getNewBounds().getMinY() + (deltaY),
                                        activeUnit.getNewBounds().getWidth(),
                                        activeUnit.getNewBounds().getHeight()));
                            }
                            break;
                            case LEFT: {
                                double deltaX = tileUnit.getBounds().getMaxX() - activeUnit.getNewBounds().getMinX();
                                activeUnit.setNewBounds(new BoundingBox(activeUnit.getNewBounds().getMinX() + deltaX,
                                        activeUnit.getNewBounds().getMinY(),
                                        activeUnit.getNewBounds().getWidth(),
                                        activeUnit.getNewBounds().getHeight()));
                            }
                            break;
                            case DOWN: {
                                double deltaY = activeUnit.getNewBounds().getMaxY() - tileUnit.getBounds().getMinY();
                                activeUnit.setNewBounds(new BoundingBox(activeUnit.getNewBounds().getMinX(),
                                        activeUnit.getNewBounds().getMinY() - (deltaY),
                                        activeUnit.getNewBounds().getWidth(),
                                        activeUnit.getNewBounds().getHeight()));
                            }
                            break;
                            case RIGHT: {
                                double deltaX = activeUnit.getNewBounds().getMaxX() - tileUnit.getBounds().getMinX();
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
        //   if (!activeUnit.isCantMove()) {
        activeUnit.setBounds(activeUnit.getNewBounds());
//        } else {
//            activeUnit.setNewBounds(activeUnit.getBounds());
        //   }
    }


}
