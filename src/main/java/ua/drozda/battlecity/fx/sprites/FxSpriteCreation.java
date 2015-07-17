package ua.drozda.battlecity.fx.sprites;

import javafx.geometry.Rectangle2D;
import ua.drozda.battlecity.core.GameUnit;
import ua.drozda.battlecity.core.TankUnit;
import ua.drozda.battlecity.fx.FxWorld;

/**
 * Created by GFH on 12.07.2015.
 */
public class FxSpriteCreation extends FxSprite<TankUnit> {
    private static Long tankToggleTimer = GameUnit.ONE_SECOND / 6;
    private static Rectangle2D[] creationTiles;

    static {
        creationTiles = new Rectangle2D[10];
        for (int i = 0; i < 10; i++) {
            creationTiles[i] = new Rectangle2D(228 + FxWorld.tankSize * i, 320, FxWorld.tankSize,
                    FxWorld.tankSize);
        }
    }

    private boolean d = true;

    public FxSpriteCreation(TankUnit gameUnit) {
        super(gameUnit);
        maxToggle = 10;
    }

    @Override
    public Boolean canToggle(Long now) {
        return (super.canToggle(now)) && (now - toggleTime >= tankToggleTimer);
    }

    @Override
    protected void updateSprite() {
        setViewPort(creationTiles[curToggle]);
    }

    @Override
    protected int getMaxToggle() {
        return maxToggle;
    }

    @Override
    public void doToggle(Long now) {
        if (curToggle == 0) {
            d = true;
        }
        if (curToggle == getMaxToggle() - 1) {
            d = false;
        }
        if (d) {
            curToggle = ++curToggle % getMaxToggle();
        } else {
            curToggle--;
        }
        updateSprite();
        toggleTime = now;
    }
}
