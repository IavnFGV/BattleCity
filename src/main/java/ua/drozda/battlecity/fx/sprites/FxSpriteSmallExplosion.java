package ua.drozda.battlecity.fx.sprites;

import javafx.geometry.Rectangle2D;
import ua.drozda.battlecity.core.BulletUnit;
import ua.drozda.battlecity.core.GameUnit;
import ua.drozda.battlecity.fx.FxWorld;

/**
 * Created by GFH on 19.07.2015.
 */
public class FxSpriteSmallExplosion extends FxSprite<BulletUnit> {
    static private Long explosionTimer = GameUnit.getTimeInState().get(GameUnit.BasicState.EXPLODING) / 3;
    private static Rectangle2D[] explosionTiles;

    static {
        explosionTiles = new Rectangle2D[3];
        for (int i = 0; i < 3; i++) {
            explosionTiles[i] = new Rectangle2D(228 + FxWorld.tankSize * i,
                    256, FxWorld.tankSize, FxWorld.tankSize);
        }
    }

    public FxSpriteSmallExplosion(BulletUnit gameUnit) {
        super(gameUnit);
        xProperty().unbind();
        yProperty().unbind();
        xProperty().setValue(gameUnit.getX() - 12);
        yProperty().setValue(gameUnit.getY() - 12);
        gameUnit.xProperty().addListener((observable, oldValue, newValue) -> {
            this.xProperty().setValue(newValue.doubleValue() - 12);
        });
        gameUnit.yProperty().addListener((observable, oldValue, newValue) -> {
            this.yProperty().setValue(newValue.doubleValue() - 12);
        });

        setViewPort(explosionTiles[this.curToggle]);
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

    @Override
    public Boolean canToggle(Long now) {
        return (super.canToggle(now)) &&
                (getGameUnit().getBasicState() == GameUnit.BasicState.EXPLODING) &&
                (now - toggleTime >= explosionTimer);
    }
}
