package ua.drozda.battlecity.fx.sprites;

import javafx.geometry.Rectangle2D;
import ua.drozda.battlecity.core.ActiveUnit;
import ua.drozda.battlecity.core.BulletUnit;
import ua.drozda.battlecity.fx.FxWorld;

/**
 * Created by GFH on 19.07.2015.
 */
public class FxSpriteSmallExplosion extends FxSprite<BulletUnit> {
    static private Long explosionTimer = ActiveUnit.ONE_SECOND / 10;
    private static Rectangle2D[] explosionTiles;

    static {
        explosionTiles = new Rectangle2D[3];
        for (int i = 0; i < 3; i++) {
            explosionTiles[i] = new Rectangle2D(228 + FxWorld.tankSize * i,
                    256, FxWorld.tankSize, FxWorld.tankSize);
        }
    }

    @Override
    protected int getMaxToggle() {
        return 3;
    }

    @Override
    protected void updateSprite() {
        setViewPort(explosionTiles[curToggle]);
    }

    @Override
    public void doToggle(Long now) {
        curToggle = ++curToggle % getMaxToggle();
        updateSprite();
        toggleTime = now;
    }
}
