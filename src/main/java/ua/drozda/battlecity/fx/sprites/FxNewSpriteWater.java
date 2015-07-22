package ua.drozda.battlecity.fx.sprites;

import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;
import ua.drozda.battlecity.core.TileUnit;

/**
 * Created by GFH on 23.07.2015.
 */
public class FxNewSpriteWater extends FxNewSprite<TileUnit> {


    public FxNewSpriteWater(TileUnit gameUnit) {
        super(gameUnit);
    }

    @Override
    protected Rectangle2D nextSprite(int index) {
        return null;
    }

    @Override
    protected SpriteAnimation<TileUnit> createAnimation() {
        return new WaterSpriteAnimation(Duration.seconds(1), 3, 1, Animation.INDEFINITE);

    }

    protected class WaterSpriteAnimation extends SpriteAnimation<TileUnit> {
        public WaterSpriteAnimation(Duration duration, int count, int rows, int cycleCount) {
            super(duration, count, rows, cycleCount);
        }
    }

}
