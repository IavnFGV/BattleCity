package ua.drozda.battlecity.fx.sprites;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import ua.drozda.battlecity.core.GameUnit;


/**
 * Created by GFH on 22.07.2015.
 */
public abstract class FxNewSprite<T extends GameUnit> extends ImageView {

    public static Image sprite = new Image(
            FxNewSprite.class
                    .getResource("../images/final_sprites.png")
                    .toExternalForm());

    private final SpriteAnimation<T> animation;
    private final T gameUnit;

    BooleanBinding playBinding;

    public FxNewSprite(T gameUnit) {
        super(sprite);
        this.gameUnit = gameUnit;
        this.animation = createAnimation();
        playBinding = createPlayBinding();
        playBinding.addListener((observable1, oldValue, newValue) -> {
                    if (animation.isEmbedded()) return;
                    if ((oldValue == false) && (newValue == true)) {
                        animation.play();
                    } else {
                        animation.pause();
                    }
                }
        );

    }

    protected abstract SpriteAnimation<T> createAnimation();

    protected BooleanBinding createPlayBinding() {
        return Bindings.createBooleanBinding(() ->
                gameUnit.isPause(), gameUnit.pauseProperty());
    }

    protected abstract Rectangle2D nextSprite(int index);

    protected abstract class SpriteAnimation<T extends GameUnit> extends Transition {

        private final int count;
        private final int rows;
        private int lastIndex;

        public SpriteAnimation(
                Duration duration,
                int count, int rows,
                int cycleCount) {
            this.count = count;
            this.rows = rows;
            setCycleDuration(duration);
            setInterpolator(Interpolator.LINEAR);
            setCycleCount(cycleCount);
        }

        public boolean isEmbedded() {
            return parentProperty().get() != null;// todo check
        }

        protected void interpolate(double k) {
            final int index = Math.min((int) Math.floor(k * count), count - 1);
            if (index != lastIndex) {
                FxNewSprite.this.setViewport(nextSprite(index));
                lastIndex = index;
            }
        }
    }
}