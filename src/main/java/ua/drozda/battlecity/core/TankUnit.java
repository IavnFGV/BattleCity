package ua.drozda.battlecity.core;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import ua.drozda.battlecity.core.collisions.CollisionManager;
import ua.drozda.battlecity.fx.FxWorld;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by GFH on 14.06.2015.
 */
public class TankUnit extends ActiveUnit {
    public static Set<TankType> playerTanks = EnumSet.of(TankType.FIRST_PLAYER, TankType.SECOND_PLAYER);
    static private Long timeInShieldOnRespawn = 3 * GameUnit.ONE_SECOND;
    protected TankType tankType;
    protected BonusStrategy bonusStrategy;
    protected Integer starCount = 0;
    protected Boolean shield = false;
    protected Long leftTimeInShieldState = 0L;//

    private BooleanProperty shildProperty;

    public TankUnit(double x, double y, double width, double height, Integer lives, Long currentTime,
                    BasicState currentBasicState, Direction direction, Long velocity, Function<GameUnit, Boolean> registerAction,
                    Function<GameUnit, Boolean> unRegisterAction, TankType tankType, CollisionManager collisionManager) {
        super(x, y, width, height, lives, currentBasicState, direction, velocity, registerAction,
                unRegisterAction, collisionManager);
        setTankType(tankType);
        if (playerTanks.contains(tankType)) {
            setBonusStrategy(this.new PlayerBonusStrategy());
            setHeartBeatStrategy(new TankHeartBeatStrategy());
        } else {
            setBonusStrategy(this.new EnemyBonusStrategy());
        }
    }

    public static Long getTimeInShieldOnRespawn() {
        return timeInShieldOnRespawn;
    }

    public Boolean isShield() {
        return getLeftTimeInShieldState() > 0;
    }

    protected Long getLeftTimeInShieldState() {
        return leftTimeInShieldState;
    }

    protected void setLeftTimeInShieldState(Long leftTimeInShieldState) {
        this.leftTimeInShieldState = leftTimeInShieldState;
        if (leftTimeInShieldState <= 0l) {
            shildProperty().setValue(false);
        } else {
            shildProperty().setValue(true);
        }
    }

    public void setShield(Long shieldTime) {
        setLeftTimeInShieldState(shieldTime);
    }

    public final BooleanProperty shildProperty() {
        if (shildProperty == null) {
            shildProperty = new SimpleBooleanProperty();
        }
        return shildProperty;
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
    protected Boolean checkBounds(double newX, double newY) {
        if (newX < 0 ||
                newX > 24 * FxWorld.tileSize ||
                newY < 0 ||
                newY > 24 * FxWorld.tileSize
                ) {
            return false;
        }

        return super.checkBounds(newX, newY);
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

    protected class TankHeartBeatStrategy extends GameUnit.BasicHeartBeatStrategy {
        @Override
        public void perform(Long deltaTime) {
            BasicState oldState = TankUnit.this.getBasicState();
            super.perform(deltaTime);
            BasicState newState = TankUnit.this.getBasicState();
            if (oldState == BasicState.CREATING && newState == BasicState.ACTIVE) {
                TankUnit.this.setShield(getTimeInShieldOnRespawn());
            }
            changeShieldState(deltaTime);
        }

        public void changeShieldState(Long deltaTime) {
            if (isShield()) {
                setLeftTimeInShieldState(getLeftTimeInShieldState() - deltaTime);
                if ((getLeftTimeInShieldState()) <= 0) {
                    setChanged();
                }
            }

        }
    }
}
