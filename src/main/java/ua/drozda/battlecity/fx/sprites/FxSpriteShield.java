package ua.drozda.battlecity.fx.sprites;

import javafx.geometry.Rectangle2D;
import ua.drozda.battlecity.core.GameUnit;
import ua.drozda.battlecity.core.TankUnit;
import ua.drozda.battlecity.fx.FxWorld;

/**
 * Created by GFH on 12.07.2015.
 */
public class FxSpriteShield extends FxSprite<TankUnit> {
    static private Long shieldTimer = GameUnit.ONE_SECOND / 20;
    private static Rectangle2D[] shieldSprites = {new Rectangle2D(228, 288, FxWorld.tankSize, FxWorld.tankSize),
            new Rectangle2D(260, 288, FxWorld.tankSize, FxWorld.tankSize)};

    public FxSpriteShield(TankUnit gameUnit) {
        super(gameUnit);
        maxToggle = 2;
        setViewPort(shieldSprites[this.curToggle]);
    }

    @Override
    public Boolean canToggle(Long now) {
        return (super.canToggle(now)) && (getGameUnit().getBasicState() == GameUnit.BasicState.ACTIVE) && (now - toggleTime >= shieldTimer);
    }

    @Override
    public void doToggle(Long now) {
        curToggle = ++curToggle % getMaxToggle();
        updateSprite();
        toggleTime = now;
    }


    @Override
    protected int getMaxToggle() {
        return maxToggle;
    }

    @Override
    protected void updateSprite() {
        setViewPort(shieldSprites[this.curToggle]);
    }
}
