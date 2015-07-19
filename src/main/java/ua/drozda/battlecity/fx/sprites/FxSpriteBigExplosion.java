package ua.drozda.battlecity.fx.sprites;

import javafx.geometry.Rectangle2D;
import ua.drozda.battlecity.core.GameUnit;
import ua.drozda.battlecity.core.TankUnit;
import ua.drozda.battlecity.fx.FxWorld;

/**
 * Created by GFH on 13.07.2015.
 */
public class FxSpriteBigExplosion extends FxSprite<TankUnit> {
    static private Long explosionTimer = GameUnit.getTimeInState().get(GameUnit.BasicState.EXPLODING) / 5;
    private static Rectangle2D[] explosionTiles;

    static {
        explosionTiles = new Rectangle2D[5];
        for (int i = 0; i < 5; i++) {
            explosionTiles[i] = new Rectangle2D(229 + FxWorld.tileSize * 4 * i, 359, FxWorld
                    .tileSize * 4, FxWorld.tileSize * 4);
        }
    }

    public FxSpriteBigExplosion(TankUnit gameUnit) {
        super(gameUnit);
        xProperty().unbind();
        yProperty().unbind();
        xProperty().setValue(gameUnit.getX() - 16);
        yProperty().setValue(gameUnit.getY() - 16);
        gameUnit.xProperty().addListener((observable, oldValue, newValue) -> {
            this.xProperty().setValue(newValue.doubleValue() - 16);
        });
        gameUnit.yProperty().addListener((observable, oldValue, newValue) -> {
            this.yProperty().setValue(newValue.doubleValue() - 16);
        });
        maxToggle = 5;
        setViewPort(explosionTiles[this.curToggle]);
    }

    @Override
    public Boolean canToggle(Long now) {
        return (super.canToggle(now)) && (getGameUnit().getBasicState() == GameUnit.BasicState.EXPLODING) && (now -
                toggleTime >=
                explosionTimer);
    }

    @Override
    protected int getMaxToggle() {
        return maxToggle;
    }

    @Override
    protected void updateSprite() {
        setViewPort(explosionTiles[this.curToggle]);
        if (getGameUnit().getTankType() == TankUnit.TankType.FIRST_PLAYER) {
            System.out.println(getGameUnit().getBasicState() + " curToggle=" + curToggle + "toggletime = " + toggleTime);
        }
    }

    @Override
    public void doToggle(Long now) {
        if (curToggle < getMaxToggle()) {
            updateSprite();
            curToggle++;
        }
        if (curToggle == getMaxToggle()) {
            curToggle = getMaxToggle() - 1;
        }
        toggleTime = now;
    }
}
