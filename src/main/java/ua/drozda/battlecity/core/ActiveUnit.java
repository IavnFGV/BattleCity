package ua.drozda.battlecity.core;

import ua.drozda.battlecity.core.actors.MoveStrategy;

/**
 * Created by GFH on 10.06.2015.
 */
public abstract class ActiveUnit extends GameUnit {
    protected Boolean engineOn;
    protected MoveStrategy moveStrategy;
    private Direction direction;
    private Long velocity;

    public ActiveUnit(double x, double y, double width, double height, Long lives, Long currentTime, BasicState
            currentBasicState, Direction direction, Long velocity) {
        super(x, y, width, height, lives, currentTime, currentBasicState);
        this.direction = direction;
        this.velocity = velocity;
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

    public enum Direction {
        UP,
        LEFT,
        DOWN,
        RIGHT;
    }

    protected class ActiveHeartBeatStratege extends BasicHeartBeatStrategy {
        @Override
        public void changeBasicState(Long now) {
            super.changeBasicState(now);
        }
    }
}
