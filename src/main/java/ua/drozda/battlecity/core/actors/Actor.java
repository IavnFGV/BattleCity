package ua.drozda.battlecity.core.actors;

import javafx.geometry.Point2D;
import ua.drozda.battlecity.core.interfaces.Updatable;

/**
 * Created by GFH on 12.05.2015.
 */
public abstract class Actor implements Updatable {

    private int updateInterval = 16666666; // 1/60 second (16666666,66666667)
    private long lastUpdate;
    private Point2D position;

    private ActorController controller;

    private Direction direction;

    private Integer velocity;

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Actor(ActorController controller) {
        super();
        this.controller = controller;
    }

    public Integer getVelocity() {
        if (controller == null) {
            return velocity;
        } else {
            return controller.getVelocity();
        }
    }

    public void setVelocity(Integer velocity) {
        this.velocity = velocity;
    }

    public Actor(Direction direction, Integer velocity) {
        super();
        this.direction = direction;
        this.velocity = velocity;

    }

    public Direction getDirection() {
        if (controller == null) {
            return direction;
        } else {
            return controller.getDirection();
        }
    }

    public void update(Long nanoSeconds) {
        if ((controller.getVelocity() > 0) && ((nanoSeconds - lastUpdate) >= updateInterval)) {
            switch (getDirection()) {
                case DOWN:
                    position.add(0, -getVelocity() / updateInterval);
                    if (position.getY() < 0) {
                        position = new Point2D(position.getX(), 0);
                    }
                    break;
                case LEFT:
                    position.add(-getVelocity() / updateInterval, 0);
                    if (position.getX() < 0) {
                        position = new Point2D(0, position.getY());
                    }
                    break;
                case RIGHT:
                    position.add(getVelocity() / updateInterval, 0);
                    if (position.getX() > 26) {
                        position = new Point2D(26, position.getY());
                    }
                    break;
                case UP:
                    position.add(0, getVelocity() / updateInterval);
                    if (position.getY() > 26) {
                        position = new Point2D(position.getX(), 26);
                    }
                    break;
            }
        }
    }

    public Object update(Object... args) {
        if (args[0] instanceof Long) {
            update((Long) args[0]);
        }
        return null;
    }
}
