package ua.drozda.battlecity.core;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

import java.util.function.Function;

/**
 * Created by GFH on 10.06.2015.
 */
public abstract class ActiveUnit extends GameUnit {
    protected Boolean engineOn;
    protected MoveStrategy moveStrategy;
    protected Long lastMove;
    private Bounds newBounds;
    private Direction direction;
    private Long velocity;

    public ActiveUnit(double x, double y, double width, double height, Long lives, Long currentTime, BasicState
            currentBasicState, Direction direction, Long velocity, Function<GameUnit, Boolean> registerAction, Function<GameUnit, Boolean> unRegisterAction) {
        super(x, y, width, height, lives, currentTime, currentBasicState, registerAction, unRegisterAction);
        this.setDirection(direction);
        this.setVelocity(velocity);
        this.setNewBounds(getBounds());
    }

    public Bounds getNewBounds() {
        return newBounds;
    }

    public void setNewBounds(Bounds newBounds) {
        this.newBounds = newBounds;
    }

    public Boolean isEngineOn() {
        return engineOn;
    }

    public void setEngineOn(Boolean engineOn) {
        this.engineOn = engineOn;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Long getVelocity() {
        return velocity;
    }

    public void setVelocity(Long velocity) {
        this.velocity = velocity;
    }

    public void move(Long now) {

        if (!isPause()) {
            getMoveStrategy().perform(now - getLastMove());
        }
        setLastMove(now);
        ///  notifyObservers(); TODO AFTER COLLISION AFTERCHECK
    }

    public MoveStrategy getMoveStrategy() {
        if (this.moveStrategy == null) {
            this.setMoveStrategy(this.new MoveStrategy());
        }
        return moveStrategy;
    }

    public Long getLastMove() {
        return lastMove;
    }

    public void setLastMove(Long lastMove) {
        this.lastMove = lastMove;
    }

    public void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }


    public enum Direction {
        UP,
        LEFT,
        DOWN,
        RIGHT;
    }

    protected class MoveStrategy {

        public void perform(Long deltaTime) {
            calcNewPosition(deltaTime);
        }

        protected Bounds calcNewPosition(Long deltaTime) { // this method can be override in children
            if (isEngineOn() && getVelocity() > 0) {
                Double deltaPosition = Double.valueOf((deltaTime * getVelocity())); // TODO MAY FROZE???
                switch (getDirection()) {
                    case UP:
                        newBounds = new BoundingBox(getBounds().getMinX(), getBounds().getMinY() - deltaPosition,
                                getBounds().getWidth(), getBounds().getHeight());
                        break;
                    case LEFT:
                        newBounds = new BoundingBox(getBounds().getMinX() - deltaPosition, getBounds().getMinY(),
                                getBounds().getWidth(), getBounds().getHeight());
                        break;
                    case DOWN:
                        newBounds = new BoundingBox(getBounds().getMinX(), getBounds().getMinY() + deltaPosition,
                                getBounds().getWidth(), getBounds().getHeight());
                        break;
                    case RIGHT:
                        newBounds = new BoundingBox(getBounds().getMinX() + deltaPosition, getBounds().getMinY(),
                                getBounds().getWidth(), getBounds().getHeight());
                        break;
                }
            }
            return newBounds;
        }
    }
}
