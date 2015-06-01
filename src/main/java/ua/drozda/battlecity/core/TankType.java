package ua.drozda.battlecity.core;

/**
 * Created by GFH on 01.06.2015.
 */
public enum TankType {
    FirstPlayer(false) {},
    SecondPlayer(false) {},
    SimpleEnemy(false) {},
    SimpleEnemyX(true) {},
    FastEnemy(false) {},
    FastEnemyX(true) {},
    PowerEnemy(false) {},
    PowerEnemyX(true) {},
    ArmorEnemy(false) {},
    ArmorEnemyX(true) {};

    private boolean bonus;

    TankType(boolean bonus) {
        this.bonus = bonus;
    }

    public Boolean isBonus() {
        return bonus;
    }
}
