package ua.drozda.battlecity.fx.sprites;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import ua.drozda.battlecity.core.GameUnit;
import ua.drozda.battlecity.core.interfaces.Togglable;
import ua.drozda.battlecity.fx.FxWorld;

import java.util.Observable;
import java.util.Observer;

import static ua.drozda.battlecity.core.StaticServices.MESSAGES;

/**
 * MAYBE ITS A ONE BIG MISTAKE  try to use fx animation instead!!!
 */
public abstract class FxSprite<T extends GameUnit> implements Togglable, Observer {
    protected Integer curToggle = 0;
    protected Long toggleTime = 0l;
    protected int maxToggle = 1;
    protected boolean initFlag = false;
    private ImageView imageView = new ImageView(FxWorld.sprites);
    private T gameUnit;
    private BooleanProperty pauseProperty;

    public FxSprite(T gameUnit) {
        if (gameUnit == null) {
            throw new IllegalArgumentException(MESSAGES.getString("fxsprite.constructor.nullgameunit"));
        }
        xProperty().bind(gameUnit.xProperty());
        yProperty().bind(gameUnit.yProperty());
        this.pauseProperty().bind(gameUnit.pauseProperty());
        gameUnit.addObserver(this);
        this.gameUnit = gameUnit;
    }

    public final DoubleProperty xProperty() {
        return imageView.xProperty();
    }

    public final DoubleProperty yProperty() {
        return imageView.yProperty();
    }

    public BooleanProperty pauseProperty() {
        if (pauseProperty == null) {
            pauseProperty = new SimpleBooleanProperty(false);
        }
        return pauseProperty;
    }

    public void initToggle() {
        initFlag = true;
    }

    public void setPause(Boolean pause) {
        pauseProperty().setValue(pause);
    }

    public final BooleanProperty visibleProperty() {
        return imageView.visibleProperty();
    }

    public final boolean isVisible() {
        return getImageView().isVisible();
    }

    public final void setVisible(boolean visible) {
        getImageView().setVisible(visible);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public T getGameUnit() {
        return gameUnit;
    }

    public void setGameUnit(T gameUnit) {
        this.gameUnit = gameUnit;
    }

    public final Rectangle2D getViewPort() {
        return imageView.getViewport();
    }

    public final void setViewPort(Rectangle2D value) {
        imageView.setViewport(value);
    }

    protected abstract int getMaxToggle();

    @Override
    public void update(Observable o, Object arg) {
        updateSprite();
    }

    protected abstract void updateSprite();

    @Override
    public Boolean canToggle(Long now) {
        if (initFlag) {
            toggleTime = now;
            initFlag = false;
        }
        return !isPause();
    }

    public Boolean isPause() {
        return pauseProperty().getValue();
    }

    @Override
    public void doToggle(Long now) {
        updateSprite();
    }
}
