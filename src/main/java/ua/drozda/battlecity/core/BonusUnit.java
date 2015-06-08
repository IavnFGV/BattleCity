package ua.drozda.battlecity.core;

/**
 * Created by GFH on 09.06.2015.
 */
public class BonusUnit extends GameUnit {
    private BonusType bonusType;

    public BonusUnit(double x, double y, double width, double height, Long currentTime, BonusType bonusType) {
        super(x, y, width, height, 0L, currentTime, BasicState.ACTIVE);
        this.bonusType = bonusType;
    }

    public BonusType getBonusType() {
        return bonusType;
    }

    public void setBonusType(BonusType bonusType) {
        this.bonusType = bonusType;
    }

    public enum BonusType {
        HELMET,
        CLOCK,
        SPADE,
        STAR,
        GRANADE,
        TANK,
        GUN;
    }
}
