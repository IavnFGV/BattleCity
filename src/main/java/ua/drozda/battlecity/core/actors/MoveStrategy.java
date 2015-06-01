package ua.drozda.battlecity.core.actors;

import javafx.geometry.Point2D;

import java.util.EnumSet;

/**
 * Created by GFH on 16.05.2015.
 */
public class MoveStrategy<T extends Actor> {

    protected static EnumSet<ActorState> movingStates = EnumSet.of(ActorState.STATE_ALIVE_0); //todo more states
    private T actor;

    public MoveStrategy(T actor) {
        this.actor = actor;
    }


    public T getActor() {
        return actor;
    }

    public void setActor(T actor) {
        this.actor = actor;
    }

    public void performMove() {
        if (actor.isPause()) { // todo is it necessary
            return;
        }
        Point2D newPosition = calcNewPosition();
        applyNewPosition(newPosition);
    }

    protected Point2D calcNewPosition() { // this method can be override in children

        if (actor.getVelocity() > 0) {
            Point2D newPosition = Point2D.ZERO;
            Double deltaPosition = Double.valueOf((actor.getDeltaTime() * actor.getVelocity()));
            //so we cant call every loop - actor will froze in one point!!!!!!
            switch (actor.getDirection()) {
                case UP:
                    newPosition = actor.getPosition().add(0, -deltaPosition);
                    break;
                case RIGHT:
                    newPosition = actor.getPosition().add(deltaPosition, 0);
                    break;
                case DOWN:
                    newPosition = actor.getPosition().add(0, deltaPosition);
                    break;
                case LEFT:
                    newPosition = actor.getPosition().add(-deltaPosition, 0);
                    break;
            }
            return actor.getCollisionManager().newPosition(actor, newPosition);
        }
        return actor.getPosition();
    }

    protected void applyNewPosition(Point2D newPosition) {// this method can be override in children
        actor.setPosition(newPosition);
    }
}
