package ua.drozda.battlecity.core.actors;

import ua.drozda.battlecity.core.collisions.CollisionManager;

/**
 * Created by GFH on 12.05.2015.
 */
public class EnemyTank extends Tank {
    public EnemyTank(CollisionManager collisionManager) {
        super(collisionManager);
    }

    public EnemyTank(Double velocity, CollisionManager collisionManager) {
        super(velocity, collisionManager);
    }
}
