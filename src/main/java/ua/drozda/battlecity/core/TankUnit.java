package ua.drozda.battlecity.core;

import javafx.geometry.Bounds;
import ua.drozda.battlecity.fx.FxWorld;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by GFH on 14.06.2015.
 */
public class TankUnit extends ActiveUnit {
    public static Set<TankType> playerTanks = EnumSet.of(TankType.FIRST_PLAYER, TankType.SECOND_PLAYER);
    protected TankType tankType;
    protected BonusStrategy bonusStrategy;
    protected Integer starCount = 0;


    public TankUnit(double x, double y, double width, double height, Long lives, Long currentTime,
                    BasicState currentBasicState, Direction direction, Long velocity, Function<GameUnit, Boolean> registerAction,
                    Function<GameUnit, Boolean> unRegisterAction, TankType tankType) {
        super(x, y, width, height, lives, currentTime, currentBasicState, direction, velocity, registerAction,
                unRegisterAction);
        setTankType(tankType);
        if (playerTanks.contains(tankType)) {
            setBonusStrategy(this.new PlayerBonusStrategy());
        } else {
            setBonusStrategy(this.new EnemyBonusStrategy());
        }
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

    protected void addBonus(BonusUnit.BonusType bonusType) {
        getBonusStrategy().perform(bonusType);
    }

    public BonusStrategy getBonusStrategy() {
        return bonusStrategy;
    }

    public void setBonusStrategy(BonusStrategy bonusStrategy) {
        this.bonusStrategy = bonusStrategy;
    }

    @Override
    public String toString() {
        return "TankUnit{" +
                "tankType=" + tankType +
                ", bonusStrategy=" + bonusStrategy +
                ", starCount=" + starCount +
                "} " + super.toString();
    }

    @Override
    public MoveStrategy getMoveStrategy() {
        if (this.moveStrategy == null) {
            //ActiveUnit.class.getDeclaredClasses()
            this.setMoveStrategy(this.new TankMoveStrategy());
        }
        return moveStrategy;
    }

    @Override
    protected Boolean checkBounds(Bounds bounds) {
        if (bounds.getMinX() < 0 ||
                bounds.getMinX() > 24 * FxWorld.tileSize ||
                bounds.getMinY() < 0 ||
                bounds.getMinY() > 24 * FxWorld.tileSize
                ) {
            return false;
        }
        return super.checkBounds(bounds);
    }

    public enum TankType {
        FIRST_PLAYER, SECOND_PLAYER, SIMPLE_ENEMY, FAST_ENEMY, POWER_ENEMY, ARMOR_ENEMY, SIMPLE_ENEMY_X, FAST_ENEMY_X, POWER_ENEMY_X, ARMOR_ENEMY_X
    }

    protected abstract class BonusStrategy {
        public abstract void perform(BonusUnit.BonusType bonusType);
    }

    protected class PlayerBonusStrategy extends BonusStrategy {
        @Override
        public void perform(BonusUnit.BonusType bonusType) {
            switch (bonusType) {
                case HELMET:
                    break;
                case CLOCK:
                    break;
                case SPADE:
                    break;
                case STAR:
                    setStarCount(getStarCount() + 1);
                    break;
                case GRANADE:
                    break;
                case TANK:
                    break;
                case GUN:
                    break;
            }
        }
    }

    protected class EnemyBonusStrategy extends BonusStrategy {
        @Override
        public void perform(BonusUnit.BonusType bonusType) {
            switch (bonusType) {
                case HELMET:
                    break;
                case CLOCK:
                    break;
                case SPADE:
                    break;
                case STAR:
                    break;
                case GRANADE:
                    break;
                case TANK:
                    break;
                case GUN:
                    break;
            }
        }
    }

    protected class TankMoveStrategy extends MoveStrategy {
        @Override
        public void perform(Long deltaTime) {
            super.perform(deltaTime);
        }
    }

}
