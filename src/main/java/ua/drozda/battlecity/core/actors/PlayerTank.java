package ua.drozda.battlecity.core.actors;

import ua.drozda.battlecity.core.collisions.CollisionManager;

/**
 * Created by GFH on 12.05.2015.
 */
public class PlayerTank extends Tank {

    public PlayerTank(CollisionManager collisionManager) {
        super(collisionManager);
    }

    public PlayerTank(Double velocity, CollisionManager collisionManager) {
        super(velocity, collisionManager);
    }
}
