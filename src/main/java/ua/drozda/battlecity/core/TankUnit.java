package ua.drozda.battlecity.core;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point2D;
import ua.drozda.battlecity.core.collisions.CollisionManager;
import ua.drozda.battlecity.fx.FxWorld;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Function;

import static java.lang.Math.abs;
import static java.lang.Math.round;

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

    protected FireStrategy[] fireStrategy;
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
            fireStrategy = new FireStrategy[4];
            fireStrategy[0] = new SlowFireStrategy();//TODO  ADD CLASSES
        } else {
            setBonusStrategy(this.new EnemyBonusStrategy());
            fireStrategy = new FireStrategy[2];
            fireStrategy[0] = new SlowFireStrategy();
            fireStrategy[1] = new SlowFireStrategy(); //TODO ADD CLASSES
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

    public void fire() {
        if (!isPause()) {
            getFireStrategy().perform();
        }
    }

    public FireStrategy getFireStrategy() {
        if (playerTanks.contains(getTankType()))
            return fireStrategy[getStarCount()];
        if ((getTankType() == TankType.POWER_ENEMY)
                || (getTankType() == TankType.POWER_ENEMY_X)) {
            return fireStrategy[1];
        }
        return fireStrategy[0];
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

    public void setShield(Long shieldTime) {
        setLeftTimeInShieldState(shieldTime);
    }

    public final BooleanProperty shildProperty() {
        if (shildProperty == null) {
            shildProperty = new SimpleBooleanProperty();
        }
        return shildProperty;
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

    @Override
    public void setDirection(Direction direction) {
        if (!isPause()) {
            if ((direction != getDirection()) && (getBasicState() == BasicState.ACTIVE)) {
                fixPosition();
            }
            this.direction = direction;
        }
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

    private void fixPosition() {
        long cellSize = round(this.getWidth() / 2);
        Long x = nearest(getX(), cellSize);
        Long y = nearest(getY(), cellSize);
        Double newX = getX();
        Double newY = getY();

        if (abs(newX - x) < (cellSize / 2 + 1)) {
            newX = Double.valueOf(x);
        }
        if (abs(newY - y) < (cellSize / 2 + 1)) {
            newY = Double.valueOf(y);
        }
//        setNewBounds(new BoundingBox(newX, newY, cellSize * 2, cellSize * 2));
        if (collisionManager != null) {
            collisionManager.fixPosition(this, newX, newY);
        }
    }

    private long nearest(double num, long base) {
        return (round(num / (base * 1.)) * base);
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

    protected abstract class FireStrategy {
        private int activeBullets;

        public int getActiveBullets() {
            return activeBullets;
        }

        public void perform() {
            Point2D startPosition = getStartPosition();
            if (canFire()) {
                BulletUnit bulletUnit = new BulletUnit(startPosition.getX(), startPosition.getY(), 8, 8, getBulletLifes(),
                        BasicState.ACTIVE,
                        getDirection(),
                        getBulletSpeed(),
                        TankUnit.this,
                        this::onActiveAction,
                        this::onDeadAction, collisionManager); // TODO WRONG MAGIC NUMBERS
                bulletUnit.initUnit(TankUnit.this.getLastHeartBeat());
                bulletUnit.setEngineOn(true);
            }
        }

        protected Point2D getStartPosition() {
            Point2D result = new Point2D(getX(), getY());
            switch (getDirection()) {
                case UP:
                    return result.add(16 - 4, -8); //size of image
                case LEFT:
                    return result.add(-8, 16 - 4); // size of image
                case DOWN:
                    return result.add(16 - 4, 32);
                case RIGHT:
                    return result.add(32, 16 - 4);
            }
            return result;
        }

        protected boolean canFire() {
            return true;
        }

        protected abstract int getBulletLifes();

        protected abstract long getBulletSpeed();

        public Boolean onDeadAction(GameUnit gameUnit) {
            decActiveBullets();
            return getUnRegistrateAction().apply(gameUnit);
        }

        public void decActiveBullets() {
            this.activeBullets -= 1;
        }

        public Boolean onActiveAction(GameUnit gameUnit) {
            incActiveBullets();
            return getRegistrateAction().apply(gameUnit);
        }

        public void incActiveBullets() {
            this.activeBullets += 1;
        }

    }


    protected class SlowFireStrategy extends FireStrategy {

        @Override
        protected boolean canFire() {
            return getActiveBullets() == 0;
        }

        @Override
        protected int getBulletLifes() {
            return 1;
        }

        @Override
        protected long getBulletSpeed() {
            return 4;
        }
    }
}
