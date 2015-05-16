package ua.drozda.battlecity.core.actors;

import javafx.geometry.Point2D;

import java.util.EnumSet;

/**
 * Created by GFH on 16.05.2015.
 */
public class MoveStrategy<T extends Actor> {

    private static EnumSet<ActorState> movingStates = EnumSet.of(ActorState.STATE_ALIVE);
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
        if (actor.isPause()) {
            return;
        }
        if (canMove()) {
            moveActor();
        }
    }

    protected boolean canMove() { // this method can be override in children
        return movingStates.contains(actor.getActorState());
    }

    protected void moveActor() { // this method can be override in children

        if (actor.getVelocity() > 0) {

            Point2D newPosition = Point2D.ZERO;
            Double deltaPosition = Double.valueOf((actor.getDeltaTime() * actor.getVelocity()));
            //so we cant call every loop - actor will froze in one point!!!!!!
            switch (actor.getDirection()) {
                case UP:
                    newPosition = actor.getPosition().add(0, deltaPosition);
                    break;
                case RIGHT:
                    newPosition = actor.getPosition().add(deltaPosition, 0);
                    break;
                case DOWN:
                    newPosition = actor.getPosition().add(0, -deltaPosition);
                    break;
                case LEFT:
                    newPosition = actor.getPosition().add(-deltaPosition, 0);
                    break;
            }

            if (actor.getCollisionManager().canMove(actor, newPosition)) {
                actor.setPosition(newPosition);
            }
        }
    }
}
