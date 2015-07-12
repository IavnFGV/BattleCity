package ua.drozda.battlecity.fx;

import javafx.geometry.Rectangle2D;
import ua.drozda.battlecity.core.TankUnit;

/**
 * Created by GFH on 12.07.2015.
 */
public class FxSpriteShield extends FxSprite<TankUnit> {
    static private Long shieldTimer = 5000_000l;
    private static Rectangle2D[] shieldSprites = {new Rectangle2D(228, 288, FxWorld.tankSize, FxWorld.tankSize),
            new Rectangle2D(260, 288, FxWorld.tankSize, FxWorld.tankSize)};

    public FxSpriteShield(TankUnit gameUnit) {
        super(gameUnit);
        maxToggle = 2;
        setViewPort(shieldSprites[this.curToggle]);
    }

    @Override
    public void toggle(Long now) {
        curToggle = ++curToggle % maxToggle;
        updateSprite();
        toggleTime = now;
    }

    @Override
    public Boolean canToggle(Long now) {
        return now - toggleTime >= shieldTimer;
    }

    @Override
    protected void updateSprite() {
        setViewPort(shieldSprites[this.curToggle]);
    }
}
