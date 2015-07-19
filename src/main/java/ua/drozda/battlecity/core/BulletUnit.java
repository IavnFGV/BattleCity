package ua.drozda.battlecity.core;

import ua.drozda.battlecity.core.collisions.CollisionManager;
import ua.drozda.battlecity.fx.FxWorld;

import java.util.function.Function;

/**
 * Created by GFH on 14.06.2015.
 */
public class BulletUnit extends ActiveUnit {
    protected TankUnit owner;

    public BulletUnit(double x, double y, double width, double height, Integer lives, BasicState
            currentBasicState, Direction direction, Long velocity, TankUnit owner, Function<GameUnit, Boolean> registerAction,
                      Function<GameUnit, Boolean> unRegisterAction, CollisionManager collisionManager) {
        super(x, y, width, height, lives, currentBasicState, direction, velocity, registerAction,
                unRegisterAction, collisionManager);
        setOwner(owner);
    }

    @Override
    protected Boolean checkBounds(double newX, double newY) {
        if (newX < 0 - 8 ||
                newX > 24 * FxWorld.tileSize + 32 ||
                newY < 0 - 8 ||
                newY > 24 * FxWorld.tileSize + 32
                ) {
            this.decLifes(3);
            return false;
        }

        return super.checkBounds(newX, newY);
    }

    @Override
    protected Long getTimeInState(BasicState state) {
//        if (state == BasicState.EXPLODING) {
//            return 3*ONE_SECOND ;
//        }
        return super.getTimeInState(state);
    }

    public TankUnit getOwner() {
        return owner;
    }

    public void setOwner(TankUnit owner) {
        this.owner = owner;
    }

    @Override
    public MoveStrategy getMoveStrategy() {
        if (this.moveStrategy == null) {
            //ActiveUnit.class.getDeclaredClasses()
            this.setMoveStrategy(this.new BulletMoveStrategy());
        }
        return moveStrategy;
    }

    protected class BulletMoveStrategy extends MoveStrategy {
        @Override
        public void perform(Long deltaTime) {
            if (calcNewPosition(deltaTime)) {
                //do not need to check on Bullet
                setX(newX);
                setY(newY);
            }
        }
    }

}
