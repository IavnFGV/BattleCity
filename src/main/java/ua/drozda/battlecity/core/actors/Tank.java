package ua.drozda.battlecity.core.actors;

import ua.drozda.battlecity.core.collisions.CollisionManager;

/**
 * Created by GFH on 14.05.2015.
 */
public class Tank extends Actor {

    public Tank(CollisionManager collisionManager) {
        super(collisionManager);
    }

    public Tank(Double velocity, CollisionManager collisionManager) {
        super(velocity, collisionManager);
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
