package ua.drozda.battlecity.core.collisions;


import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
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

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void fixPosition(ActiveUnit activeUnit, Double newX, Double newY) {
        Predicate<GameUnit> onlyTank = gameUnit -> (gameUnit instanceof TankUnit);
        Predicate<GameUnit> notMe = gameUnit -> (gameUnit != activeUnit);
        Predicate<GameUnit> onlyTankAndNotMe = onlyTank.and(notMe);
        List<GameUnit> tanks = world.getUnitList().stream().filter(onlyTankAndNotMe).collect(Collectors.toList());
        if (tanks.size() == 0) {// no more tanks
            activeUnit.setX(newX);
            activeUnit.setY(newY);
            return;
        }
        if (!tanks.stream().map(gameUnit -> {
            Bounds newBounds = new BoundingBox(newX + 1,
                    newY + 1,
                    activeUnit.getWidth() - 2,
                    activeUnit.getHeight() - 2);
            return newBounds.intersects(gameUnit.getBounds());
        }).anyMatch(b -> (b == true))) {
            activeUnit.setX(newX);
            activeUnit.setY(newY);
        }
    }

    public void newPosition(ActiveUnit activeUnit, Number newValueX, Number newValueY) {
        Bounds newFixedBounds = new BoundingBox(newValueX.intValue() + 1,
                // newBounds will always intersects another bounds if we are absolutely near another
                // so we give 1 pixel on every direction to handle this situation
                newValueY.intValue() + 1,
                activeUnit.getWidth() - 2,
                activeUnit.getHeight() - 2);
        Bounds newBounds = new BoundingBox(newValueX.intValue(),
                newValueY.intValue(),
                activeUnit.getWidth(),
                activeUnit.getHeight());
        Point2D result = new Point2D(newValueX.doubleValue(), newValueY.doubleValue());
        Point2D newPosition = world.getUnitList().stream().filter(u -> (u != activeUnit)).map(u -> {
                    if (!canRide(u) && u.getBounds().intersects(newFixedBounds)) {
//                        System.out.println("OtherUnit =" + u.getClass() + ";activeUnit = [" + activeUnit
//                                .getClass() +
//                                "]");
                        switch (activeUnit.getDirection()) {
                            case UP: {
                                double deltaY = u.getBounds().getMaxY() - newBounds.getMinY();
                                return result.add(0, deltaY);
                            }
                            case LEFT: {
                                double deltaX = u.getBounds().getMaxX() - newBounds.getMinX();
                                return result.add(deltaX, 0);
                            }
                            case DOWN: {
                                double deltaY = newBounds.getMaxY() - u.getBounds().getMinY();
                                return result.add(0, -deltaY);

                            }
                            case RIGHT: {
                                double deltaX = newBounds.getMaxX() - u.getBounds().getMinX();
                                return result.add(-deltaX, 0);
                            }
                        }
                    }
                    return result;
                }
        ).filter(point2D -> (!point2D.equals(result))).findFirst().orElse(result);
        activeUnit.setX(newPosition.getX());
        activeUnit.setY(newPosition.getY());
    }


    private Boolean canRide(GameUnit unit) {
        Boolean result = false;
        if (unit instanceof TileUnit) {
            result = (rideTiles.contains(((TileUnit) unit).getTileType()));
        }
        return result;
    }


}
