package ua.drozda.battlecity.core;

import java.util.function.Function;

/**
 * Created by GFH on 14.06.2015.
 */
public class BulletUnit extends ActiveUnit {
    protected TankUnit owner;

    public BulletUnit(double x, double y, double width, double height, Long lives, Long currentTime, BasicState
            currentBasicState, Direction direction, Long velocity, TankUnit owner, Function<GameUnit, Boolean> registerAction,
                      Function<GameUnit, Boolean> unRegisterAction) {
        super(x, y, width, height, lives, currentTime, currentBasicState, direction, velocity, registerAction,
                unRegisterAction);
        setOwner(owner);
    }

    public TankUnit getOwner() {
        return owner;
    }

    public void setOwner(TankUnit owner) {
        this.owner = owner;
    }
}
