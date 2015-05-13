package ua.drozda.battlecity.core.actors;

import ua.drozda.battlecity.core.interfaces.Updatable;

/**
 * Created by GFH on 12.05.2015.
 */
public class ActorController implements Updatable {

    private Integer velocity;
    private Integer acceleration;
    private Direction direction;
    private Actor actor;
    private boolean isStop;

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Actor getActor() {
        return actor;
    }

    public void update(Object... args) {
        int size = args.length;
        if (size >= 2) {
            direction = (Direction) args[1];
        }
        if (size >= 3) {
            isStop = false;
            velocity = (Integer) args[2];
        } else {
            isStop = true;
        }
        if (size >= 4) {
            acceleration = (Integer) args[3];
        } else {
            acceleration = 0;
        }
        actor.update(args[0]);
    }

    public Integer getVelocity() {
        return isStop ? 0 : velocity;
    }

    public void setVelocity(Integer velocity) {
        this.velocity = velocity;
    }

    public Integer getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Integer acceleration) {
        this.acceleration = acceleration;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
