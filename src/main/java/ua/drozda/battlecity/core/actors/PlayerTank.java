package ua.drozda.battlecity.core.actors;

import ua.drozda.battlecity.core.TankType;
import ua.drozda.battlecity.core.collisions.CollisionManager;

/**
 * Created by GFH on 12.05.2015.
 */
public class PlayerTank extends Tank {

    public PlayerTank(CollisionManager collisionManager) {
        super(collisionManager);

    }

    public PlayerTank(CollisionManager collisionManager, TankType tankType) {
        super(collisionManager, tankType);
    }

    public PlayerTank(Double velocity, CollisionManager collisionManager, TankType tankType) {
        super(velocity, collisionManager, tankType);
    }

    public PlayerTank(Double velocity, CollisionManager collisionManager) {
        super(velocity, collisionManager);
    }
}
