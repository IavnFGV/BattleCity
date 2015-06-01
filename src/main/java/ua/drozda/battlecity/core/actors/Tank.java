package ua.drozda.battlecity.core.actors;

import javafx.geometry.Point2D;
import ua.drozda.battlecity.core.TankType;
import ua.drozda.battlecity.core.collisions.CollisionManager;

/**
 * Created by GFH on 14.05.2015.
 */
public class Tank extends Actor {

    protected TankType tankType;
    protected Integer starCount = 0;

    public Tank(Double velocity, CollisionManager collisionManager, TankType tankType) {
        super(velocity, collisionManager);
        this.tankType = tankType;

    }

    public Tank(CollisionManager collisionManager, TankType tankType) {
        super(collisionManager);
        this.tankType = tankType;
        if (tankType == TankType.FirstPlayer) {
            setPosition(new Point2D(0, 0));
        }
    }

    public Tank(CollisionManager collisionManager) {
        super(collisionManager);
    }

    public Tank(Double velocity, CollisionManager collisionManager) {
        super(velocity, collisionManager);
    }

    public TankType getTankType() {
        return tankType;
    }

    public void setTankType(TankType tankType) {
        this.tankType = tankType;
    }

    public Integer getStarCount() {

        return starCount;
    }

    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }

    @Override
    public Integer getMaxToggle() {
        return 2;
    }

    @Override
    public void nextToggle() {
        heartState = (heartState + 1) % getMaxToggle();
    }

}
