package ua.drozda.battlecity.core.actors;

import ua.drozda.battlecity.core.collisions.CollisionManager;

/**
 * Created by GFH on 12.05.2015.
 */
public class Bullet extends Actor {
    private Integer power = 100;
    private Tank ownerTank;

    public Bullet(Double velocity, CollisionManager collisionManager, Integer power, Tank ownerTank) {
        super(velocity, collisionManager);
        this.ownerTank = ownerTank;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Tank getOwnerTank() {
        return ownerTank;
    }

    public void setOwnerTank(Tank ownerTank) {
        this.ownerTank = ownerTank;
    }

    @Override
    protected ActorState getIntialState() {
        return ActorState.STATE_ALIVE_0;
    }

    @Override
    public Integer getMaxToggle() {
        return 0;
    }

    @Override
    public void nextToggle() {
    }
}
