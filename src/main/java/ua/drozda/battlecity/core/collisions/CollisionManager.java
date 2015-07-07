package ua.drozda.battlecity.core.collisions;


import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import ua.drozda.battlecity.core.ActiveUnit;
import ua.drozda.battlecity.core.GameUnit;
import ua.drozda.battlecity.core.TileUnit;
import ua.drozda.battlecity.core.World;

import java.util.EnumSet;
import java.util.Set;

/**
 * Created by GFH on 14.05.2015.
 */
public class CollisionManager {
    double localNewX;
    double localNewY;
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
//        Predicate<GameUnit> onlyTank = gameUnit -> (gameUnit instanceof TankUnit);
//        Predicate<GameUnit> notMe = gameUnit -> (gameUnit != activeUnit);
//        Predicate<GameUnit> onlyTankAndNotMe = onlyTank.and(notMe);
//        List<GameUnit> tanks = world.getUnitList().stream().filter(onlyTankAndNotMe).collect(Collectors.toList());
//        if (tanks.size() == 0) {// no more tanks
//            activeUnit.setBounds(activeUnit.getNewBounds());
//            return;
//        }
//        tanks.forEach(gameUnit -> {
//            Bounds newBounds = new BoundingBox(activeUnit.getNewBounds().getMinX() + 1,
//                    activeUnit.getNewBounds().getMinY() + 1,
//                    activeUnit.getNewBounds().getWidth() - 2,
//                    activeUnit.getNewBounds().getHeight() - 2);
//            if (!newBounds.intersects(gameUnit.getBounds())) {
//                activeUnit.setBounds(activeUnit.getNewBounds());
//            }
//        });
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
        localNewX = newValueX.doubleValue();
        localNewY = newValueY.doubleValue();
        world.getUnitList().stream().filter(u -> (u != activeUnit))
                .forEach(u -> {
                            if (!canRide(u) && u.getBounds().intersects(newFixedBounds)) {
                                System.out.println("OtherUnit =" + u.getClass() + ";activeUnit = [" + activeUnit
                                        .getClass() +
                                        "]");
                                switch (activeUnit.getDirection()) {
                                    case UP: {
                                        double deltaY = u.getBounds().getMaxY() - newBounds.getMinY();
                                        localNewY = newValueY.intValue() + deltaY;
                                    }
                                    break;
                                    case LEFT: {
                                        double deltaX = u.getBounds().getMaxX() - newBounds.getMinX();
                                        localNewX = newValueX.intValue() + deltaX;
                                    }
                                    break;
                                    case DOWN: {
                                        double deltaY = newBounds.getMaxY() - u.getBounds().getMinY();
                                        localNewY = newValueY.intValue() - deltaY;

                                    }
                                    break;
                                    case RIGHT: {
                                        double deltaX = newBounds.getMaxX() - u.getBounds().getMinX();
                                        localNewX = newValueX.intValue() - deltaX;
                                    }
                                    break;
                                }
                                return;
                            }
                        }
                );
        activeUnit.setX(localNewX);
        activeUnit.setY(localNewY);
        return;

    }

    private Boolean canRide(GameUnit unit) {
        Boolean result = false;
        if (unit instanceof TileUnit) {
            result = (rideTiles.contains(((TileUnit) unit).getTileType()));
        }
        return result;
    }


}
